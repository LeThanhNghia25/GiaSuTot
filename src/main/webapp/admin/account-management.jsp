<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <title>Quản lý người dùng</title>
  <link href="${pageContext.request.contextPath}/admin/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
  <link href="https://fonts.googleapis.com/css?family=Nunito:200,300,400,700,900" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/admin/css/sb-admin-2.min.css" rel="stylesheet">
</head>
<body id="page-top">
<div id="wrapper">
  <!-- Sidebar -->
  <div id="sidebar">
    <%@ include file="slibar.jsp" %>
  </div>
  <!-- Content Wrapper -->
  <div id="content-wrapper" class="d-flex flex-column">
    <div id="content">
      <!-- Topbar -->
      <%@ include file="header.jsp" %>
      <!-- Main Content -->
      <div class="container-fluid">
        <h1 class="h3 mb-4 text-gray-800">Quản lý người dùng</h1>
        <div class="card shadow mb-4">
          <div class="card-header py-3">
            <h6 class="m-0 font-weight-bold text-primary">Danh sách người dùng</h6>
          </div>
          <div class="card-body">
            <div class="table-responsive">
              <table class="table table-bordered" width="100%" cellspacing="0">
                <thead>
                <tr>
                  <th>ID</th>
                  <th>Email</th>
                  <th>Vai trò</th>
                  <th>Trạng thái</th>
                  <th>Hành động</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="account" items="${accounts}">
                  <tr>
                    <td>${account.id}</td>
                    <td>${account.email}</td>
                    <td>
                      <c:choose>
                        <c:when test="${account.role == 0}">
                          Admin
                        </c:when>
                        <c:when test="${account.role == 1}">
                          Học sinh
                        </c:when>
                        <c:when test="${account.role == 2}">
                          Gia sư
                        </c:when>
                        <c:otherwise>
                          Không xác định
                        </c:otherwise>
                      </c:choose>
                    </td>
                    <td>
                      <c:choose>
                        <c:when test="${account.status == 'active'}"> <!-- Đổi từ statusAcc -->
                          <span class="badge badge-success">Đang hoạt động</span>
                        </c:when>
                        <c:otherwise>
                          <span class="badge badge-secondary">Khóa</span>
                        </c:otherwise>
                      </c:choose>
                    </td>
                    <td>
                      <a href="${pageContext.request.contextPath}/admin/account?action=edit&id=${account.id}" class="btn btn-sm btn-primary">Sửa</a>
                      <c:choose>
                        <c:when test="${account.status == 'active'}"> <!-- Đổi từ statusAcc -->
                          <a href="${pageContext.request.contextPath}/admin/account?action=delete&id=${account.id}" class="btn btn-sm btn-danger"
                             onclick="return confirm('Bạn có chắc muốn khóa tài khoản này không?')">Khóa</a>
                        </c:when>
                        <c:otherwise>
                          <a href="${pageContext.request.contextPath}/admin/account?action=restore&id=${account.id}" class="btn btn-sm btn-success"
                             onclick="return confirm('Bạn có chắc muốn mở khóa lại tài khoản này không?')">Mở khóa</a>
                        </c:otherwise>
                      </c:choose>
                    </td>
                  </tr>
                </c:forEach>
                </tbody>
              </table>
              <c:if test="${empty accounts}">
                <div class="text-center text-muted">Không có người dùng nào.</div>
              </c:if>
            </div>
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