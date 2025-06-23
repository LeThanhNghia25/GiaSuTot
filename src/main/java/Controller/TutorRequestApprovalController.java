package Controller;

import DAO.TutorRequestDAO;
import DAO.AccountDAO;
import Utils.DBConnection;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import Utils.EmailSender;

import java.io.IOException;
import java.sql.*;

@WebServlet(name = "TutorRequestApprovalController", urlPatterns = {"/admin/tutor-request-action"})
public class TutorRequestApprovalController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action"); // approve / reject
        int requestId = Integer.parseInt(request.getParameter("requestId"));

        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM tutor_requests WHERE id = ?");
            ps.setInt(1, requestId);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                response.sendRedirect("tutor-requests?error=Không tìm thấy yêu cầu");
                return;
            }

            String accountId = rs.getString("account_id");

            if ("approve".equals(action)) {
                // Thêm vào bảng tutor
                PreparedStatement insert = conn.prepareStatement("""
                    INSERT INTO tutor (id, name, email, birth, phone, address, specialization, description,
                                       id_card_number, bank_account_number, bank_name, account_id, evaluate)
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 5)
                """);
                insert.setString(1, "tutor_" + accountId); // hoặc UUID.randomUUID().toString()
                insert.setString(2, rs.getString("name"));
                insert.setString(3, rs.getString("email"));
                insert.setDate(4, Date.valueOf(rs.getString("birth")));
                insert.setString(5, rs.getString("phone"));
                insert.setString(6, rs.getString("address"));
                insert.setString(7, rs.getString("specialization"));
                insert.setString(8, rs.getString("description"));
                insert.setLong(9, rs.getLong("id_card_number"));
                insert.setLong(10, rs.getLong("bank_account_number"));
                insert.setString(11, rs.getString("bank_name"));
                insert.setString(12, accountId);
                insert.executeUpdate();

                // Cập nhật role
                PreparedStatement updateRole = conn.prepareStatement("UPDATE account SET role = 2 WHERE id = ?");
                updateRole.setString(1, accountId);
                updateRole.executeUpdate();

                String email = rs.getString("email");
                String name = rs.getString("name");
                EmailSender.sendTutorApprovedEmail(email, name);

            }

            // Xóa khỏi bảng yêu cầu
            PreparedStatement delete = conn.prepareStatement("DELETE FROM tutor_requests WHERE id = ?");
            delete.setInt(1, requestId);
            delete.executeUpdate();

            response.sendRedirect("tutor-requests?success=Thao tác thành công");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("tutor-requests?error=Lỗi xử lý");
        }
    }
}
