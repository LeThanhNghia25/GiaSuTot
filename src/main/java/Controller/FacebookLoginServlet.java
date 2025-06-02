package Controller;


import com.github.scribejava.apis.FacebookApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth20Service;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/facebook-login")
public class FacebookLoginServlet extends HttpServlet {

    // TODO: Bạn nên đưa clientId và secret vào file cấu hình riêng, không nên hard-code
    private static final String clientId = "697861399606584";
    private static final String clientSecret = "4adef5eff585472436ff0d2c3909cc3c";

    private static final String redirectUri = "http://localhost:8080/GiaSuTot_war_exploded/facebook-callback";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Tạo đối tượng OAuth 2.0 service cho Facebook
        OAuth20Service service = new ServiceBuilder(clientId)
                .apiSecret(clientSecret)
                .callback(redirectUri)
                .defaultScope("email") // yêu cầu quyền truy cập email
                .build(FacebookApi.instance());

        // Lấy URL cho phép người dùng đăng nhập Facebook
        String authorizationUrl = service.getAuthorizationUrl();

        // Điều hướng người dùng tới trang đăng nhập của Facebook
        response.sendRedirect(authorizationUrl);
    }
}
