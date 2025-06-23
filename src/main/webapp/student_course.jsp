<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.time.*" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.time.temporal.WeekFields" %>




<html>
<head>
    <title>Khoá của bạn</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<!-- Header Start -->
<%@ include file="header.jsp" %>
<!-- Header End -->

<div class="container mt-5">
    <div class="row">
        <div class="col">
            <nav aria-label="breadcrumb" class="bg-white rounded-3 p-3 mb-4">
                <ol class="breadcrumb mb-0">
                    <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/index.jsp">Trang chủ</a></li>

                </ol>
            </nav>
        </div>
    </div>
    <h2 class="mb-3">Khóa Học của bạn</h2>







        <table class="table table-bordered">
            <thead class="table-light">
            <tr>
                <th>ID</th>
                <th>Tên môn học</th>
                <th>Gia sư</th>
                <th>Level</th>
                <th>Mô tả</th>
                <th>Học phí</th>
                <th>Trạng thái</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="item" items="${data.keySet()}">
                <tr>
                    <td>${item.getId()}</td>
                    <td>${item.getSubject().getName()}</td>
                    <td>${item.getTutor().getName()}</td>
                    <td>${item.getSubject().getLevel()}</td>
                    <td>${item.getSubject().getDescription()}</td>
                    <td>${item.getSubject().getFee()}</td>
                    <td>${data.get(item)}</td>

                </tr>
            </c:forEach>
            </tbody>
        </table>

</div>
<!-- Footer Start -->
<%@ include file="footer.jsp" %>
<!-- Footer End -->
</body>
</html>
