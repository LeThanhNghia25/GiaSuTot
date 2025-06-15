package Controller;

import DAO.AccountDAO;
import DAO.StudentDAO;
import DAO.TutorDAO;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Account;
import model.Student;
import model.Tutor;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.stream.Collectors;

@WebServlet("/oauth2callback")
public class GoogleCallbackServlet extends HttpServlet {
    // a = 89847902492-afni2s5f5hb60nuqbd8h6eisrp0ipk35.apps.googleusercontent.com (Lúc làm thế a vào nhé)
    private static final String CLIENT_ID = "a";
    // b = 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
    private static final String CLIENT_SECRET = "b";
    // http://localhost:8080/GiaSuTot_war/oauth2callback ( Thay bằng đường dẫn của mọi người, nếu khác 2 đường dẫn này thì nhắn lại để H cấp quyền)
    private static final String REDIRECT_URI = "http://localhost:8080/GiaSuTot_war_exploded/oauth2callback";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        if (code == null || code.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing authorization code");
            return;
        }

        String accessToken = getAccessToken(code);
        if (accessToken == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Failed to get access token");
            return;
        }

        JsonObject userInfo = getUserInfo(accessToken);
        if (userInfo == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Failed to get user info");
            return;
        }

        String email = userInfo.has("email") ? userInfo.get("email").getAsString() : null;
        String name = userInfo.has("name") ? userInfo.get("name").getAsString() : null;

        if ((name == null || name.isEmpty()) && userInfo.has("given_name") && userInfo.has("family_name")) {
            name = userInfo.get("given_name").getAsString() + " " + userInfo.get("family_name").getAsString();
        }

        System.out.println("Google email: " + email);
        System.out.println("Google name: " + name);

        if (email == null || name == null) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Missing user info from Google");
            return;
        }

        AccountDAO dao = new AccountDAO();
        StudentDAO stDao = new StudentDAO();
        TutorDAO tutorDAO = new TutorDAO();

        try {
            Account existingAccount = dao.getAccountByEmail(email);
            if (existingAccount != null) {
                setUserSession(request.getSession(), existingAccount);
                response.sendRedirect(request.getContextPath() + "/index.jsp");
                return;
            }

            // Tạo mới account + student
            Account account = new Account();
            Student student = new Student();

            account.setId(dao.generateAccountId());
            account.setEmail(email);
            account.setPassword("0"); // không dùng mật khẩu
            account.setRole(1); // mặc định học sinh
            account.setStatus("inactive");

            student.setId(stDao.generateStudentId());
            student.setName(name);
            student.setAccount(account);

            if (!dao.insertgg(account)) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to save account");
                return;
            }

            if (!stDao.insertggSt(student)) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to save student");
                return;
            }

            // Lấy lại account từ DB để chắc chắn đúng ID
            Account savedAccount = dao.getAccountByEmail(email);
            setUserSession(request.getSession(), savedAccount);
            response.sendRedirect(request.getContextPath() + "/index.jsp");

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error: " + e.getMessage());
        }
    }

    private String getAccessToken(String code) throws IOException {
        URL url = new URL("https://oauth2.googleapis.com/token");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        String params = "code=" + code
                + "&client_id=" + CLIENT_ID
                + "&client_secret=" + CLIENT_SECRET
                + "&redirect_uri=" + REDIRECT_URI
                + "&grant_type=authorization_code";

        try (OutputStream os = conn.getOutputStream()) {
            os.write(params.getBytes(StandardCharsets.UTF_8));
            os.flush();
        }

        int responseCode = conn.getResponseCode();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                responseCode == HttpURLConnection.HTTP_OK ? conn.getInputStream() : conn.getErrorStream(),
                StandardCharsets.UTF_8))) {
            String response = reader.lines().collect(Collectors.joining());
            System.out.println("Token response: " + response);

            JsonObject json = JsonParser.parseString(response).getAsJsonObject();
            return json.has("access_token") ? json.get("access_token").getAsString() : null;
        }
    }

    private JsonObject getUserInfo(String accessToken) throws IOException {
        URL urlUserInfo = new URL("https://www.googleapis.com/oauth2/v2/userinfo?access_token=" + accessToken);
        HttpURLConnection connUser = (HttpURLConnection) urlUserInfo.openConnection();
        connUser.setRequestMethod("GET");

        int responseCode = connUser.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            System.err.println("Failed to get user info. Response code: " + responseCode);
            return null;
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connUser.getInputStream(), StandardCharsets.UTF_8))) {
            String userInfo = reader.lines().collect(Collectors.joining());
            return JsonParser.parseString(userInfo).getAsJsonObject();
        }
    }

    private void setUserSession(HttpSession session, Account account) throws SQLException {
        session.setAttribute("account", account);

        StudentDAO studentDAO = new StudentDAO();
        TutorDAO tutorDAO = new TutorDAO();

        if (account.getRole() == 1) {
            Student student = studentDAO.getStudentByAccountId(account.getId());
            if (student != null) {
                session.setAttribute("userName", student.getName());
                session.setAttribute("role", "student");
            }
        } else if (account.getRole() == 2) {
            Tutor tutor = tutorDAO.getTutorByAccountId(account.getId());
            if (tutor != null) {
                session.setAttribute("userName", tutor.getName());
                session.setAttribute("role", "tutor");
            }
        }
    }
}
