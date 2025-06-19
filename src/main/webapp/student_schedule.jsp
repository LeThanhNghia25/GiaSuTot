<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.time.*" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.time.temporal.WeekFields" %>

<%
    String weekParam = request.getParameter("week");
    LocalDate today = LocalDate.now();

    // Cách mới: Lấy thứ hai từ tuần (không parse trực tiếp ngày)
    LocalDate monday;
    if (weekParam != null && !weekParam.isEmpty()) {
        // Parse thủ công yyyy-Www
        String[] parts = weekParam.split("-W");
        int year = Integer.parseInt(parts[0]);
        int week = Integer.parseInt(parts[1]);

        WeekFields weekFields = WeekFields.ISO;
        monday = LocalDate.ofYearDay(year, 1)
                .with(weekFields.weekOfWeekBasedYear(), week)
                .with(weekFields.dayOfWeek(), 1); // Thứ 2
    } else {
        monday = today.with(DayOfWeek.MONDAY);
    }

    LocalDate prevMonday = monday.minusWeeks(1);
    LocalDate nextMonday = monday.plusWeeks(1);

    DateTimeFormatter weekFormatter = DateTimeFormatter.ofPattern("yyyy-'W'ww");
    String weekStr = monday.format(weekFormatter);
    String prevWeekStr = prevMonday.format(weekFormatter);
    String nextWeekStr = nextMonday.format(weekFormatter);
%>


<html>
<head>
    <title>Lịch học của bạn</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<!-- Header Start -->
<%@ include file="header.jsp" %>
<!-- Header End -->

<div class="container mt-5">
    <h2 class="mb-3">Lịch học trong tuần</h2>

    <!-- Form chọn tuần -->
    <form class="mb-3 d-flex gap-3 align-items-center" method="get">
        <input type="week" name="week" class="form-control w-auto" value="<%= weekStr %>"/>
        <button type="submit" class="btn btn-primary">Xem tuần</button>
        <a href="?week=<%= prevWeekStr %>" class="btn btn-outline-secondary">⟵ Tuần trước</a>
        <a href="?week=<%= nextWeekStr %>" class="btn btn-outline-secondary">Tuần sau ⟶</a>
    </form>

    <!-- Thông báo lỗi -->
    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>

    <!-- Không có lịch -->
    <c:if test="${empty schedule}">
        <p>Không có lịch học trong tuần này.</p>
    </c:if>

    <!-- Có lịch -->
    <c:if test="${not empty schedule}">
        <table class="table table-bordered">
            <thead class="table-light">
            <tr>
                <th>Thứ</th>
                <th>Môn học</th>
                <th>Gia sư</th>
                <th>Giờ học</th>
                <th>Ngày học</th>
                <th>Trạng thái</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="item" items="${schedule}">
                <tr>
                    <td>${item.dayOfWeek}</td>
                    <td>${item.subjectName}</td>
                    <td>${item.tutorName}</td>
                    <td>${item.hourString}</td>
                    <td>${item.dateString}</td>
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
<!-- Footer Start -->
<%@ include file="footer.jsp" %>
<!-- Footer End -->
</body>
</html>
