<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <title>Sửa môn học</title>
  <link href="${pageContext.request.contextPath}/admin/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
  <link href="https://fonts.googleapis.com/css?family=Nunito:200,300,400,700,900" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/admin/css/sb-admin-2.min.css" rel="stylesheet">
</head>
<body id="page-top">
<div id="wrapper">
  <!-- Sidebar -->
  <div id="sidebar">
    <%@ include file="slidebar.jsp" %>
  </div>
  <!-- Content Wrapper -->
  <div id="content-wrapper" class="d-flex flex-column">
    <div id="content">
      <!-- Topbar -->
      <%@ include file="topbar.jsp" %>
      <!-- Main Content -->
      <div class="container-fluid">
        <h1 class="h3 mb-4 text-gray-800">Sửa môn học</h1>
        <c:if test="${not empty error}">
          <div class="alert alert-danger">${error}</div>
        </c:if>
        <div class="card shadow mb-4">
          <div class="card-header py-3">
            <h6 class="m-0 font-weight-bold text-primary">Thông tin môn học</h6>
          </div>
          <div class="card-body">
            <form action="${pageContext.request.contextPath}/admin/subject" method="post">
              <input type="hidden" name="action" value="edit">
              <input type="hidden" name="id" value="${subject.id}">
              <div class="form-group">
                <label for="name">Tên môn học</label>
                <input type="text" class="form-control" id="name" name="name" value="${subject.name}" required>
              </div>
              <div class="form-group">
                <label for="description">Mô tả</label>
                <textarea class="form-control" id="description" name="description" rows="4">${subject.description}</textarea>
              </div>
              <div class="form-group">
                <label for="status">Trạng thái</label>
                <select class="form-control" id="status" name="status">
                  <option value="1" <c:if test="${subject.status == 1}">selected</c:if>>Đang hoạt động</option>
                  <option value="0" <c:if test="${subject.status == 0}">selected</c:if>>Đã ẩn</option>
                </select>
              </div>
              <button type="submit" class="btn btn-primary">Lưu</button>
              <a href="${pageContext.request.contextPath}/admin/subject" class="btn btn-secondary">Hủy</a>
            </form>
          </div>
        </div>
      </div>
    </div>
    <!-- Footer -->
    <%@ include file="footer.jsp" %>
  </div>
</div>
<!-- Scroll to Top Button -->
<a class="scroll-to-top rounded" href="#page-top">
  <i class="fas fa-angle-up"></i>
</a>
<script src="${pageContext.request.contextPath}/admin/vendor/jquery/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/admin/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/admin/vendor/jquery-easing/jquery.easing.min.js"></script>
<script src="${pageContext.request.contextPath}/admin/js/sb-admin-2.min.js"></script>
</body>
</html>