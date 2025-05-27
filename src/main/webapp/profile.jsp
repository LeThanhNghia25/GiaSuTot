<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Tutor" %>
<html>
<head>
  <title>Profile</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" integrity="sha512-Fo3rlrZj/k7ujTUc8a3WY7eE3zC5GfCgAC0zw09Vqx4+l+5a+9TNY+X3qQgk7cA==" crossorigin="anonymous" referrerpolicy="no-referrer" />
  <style>
    .overlay-form {
      position: fixed;
      top: 50%;
      left: 50%;
      transform: translate(-50%, -50%);
      width: 50%;
      max-width: 600px;
      z-index: 1050;
      background-color: white;
      box-shadow: 0 5px 15px rgba(0,0,0,0.3);
      border-radius: 10px;
      max-height: 90vh;
      overflow-y: auto;
      padding: 18px;
    }

    .overlay-backdrop {
      position: fixed;
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
      background-color: rgba(0,0,0,0.5);
      z-index: 1040;
    }
  </style>
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
            <li class="breadcrumb-item"><a href="index.jsp">Trang chủ</a></li>
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
              <button type="button" class="btn btn-primary">Nhắn tin</button>
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
        <!-- Thông tin cá nhân -->
        <div class="card mb-4" id="profileInfo">
          <div class="card-body">
            <div class="d-flex justify-content-between">
              <h5>Thông tin cá nhân</h5>
              <button type="button" class="btn btn-warning btn-sm" onclick="toggleEditForm()">Chỉnh sửa</button>
            </div>
            <hr>
            <div class="row">
              <div class="col-sm-3"><p class="mb-0">Họ và tên</p></div>
              <div class="col-sm-9"><p class="text-muted mb-0"><%= tutor.getName() %></p></div>
            </div>
            <hr>
            <div class="row">
              <div class="col-sm-3"><p class="mb-0">Ngày sinh</p></div>
              <div class="col-sm-9"><p class="text-muted mb-0"><%= tutor.getBirth() %></p></div>
            </div>
            <hr>
            <div class="row">
              <div class="col-sm-3"><p class="mb-0">Email</p></div>
              <div class="col-sm-9"><p class="text-muted mb-0"><%= tutor.getEmail() %></p></div>
            </div>
            <hr>
            <div class="row">
              <div class="col-sm-3"><p class="mb-0">Số điện thoại</p></div>
              <div class="col-sm-9"><p class="text-muted mb-0"><%= tutor.getPhone() %></p></div>
            </div>
            <hr>
            <div class="row">
              <div class="col-sm-3"><p class="mb-0">CCCD</p></div>
              <div class="col-sm-9"><p class="text-muted mb-0"><%= tutor.getCccd() %></p></div>
            </div>
            <hr>
            <div class="row">
              <div class="col-sm-3"><p class="mb-0">Số tài khoản</p></div>
              <div class="col-sm-9"><p class="text-muted mb-0"><%= tutor.getBankCode() %></p></div>
            </div>
            <hr>
            <div class="row">
              <div class="col-sm-3"><p class="mb-0">Tên ngân hàng</p></div>
              <div class="col-sm-9"><p class="text-muted mb-0"><%= tutor.getBankName() %></p></div>
            </div>
            <hr>
            <div class="row">
              <div class="col-sm-3"><p class="mb-0">Địa chỉ</p></div>
              <div class="col-sm-9"><p class="text-muted mb-0"><%= tutor.getAddress() %></p></div>
            </div>
            <hr>
            <div class="row">
              <div class="col-sm-3"><p class="mb-0">Chuyên môn</p></div>
              <div class="col-sm-9"><p class="text-muted mb-0"><%= tutor.getSpecialization() %></p></div>
            </div>
          </div>
        </div>

        <!-- Mô tả thêm -->
        <div class="card mb-4" id="profileInfo">
          <div class="card-body">
            <h5 class="mb-3">Mô tả thêm</h5>
            <p class="text-muted"><%= tutor.getDescribeTutor() %></p>
          </div>
        </div>
      </div>
    </div>

    <!-- Backdrop -->
    <div id="backdrop" class="overlay-backdrop" style="display: none;"></div>

    <!-- Form chỉnh sửa dạng overlay -->
    <div id="editForm" class="overlay-form" style="display: none;">
      <h5 class="mb-3">Chỉnh sửa thông tin cá nhân</h5>
      <form action="tutor" method="post">
        <input type="hidden" name="id_tutor" value="<%= tutor.getId() %>">

        <div class="mb-3">
          <label class="form-label">Họ và tên</label>
          <input type="text" class="form-control" name="name" value="<%= tutor.getName() %>">
        </div>
        <div class="mb-3">
          <label class="form-label">Ngày sinh</label>
          <input type="date" class="form-control" name="birth" value="<%= tutor.getBirth() %>">
        </div>
        <div class="mb-3">
          <label class="form-label">Email</label>
          <input type="email" class="form-control" name="email" value="<%= tutor.getEmail()%>">
        </div>
        <div class="mb-3">
          <label class="form-label">Số điện thoại</label>
          <input type="text" class="form-control" name="phone" value="<%= tutor.getPhone() %>">
        </div>
        <div class="mb-3">
          <label class="form-label">CCCD</label>
          <input type="text" class="form-control" name="cccd" value="<%= tutor.getCccd() %>"readonly>
        </div>
        <div class="mb-3">
          <label class="form-label">Số tài khoản</label>
          <input type="text" class="form-control" name="bank_code" value="<%= tutor.getBankCode() %>">
        </div>
        <div class="mb-3">
          <label class="form-label">Tên ngân hàng</label>
          <select class="form-select" name="bank_name">
            <option value="BIDV" <%= "BIDV".equals(tutor.getBankName()) ? "selected" : "" %>>BIDV</option>
            <option value="Techcombank" <%= "Techcombank".equals(tutor.getBankName()) ? "selected" : "" %>>Techcombank</option>
            <option value="MB" <%= "MB".equals(tutor.getBankName()) ? "selected" : "" %>>MB</option>
            <option value="TPBank" <%= "TPBank".equals(tutor.getBankName()) ? "selected" : "" %>>TPBank</option>
            <option value="Sacombank" <%= "Sacombank".equals(tutor.getBankName()) ? "selected" : "" %>>Sacombank</option>
          </select>
        </div>

        <div class="mb-3">
          <label class="form-label">Địa chỉ</label>
          <input type="text" class="form-control" name="address" value="<%= tutor.getAddress() %>">
        </div>
        <div class="mb-3">
          <label class="form-label">Chuyên môn</label>
          <input type="text" class="form-control" name="specialization" value="<%= tutor.getSpecialization() %>">
        </div>
        <div class="mb-3">
          <label class="form-label">Mô tả</label>
          <textarea class="form-control" name="describe_tutor" rows="3"><%= tutor.getDescribeTutor() %></textarea>
        </div>
        <div class="d-flex justify-content-center">
          <button type="submit" class="btn btn-success me-2">Lưu thay đổi</button>
          <button type="button" class="btn btn-secondary" onclick="toggleEditForm()">Hủy</button>
        </div>

      </form>
    </div>

    <% } %>
  </div>
</section>

<script>
  function toggleEditForm() {
    const editForm = document.getElementById("editForm");
    const profileInfoElements = document.querySelectorAll("#profileInfo");
    const backdrop = document.getElementById("backdrop");

    const isHidden = editForm.style.display === "none" || editForm.style.display === "";

    editForm.style.display = isHidden ? "block" : "none";
    backdrop.style.display = isHidden ? "block" : "none";

    profileInfoElements.forEach(elem => {
      elem.style.display = isHidden ? "none" : "block";
    });
  }
</script>

</body>
</html>
