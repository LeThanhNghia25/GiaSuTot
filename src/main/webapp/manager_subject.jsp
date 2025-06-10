<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Quản lý môn học</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" integrity="sha512-DTOQO9RWCH3ppGqcWaEA1BIZOC6xxalwEsw9c2QQeAIftl+Vegovlnee1c9QX4TctnWMn13TZye+giMm8e2LwA==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <style>
        .container {
            padding-top: 20px;
            padding-bottom: 20px;
        }
        .card-body {
            background-color: #fff;
            border-radius: 8px;
        }
    </style>
</head>
<body>
<section style="background-color: #eee;">
    <div class="container">
        <div class="row">
            <div class="col">
                <nav aria-label="breadcrumb" class="bg-white rounded-3 p-3 mb-4">
                    <ol class="breadcrumb mb-0">
                        <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/index.jsp">Trang chủ</a></li>
                        <li class="breadcrumb-item active" aria-current="page">Quản lý môn học</li>
                    </ol>
                </nav>
            </div>
        </div>

        <!-- Hiển thị thông báo -->
        <c:if test="${not empty error}">
            <div class="alert alert-danger" role="alert">${error}</div>
        </c:if>

        <!-- Danh sách môn học -->
        <div class="card mb-4">
            <div class="card-body">
                <h5 class="mb-3">Danh sách môn học (Active)</h5>
                <table class="table table-bordered">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Tên môn</th>
                        <th>Cấp độ</th>
                        <th>Mô tả</th>
                        <th>Học phí</th>
                        <th>Buổi học</th>
                        <th>Danh sách học viên/ Buổi học hoàn thành</th>

                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="subject" items="${subjects}">
                        <tr>
                            <td>${subject.id}</td>
                            <td>${subject.name}</td>
                            <td>${subject.level}</td>
                            <td>${subject.description}</td>
                            <td>${subject.fee}</td>
                            <td>
                                <c:forEach var="rs" items="${registeredSubjectsMap[subject.id]}">
                                    <c:if test="${rs.status == 'completed'}">
                                        ${rs.number_of_lessons}<br>
                                    </c:if>
                                </c:forEach>
                            </td>


                            <td>
                                <c:forEach var="rs" items="${registeredSubjectsMap[subject.id]}">
                                    <c:if test="${rs.status == 'completed'}">
                                        <c:set var="mapKey" value="${rs.course_id}_${rs.student_id}" />
                                        • <a href="${pageContext.request.contextPath}/student?id=${rs.student.id}">
                                            ${rs.student.name}
                                    </a> /
                                        ${lessonCountsMap[mapKey] != null ? lessonCountsMap[mapKey] : 0} buổi<br>
                                    </c:if>
                                </c:forEach>
                            </td>


                        </tr>
                    </c:forEach>
                    <c:if test="${empty subjects}">
                        <tr>
                            <td colspan="7" class="text-center">Không có môn học active nào.</td>
                        </tr>
                    </c:if>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</section>

<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
</body>
</html>