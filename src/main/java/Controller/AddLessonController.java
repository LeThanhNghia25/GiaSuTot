package Controller;

import DAO.AddLessonDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet(name = "addLessonController", urlPatterns = {"/addLesson"})
public class AddLessonController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStudent = request.getParameter("idStudent");
        String idCourse = request.getParameter("course_id");
        String dateStr = request.getParameter("date"); // Ví dụ: "2025-06-12"
        String timeStr = request.getParameter("time"); // Ví dụ: "14:30"

        // Đảm bảo datetimeStr có định dạng đúng (yyyy-MM-dd HH:mm:ss)
        String datetimeStr = dateStr + " " + timeStr + ":00"; // Thêm :00 cho giây
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setLenient(false); // Không cho phép định dạng không hợp lệ

        try {
            Date lessonDateTime = sdf.parse(datetimeStr); // Parse để kiểm tra định dạng
            AddLessonDao addLessonDao = new AddLessonDao();
            addLessonDao.insertLession(idCourse, idStudent, datetimeStr);
            // Trả về phản hồi thành công dưới dạng JSON
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"status\": \"success\", \"message\": \"Buổi học đã được tạo thành công!\"}");
        } catch (SQLException e) {
            // Xử lý lỗi SQL
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"status\": \"error\", \"message\": \"Lưu buổi học thất bại: " + e.getMessage() + "\"}");
            e.printStackTrace(); // Ghi log lỗi để debug
        } catch (Exception e) {
            // Xử lý lỗi định dạng hoặc khác
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"status\": \"error\", \"message\": \"Định dạng ngày giờ không hợp lệ: " + e.getMessage() + "\"}");
            e.printStackTrace();
        }
    }
}