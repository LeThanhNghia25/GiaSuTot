<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="utf-8">
    <title>Đặt lại mật khẩu </title>
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
    <link href="css/login-signup.css" rel="stylesheet">
    <link href="css/modal.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
    <link rel="stylesheet" href="<c:url value='/css/forgot_password.css'/>">

    <style>
        .container-reset {
            max-width: 500px;
            margin: 50px auto;
            padding: 20px;
            background: #f8f9fa;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            text-align: center;
        }
        .container-reset h2 {
            margin-bottom: 20px;
            font-family: 'Nunito', sans-serif;
            font-weight: 700;
        }
        .container-reset form {
            display: flex;
            flex-direction: column;
            gap: 15px;
        }
        .container-reset input {
            padding: 10px;
            border: 1px solid #ced4da;
            border-radius: 4px;
            font-size: 16px;
        }
        .container-reset button {
            padding: 10px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }
        .container-reset button:hover {
            background-color: #0056b3;
        }
        .error-message {
            color: red;
            font-size: 14px;
            margin-top: 10px;
        }
    </style>
</head>
<body>
<%@ include file="header.jsp" %>

<div class="container-reset">
    <h2>Đặt lại mật khẩu</h2>
    <form action="reset-password" method="post">
        <label for="password">Mật khẩu mới:</label>
        <input type="password" name="password" id="password" required placeholder="Nhập mật khẩu mới">
        <label for="confirm-password">Xác nhận mật khẩu:</label>
        <input type="password" name="confirm-password" id="confirm-password" required placeholder="Xác nhận mật khẩu">
        <c:if test="${not empty error}">
            <div class="error-message">${error}</div>
        </c:if>
        <button type="submit">Đặt lại mật khẩu</button>
    </form>
</div>

<%@ include file="footer.jsp" %>
</body>
</html>