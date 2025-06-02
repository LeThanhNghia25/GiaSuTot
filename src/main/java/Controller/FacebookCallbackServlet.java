package Controller;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@WebServlet("/facebook-callback")
public class FacebookCallbackServlet extends HttpServlet {

    private static final String APP_ID = "697861399606584";
    private static final String APP_SECRET = "4adef5eff585472436ff0d2c3909cc3c";
    private static final String REDIRECT_URI = "http://localhost:8080/GiaSuTot_war_exploded/facebook-callback";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String code = request.getParameter("code");

        if (code == null || code.isEmpty()) {
            response.sendRedirect("login.jsp?error=facebook");
            return;
        }

        // Bước 1: Lấy access token từ Facebook
        String tokenUrl = "https://graph.facebook.com/v16.0/oauth/access_token"
                + "?client_id=" + APP_ID
                + "&redirect_uri=" + REDIRECT_URI
                + "&client_secret=" + APP_SECRET
                + "&code=" + code;

        String accessToken = null;
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(tokenUrl).openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder responseBuilder = new StringBuilder();
            String input;
            while ((input = in.readLine()) != null) {
                responseBuilder.append(input);
            }
            in.close();

            JSONObject json = new JSONObject(responseBuilder.toString());
            accessToken = json.getString("access_token");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("login.jsp?error=token");
            return;
        }

        // Bước 2: Lấy thông tin người dùng từ Facebook
        String userInfoUrl = "https://graph.facebook.com/me?fields=id,name,email&access_token=" + accessToken;
        try {
            HttpURLConnection userConn = (HttpURLConnection) new URL(userInfoUrl).openConnection();
            userConn.setRequestMethod("GET");

            BufferedReader userReader = new BufferedReader(new InputStreamReader(userConn.getInputStream()));
            StringBuilder userResponseBuilder = new StringBuilder();
            String line;
            while ((line = userReader.readLine()) != null) {
                userResponseBuilder.append(line);
            }
            userReader.close();

            JSONObject userJson = new JSONObject(userResponseBuilder.toString());
            String facebookId = userJson.optString("id");
            String name = userJson.optString("name");
            String email = userJson.optString("email");

            // Lưu thông tin vào session hoặc xử lý đăng nhập người dùng
            HttpSession session = request.getSession();
            session.setAttribute("facebookId", facebookId);
            session.setAttribute("name", name);
            session.setAttribute("email", email);
            session.setAttribute("loginType", "facebook");

            // Redirect tới trang chính hoặc dashboard
            response.sendRedirect("index.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("login.jsp?error=userinfo");
        }
    }
}
