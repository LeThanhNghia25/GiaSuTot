<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>Header</title>
  <!-- Font Awesome CSS cho biểu tượng -->
  <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet">

  <meta charset="utf-8">
  <meta content="width=device-width, initial-scale=1.0" name="viewport">
  <meta content="" name="keywords">
  <meta content="" name="description">

  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

  <!-- Customized Bootstrap Stylesheet -->
  <link href="css/bootstrap.min.css" rel="stylesheet">

  <!-- Template Stylesheet -->
  <link href="css/style.css" rel="stylesheet">
  <link href="css/login-signup.css" rel="stylesheet">
  <link href="css/modal.css" rel="stylesheet">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
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
      <a href="" class="nav-item nav-link">Profile</a>
    </div>

    <!-- Thanh tìm kiếm -->
    <form class="d-flex ms-3 me-2" role="search" method="GET" action="courses.jsp">
      <input class="form-control me-2" type="search" name="search" placeholder="Tìm kiếm khóa học..." aria-label="Search"
             value="${param.search != null ? param.search : ''}">
      <button class="btn btn-primary" type="submit"><i class="fa fa-search"></i></button>
    </form>

    <!-- Nút Bộ lọc mở modal -->
    <button type="button" class="btn btn-outline-primary me-3" data-bs-toggle="modal" data-bs-target="#modalBoLoc">
      <i class="fa fa-filter"></i> Bộ lọc
    </button>

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
                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/student">Thông tin cá nhân (Student)</a></li>
              </c:when>
              <c:when test="${sessionScope.role == 'tutor'}">
                <!-- Link tới tutor profile -->
                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/tutor">Thông tin cá nhân (Tutor)</a></li>
              </c:when>
            </c:choose>

            <li><hr class="dropdown-divider"></li>
            <li><a class="dropdown-item text-danger" href="${pageContext.request.contextPath}/account?action=logout">Đăng xuất</a></li>
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

<!-- Modal Bộ lọc -->
<div class="modal fade" id="modalBoLoc" tabindex="-1" aria-labelledby="modalBoLocLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <form id="formBoLoc" method="GET" action="courses.jsp">
        <div class="modal-header">
          <h5 class="modal-title" id="modalBoLocLabel">Bộ lọc khóa học</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Đóng"></button>
        </div>
        <div class="modal-body">
          <!-- Tên môn học -->
          <div class="mb-3">
            <label for="tenMonHoc" class="form-label">Tên môn học</label>
            <input type="text" class="form-control" id="tenMonHoc" name="tenMon" placeholder="Nhập tên môn học"
                   value="${param.tenMon != null ? param.tenMon : ''}">
          </div>
          <!-- Lớp -->
          <div class="mb-3">
            <label for="lop" class="form-label">Lớp</label>
            <select id="lop" name="lop" class="form-select">
              <option value="">-- Chọn lớp --</option>
              <option value="6" ${param.lop == '6' ? 'selected' : ''}>Lớp 6</option>
              <option value="7" ${param.lop == '7' ? 'selected' : ''}>Lớp 7</option>
              <option value="8" ${param.lop == '8' ? 'selected' : ''}>Lớp 8</option>
              <option value="9" ${param.lop == '9' ? 'selected' : ''}>Lớp 9</option>
              <option value="10" ${param.lop == '10' ? 'selected' : ''}>Lớp 10</option>
              <option value="11" ${param.lop == '11' ? 'selected' : ''}>Lớp 11</option>
              <option value="12" ${param.lop == '12' ? 'selected' : ''}>Lớp 12</option>
            </select>
          </div>
          <!-- Tỉnh/Thành phố -->
          <div class="mb-3">
            <label for="tinh" class="form-label">Tỉnh/Thành phố</label>
            <select id="tinh" name="tinh" class="form-select">
              <option value="">-- Chọn tỉnh --</option>
              <option value="HCM" ${param.tinh == 'HCM' ? 'selected' : ''}>TP.HCM</option>
              <option value="HN" ${param.tinh == 'HN' ? 'selected' : ''}>Hà Nội</option>
              <option value="DN" ${param.tinh == 'DN' ? 'selected' : ''}>Đà Nẵng</option>
              <!-- Thêm tỉnh khác -->
            </select>
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
          <button type="submit" class="btn btn-primary">Áp dụng bộ lọc</button>
        </div>
      </form>
    </div>
  </div>
</div>

<!-- Bootstrap JS và Popper.js -->
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
</body>
</html>