<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Quản lý môn dạy</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" integrity="sha512-DTOQO9RWCH3ppGqcWaEA1BIZOC6xxalwEsw9c2QQeAIftl+Vegovlnee1c9QX4TctnWMn13TZye+giMm8e2LwA==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <style>
        .container {
            padding-top: 20px;
            padding-bottom: 20px;
        }
        .card-body {
            background-color: #fff;
            border-radius: 8px;
        }
        .modal-body {
            max-height: 70vh;
            overflow-y: auto;
        }
    </style>
</head>
<body>
<section style="background-color: #eee;">
    <div class="container">
        <div class="row">
            <div class="col">
                <nav aria-label="breadcrumb" class="bg-white rounded-3 p-3 mb-4">
                    <ol class="breadcrumb mb-0">
                        <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/index.jsp">Trang chủ</a></li>
                        <li class="breadcrumb-item active" aria-current="page">Quản lý môn học</li>
                    </ol>
                </nav>
            </div>
        </div>

        <!-- Hiển thị thông báo -->
        <c:if test="${not empty success}">
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                    ${success}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    ${error}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>

        <!-- Danh sách môn học -->
        <div class="card mb-4">
            <div class="card-body">
                <div class="d-flex justify-content-between align-items-center mb-3">
                    <h5 class="mb-0">Danh sách môn học (Active)</h5>
                    <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#registerSubjectModal">
                        <i class="fas fa-plus"></i> Đăng ký môn dạy
                    </button>
                </div>
                <table class="table table-bordered">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Tên môn</th>
                        <th>Cấp độ</th>
                        <th>Mô tả</th>
                        <th>Học phí</th>
                        <th>Buổi học</th>
                        <th>Danh sách học viên/ Buổi học hoàn thành</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="subject" items="${subjects}">
                        <tr>
                            <td>${subject.id}</td>
                            <td>${subject.name}</td>
                            <td>${subject.level}</td>
                            <td>${subject.description}</td>
                            <td>${subject.fee}</td>
                            <td>
                                <c:forEach var="rs" items="${registeredSubjectsMap[subject.id]}">
                                    <c:if test="${rs.status == 'completed'}">
                                        ${rs.number_of_lessons}<br>
                                    </c:if>
                                </c:forEach>
                            </td>
                            <td>
                                <c:forEach var="rs" items="${registeredSubjectsMap[subject.id]}">
                                    <c:if test="${rs.status == 'completed'}">
                                        <c:set var="mapKey" value="${rs.course_id}_${rs.student_id}" />
                                        • <a href="${pageContext.request.contextPath}/student?id=${rs.student.id}">
                                            ${rs.student.name}
                                    </a> /
                                        ${lessonCountsMap[mapKey] != null ? lessonCountsMap[mapKey] : 0} buổi<br>
                                    </c:if>
                                </c:forEach>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty subjects}">
                        <tr>
                            <td colspan="7" class="text-center">Không có môn học active nào.</td>
                        </tr>
                    </c:if>
                    </tbody>
                </table>
            </div>
        </div>

        <!-- Modal Đăng ký môn dạy -->
        <div class="modal fade" id="registerSubjectModal" tabindex="-1" aria-labelledby="registerSubjectModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="registerSubjectModalLabel">Đăng ký môn dạy</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <form id="registerSubjectForm" action="${pageContext.request.contextPath}/registerSubject" method="post">
                            <input type="hidden" name="status" value="inactive">
                            <div class="mb-3">
                                <label for="name" class="form-label">Tên môn học <span class="text-danger">*</span></label>
                                <select class="form-select" id="name" name="name" required>
                                    <option value="" disabled selected>Chọn môn học</option>
                                    <option value="Toán">Toán</option>
                                    <option value="Lý">Lý</option>
                                    <option value="Hóa">Hóa</option>
                                    <option value="Văn">Văn</option>
                                    <option value="Anh">Anh</option>
                                    <option value="Sinh">Sinh</option>
                                    <option value="Sử">Sử</option>
                                    <option value="Địa">Địa</option>
                                </select>
                            </div>
                            <div class="mb-3">
                                <label for="level" class="form-label">Cấp độ <span class="text-danger">*</span></label>
                                <select class="form-select" id="level" name="level" required>
                                    <option value="" disabled selected>Chọn cấp độ</option>
                                    <option value="Lớp 6">Lớp 6</option>
                                    <option value="Lớp 7">Lớp 7</option>
                                    <option value="Lớp 8">Lớp 8</option>
                                    <option value="Lớp 9">Lớp 9</option>
                                    <option value="Lớp 10">Lớp 10</option>
                                    <option value="Lớp 11">Lớp 11</option>
                                    <option value="Lớp 12">Lớp 12</option>
                                </select>
                            </div>
                            <div class="mb-3">
                                <label for="description" class="form-label">Mô tả</label>
                                <textarea class="form-control" id="description" name="description" rows="4" placeholder="Nhập mô tả về môn học"></textarea>
                            </div>
                            <div class="mb-3">
                                <label for="fee" class="form-label">Học phí <span class="text-danger">*</span></label>
                                <select class="form-select" id="fee" name="fee" required>
                                    <option value="" disabled selected>Chọn học phí</option>
                                    <option value="1200000">1.200.000 VND</option>
                                    <option value="1400000">1.400.000 VND</option>
                                    <option value="1600000">1.600.000 VND</option>
                                    <option value="1800000">1.800.000 VND</option>
                                    <option value="2000000">2.000.000 VND</option>
                                    <option value="2200000">2.200.000 VND</option>
                                    <option value="2400000">2.400.000 VND</option>
                                    <option value="2600000">2.600.000 VND</option>
                                    <option value="2800000">2.800.000 VND</option>
                                    <option value="3000000">3.000.000 VND</option>
                                </select>
                            </div>
                            <div class="d-flex justify-content-end">
                                <button type="submit" class="btn btn-success me-2">Gửi đăng ký</button>
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
<script>
    document.getElementById('registerSubjectForm').addEventListener('submit', function(event) {
        const name = document.getElementById('name').value;
        const level = document.getElementById('level').value;
        const fee = document.getElementById('fee').value;

        if (!name) {
            alert('Vui lòng chọn tên môn học.');
            event.preventDefault();
            return;
        }
        if (!level) {
            alert('Vui lòng chọn cấp độ.');
            event.preventDefault();
            return;
        }
        if (!fee) {
            alert('Vui lòng chọn học phí.');
            event.preventDefault();
            return;
        }
    });
</script>
</body>
</html>