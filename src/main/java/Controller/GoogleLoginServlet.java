package Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/login-google")
public class GoogleLoginServlet extends HttpServlet {
    // a = 89847902492-afni2s5f5hb60nuqbd8h6eisrp0ipk35.apps.googleusercontent.com
    private static final String CLIENT_ID = "89847902492-afni2s5f5hb60nuqbd8h6eisrp0ipk35.apps.googleusercontent.com";
    private static final String REDIRECT_URI = "http://localhost:8080/GiaSuTot_war_exploded/oauth2callback";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String oauthUrl = "https://accounts.google.com/o/oauth2/v2/auth?"
                + "scope=openid%20email%20profile"
                + "&access_type=offline"
                + "&include_granted_scopes=true"
                + "&response_type=code"
                + "&redirect_uri=" + REDIRECT_URI
                + "&client_id=" + CLIENT_ID
                + "&prompt=consent";

        response.sendRedirect(oauthUrl);
    }
}
