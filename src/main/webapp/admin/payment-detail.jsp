<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <title>Chi tiết Biên lai - Quản trị</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/admin/vendor/fontawesome-free/css/all.min.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/admin/css/sb-admin-2.css" rel="stylesheet">
</head>
<body id="page-top">
<div id="content-wrapper" class="d-flex flex-column">
  <div id="content">
    <nav class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">
      <button id="sidebarToggleTop" class="btn btn-link d-md-none rounded-circle mr-3">
        <i class="fa fa-bars"></i>
      </button>
      <h1 class="h3 mb-0 text-gray-800">Chi tiết Biên lai</h1>
    </nav>

    <div class="container-fluid">
      <c:if test="${not empty message}">
        <div class="alert alert-info">${message}</div>
      </c:if>

      <div class="card shadow mb-4">
        <div class="card-header py-3">
          <h6 class="m-0 font-weight-bold text-primary">Thông tin Biên lai</h6>
        </div>
        <div class="card-body">
          <div class="row">
            <div class="col-md-12">
              <p><strong>Mã Khóa Học:</strong> ${courseId}</p>
              <p><strong>Học Sinh:</strong> ${studentId}</p>
              <p><strong>Tên File:</strong> ${fileName}</p>
              <p><strong>Đường Dẫn:</strong> ${filePath}</p>
              <p><strong>Ngày thanh toán:</strong> <fmt:formatDate value="${paymentDate}" pattern="dd/MM/yyyy HH:mm:ss" /></p>
            </div>
          </div>
          <a href="${pageContext.request.contextPath}/admin/payment-list" class="btn btn-secondary">Quay lại</a>
        </div>
      </div>
    </div>
  </div>

  <footer class="sticky-footer bg-white">
    <div class="container my-auto">
      <div class="copyright text-center my-auto">
        <span>Copyright © Gia Sư Tốt 2025</span>
      </div>
    </div>
  </footer>
</div>

<a class="scroll-to-top rounded" href="#page-top">
  <i class="fas fa-angle-up"></i>
</a>

<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/admin/js/sb-admin-2.min.js"></script>
</body>
</html>