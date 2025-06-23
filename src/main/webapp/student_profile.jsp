<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Student" %>
<%@ page import="model.Account" %>
<html>
<head>
    <title>Profile Student</title>
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
    Student student = (Student) request.getAttribute("student");
    Account acc = (Account) session.getAttribute("account");
    int role = (acc != null) ? acc.getRole() : -1;
    boolean isStudent = (role == 1);
%>
<section style="background-color: #eee;">
    <div class="container py-5">
        <!-- Thông báo thành công hoặc lỗi -->
        <% if (session.getAttribute("success") != null) { %>
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            <%= session.getAttribute("success") %>
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
        <% session.removeAttribute("success"); %>
        <% } %>
        <% if (session.getAttribute("error") != null) { %>
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            <%= session.getAttribute("error") %>
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
        <% session.removeAttribute("error"); %>
        <% } %>

        <div class="row">
            <div class="col">
                <nav aria-label="breadcrumb" class="bg-white rounded-3 p-3 mb-4">
                    <ol class="breadcrumb mb-0">
                        <li class="breadcrumb-item"><a href="index.jsp">Trang chủ</a></li>
                        <li class="breadcrumb-item active" aria-current="page">Hồ sơ học viên</li>
                    </ol>
                </nav>
            </div>
        </div>

        <% if (student == null) { %>
        <div class="alert alert-danger" role="alert">
            Không tìm thấy thông tin học viên.
        </div>
        <% } else { %>
        <div class="row">
            <div class="col-lg-4">
                <div class="card mb-4">
                    <div class="card-body text-center">
                        <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava3.webp" alt="avatar"
                             class="rounded-circle img-fluid" style="width: 150px;">
                        <h5 class="my-3"><%= student.getName() %></h5>
                        <% if (isStudent) { %>
                        <div class="d-flex justify-content-center mb-2">
                            <button type="button" class="btn btn-primary me-2" onclick="toggleTutorRequestForm()">Trở thành gia sư</button>
                            <button type="button" class="btn btn-danger" onclick="toggleChangePasswordForm()">Đổi mật khẩu</button>
                        </div>
                        <% } %>

                    </div>
                </div>
            </div>

            <div class="col-lg-8">
                <!-- Thông tin cá nhân -->
                <div class="card mb-4" id="profileInfo">
                    <div class="card-body">
                        <div class="d-flex justify-content-between">
                            <h5>Thông tin cá nhân</h5>
                            <% if (isStudent) { %>
                            <button type="button" class="btn btn-warning btn-sm" onclick="toggleEditForm()">Chỉnh sửa</button>
                            <% } %>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-sm-3"><p class="mb-0">Họ và tên</p></div>
                            <div class="col-sm-9"><p class="text-muted mb-0"><%= student.getName() %></p></div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-sm-3"><p class="mb-0">Ngày sinh</p></div>
                            <div class="col-sm-9"><p class="text-muted mb-0"><%= student.getBirth() %></p></div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-sm-3"><p class="mb-0">Email</p></div>
                            <div class="col-sm-9"><p class="text-muted mb-0"><%= student.getAccount().getEmail() %></p></div>
                        </div>
                    </div>
                </div>

                <!-- Mô tả thêm -->
                <div class="card mb-4" id="profileInfo">
                    <div class="card-body">
                        <h5 class="mb-3">Mô tả thêm</h5>
                        <p class="text-muted"><%= student.getDescription() %></p>
                    </div>
                </div>
            </div>
        </div>

        <!-- Backdrop -->
        <div id="backdrop" class="overlay-backdrop" style="display: none;"></div>
        <% if (isStudent) { %>
        <!-- Form chỉnh sửa dạng overlay -->
        <div id="editForm" class="overlay-form" style="display: none;">
            <h5 class="mb-3">Chỉnh sửa thông tin cá nhân</h5>
            <form action="student" method="post">
                <input type="hidden" name="action" value="update">
                <input type="hidden" name="id_st" value="<%= student.getId() %>">

                <div class="mb-3">
                    <label class="form-label">Họ và tên</label>
                    <input type="text" class="form-control" name="name" value="<%= student.getName() %>">
                </div>
                <div class="mb-3">
                    <label class="form-label">Ngày sinh</label>
                    <input type="date" class="form-control" name="birth" value="<%= student.getBirth() %>">
                </div>
                <div class="mb-3">
                    <label class="form-label">Email</label>
                    <input type="email" class="form-control" name="email" value="<%= student.getAccount().getEmail() %>" readonly>
                </div>
                <div class="mb-3">
                    <label class="form-label">Mô tả</label>
                    <textarea class="form-control" name="describe" rows="3"><%= student.getDescription() %></textarea>
                </div>
                <div class="d-flex justify-content-center">
                    <button type="submit" class="btn btn-success me-2">Lưu thay đổi</button>
                    <button type="button" class="btn btn-secondary" onclick="toggleEditForm()">Hủy</button>
                </div>
            </form>
        </div>


        <!-- Form đổi mật khẩu -->
        <div id="changePasswordForm" class="overlay-form" style="display: none;">
            <h5 class="mb-3">Đổi mật khẩu</h5>
            <form action="reset-password" method="post">
                <input type="hidden" name="source" value="profile"> <!-- Dòng này rất quan trọng -->
                <input type="hidden" name="email" value="<%= acc.getEmail() %>">

                <div class="mb-3">
                    <label class="form-label">Mật khẩu hiện tại</label>
                    <input type="password" class="form-control" name="old-password" required>
                </div>

                <div class="mb-3">
                    <label class="form-label">Mật khẩu mới</label>
                    <input type="password" class="form-control" name="password" required>
                </div>

                <div class="mb-3">
                    <label class="form-label">Xác nhận mật khẩu mới</label>
                    <input type="password" class="form-control" name="confirm-password" required>
                </div>

                <div class="d-flex justify-content-center">
                    <button type="submit" class="btn btn-success me-2">Xác nhận</button>
                    <button type="button" class="btn btn-secondary" onclick="toggleChangePasswordForm()">Hủy</button>
                </div>
            </form>

        </div>



        <!-- Form yêu cầu trở thành gia sư -->
        <div id="tutorRequestForm" class="overlay-form" style="display: none;">
            <h5 class="mb-3">Yêu cầu trở thành gia sư</h5>
            <form action="${pageContext.request.contextPath}/tutor-request" method="post">
                <input type="hidden" name="accountId" value="<%= acc.getId() %>">

                <div class="mb-3">
                    <label class="form-label">Họ và tên</label>
                    <input type="text" class="form-control" name="name" value="<%= student.getName() %>" readonly required>
                </div>

                <div class="mb-3">
                    <label class="form-label">Ngày sinh</label>
                    <input type="date" class="form-control" name="birth" value="<%= student.getBirth() %>" required>
                </div>

                <div class="mb-3">
                    <label class="form-label">Email</label>
                    <input type="email" class="form-control" name="email" value="<%= student.getAccount().getEmail() %>" readonly required>
                </div>

                <div class="mb-3">
                    <label class="form-label">Số điện thoại</label>
                    <input type="text" class="form-control" name="phone" placeholder="Nhập số điện thoại" required>
                </div>

                <div class="mb-3">
                    <label class="form-label">Căn cước công dân (CCCD)</label>
                    <input type="text" class="form-control" name="idCardNumber" placeholder="Nhập số CCCD (12 chữ số)" required>
                </div>

                <div class="mb-3">
                    <label class="form-label">Số tài khoản ngân hàng</label>
                    <input type="text" class="form-control" name="bankAccountNumber" placeholder="Nhập số tài khoản" required>
                </div>

                <div class="mb-3">
                    <label class="form-label">Tên ngân hàng</label>
                    <select class="form-select" name="bankName" required>
                        <option value="" selected disabled>-- Chọn ngân hàng --</option>
                        <option value="BIDV">BIDV</option>
                        <option value="Techcombank">Techcombank</option>
                        <option value="MB">MB</option>
                        <option value="TPBank">TPBank</option>
                        <option value="Sacombank">Sacombank</option>
                    </select>
                </div>

                <div class="mb-3">
                    <label class="form-label">Địa chỉ</label>
                    <input type="text" class="form-control" name="address" placeholder="Nhập địa chỉ" required>
                </div>

                <div class="mb-3">
                    <label class="form-label">Chuyên môn (Môn học muốn dạy)</label>
                    <select class="form-select" name="specialization" required>
                        <option value="" selected disabled>-- Chọn môn --</option>
                        <option value="Toán">Toán</option>
                        <option value="Lý">Lý</option>
                        <option value="Hóa">Hóa</option>
                        <option value="Anh">Anh</option>
                        <option value="Văn">Văn</option>
                    </select>
                </div>

                <div class="mb-3">
                    <label class="form-label">Mô tả thêm</label>
                    <textarea class="form-control" name="description" rows="4" placeholder="Mô tả kinh nghiệm hoặc thông tin bổ sung" required></textarea>
                </div>

                <div class="d-flex justify-content-center">
                    <button type="submit" class="btn btn-success me-2">Gửi yêu cầu</button>
                    <button type="button" class="btn btn-secondary" onclick="toggleTutorRequestForm()">Hủy</button>
                </div>
            </form>

        </div>
        <% } %>
        <% } %>
    </div>
