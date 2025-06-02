<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Subject" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Quản lý môn học</title>
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
<section style="background-color: #eee;">
    <div class="container py-5">
        <div class="row">
            <div class="col">
                <nav aria-label="breadcrumb" class="bg-white rounded-3 p-3 mb-4">
                    <ol class="breadcrumb mb-0">
                        <li class="breadcrumb-item"><a href="index.jsp">Trang chủ</a></li>
                        <li class="breadcrumb-item active" aria-current="page">Quản lý môn học</li>
                    </ol>
                </nav>
            </div>
        </div>

        <!-- Hiển thị thông báo -->
        <c:if test="${not empty error}">
            <div class="alert alert-danger" role="alert">${error}</div>
        </c:if>
        <c:if test="${not empty success}">
            <div class="alert alert-success" role="alert">${success}</div>
        </c:if>

        <!-- Nút thêm môn học -->
        <div class="mb-4">
            <button type="button" class="btn btn-primary" onclick="toggleAddForm()">Thêm môn học</button>
        </div>

        <!-- Danh sách môn học -->
        <div class="card mb-4">
            <div class="card-body">
                <h5 class="mb-3">Danh sách môn học</h5>
                <table class="table table-bordered">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Tên môn</th>
                        <th>Cấp độ</th>
                        <th>Mô tả</th>
                        <th>Học phí</th>
                        <th>Trạng thái</th>
                        <th>Hành động</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="subject" items="${subjects}">
                        <tr>
                            <td>${subject.idSub}</td>
                            <td>${subject.name}</td>
                            <td>${subject.level}</td>
                            <td>${subject.describeSb}</td>
                            <td>${subject.fee}</td>
                            <td>${subject.statusSub}</td>
                            <td>
                                <button type="button" class="btn btn-warning btn-sm" onclick="showEditForm('${subject.idSub}', '${subject.name}', '${subject.level}', '${subject.describeSb}', '${subject.fee}', '${subject.statusSub}')">Sửa</button>
                                <form action="subjects" method="post" style="display:inline;">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="id_sub" value="${subject.idSub}">
                                    <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('Bạn có chắc muốn xóa môn học này?')">Xóa</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

        <!-- Backdrop -->
        <div id="backdrop" class="overlay-backdrop" style="display: none;"></div>

        <!-- Form thêm môn học -->
        <div id="addForm" class="overlay-form" style="display: none;">
            <h5 class="mb-3">Thêm môn học</h5>
            <form action="subjects" method="post">
                <input type="hidden" name="action" value="add">
                <div class="mb-3">
                    <label class="form-label">Tên môn học</label>
                    <input type="text" class="form-control" name="name" required>
                </div>
                <div class="mb-3">
                    <label class="form-label">Cấp độ</label>
                    <input type="text" class="form-control" name="level" required>
                </div>
                <div class="mb-3">
                    <label class="form-label">Mô tả</label>
                    <textarea class="form-control" name="describe_sb" rows="3"></textarea>
                </div>
                <div class="mb-3">
                    <label class="form-label">Học phí</label>
                    <input type="number" step="0.01" class="form-control" name="fee" required>
                </div>
                <div class="mb-3">
                    <label class="form-label">Trạng thái</label>
                    <select class="form-select" name="status_sub" required>
                        <option value="active">Active</option>
                        <option value="inactive">Inactive</option>
                    </select>
                </div>
                <div class="d-flex justify-content-center">
                    <button type="submit" class="btn btn-success me-2">Lưu</button>
                    <button type="button" class="btn btn-secondary" onclick="toggleAddForm()">Hủy</button>
                </div>
            </form>
        </div>

        <!-- Form sửa môn học -->
        <div id="editForm" class="overlay-form" style="display: none;">
            <h5 class="mb-3">Sửa môn học</h5>
            <form action="subjects" method="post">
                <input type="hidden" name="action" value="update">
                <input type="hidden" name="id_sub" id="edit_id_sub">
                <div class="mb-3">
                    <label class="form-label">Tên môn học</label>
                    <input type="text" class="form-control" name="name" id="edit_name" required>
                </div>
                <div class="mb-3">
                    <label class="form-label">Cấp độ</label>
                    <input type="text" class="form-control" name="level" id="edit_level" required>
                </div>
                <div class="mb-3">
                    <label class="form-label">Mô tả</label>
                    <textarea class="form-control" name="describe_sb" id="edit_describe_sb" rows="3"></textarea>
                </div>
                <div class="mb-3">
                    <label class="form-label">Học phí</label>
                    <input type="number" step="0.01" class="form-control" name="fee" id="edit_fee" required>
                </div>
                <div class="mb-3">
                    <label class="form-label">Trạng thái</label>
                    <select class="form-select" name="status_sub" id="edit_status_sub" required>
                        <option value="active">Active</option>
                        <option value="inactive">Inactive</option>
                    </select>
                </div>
                <div class="d-flex justify-content-center">
                    <button type="submit" class="btn btn-success me-2">Lưu</button>
                    <button type="button" class="btn btn-secondary" onclick="toggleEditForm()">Hủy</button>
                </div>
            </form>
        </div>
    </div>
</section>

<script>
    function toggleAddForm() {
        const addForm = document.getElementById("addForm");
        const backdrop = document.getElementById("backdrop");
        const isHidden = addForm.style.display === "none" || addForm.style.display === "";
        addForm.style.display = isHidden ? "block" : "none";
        backdrop.style.display = isHidden ? "block" : "none";
    }

    function toggleEditForm() {
        const editForm = document.getElementById("editForm");
        const backdrop = document.getElementById("backdrop");
        const isHidden = editForm.style.display === "none" || editForm.style.display === "";
        editForm.style.display = isHidden ? "block" : "none";
        backdrop.style.display = isHidden ? "block" : "none";
    }

    function showEditForm(idSub, name, level, describeSb, fee, statusSub) {
        document.getElementById("edit_id_sub").value = idSub;
        document.getElementById("edit_name").value = name;
        document.getElementById("edit_level").value = level;
        document.getElementById("edit_describe_sb").value = describeSb;
        document.getElementById("edit_fee").value = fee;
        document.getElementById("edit_status_sub").value = statusSub;
        toggleEditForm();
    }
</script>

<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
</body>
</html>