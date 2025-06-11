package Controller;

import DAO.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.gson.Gson;

@WebServlet(name = "lessonController2", urlPatterns = {"/lesson2"})
public class LesonController2  extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Received POST request for /lesson"); // Debug
        String CourseId = request.getParameter("course_id");
        Map<String, Object> responseData = new HashMap<>();

        System.out.println("Received course id in doPost: " + CourseId);

        if (CourseId != null && !CourseId.isEmpty()) {

            CourseDAO courseDAO = new CourseDAO();
            try {
                System.out.println("Fetching student with ID: " + CourseId);
               Course  course = courseDAO.getCourseById(CourseId);
                if (course != null) {
                    System.out.println("course found: " + course.getId());
                    System.out.println("Fetching registered subjects...");
                    SearchDAO searchDAO = new SearchDAO();
                    Subject subject = searchDAO.FindByIdCourse(CourseId);
                    if (subject != null) {

                            responseData.put("status", "success");

                            responseData.put("subject", subject.getName());
//                            responseData.put("courseId", course.getId());
                            // responseData.put("course_id", mapSubjectToDropdownValue(course.getId()));

                    } else {
                        responseData.put("status", "error");
                        responseData.put("message", "Không tìm thấy khóa học : " + CourseId);
                        System.out.println("No registered subject found for studentId: " + CourseId);
                    }
                } else {
                    responseData.put("status", "error");
                    responseData.put("message", "Không tìm  " + CourseId);
                    System.out.println("Student not found for studentId: " + CourseId);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                responseData.put("status", "error");
                responseData.put("message", "Lỗi khi truy vấn cơ sở dữ liệu: " + e.getMessage());
                System.out.println("SQLException in doPost: " + e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                responseData.put("status", "error");
                responseData.put("message", "Lỗi bất ngờ: " + e.getMessage());
                System.out.println("Unexpected error in doPost: " + e.getMessage());
            }
        } else {
            responseData.put("status", "error");
            responseData.put("message", "Mã khóa học không hợp lệ");
            System.out.println("Invalid studentId received: " + CourseId);
        }

        // Trả về JSON
        String jsonResponse = new Gson().toJson(responseData);
        System.out.println("Returning response: " + jsonResponse);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(jsonResponse);
        out.flush();
    }
}
