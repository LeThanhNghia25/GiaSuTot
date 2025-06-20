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

import model.Course;

@WebServlet("/submit-receipt")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB
public class SubmitReceiptController extends HttpServlet {
    private static final String UPLOAD_DIR = "uploads";
    private CourseDAO courseDAO;
    private PaymentDAO paymentDAO;

    @Override
    public void init() {
        courseDAO = new CourseDAO();
        paymentDAO = new PaymentDAO();
        System.out.println("SubmitReceiptController initialized.");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String courseId = request.getParameter("courseId");
        String studentId = request.getParameter("studentId");
        Part filePart = request.getPart("receipt");
        String fileName = filePart != null ? Paths.get(filePart.getSubmittedFileName()).getFileName().toString() : "unknown";

        if (filePart == null || filePart.getSize() == 0) {
            response.sendRedirect(request.getContextPath() + "/payment-info?courseId=" + courseId + "&studentId=" + studentId + "&message=" + URLEncoder.encode("Lỗi: Vui lòng chọn tệp biên lai hợp lệ", StandardCharsets.UTF_8));
            return;
        }

        String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIR;
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
        } catch (IOException e) {
            System.err.println("Error saving file: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/payment-info?courseId=" + courseId + "&studentId=" + studentId + "&message=" + URLEncoder.encode("Lỗi: Không thể lưu tệp biên lai", StandardCharsets.UTF_8));
            return;
        }

        // Lấy thông tin tutor và amount từ course
        Course course = null;
        try {
            course = courseDAO.getCourseById(courseId);
            if (course == null) {
                throw new Exception("Khóa học không tồn tại");
            }
            String tutorId = course.getTutorId();
            double amount = course.getSubject().getFee();

            // Lưu vào bảng payment
            paymentDAO.insertPayment(courseId, tutorId, studentId, amount);
        } catch (Exception e) {
            System.err.println("Error fetching course or saving payment: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/payment-info?courseId=" + courseId + "&studentId=" + studentId + "&message=" + URLEncoder.encode("Lỗi: " + e.getMessage(), StandardCharsets.UTF_8));
            return;
        }

        // Gửi email biên lai
        String to = "lethanhnghia0938@gmail.com";
        String errorMessage = ""; // Khai báo biến để lưu thông báo lỗi
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