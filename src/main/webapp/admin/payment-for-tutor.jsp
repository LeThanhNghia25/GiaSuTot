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
                  <th>ID Học viên</th>
                  <th>Tên Học viên</th>
                  <th>Ngày Bắt Đầu</th>
                  <th>Ngày Hoàn Thành</th>
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
                    <td>${course.studentId}</td>
                    <td>${course.studentName}</td>
                    <td>
                      <c:if test="${not empty course.startDate}">
                        <fmt:parseDate value="${course.startDate}" pattern="yyyy-MM-dd" var="parsedStartDate"/>
                        <fmt:formatDate value="${parsedStartDate}" pattern="dd/MM/yyyy"/>
                      </c:if>
                      <c:if test="${empty course.startDate}">
                        Chưa có dữ liệu
                      </c:if>
                    </td>
                    <td>
                      <c:if test="${not empty course.endDate}">
                        <fmt:parseDate value="${course.endDate}" pattern="yyyy-MM-dd" var="parsedEndDate"/>
                        <fmt:formatDate value="${parsedEndDate}" pattern="dd/MM/yyyy"/>
                      </c:if>
                      <c:if test="${empty course.endDate}">
                        Chưa có dữ liệu
                      </c:if>
                    </td>
                    <td>
                      <fmt:setLocale value="vi_VN"/>
                      <fmt:formatNumber value="${course.subject.fee}" type="currency" currencySymbol="₫" groupingUsed="true"/>
                    </td>
                    <td>
                      <fmt:setLocale value="vi_VN"/>
                      <fmt:formatNumber value="${course.subject.fee * 0.7}" type="currency" currencySymbol="₫" groupingUsed="true"/>
                    </td>
                    <td>
                      <form class="payment-form" method="post">
                        <input type="hidden" name="courseId" value="${course.id}">
                        <input type="hidden" name="tutorId" value="${course.tutor.id}">
                        <input type="hidden" name="studentId" value="${course.studentId}">
                        <input type="hidden" name="amount" value="${course.subject.fee * 0.7}">
                        <input type="hidden" name="action" value="showModal">
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

<!-- Modal -->
<div class="modal fade" id="paymentModal" tabindex="-1" role="dialog" aria-labelledby="paymentModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="paymentModalLabel">Xác nhận thanh toán</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">×</span>
        </button>
      </div>
      <div class="modal-body">
        <p><strong>Tên ngân hàng:</strong> <span id="bankName"></span></p>
        <p><strong>Số tài khoản:</strong> <span id="bankAccountNumber"></span></p>
        <p><strong>Số tiền:</strong> <span id="amount"></span></p>
      </div>
      <div class="modal-footer">
        <form id="confirmPaymentForm" action="${pageContext.request.contextPath}/admin/payment" method="post">
          <input type="hidden" name="courseId" id="modalCourseId">
          <input type="hidden" name="tutorId" id="modalTutorId">
          <input type="hidden" name="studentId" id="modalStudentId">
          <input type="hidden" name="amount" id="modalAmount">
          <input type="hidden" name="action" value="confirmPayment">
          <button type="submit" class="btn btn-success">Đã thanh toán</button>
        </form>
        <form id="deferPaymentForm" method="post">
          <input type="hidden" name="courseId" id="modalCourseIdDefer">
          <input type="hidden" name="tutorId" id="modalTutorIdDefer">
          <input type="hidden" name="studentId" id="modalStudentIdDefer">
          <input type="hidden" name="amount" id="modalAmountDefer">
          <input type="hidden" name="action" value="deferPayment">
          <button type="submit" class="btn btn-secondary">Tạm hoãn</button>
        </form>
      </div>
    </div>
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
    // Xử lý form để mở modal
    $('.payment-form').submit(function(e) {
      e.preventDefault();
      var form = $(this);
      $.ajax({
        url: '${pageContext.request.contextPath}/admin/payment',
        type: 'POST',
        data: form.serialize(),
        dataType: 'json',
        success: function(data) {
          $('#bankName').text(data.tutor.bankName || 'Không có dữ liệu');
          $('#bankAccountNumber').text(data.tutor.bankAccountNumber || 'Không có dữ liệu');
          $('#amount').text(new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(data.amount));

          $('#modalCourseId, #modalCourseIdDefer').val(data.courseId);
          $('#modalTutorId, #modalTutorIdDefer').val(data.tutor.id);
          $('#modalStudentId, #modalStudentIdDefer').val(data.studentId);
          $('#modalAmount, #modalAmountDefer').val(data.amount);

          $('#paymentModal').modal('show');
        },
        error: function(xhr, status, error) {
          console.log('AJAX Error - Status: ' + status + ', Error: ' + error);
          console.log('Response: ', xhr.responseText);
          alert('Lỗi khi tải thông tin thanh toán: ' + (xhr.responseText || error));
        }
      });
    });

    // Xử lý form "Tạm hoãn" để không load lại trang
    $('#deferPaymentForm').submit(function(e) {
      e.preventDefault();
      var form = $(this);
      $.ajax({
        url: '${pageContext.request.contextPath}/admin/payment',
        type: 'POST',
        data: form.serialize(),
        dataType: 'json',
        success: function(data) {
          $('#paymentModal').modal('hide'); // Đóng modal sau khi tạm hoãn
        },
        error: function(xhr, status, error) {
          console.log('AJAX Error - Status: ' + status + ', Error: ' + error);
          console.log('Response: ', xhr.responseText);
          alert('Lỗi khi tạm hoãn thanh toán: ' + (xhr.responseText || error));
        }
      });
    });
  });
</script>
</body>
</html>