
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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset-password.css">

</head>
<body>
<%@ include file="header.jsp" %>

<div class="container">
    <h2>Khôi phục mật khẩu</h2>

    <form action="forgot_password" method="post">
        <label for="email">Nhập email của bạn:</label>
        <input type="email" id="email" name="email" required>
        <button type="submit">Gửi mã xác thực</button>
    </form>

    <c:if test="${not empty verificationRequested}">
        <form action="verify-email" method="post">
            <p>Nhập mã xác thực 4 số đã gửi đến email:</p>
            <div class="verification-inputs">
                <input type="text" name="code1" maxlength="1" required>
                <input type="text" name="code2" maxlength="1" required>
                <input type="text" name="code3" maxlength="1" required>
                <input type="text" name="code4" maxlength="1" required>
            </div>
            <button type="submit">Xác nhận</button>
        </form>
    </c:if>
</div>

<%@ include file="footer.jsp" %>
<script src="${pageContext.request.contextPath}/js/login-signup.js"></script>
<script src="${pageContext.request.contextPath}/js/reset-password.js"></script>
</body>

</html>