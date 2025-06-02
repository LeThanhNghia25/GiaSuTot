<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <title>Quản trị</title>
  <!-- Bootstrap CSS -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/css/bootstrap.min.css" rel="stylesheet">
  <!-- FontAwesome -->
  <link href="${pageContext.request.contextPath}/admin/vendor/fontawesome-free/css/all.min.css" rel="stylesheet">
  <!-- Custom CSS -->
  <link href="${pageContext.request.contextPath}/admin/css/sb-admin-2.css" rel="stylesheet">
</head>
<body>
<ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">
  <!-- Sidebar - Thương hiệu -->
  <a class="sidebar-brand d-flex align-items-center justify-content-center" href="${pageContext.request.contextPath}/admin/index.jsp">
    <div class="sidebar-brand-icon rotate-n-15">
      <i class="fas fa-laugh-wink"></i>
    </div>
    <div class="sidebar-brand-text mx-3">Quản trị <sup>2</sup></div>
  </a>
  <hr class="sidebar-divider my-0">
  <!-- Trang chủ -->
  <li class="nav-item active">
    <a class="nav-link" href="${pageContext.request.contextPath}/index.jsp">
      <i class="fas fa-fw fa-tachometer-alt"></i>
      <span>Bảng điều khiển</span>
    </a>
  </li>
  <hr class="sidebar-divider">
  <!-- Giao diện -->
  <div class="sidebar-heading">Giao diện</div>
  <!-- Thành phần -->
  <li class="nav-item">
    <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseTwo" aria-expanded="true" aria-controls="collapseTwo">
      <i class="fas fa-fw fa-cog"></i>
      <span>Thành phần</span>
    </a>
    <div id="collapseTwo" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
      <div class="bg-white py-2 collapse-inner rounded">
        <h6 class="collapse-header">Tùy chỉnh thành phần:</h6>
        <a class="collapse-item" href="${pageContext.request.contextPath}/buttons.jsp">Nút bấm</a>
        <a class="collapse-item" href="${pageContext.request.contextPath}/cards.jsp">Thẻ</a>
      </div>
    </div>
  </li>
  <!-- Tiện ích -->
  <li class="nav-item">
    <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseUtilities" aria-expanded="true" aria-controls="collapseUtilities">
      <i class="fas fa-fw fa-wrench"></i>
      <span>Tiện ích</span>
    </a>
    <div id="collapseUtilities" class="collapse" aria-labelledby="headingUtilities" data-parent="#accordionSidebar">
      <div class="bg-white py-2 collapse-inner rounded">
        <h6 class="collapse-header">Tùy chỉnh tiện ích:</h6>
        <a class="collapse-item" href="${pageContext.request.contextPath}/utilities-color.jsp">Màu sắc</a>
        <a class="collapse-item" href="${pageContext.request.contextPath}/utilities-border.jsp">Viền</a>
        <a class="collapse-item" href="${pageContext.request.contextPath}/utilities-animation.jsp">Hiệu ứng</a>
        <a class="collapse-item" href="${pageContext.request.contextPath}/utilities-other.jsp">Khác</a>
      </div>
    </div>
  </li>
  <hr class="sidebar-divider">
  <!-- Tiện ích bổ sung -->
  <div class="sidebar-heading">Bổ sung</div>
  <!-- Quản lý môn học -->
  <li class="nav-item">
    <a class="nav-link" href="${pageContext.request.contextPath}/admin/subject">
      <i class="fas fa-fw fa-folder"></i>
      <span>Quản lý môn học</span>
    </a>
  </li>
  <!-- Quản lý người dùng -->
  <li class="nav-item">
    <a class="nav-link" href="${pageContext.request.contextPath}/admin/account">
      <i class="fas fa-fw fa-users"></i>
      <span>Quản lý người dùng</span>
    </a>
  </li>
  <!-- Biểu đồ -->
  <li class="nav-item">
    <a class="nav-link" href="${pageContext.request.contextPath}/charts.jsp">
      <i class="fas fa-fw fa-chart-area"></i>
      <span>Biểu đồ</span>
    </a>
  </li>
  <!-- Bảng -->
  <li class="nav-item">
    <a class="nav-link" href="${pageContext.request.contextPath}/tables.jsp">
      <i class="fas fa-fw fa-table"></i>
      <span>Bảng</span>
    </a>
  </li>
  <hr class="sidebar-divider d-none d-md-block">
  <!-- Nút thu gọn sidebar -->
  <div class="text-center d-none d-md-inline">
    <button class="rounded-circle border-0" id="sidebarToggle"></button>
  </div>
</ul>
<!-- JS Libraries -->
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/admin/js/sb-admin-2.min.js"></script>
</body>
</html>