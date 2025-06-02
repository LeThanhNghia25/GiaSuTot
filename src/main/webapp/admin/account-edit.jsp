<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Chỉnh sửa tài khoản</title>
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
            <%@ include file="header.jsp" %>
            <!-- Main Content -->
            <div class="container-fluid">
                <h1 class="h3 mb-4 text-gray-800">Chỉnh sửa tài khoản</h1>
                <c:if test="${not empty error}">
                    <div class="alert alert-danger">${error}</div>
                </c:if>
                <div class="card shadow mb-4">
                    <div class="card-header py-3">
                        <h6 class="m-0 font-weight-bold text-primary">Thông tin tài khoản</h6>
                    </div>
                    <div class="card-body">
                        <form method="post" action="${pageContext.request.contextPath}/admin/account">
                            <input type="hidden" name="action" value="edit">
                            <input type="hidden" name="id" value="${account.id}"> <!-- Đổi từ id_acc -->
                            <div class="form-group">
                                <label for="email">Email</label>
                                <input type="email" class="form-control" id="email" name="email" value="${account.email}" required>
                            </div>
                            <div class="form-group">
                                <label for="password">Mật khẩu (để trống nếu không muốn thay đổi)</label>
                                <input type="password" class="form-control" id="password" name="password" placeholder="Nhập mật khẩu mới nếu muốn thay đổi">
                            </div>
                            <div class="form-group">
                                <label for="role">Vai trò</label>
                                <select class="form-control" id="role" name="role">
                                    <option value="0" <c:if test="${account.role == 0}">selected</c:if>>Admin</option>
                                    <option value="1" <c:if test="${account.role == 1}">selected</c:if>>Học sinh</option>
                                    <option value="2" <c:if test="${account.role == 2}">selected</c:if>>Gia sư</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="status">Trạng thái</label>
                                <select class="form-control" id="status" name="status">
                                    <option value="active" <c:if test="${account.status == 'active'}">selected</c:if>>Đang hoạt động</option> <!-- Đổi từ statusAcc -->
                                    <option value="inactive" <c:if test="${account.status == 'inactive'}">selected</c:if>>Khóa</option> <!-- Đổi từ statusAcc -->
                                </select>
                            </div>
                            <button type="submit" class="btn btn-primary">Lưu thay đổi</button>
                            <a href="${pageContext.request.contextPath}/admin/account" class="btn btn-secondary">Hủy</a>
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