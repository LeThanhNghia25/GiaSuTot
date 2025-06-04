<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="model.Course" %>
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
    <link href="css/style.css" rel="stylesheet">
    <style>
        .course-grid {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            gap: 20px;
            margin-bottom: 20px;
        }
        .course-item {
            background: #f8f9fa;
            border-radius: 10px;
            overflow: hidden;
            transition: transform 0.3s;
        }
        .course-item:hover {
            transform: scale(1.05);
        }
        .course-actions {
            display: flex;
            justify-content: center;
            gap: 10px;
            padding: 10px;
        }
        .pagination {
            display: flex;
            justify-content: center;
            gap: 10px;
        }
        .pagination a {
            text-decoration: none;
            padding: 5px 10px;
            color: #007bff;
            border: 1px solid #007bff;
            border-radius: 5px;
        }
        .pagination a.active {
            background: #007bff;
            color: white;
        }
    </style>
</head>
<body>
<!-- Header Start -->
<%@ include file="header.jsp" %>
<!-- Header End -->

<!-- Courses Start -->
<div class="container-xxl py-5">
    <div class="container">
        <div class="text-center wow fadeInUp" data-wow-delay="0.1s">
            <h6 class="section-title bg-white text-center text-primary px-3">Courses</h6>
            <h1 class="mb-5">All Available Courses</h1>
        </div>
        <c:choose>
            <c:when test="${empty allCourses}">
                <div class="text-center">
                    <p class="text-muted">Hiện tại không có khóa học nào khả dụng.</p>
                </div>
            </c:when>
            <c:otherwise>
                <div class="course-grid">
                <c:forEach var="course" items="${allCourses}" varStatus="loop">
                    <div class="course-item wow fadeInUp" data-wow-delay="${loop.index % 3 == 0 ? '0.1s' : loop.index % 3 == 1 ? '0.3s' : '0.5s'}">
                        <div class="position-relative overflow-hidden">
                            <img class="img-fluid" src="img/course-${loop.index % 3 + 1}.jpg" alt="${course.subject.name}">
                            <div class="w-100 d-flex justify-content-center position-absolute bottom-0 start-0 mb-4">
                                <a href="#" class="flex-shrink-0 btn btn-sm btn-primary px-3 border-end" style="border-radius: 30px 0 0 30px;">Read More</a>
                            </div>
                        </div>
                        <div class="text-center p-4 pb-0">
                            <h3 class="mb-0">$<c:out value="${course.subject.fee}"/></h3>
                            <div class="mb-3">
                                <small class="fa fa-star text-primary"></small>
                                <small class="fa fa-star text-primary"></small>
                                <small class="fa fa-star text-primary"></small>
                                <small class="fa fa-star text-primary"></small>
                                <small class="fa fa-star text-primary"></small>
                                <small>(123)</small>
                            </div>
                            <h5 class="mb-4"><c:out value="${course.subject.name}"/> - <c:out value="${course.subject.level}"/></h5>
                        </div>
                        <div class="d-flex border-top">
                            <small class="flex-fill text-center border-end py-2"><i class="fa fa-user-tie text-primary me-2"></i>${course.tutor.name}</small>
                            <small class="flex-fill text-center border-end py-2"><i class="fa fa-clock text-primary me-2"></i>2.5 Hrs</small>
                            <small class="flex-fill text-center py-2"><i class="fa fa-user text-primary me-2"></i>50 Students</small>
                        </div>
                        <div class="course-actions">
                            <form action="${pageContext.request.contextPath}/courses" method="post" style="margin: 0;">
                                <input type="hidden" name="courseId" value="${course.id}">
                                <input type="hidden" name="action" value="register">
                                <button type="submit" class="btn btn-primary btn-sm">Đăng ký</button>
                            </form>
                            <form action="${pageContext.request.contextPath}/courses" method="post" style="margin: 0;">
                                <input type="hidden" name="courseId" value="${course.id}">
                                <input type="hidden" name="action" value="trial">
                                <button type="submit" class="btn btn-secondary btn-sm">Đăng ký học thử</button>
                            </form>
                        </div>
                    </div>
                    <c:if test="${(loop.index + 1) % 3 == 0 && loop.index < (allCourses.size() - 1)}">
                        </div><div class="course-grid">
                    </c:if>
                </c:forEach>
                </div>
                <!-- Phân trang -->
                <c:if test="${not empty allCourses and totalPages > 1}">
                    <div class="pagination">
                        <c:forEach var="i" begin="1" end="${totalPages}">
                            <a href="${pageContext.request.contextPath}/courses?page=${i}&tenMon=${param.tenMon}&lop=${param.lop}&tinh=${param.tinh}"
                               class="${i == currentPage ? 'active' : ''}">${i}</a>
                        </c:forEach>
                    </div>
                </c:if>
            </c:otherwise>
        </c:choose>
    </div>
</div>
<!-- Courses End -->

<!-- Footer Start -->
<%@ include file="footer.jsp" %>
<!-- Footer End -->

<!-- JavaScript Libraries -->
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="libs/wow/wow.min.js"></script>
<script src="libs/easing/easing.min.js"></script>
<script src="libs/waypoints/waypoints.min.js"></script>
<script src="libs/owlcarousel/owl.carousel.min.js"></script>

<!-- Template Javascript -->
<script src="js/main.js"></script>
</body>
</html>