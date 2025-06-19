package Controller;

import DAO.CourseDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@WebServlet("/payment")
public class PaymentController extends HttpServlet {
    private CourseDAO courseDAO;
    private static final String PARTNER_CODE = "MOMO_PARTNER_CODE"; // Thay bằng mã đối tác từ MoMo
    private static final String ACCESS_KEY = "MOMO_ACCESS_KEY"; // Thay bằng Access Key
    private static final String SECRET_KEY = "MOMO_SECRET_KEY"; // Thay bằng Secret Key
    private static final String ENDPOINT = "https://test-payment.momo.vn/v2/gateway/api/create";
    private static final String RETURN_URL = "http://localhost:8080/GiaSuTot/payment-return"; // URL trả về
    private static final String NOTIFY_URL = "http://localhost:8080/GiaSuTot/payment-notify"; // URL thông báo

    @Override
    public void init() {
        courseDAO = new CourseDAO();
        System.out.println("PaymentController initialized.");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String courseId = request.getParameter("courseId");
        String studentId = (String) session.getAttribute("studentId");
        if (courseId == null || studentId == null) {
            response.sendRedirect(request.getContextPath() + "/courses?message=Dữ liệu không hợp lệ");
            return;
        }

        // Lấy thông tin khóa học
        try {
            request.setAttribute("courseId", courseId);
            request.setAttribute("studentId", studentId);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/payment.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/courses?message=Lỗi khi tải trang thanh toán");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String courseId = request.getParameter("courseId");
        String studentId = (String) session.getAttribute("studentId");
        if (courseId == null || studentId == null) {
            response.sendRedirect(request.getContextPath() + "/courses?message=Dữ liệu không hợp lệ");
            return;
        }

        try {
            // Tạo đơn hàng MoMo
            Map<String, String> params = createMoMoPaymentRequest(courseId, studentId);
            String paymentUrl = sendPaymentRequest(params);
            if (paymentUrl != null) {
                response.sendRedirect(paymentUrl); // Chuyển hướng đến MoMo
            } else {
                response.sendRedirect(request.getContextPath() + "/courses?message=Lỗi khi tạo yêu cầu thanh toán");
            }
        } catch (Exception e) {
            System.err.println("Error in payment: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/courses?message=Lỗi khi xử lý thanh toán");
        }
    }

    private Map<String, String> createMoMoPaymentRequest(String courseId, String studentId) throws SQLException {
        Map<String, String> params = new HashMap<>();
        params.put("partnerCode", PARTNER_CODE);
        params.put("accessKey", ACCESS_KEY);
        params.put("requestId", UUID.randomUUID().toString());
        params.put("amount", String.valueOf(courseDAO.getCourseById(courseId).getSubject().getFee()));
        params.put("orderId", "ORDER_" + System.currentTimeMillis());
        params.put("orderInfo", "Thanh toan khoa hoc " + courseId);
        params.put("returnUrl", RETURN_URL);
        params.put("notifyUrl", NOTIFY_URL);
        params.put("extraData", "studentId=" + studentId + "&courseId=" + courseId);
        params.put("requestType", "captureMoMoWallet");
        params.put("signature", createSignature(params));

        return params;
    }

    private String createSignature(Map<String, String> params) {
        String data = "partnerCode=" + PARTNER_CODE +
                "&accessKey=" + ACCESS_KEY +
                "&requestId=" + params.get("requestId") +
                "&amount=" + params.get("amount") +
                "&orderId=" + params.get("orderId") +
                "&orderInfo=" + params.get("orderInfo") +
                "&returnUrl=" + params.get("returnUrl") +
                "&notifyUrl=" + params.get("notifyUrl") +
                "&extraData=" + params.get("extraData");
        return hashSHA256(data + SECRET_KEY);
    }

    private String hashSHA256(String data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error hashing signature", e);
        }
    }

    private String sendPaymentRequest(Map<String, String> params) throws IOException {
        URL url = new URL(ENDPOINT);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; utf-8");
        conn.setDoOutput(true);

        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            postData.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        postData.setLength(postData.length() - 1); // Remove last &

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = postData.toString().getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line.trim());
                }
                // Parse JSON response (cần thư viện như Gson)
                // Giả sử response chứa payUrl
                return new com.google.gson.JsonParser().parse(response.toString())
                        .getAsJsonObject().get("payUrl").getAsString();
            }
        } else {
            System.err.println("MoMo API Error: " + responseCode);
            return null;
        }
    }
}