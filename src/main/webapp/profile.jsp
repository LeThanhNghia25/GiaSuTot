<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <head>
    <title>Profile</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Font Awesome (để hiển thị icon như fa-users, fa-star, v.v.) -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" integrity="sha512-Fo3rlrZj/k7ujTUc8a3WY7eE3zC5GfCgAC0zw09Vqx4+l+5a+9TNY+X3q5F6l3q1zMxg8b6E0tJ62CrzQgk7cA==" crossorigin="anonymous" referrerpolicy="no-referrer" />
  </head>

</head>
<body>
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

    <div class="row">
      <div class="col-lg-4">
        <div class="card mb-4">
          <div class="card-body text-center">
            <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava3.webp" alt="avatar"
                 class="rounded-circle img-fluid" style="width: 150px;">
            <h5 class="my-3">Nguyễn Văn A</h5>
            <p class="text-muted mb-1">Gia sư Toán - Lý - Hóa</p>
            <p class="text-muted mb-4">TP. Hồ Chí Minh, Việt Nam</p>
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
                <i class="fas fa-graduation-cap text-primary"></i>
                <p class="mb-0">Đại học Sư phạm TP.HCM</p>
              </li>
              <li class="list-group-item d-flex justify-content-between align-items-center p-3">
                <i class="fas fa-book text-success"></i>
                <p class="mb-0">Kinh nghiệm: 5 năm</p>
              </li>
              <li class="list-group-item d-flex justify-content-between align-items-center p-3">
                <i class="fas fa-users text-info"></i>
                <p class="mb-0">Học viên đã dạy: 120+</p>
              </li>
              <li class="list-group-item d-flex justify-content-between align-items-center p-3">
                <i class="fas fa-star text-warning"></i>
                <p class="mb-0">Đánh giá: 4.8/5</p>
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
                <p class="text-muted mb-0">Nguyễn Văn A</p>
              </div>
            </div>
            <hr>
            <div class="row">
              <div class="col-sm-3">
                <p class="mb-0">Email</p>
              </div>
              <div class="col-sm-9">
                <p class="text-muted mb-0">nguyenvana@giasuonline.vn</p>
              </div>
            </div>
            <hr>
            <div class="row">
              <div class="col-sm-3">
                <p class="mb-0">Số điện thoại</p>
              </div>
              <div class="col-sm-9">
                <p class="text-muted mb-0">0909 123 456</p>
              </div>
            </div>
            <hr>
            <div class="row">
              <div class="col-sm-3">
                <p class="mb-0">Địa chỉ</p>
              </div>
              <div class="col-sm-9">
                <p class="text-muted mb-0">Quận 3, TP. Hồ Chí Minh</p>
              </div>
            </div>
            <hr>
            <div class="row">
              <div class="col-sm-3">
                <p class="mb-0">Chuyên môn</p>
              </div>
              <div class="col-sm-9">
                <p class="text-muted mb-0">Toán lớp 6–12, Lý – Hóa THCS</p>
              </div>
            </div>
          </div>
        </div>

        <!-- Có thể thay bằng danh sách khóa học đã dạy, học sinh đánh giá... -->
        <div class="card">
          <div class="card-body">
            <h5 class="mb-3">Mô tả thêm</h5>
            <p class="text-muted">
              Tôi là gia sư có 5 năm kinh nghiệm giảng dạy học sinh từ lớp 6 đến lớp 12. Phương pháp dạy logic, dễ hiểu, giúp học viên tiến bộ nhanh chóng. Đã từng luyện thi vào 10, thi THPT Quốc gia cho nhiều học viên đạt kết quả cao.
            </p>
          </div>
        </div>

      </div>
    </div>
  </div>
</section>

</body>
</html>
