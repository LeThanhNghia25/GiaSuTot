package Controller;

import DAO.CourseDAO;
import DAO.PaymentDAO;
import Utils.EmailSender;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.util.regex.Pattern;

import model.Course;

@WebServlet("/submit-receipt")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB
public class SubmitReceiptController extends HttpServlet {
    private static final String UPLOAD_DIR = "D:/uploads"; // Thư mục cố định bên ngoài
    private CourseDAO courseDAO;
    private PaymentDAO paymentDAO;

    @Override
    public void init() {
        courseDAO = new CourseDAO();
        paymentDAO = new PaymentDAO();
        System.out.println("SubmitReceiptController initialized.");
    }

    private String normalizeFileName(String fileName) {
        String normalized = Normalizer.normalize(fileName, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalized).replaceAll("")
                .replaceAll("[^a-zA-Z0-9.-]", "_")
                .replaceAll(" ", "_");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String courseId = request.getParameter("courseId");
        String studentId = request.getParameter("studentId");
        Part filePart = request.getPart("receipt");
        String originalFileName = filePart != null ? Paths.get(filePart.getSubmittedFileName()).getFileName().toString() : "unknown";
        String fileName = normalizeFileName(originalFileName);

        if (filePart == null || filePart.getSize() == 0) {
            response.sendRedirect(request.getContextPath() + "/payment-info?courseId=" + courseId + "&studentId=" + studentId + "&message=" + URLEncoder.encode("Lỗi: Vui lòng chọn tệp biên lai hợp lệ", StandardCharsets.UTF_8));
            return;
        }

        String uploadPath = UPLOAD_DIR;
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            if (!uploadDir.mkdir()) {
                response.sendRedirect(request.getContextPath() + "/payment-info?courseId=" + courseId + "&studentId=" + studentId + "&message=" + URLEncoder.encode("Lỗi: Không thể tạo thư mục uploads", StandardCharsets.UTF_8));
                return;
            }
        }

        Path filePath = Paths.get(uploadPath + File.separator + fileName);
        try {
            filePart.write(filePath.toString());
            System.out.println("File saved successfully to: " + filePath.toString());
        } catch (IOException e) {
            System.err.println("Error saving file: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/payment-info?courseId=" + courseId + "&studentId=" + studentId + "&message=" + URLEncoder.encode("Lỗi: Không thể lưu tệp biên lai", StandardCharsets.UTF_8));
            return;
        }

        Course course = null;
        try {
            course = courseDAO.getCourseById(courseId);
            if (course == null) {
                throw new Exception("Khóa học không tồn tại");
            }
            String tutorId = course.getTutorId();
            double amount = course.getSubject().getFee();

            paymentDAO.insertPayment(courseId, tutorId, studentId, amount, fileName, "/uploads/" + fileName); // Đường dẫn tương đối
        } catch (Exception e) {
            System.err.println("Error fetching course or saving payment: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/payment-info?courseId=" + courseId + "&studentId=" + studentId + "&message=" + URLEncoder.encode("Lỗi: " + e.getMessage(), StandardCharsets.UTF_8));
            return;
        }

        String to = "lethanhnghia0938@gmail.com";
        String errorMessage = "";
        try {
            EmailSender.sendReceiptEmail(to, studentId, courseId, fileName, filePath.toString());
            System.out.println("Email sent successfully to " + to);
        } catch (Exception e) {
            System.err.println("Failed to send email: " + e.getMessage());
            errorMessage = " (Gửi email thất bại, vui lòng liên hệ admin.)";
        }

        String successMessage = "Biên lai cho khóa học " + courseId + " từ học sinh " + studentId + " đã được gửi. Vui lòng chờ admin xác nhận!" + errorMessage;
        System.out.println(successMessage);
        response.sendRedirect(request.getContextPath() + "/courses?message=" + URLEncoder.encode(successMessage, StandardCharsets.UTF_8));
    }
}