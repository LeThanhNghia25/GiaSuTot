<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Quản trị</title>

  <!-- Link đến Bootstrap CSS -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/css/bootstrap.min.css" rel="stylesheet">

  <!-- FontAwesome (Biểu tượng cho Sidebar) -->
  <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css" rel="stylesheet">

  <!-- Các CSS khác nếu cần thiết -->
  <link href="css/style.css" rel="stylesheet">

</head>
<body>
<!-- sidebar.html -->
<ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">

  <!-- Sidebar - Thương hiệu -->
  <a class="sidebar-brand d-flex align-items-center justify-content-center" href="index.jsp">
    <div class="sidebar-brand-icon rotate-n-15">
      <i class="fas fa-laugh-wink"></i>
    </div>
    <div class="sidebar-brand-text mx-3">Quản trị <sup>2</sup></div>
  </a>

  <hr class="sidebar-divider my-0">

  <!-- Trang chủ -->
  <li class="nav-item active">
    <a class="nav-link" href="index.jsp">
      <i class="fas fa-fw fa-tachometer-alt"></i>
      <span>Bảng điều khiển</span>
    </a>
  </li>

  <hr class="sidebar-divider">

  <!-- Giao diện -->
  <div class="sidebar-heading">
    Giao diện
  </div>

  <!-- Thành phần -->
  <li class="nav-item">
    <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseTwo"
       aria-expanded="true" aria-controls="collapseTwo">
      <i class="fas fa-fw fa-cog"></i>
      <span>Thành phần</span>
    </a>
    <div id="collapseTwo" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
      <div class="bg-white py-2 collapse-inner rounded">
        <h6 class="collapse-header">Tùy chỉnh thành phần:</h6>
        <a class="collapse-item" href="buttons.jsp">Nút bấm</a>
        <a class="collapse-item" href="cards.jsp">Thẻ</a>
      </div>
    </div>
  </li>

  <!-- Tiện ích -->
  <li class="nav-item">
    <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseUtilities"
       aria-expanded="true" aria-controls="collapseUtilities">
      <i class="fas fa-fw fa-wrench"></i>
      <span>Tiện ích</span>
    </a>
    <div id="collapseUtilities" class="collapse" aria-labelledby="headingUtilities"
         data-parent="#accordionSidebar">
      <div class="bg-white py-2 collapse-inner rounded">
        <h6 class="collapse-header">Tùy chỉnh tiện ích:</h6>
        <a class="collapse-item" href="utilities-color.jsp">Màu sắc</a>
        <a class="collapse-item" href="utilities-border.jsp">Viền</a>
        <a class="collapse-item" href="utilities-animation.jsp">Hiệu ứng</a>
        <a class="collapse-item" href="utilities-other.jsp">Khác</a>
      </div>
    </div>
  </li>

  <hr class="sidebar-divider">

  <!-- Tiện ích bổ sung -->
  <div class="sidebar-heading">
    Bổ sung
  </div>

  <!-- Trang -->
  <li class="nav-item">
    <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapsePages"
       aria-expanded="true" aria-controls="collapsePages">
      <i class="fas fa-fw fa-folder"></i>
      <span>Trang</span>
    </a>
    <div id="collapsePages" class="collapse" aria-labelledby="headingPages" data-parent="#accordionSidebar">
      <div class="bg-white py-2 collapse-inner rounded">
        <h6 class="collapse-header">Màn hình đăng nhập:</h6>
        <a class="collapse-item" href="login.jsp">Đăng nhập</a>
        <a class="collapse-item" href="register.jsp">Đăng ký</a>
        <a class="collapse-item" href="forgot-password.jsp">Quên mật khẩu</a>
        <div class="collapse-divider"></div>
        <h6 class="collapse-header">Trang khác:</h6>
        <a class="collapse-item" href="404.jsp">Lỗi 404</a>
        <a class="collapse-item" href="blank.jsp">Trang trắng</a>
      </div>
    </div>
  </li>

  <!-- Biểu đồ -->
  <li class="nav-item">
    <a class="nav-link" href="charts.jsp">
      <i class="fas fa-fw fa-chart-area"></i>
      <span>Biểu đồ</span>
    </a>
  </li>

  <!-- Bảng -->
  <li class="nav-item">
    <a class="nav-link" href="tables.jsp">
      <i class="fas fa-fw fa-table"></i>
      <span>Bảng</span>
    </a>
  </li>

  <hr class="sidebar-divider d-none d-md-block">

  <!-- Nút thu gọn sidebar -->
  <div class="text-center d-none d-md-inline">
    <button class="rounded-circle border-0" id="sidebarToggle"></button>
  </div>

  <!-- Thông điệp Sidebar -->
  <div class="sidebar-card d-none d-lg-flex">
    <img class="sidebar-card-illustration mb-2" src="img/undraw_rocket.svg" alt="...">
    <p class="text-center mb-2"><strong>SB Admin Pro</strong> đi kèm với các tính năng cao cấp, thành phần và nhiều hơn nữa!</p>
    <a class="btn btn-success btn-sm" href="https://startbootstrap.com/theme/sb-admin-pro">Nâng cấp lên Pro!</a>
  </div>

</ul>

<!-- Link đến các thư viện JS -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
