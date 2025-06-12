package Controller;

import DAO.AddLessonDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

import static java.lang.System.out;

@WebServlet(name = "addLessonController", urlPatterns = {"/addLesson"})
public class AddLessonController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStudent = request.getParameter("idStudent");
        String idCourse = request.getParameter("course_id");
        String dateStr = request.getParameter("date");
        String timeStr = request.getParameter("time");

        String datetimeStr = dateStr + " " + timeStr+":00";
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date lessonDateTime;
    ;
        try {
            //lessonDateTime = sdf.parse(datetimeStr);
            AddLessonDao addLessonDao = new AddLessonDao();
            addLessonDao.insertLession(idCourse, idStudent, datetimeStr);
            response.sendRedirect("schedule");
        } catch (SQLException e) {
            out.write("{\"status\": \"error\", \"message\": \"Lưu buổi học thất bại!\"}".getBytes());throw new RuntimeException(e);

    }
}
}
