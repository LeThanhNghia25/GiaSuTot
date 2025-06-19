<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Chờ xác thực tài khoản</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background: #f8f9fa;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        .card {
            max-width: 600px;
            margin: 80px auto;
            padding: 30px;
            border-radius: 16px;
            box-shadow: 0 6px 18px rgba(0,0,0,0.1);
        }

        .btn-home {
            margin-top: 20px;
        }
    </style>
</head>
<body>

<%@ include file="header.jsp" %>

<div class="container">
    <div class="card text-center">
        <h2 class="text-primary">Vui lòng xác thực tài khoản</h2>
        <p class="mt-4">
            Chúng tôi đã gửi một email xác thực đến địa chỉ bạn vừa đăng nhập.
            <br>Hãy kiểm tra hộp thư đến (hoặc thư rác) và nhấn vào liên kết trong email để kích hoạt tài khoản.
        </p>

        <c:if test="${not empty message}">
            <div class="alert alert-success mt-3">${message}</div>
        </c:if>

        <c:if test="${not empty error}">
            <div class="alert alert-danger mt-3">${error}</div>
        </c:if>

        <a href="index.jsp" class="btn btn-outline-primary btn-home">Quay về trang chủ</a>
    </div>
</div>

<%@ include file="footer.jsp" %>

</body>
</html>
