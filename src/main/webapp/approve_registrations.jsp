<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <title>Duyệt đăng ký học sinh - GIASUTOT</title>
  <link href="${pageContext.request.contextPath}/admin/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
  <link href="https://fonts.googleapis.com/css?family=Nunito:200,300,400,700,900" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/admin/css/sb-admin-2.min.css" rel="stylesheet">
</head>
<body id="page-top">
<div id="wrapper">
  <!-- Content Wrapper -->
  <div id="content-wrapper" class="d-flex flex-column">
    <div id="content">
      <!-- Main Content -->
      <div class="container-fluid">
        <h1 class="h3 mb-4 text-gray-800">Duyệt đăng ký học sinh</h1>
        <nav aria-label="breadcrumb" class="mb-4">
          <ol class="breadcrumb">
            <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/index.jsp">Trang chủ</a></li>
            <li class="breadcrumb-item active" aria-current="page">Duyệt đăng ký</li>
          </ol>
        </nav>
        <c:if test="${not empty message}">
          <div class="alert alert-info">${message}</div>
        </c:if>
        <div class="card shadow mb-4">
          <div class="card-header py-3">
            <h6 class="m-0 font-weight-bold text-primary">Danh sách yêu cầu đăng ký</h6>
          </div>
          <div class="card-body">
            <div class="table-responsive">
              <table class="table table-bordered" width="100%" cellspacing="0">
                <thead>
                <tr>
                  <th>Mã khóa học</th>
                  <th>Học sinh</th>
                  <th>Hành động</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="request" items="${pendingRequests}">
                  <tr>
                    <td>${request.courseId}</td>
                    <td>${request.studentId}</td>
                    <td>
                      <form action="${pageContext.request.contextPath}/approve-registrations" method="post">
                        <input type="hidden" name="courseId" value="${request.courseId}">
                        <input type="hidden" name="studentId" value="${request.studentId}">
                        <button type="submit" name="action" value="approve" class="btn btn-sm btn-success">Chấp nhận</button>
                        <button type="submit" name="action" value="reject" class="btn btn-sm btn-danger">Từ chối</button>
                      </form>
                    </td>
                  </tr>
                </c:forEach>
                </tbody>
              </table>
              <c:if test="${empty pendingRequests}">
                <div class="text-center text-muted">Không có yêu cầu nào đang chờ duyệt.</div>
              </c:if>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- Footer -->
    <%@ include file="/footer.jsp" %> <!-- Thay bằng file footer của bạn nếu có -->
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