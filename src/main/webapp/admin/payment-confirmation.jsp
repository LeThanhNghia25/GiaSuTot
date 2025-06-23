<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
    <style>
        .loading {
            display: none;
            position: fixed;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            z-index: 1000;
        }
    </style>
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
                                    <th>Thời gian thanh toán</th>
                                    <th>Hành động</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="payment" items="${pendingPayments}" varStatus="loop">
                                    <tr>
                                        <td>${loop.count}</td>
                                        <td>${payment.courseId}</td>
                                        <td>${payment.studentId}</td>
                                        <td>
                                            <c:if test="${not empty payment.paymentDate}">
                                                <fmt:formatDate value="${payment.paymentDate}" pattern="dd/MM/yyyy HH:mm:ss" />
                                            </c:if>
                                            <c:if test="${empty payment.paymentDate}">
                                                Chưa có
                                            </c:if>
                                        </td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/admin/approve-payment?courseId=${payment.courseId}&studentId=${payment.studentId}"
                                               class="btn btn-success btn-sm">Xác nhận</a>
                                            <a href="${pageContext.request.contextPath}/admin/payment-detail?courseId=${payment.courseId}&studentId=${payment.studentId}"
                                               class="btn btn-info btn-sm ml-2">Xem chi tiết</a>
                                            <a href="#" class="btn btn-danger btn-sm ml-2 cancel-payment"
                                               data-course-id="${payment.courseId}" data-student-id="${payment.studentId}">
                                                Hủy xác nhận
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                                <c:if test="${empty pendingPayments}">
                                    <tr>
                                        <td colspan="5" class="text-center">Không có biên lai nào để hiển thị.</td>
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

<!-- Loading Indicator -->
<div class="loading">
    <div class="spinner-border text-primary" role="status">
        <span class="sr-only">Loading...</span>
    </div>
</div>

<!-- JS Libraries -->
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/admin/js/sb-admin-2.min.js"></script>
<script>
    $(document).ready(function() {
        $('.cancel-payment').on('click', function(e) {
            e.preventDefault();
            if (!confirm('Bạn có chắc muốn hủy xác nhận?')) {
                return;
            }

            var $button = $(this);
            var courseId = $button.data('course-id');
            var studentId = $button.data('student-id');

            $('.loading').show(); // Hiển thị loading

            $.ajax({
                url: '${pageContext.request.contextPath}/admin/cancel-payment',
                type: 'POST',
                data: {
                    courseId: courseId,
                    studentId: studentId
                },
                success: function(response) {
                    alert(response.message);
                    location.reload();
                },
                error: function(xhr, status, error) {
                    alert('Lỗi khi hủy xác nhận: ' + error);
                },
                complete: function() {
                    $('.loading').hide(); // Ẩn loading khi hoàn tất
                }
            });
        });
    });
</script>
</body>
</html>