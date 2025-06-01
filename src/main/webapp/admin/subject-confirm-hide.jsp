<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <title>Xác nhận ẩn môn học</title>
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
        <h1 class="h3 mb-4 text-gray-800">Xác nhận ẩn môn học</h1>
        <div class="card shadow mb-4">
          <div class="card-header py-3">
            <h6 class="m-0 font-weight-bold text-primary">Danh sách học sinh đang học</h6>
          </div>
          <div class="card-body">
            <p>Có học sinh đang học môn học này. Nếu bạn ẩn môn học, các đăng ký sẽ bị hủy và học sinh sẽ được hoàn tiền. Bạn có muốn tiếp tục ẩn không?</p>
            <c:if test="${not empty enrolledStudents}">
              <table class="table table-bordered">
                <thead>
                <tr>
                  <th>ID</th>
                  <th>Tên</th>
                  <th>Ngày sinh</th>
                  <th>Mô tả</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="student" items="${enrolledStudents}">
                  <tr>
                    <td>${student.id}</td>
                    <td>${student.name}</td>
                    <td>${student.birth}</td>
                    <td>${student.description}</td>
                  </tr>
                </c:forEach>
                </tbody>
              </table>
            </c:if>
            <form method="post" action="${pageContext.request.contextPath}/admin/subject">
              <input type="hidden" name="action" value="confirmHide">
              <input type="hidden" name="id" value="${subjectId}">
              <button type="submit" class="btn btn-danger">Xác nhận ẩn môn học</button>
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