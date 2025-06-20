<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="utf-8">
  <title>Thông Tin Thanh Toán</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
  <h2>Thông Tin Thanh Toán</h2>
  <c:if test="${not empty message}">
    <div class="alert alert-info">${message}</div>
  </c:if>
  <p>Cảm ơn bạn đã đăng ký khóa học: <strong>${course.subject.name}</strong> (Mã: ${course.id})</p>
  <p>Số tiền cần thanh toán: <strong>${amount} VND</strong></p>
  <h4>Thông tin tài khoản nhận tiền:</h4>
  <ul>
    <li><strong>Ngân hàng:</strong> Ngân hàng BIDV</li>
    <li><strong>Số tài khoản:</strong> 3144064829</li>
    <li><strong>Chủ tài khoản:</strong> Lê Thành Nghĩa</li>
    <li><strong>Nội dung chuyển khoản:</strong> Thanh toán khóa học ${course.id} - ${studentId}</li>
  </ul>
  <p>Hoặc quét mã QR:</p>
  <p><em><img src="img/bank.jpg" alt="" style="object-fit: cover;"></em></p>

  <h4>Gửi biên lai:</h4>
  <p>Vui lòng gửi ảnh biên lai chuyển khoản qua email: <a href="lethanhnghia0938@gmail.com">lethanhnghia0938@gmail.com</a> hoặc điền form dưới đây. Sau khi gửi, admin sẽ kiểm tra thủ công.</p>
  <form action="${pageContext.request.contextPath}/submit-receipt" method="post" enctype="multipart/form-data">
    <input type="hidden" name="courseId" value="${course.id}">
    <input type="hidden" name="studentId" value="${studentId}">
    <div class="mb-3">
      <label for="receipt" class="form-label">Tải lên biên lai:</label>
      <input type="file" class="form-control" id="receipt" name="receipt" accept="image/*" required>
    </div>
    <button type="submit" class="btn btn-primary">Gửi Biên Lai</button>
  </form>

  <a href="${pageContext.request.contextPath}/courses" class="btn btn-secondary mt-2">Quay lại</a>
</div>
</body>
</html>