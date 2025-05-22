<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Header</title>
  <!-- Đảm bảo Bootstrap CSS được bao gồm -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <!-- Font Awesome CSS cho biểu tượng -->
  <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet">
</head>
<body>
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
      <a href="index.jsp" class="nav-item nav-link active">Trang chủ</a>
      <a href="about.jsp" class="nav-item nav-link">Về chúng tôi</a>
      <a href="courses.jsp" class="nav-item nav-link">Khóa học</a>
      <div class="nav-item dropdown">
        <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown">Pages</a>
        <div class="dropdown-menu fade-down m-0">
          <a href="team.jsp" class="dropdown-item">Our Team</a>
          <a href="testimonial.jsp" class="dropdown-item">Testimonial</a>
          <a href="404.jsp" class="dropdown-item">404 Page</a>
        </div>
      </div>
      <a href="contact.jsp" class="nav-item nav-link">Liên hệ</a>
    </div>
    <!-- Thanh tìm kiếm -->
    <form class="d-flex ms-3 me-3" role="search" method="post" action="searchServlet">
      <input class="form-control me-2" name="subName" type="search" placeholder="Tìm kiếm khóa học..." aria-label="Search">
      <button class="btn btn-primary" type="submit"><i class="fa fa-search"></i></button>
    </form>

    <!-- Nút Đăng nhập -->
    <a href="" class="btn btn-primary btn-short py-4 px-lg-5 d-none d-lg-block">Đăng nhập<i class="fa fa-arrow-right ms-2"></i></a>
  </div>
</nav>
<!-- Navbar End -->

<!-- Bootstrap JS và Popper.js -->
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
</body>
</html>