</section>

<script>
    function toggleEditForm() {
        const editForm = document.getElementById("editForm");
        const tutorRequestForm = document.getElementById("tutorRequestForm");
        const profileInfoElements = document.querySelectorAll("#profileInfo");
        const backdrop = document.getElementById("backdrop");

        const isHidden = editForm.style.display === "none" || editForm.style.display === "";

        editForm.style.display = isHidden ? "block" : "none";
        backdrop.style.display = isHidden ? "block" : "none";
        tutorRequestForm.style.display = "none";

        profileInfoElements.forEach(elem => {
            elem.style.display = isHidden ? "none" : "block";
        });
    }

    function toggleTutorRequestForm() {
        const tutorRequestForm = document.getElementById("tutorRequestForm");
        const editForm = document.getElementById("editForm");
        const profileInfoElements = document.querySelectorAll("#profileInfo");
        const backdrop = document.getElementById("backdrop");

        const isHidden = tutorRequestForm.style.display === "none" || tutorRequestForm.style.display === "";

        tutorRequestForm.style.display = isHidden ? "block" : "none";
        backdrop.style.display = isHidden ? "block" : "none";
        editForm.style.display = "none";

        profileInfoElements.forEach(elem => {
            elem.style.display = isHidden ? "none" : "block";
        });
    }

    function toggleChangePasswordForm() {
        const changePasswordForm = document.getElementById("changePasswordForm");
        const editForm = document.getElementById("editForm");
        const tutorRequestForm = document.getElementById("tutorRequestForm");
        const profileInfoElements = document.querySelectorAll("#profileInfo");
        const backdrop = document.getElementById("backdrop");

        const isHidden = changePasswordForm.style.display === "none" || changePasswordForm.style.display === "";

        changePasswordForm.style.display = isHidden ? "block" : "none";
        backdrop.style.display = isHidden ? "block" : "none";

        // Ẩn các form khác nếu đang mở
        editForm.style.display = "none";
        tutorRequestForm.style.display = "none";

        profileInfoElements.forEach(elem => {
            elem.style.display = isHidden ? "none" : "block";
        });
    }


</script>

</body>
</html>