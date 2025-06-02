<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Tutor" %>
<html>
<head>
  <title>Profile</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" integrity="sha512-Fo3rlrZj/k7ujTUc8a3WY7eE3zC5GfCgAC0zw09Vqx4+l+5a+9TNY+X3qQgk7cA==" crossorigin="anonymous" referrerpolicy="no-referrer" />
</head>
<body>
<%
  Tutor tutor = (Tutor) request.getAttribute("tutor");
%>
<section style="background-color: #eee;">
  <div class="container py-5">
    <div class="row">
      <div class="col">
        <nav aria-label="breadcrumb" class="bg-white rounded-3 p-3 mb-4">
          <ol class="breadcrumb mb-0">
            <li class="breadcrumb-item"><a href="#">Trang chủ</a></li>
            <li class="breadcrumb-item"><a href="#">Gia sư</a></li>
            <li class="breadcrumb-item active" aria-current="page">Hồ sơ gia sư</li>
          </ol>
        </nav>
      </div>
    </div>

    <% if (tutor == null) { %>
    <div class="alert alert-danger" role="alert">
      Không tìm thấy thông tin gia sư.
    </div>
    <% } else { %>
    <div class="row">
      <div class="col-lg-4">
        <div class="card mb-4">
          <div class="card-body text-center">
            <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava3.webp" alt="avatar"
                 class="rounded-circle img-fluid" style="width: 150px;">
            <h5 class="my-3"><%= tutor.getName() %></h5>
            <p class="text-muted mb-1">Gia sư <%= tutor.getSpecialization() %></p>
            <p class="text-muted mb-4"><%= tutor.getAddress() %></p>
            <div class="d-flex justify-content-center mb-2">
              <button type="button" class="btn btn-primary">Theo dõi</button>
              <button type="button" class="btn btn-outline-primary ms-1">Nhắn tin</button>
            </div>
          </div>
        </div>
        <div class="card mb-4 mb-lg-0">
          <div class="card-body p-0">
            <ul class="list-group list-group-flush rounded-3">
              <li class="list-group-item d-flex justify-content-between align-items-center p-3">
                <i class="fas fa-star text-warning"></i>
                <p class="mb-0">Đánh giá:<%= tutor.getEvaluate() %> </p>
              </li>
            </ul>
          </div>
        </div>
      </div>

      <div class="col-lg-8">
        <div class="card mb-4">
          <div class="card-body">
            <div class="row">
              <div class="col-sm-3">
                <p class="mb-0">Họ và tên</p>
              </div>
              <div class="col-sm-9">
                <p class="text-muted mb-0"><%= tutor.getName() %></p>
              </div>
            </div>
            <hr>
            <div class="row">
              <div class="col-sm-3">
                <p class="mb-0">Ngày sinh</p>
              </div>
              <div class="col-sm-9">
                <p class="text-muted mb-0"><%= tutor.getBirth() %></p>
              </div>
            </div>
            <hr>
            <div class="row">
              <div class="col-sm-3">
                <p class="mb-0">Email</p>
              </div>
              <div class="col-sm-9">
                <p class="text-muted mb-0"><%= tutor.getEmail() %></p>
              </div>
            </div>
            <hr>
            <div class="row">
              <div class="col-sm-3">
                <p class="mb-0">Số điện thoại</p>
              </div>
              <div class="col-sm-9">
                <p class="text-muted mb-0"><%= tutor.getPhone() %></p>
              </div>
            </div>
            <hr>
            <div class="row">
              <div class="col-sm-3">
                <p class="mb-0">Địa chỉ</p>
              </div>
              <div class="col-sm-9">
                <p class="text-muted mb-0"><%= tutor.getAddress() %></p>
              </div>
            </div>
            <hr>
            <div class="row">
              <div class="col-sm-3">
                <p class="mb-0">Chuyên môn</p>
              </div>
              <div class="col-sm-9">
                <p class="text-muted mb-0"><%= tutor.getSpecialization() %></p>
              </div>
            </div>
          </div>
        </div>

        <div class="card">
          <div class="card-body">
            <h5 class="mb-3">Mô tả thêm</h5>
            <p class="text-muted">
              <%= tutor.getdescribe_tutor() %>
            </p>
          </div>
        </div>
      </div>
    </div>
    <% } %>
  </div>
</section>
</body>
</html>