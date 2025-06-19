<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> <!-- Quan trọng -->
<fmt:setLocale value="vi_VN" /> <!-- Đặt locale tiếng Việt -->

<html>
<head>
    <title>Lịch học của bạn</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <h2 class="mb-4">Lịch học trong tuần</h2>

    <!-- Thông báo lỗi -->
    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>

    <!-- Không có lịch -->
    <c:if test="${empty schedule}">
        <p>Bạn chưa có lịch học nào.</p>
    </c:if>

    <!-- Có lịch -->
    <c:if test="${not empty schedule}">
        <table class="table table-bordered" style="background-color: #fff;">
            <thead class="table-light">
            <tr>
                <th>Thứ</th>
                <th>Môn học</th>
                <th>Gia sư</th>
                <th>Thời gian học</th>
                <th>Trạng thái</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="item" items="${schedule}">
                <tr>
                    <!-- Thứ + ngày -->
                    <td>
                            ${item.dayOfWeek}
                        (<fmt:formatDate value="${item.time}" pattern="dd/MM" />)
                    </td>

                    <!-- Môn học -->
                    <td>${item.subjectName}</td>

                    <!-- Gia sư -->
                    <td>${item.tutorName}</td>

                    <!-- Giờ học -->
                    <td>
                        <fmt:formatDate value="${item.time}" pattern="HH:mm" />
                    </td>

                    <!-- Trạng thái -->
                    <td>
                        <c:choose>
                            <c:when test="${item.status == 'completed'}">
                                <span class="badge bg-success">Hoàn thành</span>
                            </c:when>
                            <c:when test="${item.status == 'absent'}">
                                <span class="badge bg-danger">Vắng</span>
                            </c:when>
                            <c:otherwise>
                                <span class="badge bg-warning text-dark">Sắp tới</span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
</div>
</body>
</html>
