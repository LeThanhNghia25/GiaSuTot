<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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

    <!-- Toast CSS -->
    <style>
        .course-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
            gap: 30px;
            margin-bottom: 30px;
        }
        .course-item {
            background: #ffffff;
            border-radius: 15px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            overflow: hidden;
            transition: transform 0.3s ease, box-shadow 0.3s ease;
        }
        .course-item:hover {
            transform: translateY(-5px);
            box-shadow: 0 6px 12px rgba(0, 0, 0, 0.15);
        }
        .course-content {
            padding: 20px;
        }
        .course-header {
            background: #f8f9fa;
            padding: 15px;
            text-align: center;
        }
        .course-header h5 {
            font-size: 1.25rem;
            margin-bottom: 5px;
            color: #333;
        }
        .course-header .fee {
            font-size: 1.5rem;
            font-weight: bold;
            color: #28a745;
        }
        .course-description {
            font-size: 0.9rem;
            color: #666;
            margin-bottom: 15px;
            display: -webkit-box;
            -webkit-line-clamp: 3;
            -webkit-box-orient: vertical;
            overflow: hidden;
            text-overflow: ellipsis;
        }
        .course-info {
            display: flex;
            flex-wrap: wrap;
            border-top: 1px solid #e9ecef;
            margin-top: 15px;
        }
        .course-info small {
            flex: 1 1 50%;
            text-align: center;
            padding: 10px 0;
            font-size: 0.85rem;
            color: #555;
        }
        .course-info small i {
            margin-right: 5px;
        }
        .course-actions {
            display: flex;
            justify-content: center;
            gap: 10px;
            padding: 15px;
            border-top: 1px solid #e9ecef;
        }
        .course-actions .btn {
            font-size: 0.9rem;
            padding: 8px 15px;
        }
        .rating-stars .fa-star {
            color: #ffc107;
        }
        .pagination {
            display: flex;
            justify-content: center;
            gap: 10px;
            margin-top: 30px;
        }
        .pagination a {
            text-decoration: none;
            padding: 8px 15px;
            color: #007bff;
            border: 1px solid #007bff;
            border-radius: 5px;
            transition: background-color 0.3s, color 0.3s;
        }
        .pagination a:hover {
            background: #007bff;
            color: white;
        }
        .pagination a.active {
            background: #007bff;
            color: white;
            border-color: #007bff;
        }
        /* Message Styles */
        .message {
            margin: 10px 0;
            padding: 10px;
            border-radius: 5px;
            text-align: center;
            display: ${not empty message ? 'block' : 'none'};
        }
        .message.success {
            background-color: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
        }
        .message.error {
            background-color: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }
    </style>
</head>
<body>
<!-- Header Start -->
<%@ include file="header.jsp" %>
<!-- Header End -->

<!-- Message Display -->
<c:if test="${not empty message}">
    <div class="container">
        <div class="message ${message.contains('thành công') ? 'success' : 'error'}">
                ${message}
        </div>
    </div>
</c:if>

<!-- Courses Start -->
<div class="container-xxl py-5">
    <div class="container">
        <div class="text-center wow fadeInUp" data-wow-delay="0.1s">
            <h6 class="section-title bg-white text-center text-primary px-3">Khóa Học</h6>
            <h1 class="mb-5">Tất Cả Khóa Học Có Sẵn</h1>
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
                        <div class="course-item wow fadeInUp" data-wow-delay="${loop.index * 0.2}s">
                            <div class="course-header">
                                <h5 class="mb-4">
                                    <c:out value="${course.subject.name}"/> - <c:out value="${course.subject.level}"/>
                                </h5>
                                <div class="fee">
                                    <fmt:formatNumber value="${course.subject.fee}" pattern="#,##0"/> VND
                                </div>
                            </div>
                            <div class="course-content">
                                <p class="course-description">
                                    <c:out value="${course.subject.description}"/>
                                </p>
                                <div class="rating-stars mb-3">
                                    <c:forEach begin="1" end="${course.tutor.evaluate}">
                                        <small class="fa fa-star"></small>
                                    </c:forEach>
                                    <c:forEach begin="${course.tutor.evaluate + 1}" end="5">
                                        <small class="fa fa-star text-muted"></small>
                                    </c:forEach>
                                    <small>(${course.tutor.evaluate})</small>
                                </div>
                                <div class="course-info">
                                    <small><i class="fa fa-user-tie text-primary"></i><c:out value="${course.tutor.name}"/></small>
                                    <small><i class="fa fa-book text-primary"></i><c:out value="${course.tutor.specialization}"/></small>
                                    <small><i class="fa fa-clock text-primary"></i>${requestScope['formattedTime_' += course.id]}</small>
                                    <small><i class="fa fa-users text-primary"></i>${course.studentCount} học viên</small>
                                </div>
                            </div>
                            <div class="course-actions">
                                <form action="${pageContext.request.contextPath}/courses" method="post">
                                    <input type="hidden" name="courseId" value="${course.id}">
                                    <input type="hidden" name="action" value="register">
                                    <input type="hidden" name="page" value="${currentPage}">
                                    <button type="submit" class="btn btn-primary btn-sm">Đăng ký</button>
                                </form>
                                <a href="#" class="btn btn-secondary btn-sm">Học thử</a>
                            </div>
                        </div>
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