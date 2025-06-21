<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Xác nhận Biên lai - Quản trị</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- FontAwesome -->
    <link href="${pageContext.request.contextPath}/admin/vendor/fontawesome-free/css/all.min.css" rel="stylesheet">
    <!-- Custom CSS -->
    <link href="${pageContext.request.contextPath}/admin/css/sb-admin-2.css" rel="stylesheet">
</head>
<body id="page-top">
<div id="wrapper">
    <!-- Sidebar -->
    <div id="sidebar">
        <jsp:include page="slibar.jsp"/>
    </div>

    <!-- Content Wrapper -->
    <div id="content-wrapper" class="d-flex flex-column">
        <!-- Main Content -->
        <div id="content">
            <!-- Topbar -->
            <nav class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">
                <button id="sidebarToggleTop" class="btn btn-link d-md-none rounded-circle mr-3">
                    <i class="fa fa-bars"></i>
                </button>
                <h1 class="h3 mb-0 text-gray-800">Xác nhận Biên lai</h1>
            </nav>
            <!-- End of Topbar -->

            <!-- Begin Page Content -->
            <div class="container-fluid">
                <!-- Page Heading -->
                <h1 class="h3 mb-2 text-gray-800">Danh sách Biên lai Chờ Xác nhận</h1>
                <c:if test="${not empty message}">
                    <div class="alert alert-info">${message}</div>
                </c:if>

                <!-- DataTales Example -->
                <div class="card shadow mb-4">
                    <div class="card-header py-3">
                        <h6 class="m-0 font-weight-bold text-primary">Biên lai</h6>
                    </div>
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                <thead>
                                <tr>
                                    <th>STT</th>
                                    <th>Mã Khóa Học</th>
                                    <th>Học Sinh</th>
                                    <th>Tên File</th>
                                    <th>Đường Dẫn</th>
                                    <th>Hành động</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="payment" items="${pendingPayments}" varStatus="loop">
                                    <tr>
                                        <td>${loop.count}</td>
                                        <td>${payment.courseId}</td>
                                        <td>${payment.studentId}</td>
                                        <td>${payment.fileName != null ? payment.fileName : 'Chưa có'}</td>
                                        <td>${payment.filePath != null ? payment.filePath : 'Chưa có'}</td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/admin/approve-payment?courseId=${payment.courseId}&studentId=${payment.studentId}&fileName=${payment.fileName}&filePath=${payment.filePath}"
                                               class="btn btn-success btn-sm">Xác nhận</a>
                                            <a href="${pageContext.request.contextPath}/admin/payment-detail?courseId=${payment.courseId}&studentId=${payment.studentId}&fileName=${payment.fileName}&filePath=${payment.filePath}"
                                               class="btn btn-info btn-sm ml-2">Xem chi tiết</a>
                                            <a href="#" class="btn btn-danger btn-sm ml-2" onclick="return confirm('Bạn có chắc muốn hủy?');">Hủy xác nhận</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                                <c:if test="${empty pendingPayments}">
                                    <tr>
                                        <td colspan="6" class="text-center">Không có biên lai nào để hiển thị.</td>
                                    </tr>
                                </c:if>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <!-- /.container-fluid -->
        </div>
        <!-- End of Main Content -->

        <!-- Footer -->
        <footer class="sticky-footer bg-white">
            <div class="container my-auto">
                <div class="copyright text-center my-auto">
                    <span>Copyright © Gia Sư Tốt 2025</span>
                </div>
            </div>
        </footer>
    </div>
    <!-- End of Content Wrapper -->
</div>
<!-- End of Wrapper -->

<!-- Scroll to Top Button -->
<a class="scroll-to-top rounded" href="#page-top">
    <i class="fas fa-angle-up"></i>
</a>

<!-- JS Libraries -->
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/admin/js/sb-admin-2.min.js"></script>
</body>
</html>