
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <title>eLEARNING - eLearning HTML Template</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <meta content="" name="keywords">
    <meta content="" name="description">

    <!-- Favicon -->
    <link href="img/favicon.ico" rel="icon">

    <!-- Google Web Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Heebo:wght@400;500;600&family=Nunito:wght@600;700;800&display=swap" rel="stylesheet">

    <!-- Icon Font Stylesheet -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">

    <!-- Libraries Stylesheet -->
    <link href="libs/animate/animate.min.css" rel="stylesheet">
    <link href="libs/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">

    <!-- Customized Bootstrap Stylesheet -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Template Stylesheet -->
    <%--<link href="css/style.css" rel="stylesheet">--%>
    <link href="css/login-signup.css" rel="stylesheet">
    <link href="css/modal.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" />
    link rel="stylesheet" href="<c:url value="/css/reset-password.css"/>">

</head>
<body>
<%@ include file="header.jsp" %>

<!-- Content-->
<div class="content">
    <div class="container" id="container">
        <!-- Form lấy lại mật khẩu -->
        <c:if test="${empty verificationRequested}">
            <div class="form-container reset-password">
                <form id="reset-password-form" action="reset-password" method="post">
                    <h1>Lấy lại mật khẩu</h1>
                    <input type="hidden" name="action" value="request" />
                    <span>Nhập địa chỉ email của bạn để nhận liên kết đặt lại mật khẩu.</span>
                    <input type="email" name="email" id="reset-email" placeholder="Email">
                    <div id="email-error" style="color: red;">
                        <c:if test="${not empty error_email}">
                            ${error_email}
                        </c:if>
                    </div>
                    <button type="submit">Gửi liên kết đặt lại mật khẩu</button>
                    <p><a href="login?action=login" id="back-to-login">Quay lại đăng nhập</a></p>
                </form>
            </div>
        </c:if>

        <!-- Form đặt lại mật khẩu mới (ẩn khi không có token hợp lệ) -->
        <c:if test="${not empty verificationRequested}">
            <div class="form-container set-new-password">
                <form id="new-password-form" action="reset-password" method="post">
                    <h1>Đặt lại mật khẩu mới</h1>
                    <input type="hidden" name="action" value="reset" />
                    <input type="hidden" name="token" value="${token}" /> <!-- Đưa token vào trong form -->
                    <input type="password" name="password" id="new-password" placeholder="Mật khẩu mới" required>
                    <input type="password" id="confirm-password" placeholder="Xác nhận mật khẩu" required>
                    <div id="password-error" style="color: red;">
                        <c:if test="${not empty error_token}">
                            ${error_token}
                        </c:if>
                    </div>
                    <button type="submit">Cập nhật mật khẩu</button>
                </form>
            </div>
        </c:if>
    </div>
</div>

<%@ include file="footer.jsp" %>
<script src="${pageContext.request.contextPath}/js/login-signup.js"></script>
<script src="${pageContext.request.contextPath}/js/reset-password.js"></script>
<script>

</script>
</body>


</html>