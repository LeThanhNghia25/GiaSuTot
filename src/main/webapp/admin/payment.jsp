<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <title>Thanh toán cho gia sư</title>
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
        <h1 class="h3 mb-4 text-gray-800">Thanh toán cho gia sư</h1>
        <c:if test="${not empty error}">
          <div class="alert alert-danger">${error}</div>
        </c:if>
        <c:if test="${not empty success}">
          <div class="alert alert-success">${success}</div>
        </c:if>
        <div class="card shadow mb-4">
          <div class="card-header py-3">
            <h6 class="m-0 font-weight-bold text-primary">Danh sách khóa học đã hoàn thành</h6>
          </div>
          <div class="card-body">
            <div class="table-responsive">
              <table class="table table-bordered" width="100%" cellspacing="0">
                <thead>
                <tr>
                  <th>Mã khóa học</th>
                  <th>Tên môn học</th>
                  <th>Cấp độ</th>
                  <th>Tên gia sư</th>
                  <th>Giá tiền (VND)</th>
                  <th>Mức lương (70%)</th>
                  <th>Thanh toán</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="course" items="${completedCourses}">
                  <tr>
                    <td>${course.id}</td>
                    <td>${course.subject.name}</td>
                    <td>${course.subject.level}</td>
                    <td>${course.tutor.name}</td>
                    <td>
                      <fmt:setLocale value="vi_VN"/>
                      <fmt:formatNumber value="${course.subject.fee}" type="currency" currencySymbol="₫" groupingUsed="true"/>
                    </td>
                    <td>
                      <fmt:setLocale value="vi_VN"/>
                      <fmt:formatNumber value="${course.subject.fee * 0.7}" type="currency" currencySymbol="₫" groupingUsed="true"/>
                    </td>
                    <td>
                      <form action="${pageContext.request.contextPath}/admin/payment" method="post">
                        <input type="hidden" name="courseId" value="${course.id}">
                        <input type="hidden" name="tutorId" value="${course.tutor.id}">
                        <input type="hidden" name="amount" value="${course.subject.fee * 0.7}">
                        <button type="submit" class="btn btn-primary btn-sm">Thanh toán</button>
                      </form>
                    </td>
                  </tr>
                </c:forEach>
                </tbody>
              </table>
              <c:if test="${empty completedCourses}">
                <div class="text-center text-muted">Không có khóa học nào đã hoàn thành hoặc lỗi: ${completedCourses}</div>
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