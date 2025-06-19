<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<<<<<<< HEAD
<head>
    <meta charset="utf-8">
    <title>eLEARNING - eLearning HTML Template</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <meta content="" name="keywords">
    <meta content="" name="description">
    <!-- Favicon -->
    <link href="img/favicon.ico" rel="icon">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

    <!-- Customized Bootstrap Stylesheet -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Template Stylesheet -->
    <link href="css/style.css" rel="stylesheet">
    <link href="css/login-signup.css" rel="stylesheet">
    <link href="css/modal.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>


</head>
<body>
<%@ include file="header.jsp" %>
<!-- Content-->
<div class="content">
    <div class="container" id="container">
        <div class="form-container sign-up">
            <form id="register-form" action="${pageContext.request.contextPath}/signup-user" method="post">
                <input type="hidden" name="action" value="register">
                <h1>Tạo tài khoản</h1>
                <div class="social-icons">
                    <a href="#" class="icon-logo"><i class="fab fa-google g"></i></a>
                    <a href="#" class="icon-logo"><i class="fab fa-facebook-f f"></i></a>
                </div>
                <span>Hoặc sử dụng email để đăng kí tài khoản</span>

                <!-- Nhập Email -->
                <input type="text" name="email" id="register-email" placeholder="Email" value="${email}" >
                <div id="email-error" style="color: red; font-size: 10px;"></div>

                <input type="text" name="name" id="register-username" placeholder="Tên đăng nhập" >
                <div id="username-error" style="color: red; font-size: 10px;"></div>

                <input type="date" name="birth" id="register-birth" >
                <div id="birth-error" style="color: red; font-size: 10px;"></div>

                <input type="password" name="password" id="register-password" placeholder="Mật khẩu" >
                <div id="password-error" style="color: red; font-size: 10px;"></div>

                <div id="error-server" style="color: red; font-size: 10px;">
                    <c:if test="${not empty error_register}">
                        ${error_register}
                    </c:if>
                </div>
                <!-- Nút đăng ký -->
                <button type="submit" id="submit-register">Đăng kí</button>
            </form>
        </div>

        <div class="form-container">
            <form id="login-form" action="${pageContext.request.contextPath}/signup-user" method="post">
                <input type="hidden" name="action" value="login" />
                <h1>Đăng nhập</h1>
                <div class="social-icons">
                    <!-- Đăng nhập bằng Google -->
                    <a href="${pageContext.request.contextPath}/login-google" class="icon-logo"><i class="fab fa-google g"></i>
                    </a>
                    <!-- Đăng nhập bằng Facebook -->
                    <a href="${pageContext.request.contextPath}/facebook-login" class="icon-logo"><i class="fab fa-facebook-f f"></i>
                    </a>
                </div>
                <span>Hoặc sử dụng tài khoản đã đăng kí</span>
                <input type="text" name="email" id="login-email" placeholder="Email" required>
                <span id="email-errorlog" style="color: red; font-size: 10px;" class="error-message"></span> <!-- Thông báo lỗi email -->
                <input type="password" name="password" id="login-password" placeholder="Password" required>
                <span id="password-errorlog" style="color: red; font-size: 10px;" class="error-message"></span> <!-- Thông báo lỗi mật khẩu -->
                <div id="error-login" style="color: red; font-size: 10px;">
                    <c:if test="${not empty error_login}">
                        ${error_login}
                    </c:if>
                </div>

                <a class="blue" href="turn-page?action=resetPass">Quên mật khẩu?</a>
                <button type="submit" id="login-button">Đăng nhập</button>
            </form>
        </div>
        <div class="toggle-container none">
            <div class="toggle">
                <div class="toggle-panel toggle-left">
                    <h1>Chào mừng, trở lại!</h1>
                    <p>Nhập thông tin cá nhân của bạn để sử dụng tất cả các tính năng của trang web</p>
                    <button id="login">Đăng nhập</button>
                    <button id="homes"><a href="index.jsp" style="text-decoration: none ;color: #FFFFFF">Trang chủ</a></button>
                </div>
                <div class="toggle-panel toggle-right">
                    <h1>Chào bạn!</h1>
                    <p>Đăng ký với thông tin cá nhân của bạn để sử dụng tất cả các tính năng của trang web</p>
                    <button id="register">Đăng kí</button>
                    <button id="HOME"><a href="index.jsp" style="text-decoration: none ;color: #FFFFFF" >Trang chủ</a></button>
                </div>
            </div>
        </div>

        <%--<!-- Form xác thực email -->
        <div class="overlay" id="overlay"></div>
        <c:if test="${not empty verificationRequested}">
            <!-- Modal xác thực email -->
            <div id="verification-modal" class="modal">
                <div class="modal-content">
                    <button title="close" type="button" class="close-button" id="close-button">
                        <i class="fas fa-times"></i> <!-- Font Awesome icon for "X" -->
                    </button>

                    <h1>Xác thực email</h1>

                    <!-- Form xác thực email -->
                    <form id="verification-form" action="verify-email" method="post">
                        <div class="verification-code-inputs " style="display: flex; gap: 10px;">
                            <input class="verification-code" type="text" name="code1" maxlength="1">
                            <input class="verification-code" type="text" name="code2" maxlength="1">
                            <input class="verification-code" type="text" name="code3" maxlength="1">
                            <input class="verification-code" type="text" name="code4" maxlength="1">

                        </div>
                        <!-- Thông báo lỗi -->
                        <div id="error-message" class="error-message">
                            <c:if test="${not empty error_code}">
                                ${error_code}
                            </c:if>
                        </div>
                        <button type="submit" id="verify-button">Xác thực</button>
                    </form>

                    <!-- Form gửi lại mã -->
                    <form id="reset-password-form" action="reset-code" method="post">
                        <button type="submit" class="reset">Gửi lại</button>
                    </form>
                </div>
            </div>
        </c:if>--%>
    </div>

</div>

<%@ include file="footer.jsp" %>
<script>
    const container = document.querySelector('.container');
    const registerBtn = document.getElementById('register');
    const loginBtn = document.getElementById('login');

    registerBtn.addEventListener('click', () => {
        container.classList.add('active');
    });

    loginBtn.addEventListener('click', () => {
        container.classList.remove('active');
    });
</script>

<script>
    const registeredSuccess = ${registeredSuccess ? 'true' : 'false'};
    if (registeredSuccess) {
        container.classList.remove('active'); // chuyển sang form đăng nhập
    }
</script>
<script src="${pageContext.request.contextPath}/js/login-signup.js"></script>
<script src="${pageContext.request.contextPath}/js/main.js"></script>

</body>

</html>
