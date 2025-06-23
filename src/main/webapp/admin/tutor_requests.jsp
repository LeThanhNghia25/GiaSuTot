<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Yêu cầu trở thành gia sư</title>
    <link href="${pageContext.request.contextPath}/admin/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Nunito:200,300,400,700,900" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/admin/css/sb-admin-2.min.css" rel="stylesheet">
    <style>
        .collapse-content {
            padding: 15px;
            display: none;
        }
        .collapse-content.show {
            display: block;
        }
        .btn-group .btn {
            margin-right: 5px;
        }
    </style>
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
                <h1 class="h3 mb-4 text-gray-800">Danh sách yêu cầu trở thành gia sư</h1>
                <c:if test="${not empty param.success}">
                    <div class="alert alert-success alert-dismissible fade show" role="alert">
                            ${param.success}
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                            <span aria-hidden="true">×</span>
                        </button>
                    </div>
                </c:if>
                <c:if test="${not empty param.error}">
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                            ${param.error}
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                            <span aria-hidden="true">×</span>
                        </button>
                    </div>
                </c:if>
                <div class="card shadow mb-4">
                    <div class="card-header py-3">
                        <h6 class="m-0 font-weight-bold text-primary">Yêu cầu gia sư</h6>
                    </div>
                    <div class="card-body">
                        <div class="table-responsive">
                            <c:choose>
                                <c:when test="${empty requests}">
                                    <p class="text-muted">Không có yêu cầu gia sư nào.</p>
                                </c:when>
                                <c:otherwise>
                                    <table class="table table-bordered table-hover" id="dataTable" width="100%" cellspacing="0">
                                        <thead>
                                        <tr>
                                            <th>Tên</th>
                                            <th>Email</th>
                                            <th>Ngày sinh</th>
                                            <th>CCCD</th>
                                            <th>Ngân hàng</th>
                                            <th>Thao tác</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach var="request" items="${requests}">
                                            <tr>
                                                <td>${request.name}</td>
                                                <td>${request.email}</td>
                                                <td>${request.birth}</td>
                                                <td>${request.idCardNumber}</td>
                                                <td>${request.bankName}</td>
                                                <td>
                                                    <a href="javascript:void(0)" class="btn btn-primary btn-sm toggle-details" data-target="collapse${request.id}">
                                                        <i class="fas fa-eye"></i> <span>Xem</span>
                                                    </a>
                                                    <div class="collapse-content" id="collapse${request.id}">
                                                        <div class="card card-body">
                                                            <p><strong>SĐT:</strong> ${request.phone}</p>
                                                            <p><strong>Địa chỉ:</strong> ${request.address}</p>
                                                            <p><strong>Chuyên môn:</strong> ${request.specialization}</p>
                                                            <p><strong>Mô tả:</strong> ${request.description}</p>
                                                            <div class="btn-group">
                                                                <form action="tutor-request-action" method="post" class="d-inline">
                                                                    <input type="hidden" name="requestId" value="${request.id}">
                                                                    <input type="hidden" name="action" value="approve">
                                                                    <button type="submit" class="btn btn-success btn-sm"><i class="fas fa-check"></i> Duyệt</button>
                                                                </form>
                                                                <form action="tutor-request-action" method="post" class="d-inline">
                                                                    <input type="hidden" name="requestId" value="${request.id}">
                                                                    <input type="hidden" name="action" value="reject">
                                                                    <button type="submit" class="btn btn-danger btn-sm"><i class="fas fa-times"></i> Từ chối</button>
                                                                </form>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </c:otherwise>
                            </c:choose>
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
<script>
    $(document).ready(function() {
        $('.toggle-details').on('click', function() {
            var $this = $(this);
            var targetId = $this.data('target');
            var $target = $('#' + targetId);
            var isVisible = $target.hasClass('show');

            // Toggle visibility
            $target.toggleClass('show', !isVisible);

            // Update button text and icon
            $this.find('i').toggleClass('fa-eye fa-eye-slash');
            $this.find('span').text(isVisible ? 'Xem' : 'Đóng');
        });
    });
</script>
</body>
</html>