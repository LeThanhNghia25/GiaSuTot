<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Thanh Toán Khóa Học</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <h2>Thanh Toán Khóa Học</h2>
    <p>Bạn đang thanh toán cho khóa học với mã: ${courseId}</p>
    <form action="${pageContext.request.contextPath}/payment" method="post">
        <input type="hidden" name="courseId" value="${courseId}">
        <button type="submit" class="btn btn-primary">Thanh Toán Qua MoMo</button>
    </form>
    <a href="${pageContext.request.contextPath}/courses" class="btn btn-secondary mt-2">Hủy</a>
</div>
</body>
</html>