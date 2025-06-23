<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Font Awesome CSS cho biểu tượng -->
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet">

<meta charset="utf-8">
<meta content="width=device-width, initial-scale=1.0" name="viewport">
<meta content="" name="keywords">
<meta content="" name="description">

<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/login-signup.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/modal.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">

<!-- Spinner Start -->
<div id="spinner" class="show bg-white position-fixed translate-middle w-100 vh-100 top-50 start-50 d-flex align-items-center justify-content-center">
  <div class="spinner-border text-primary" style="width: 3rem; height: 3rem;" role="status">
    <span class="sr-only">Loading...</span>
  </div>
</div>
<!-- Spinner End -->

<!-- Navbar Start -->
<nav class="navbar navbar-expand-lg bg-white navbar-light shadow sticky-top p-0">
  <a href="index.jsp" class="navbar-brand d-flex align-items-center px-4 px-lg-5">
    <h2 class="m-0 text-primary"><i class="fa fa-book me-3"></i>GIASUTOT</h2>
  </a>
  <button type="button" class="navbar-toggler me-4" data-bs-toggle="collapse" data-bs-target="#navbarCollapse">
    <span class="navbar-toggler-icon"></span>
  </button>
  <div class="collapse navbar-collapse" id="navbarCollapse">
    <div class="navbar-nav ms-auto p-4 p-lg-0">
      <a href="index.jsp" class="nav-item nav-link">Trang chủ</a>
      <a href="about.jsp" class="nav-item nav-link">Về chúng tôi</a>
      <a href="${pageContext.request.contextPath}/courses" class="nav-item nav-link">Khóa học</a>
      <div class="nav-item dropdown">
        <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown">Pages</a>
        <div class="dropdown-menu fade-down m-0">
          <a href="team.jsp" class="dropdown-item">Our Team</a>
          <a href="testimonial.jsp" class="dropdown-item">Testimonial</a>
          <a href="404.jsp" class="dropdown-item">404 Page</a>
        </div>
      </div>
      <a href="${pageContext.request.contextPath}/tutor" class="nav-item nav-link">Profile</a>
    </div>

    <!-- Kiểm tra trạng thái đăng nhập -->
    <c:choose>
      <c:when test="${not empty sessionScope.account}">
        <!-- Nếu đã đăng nhập, hiển thị tên người dùng và nút Đăng xuất -->
        <div class="dropdown py-4 px-lg-5 d-none d-lg-block">
          <a class="btn btn-outline-primary dropdown-toggle d-flex align-items-center" href="#" role="button" id="userDropdown" data-bs-toggle="dropdown" aria-expanded="false">
            <i class="fa fa-user me-2"></i>
            <span>Xin chào, <c:out value="${sessionScope.userName}"/></span>
          </a>
          <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="userDropdown">
            <c:choose>
              <c:when test="${sessionScope.role == 'student'}">
                <!-- Link tới student profile -->
                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/student">Thông tin cá nhân</a></li>
                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/student-schedule">Xem lịch học</a></li>
                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/studentCourse">Xem khóa học</a></li>
                <li><hr class="dropdown-divider"></li>
                <li><a class="dropdown-item text-danger" href="${pageContext.request.contextPath}/account?action=logout">Đăng xuất</a></li>
              </c:when>
              <c:when test="${sessionScope.role == 'tutor'}">
                <!-- Link tới tutor profile -->
                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/tutor">Thông tin cá nhân</a></li>
                <li><hr class="dropdown-divider"></li>
                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/approve-registrations">Duyệt đăng ký</a></li>
                <li><hr class="dropdown-divider"></li>
                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/tutor-subjects">Quản lý môn học</a></li>
                <li><hr class="dropdown-divider"></li>
                <li> <a class="dropdown-item" href="${pageContext.request.contextPath}/lesson" methods="get">Tạo buổi học</a> </li>
                <li><hr class="dropdown-divider"></li>
                <li>  <a class="dropdown-item text-danger" href="${pageContext.request.contextPath}/account?action=logout">Đăng xuất</a>
                </li>
              </c:when>
            </c:choose>
          </ul>
        </div>
      </c:when>
      <c:otherwise>
        <!-- Nếu chưa đăng nhập, hiển thị nút Đăng nhập -->
        <a href="${pageContext.request.contextPath}/account?action=login" class="btn btn-primary btn-short py-4 px-lg-5 d-none d-lg-block">Đăng nhập<i class="fa fa-arrow-right ms-2"></i></a>
      </c:otherwise>
    </c:choose>
  </div>
</nav>
<!-- Navbar End -->

<!-- Script để ẩn spinner -->
<script>
  document.addEventListener('DOMContentLoaded', function() {
    const spinner = document.getElementById('spinner');
    if (spinner) {
      setTimeout(() => {
        spinner.classList.remove('show');
      }, 1000); // Ẩn spinner sau 1 giây
    }
  });
</script>