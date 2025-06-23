<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <title>Courses</title>
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
        .message {
            margin: 10px 0;
            padding: 10px;
            border-radius: 5px;
            text-align: center;
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
        .filter-section {
            background: #f8f9fa;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }
        .filter-section .form-label {
            font-weight: 600;
            color: #333;
        }
        .filter-section .form-control,
        .filter-section .form-select {
            border-radius: 5px;
        }
        .filter-section .btn-primary {
            padding: 10px;
            font-size: 1rem;
        }
        @media (max-width: 767.98px) {
            .filter-section .col-md-6 {
                margin-bottom: 15px;
            }
        }
    </style>
</head>
<body>
<!-- Header Start -->
<%@ include file="header.jsp" %>
<!-- Header End -->

<!-- Categories Start -->
<div class="container-xxl py-5 category">
    <div class="container">
        <div class="text-center wow fadeInUp" data-wow-delay="0.1s">
            <h6 class="section-title bg-white text-center text-primary px-3">Danh mục</h6>
            <h1 class="mb-5">Danh mục khóa học</h1>
        </div>
        <div class="row g-3">
            <div class="col-lg-7 col-md-6">
                <div class="row g-3">
                    <div class="col-lg-12 col-md-12 wow zoomIn" data-wow-delay="0.1s">
                        <a class="position-relative d-block overflow-hidden" href="#">
                            <img class="img-fluid" src="img/cat-1.jpg" alt="">
                            <div class="bg-white text-center position-absolute bottom-0 end-0 py-2 px-3" style="margin: 1px;">
                                <h5 class="m-0">Toán học</h5>
                                <small class="text-primary">50 Khóa học</small>
                            </div>
                        </a>
                    </div>
                    <div class="col-lg-6 col-md-12 wow zoomIn" data-wow-delay="0.3s">
                        <a class="position-relative d-block overflow-hidden" href="#">
                            <img class="img-fluid" src="img/cat-2.jpg" alt="">
                            <div class="bg-white text-center position-absolute bottom-0 end-0 py-2 px-3" style="margin: 1px;">
                                <h5 class="m-0">Vật lý</h5>
                                <small class="text-primary">30 Khóa học</small>
                            </div>
                        </a>
                    </div>
                    <div class="col-lg-6 col-md-12 wow zoomIn" data-wow-delay="0.5s">
                        <a class="position-relative d-block overflow-hidden" href="#">
                            <img class="img-fluid" src="img/cat-3.jpg" alt="">
                            <div class="bg-white text-center position-absolute bottom-0 end-0 py-2 px-3" style="margin: 1px;">
                                <h5 class="m-0">Hóa học</h5>
                                <small class="text-primary">25 Khóa học</small>
                            </div>
                        </a>
                    </div>
                </div>
            </div>
            <div class="col-lg-5 col-md-6 wow zoomIn" data-wow-delay="0.7s" style="min-height: 350px;">
                <a class="position-relative d-block h-100 overflow-hidden" href="#">
                    <img class="img-fluid position-absolute w-100 h-100" src="img/cat-4.jpg" alt="" style="object-fit: cover;">
                    <div class="bg-white text-center position-absolute bottom-0 end-0 py-2 px-3" style="margin: 1px;">
                        <h5 class="m-0">Tiếng Anh</h5>
                        <small class="text-primary">40 Khóa học</small>
                    </div>
                </a>
            </div>
        </div>
    </div>
</div>
<!-- Categories End -->

<!-- Courses Start -->
<div class="container-xxl py-5">
    <div class="container">
        <div class="text-center wow fadeInUp" data-wow-delay="0.1s">
            <h6 class="section-title bg-white text-center text-primary px-3">Khóa học</h6>
            <c:choose>
                <c:when test="${not empty param.search or not empty param.tenMon or not empty param.lop or not empty param.tinh or not empty param.tutorName}">
                    <h1 class="mb-5">Kết quả tìm kiếm</h1>
                </c:when>
                <c:otherwise>
                    <h1 class="mb-5">Tất cả khóa học có sẵn</h1>
                </c:otherwise>
            </c:choose>
        </div>

        <!-- Bộ lọc trực tiếp -->
        <div class="filter-section mb-5">
            <form method="GET" action="${pageContext.request.contextPath}/courses" class="row g-3 justify-content-center">
                <!-- Tên môn học -->
                <div class="col-lg-3 col-md-6">
                    <label for="tenMonHoc" class="form-label">Tên môn học</label>
                    <input type="text" class="form-control" id="tenMonHoc" name="tenMon" placeholder="Nhập tên môn học"
                           value="${param.tenMon != null ? param.tenMon : ''}">
                </div>
                <!-- Tên gia sư -->
                <div class="col-lg-3 col-md-6">
                    <label for="tutorName" class="form-label">Tên gia sư</label>
                    <input type="text" class="form-control" id="tutorName" name="tutorName" placeholder="Nhập tên gia sư"
                           value="${param.tutorName != null ? param.tutorName : ''}">
                </div>
                <!-- Trình Độ -->
                <div class="col-lg-2 col-md-6">
                    <label for="trinhDo" class="form-label">Trình Độ</label>
                    <input type="text" class="form-control" id="trinhDo" name="trinhDo" placeholder="Nhập trình độ"
                           value="${param.trinhDo != null ? param.trinhDo : ''}">
                </div>
                <!-- Nút áp dụng -->
                <div class="col-lg-2 col-md-6 d-flex align-items-end">
                    <button type="submit" class="btn btn-primary w-100">Lọc</button>
                </div>
            </form>
        </div>

        <!-- Message Display -->
        <c:if test="${not empty message}">
            <div class="container">
                <div class="message ${message.contains('thành công') ? 'success' : 'error'}">
                        ${message}
                </div>
            </div>
        </c:if>

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
                                <a href="${pageContext.request.contextPath}/courses?action=trial&courseId=${course.id}" class="btn btn-secondary btn-sm">Học thử</a>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <!-- Phân trang -->
                <c:if test="${not empty allCourses and totalPages > 1}">
                    <div class="pagination">
                        <c:forEach var="i" begin="1" end="${totalPages}">
                            <a href="${pageContext.request.contextPath}/courses?page=${i}&search=${param.search}&tenMon=${param.tenMon}&tutorName=${param.tutorName}&trinhDo=${param.trinhDo}"
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
<script src="${pageContext.request.contextPath}/libs/wow/wow.min.js"></script>
<script>
    try {
        new WOW().init();
        console.log("WOW initialized successfully");
    } catch (e) {
        console.error("WOW initialization failed:", e);
    }
</script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/libs/easing/easing.min.js"></script>
<script src="${pageContext.request.contextPath}/libs/waypoints/waypoints.min.js"></script>
<script src="${pageContext.request.contextPath}/libs/owlcarousel/owl.carousel.min.js"></script>
<script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>