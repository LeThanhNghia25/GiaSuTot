package Controller;

import DAO.AccountDAO;
import DAO.StudentDAO;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Account;
import model.Student;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.stream.Collectors;

@WebServlet("/oauth2callback")
public class GoogleCallbackServlet extends HttpServlet {

    private static final String CLIENT_ID = "89847902492-afni2s5f5hb60nuqbd8h6eisrp0ipk35.apps.googleusercontent.com";
    private static final String CLIENT_SECRET = "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";
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

        String googleId = userInfo.has("id") ? userInfo.get("id").getAsString() : null;
        String email = userInfo.has("email") ? userInfo.get("email").getAsString() : null;
        String name = userInfo.has("name") ? userInfo.get("name").getAsString() : null;

        if (googleId == null || email == null || name == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing user info");
            return;
        }

        AccountDAO dao;
        StudentDAO stDao;
        try {
            dao = new AccountDAO();
            stDao = new StudentDAO();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Account account;
        try {
            account = dao.findByGoogleId(googleId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (account == null) {
            // Tạo account mới
            account = new Account();
            account.setGoogle_id(googleId);
            account.setEmail(email);

            // Tạo student mới
            Student student = new Student();
            student.setName(name);
            // Nếu có ngày sinh thì có thể lấy từ nơi khác hoặc để null
            // student.setBirth(...);

            try {
                boolean insertStudent = stDao.insertggSt(student);
                if (!insertStudent) {
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to save student");
                    return;
                }

                // Gán studentId cho account nếu có FK
                account.getId(); // giả sử có getId()

                boolean insertAccount = dao.insertgg(account);
                if (!insertAccount) {
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to save account");
                    return;
                }

                account = dao.findByGoogleId(googleId);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        HttpSession session = request.getSession();
        session.setAttribute("account", account);
        response.sendRedirect("index.jsp");
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

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        responseCode == HttpURLConnection.HTTP_OK ? conn.getInputStream() : conn.getErrorStream(),
                        StandardCharsets.UTF_8))) {
            String response = reader.lines().collect(Collectors.joining());
            System.out.println("Token response: " + response);

            JsonObject json = JsonParser.parseString(response).getAsJsonObject();
            if (json.has("access_token")) {
                return json.get("access_token").getAsString();
            } else {
                System.err.println("No access_token found in response");
                return null;
            }
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
}
