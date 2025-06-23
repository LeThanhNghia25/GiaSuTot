<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="model.Tutor" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gia Sư Tốt - Tạo Buổi Học</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        .gradient-bg {
            background: linear-gradient(135deg, #6B73FF 0%, #000DFF 100%);
        }
        .session-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
        }
        .floating-btn {
            box-shadow: 0 4px 15px rgba(0, 13, 255, 0.3);
        }
        input:focus, select:focus, textarea:focus {
            border-color: #6B73FF !important;
        }
        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(10px); }
            to { opacity: 1; transform: translateY(0); }
        }
        .fade-in {
            animation: fadeIn 0.3s ease-out forwards;
        }
    </style>
</head>
<body class="bg-gray-50 font-sans">
<%
    Tutor tutor = (Tutor) request.getAttribute("tutor");
    List<String> listIdStudent = (List<String>) request.getAttribute("StIDList");
%>

<!-- Header -->
<%@ include file="header.jsp" %>
<!-- Header End -->

<!-- Main Content -->
<main class="container mx-auto px-4 py-8">
    <div class="flex flex-col md:flex-row gap-8">
        <!-- Sidebar -->
        <aside class="w-full md:w-64 bg-white rounded-lg shadow-md p-4 h-fit">
            <div class="mb-6">
                <h2 class="text-lg font-semibold text-gray-700 mb-4">Menu</h2>
                <ul class="space-y-2">
                    <li>
                        <a href="${pageContext.request.contextPath}/lesson" class="flex items-center space-x-3 p-2 rounded-lg bg-blue-50 text-blue-600">
                            <i class="fas fa-plus-circle"></i>
                            <span>Tạo buổi học</span>
                        </a>
                    </li>
                    <li>
                        <a href="schedule" class="flex items-center space-x-3 p-2 rounded-lg hover:bg-gray-100">
                            <i class="fas fa-calendar-alt"></i>
                            <span>Lịch dạy</span>
                        </a>
                    </li>
                    <li>
                        <a href="#" class="flex items-center space-x-3 p-2 rounded-lg hover:bg-gray-100">
                            <i class="fas fa-users"></i>
                            <span>Học viên</span>
                        </a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/tutor-revenue-data" class="flex items-center space-x-3 p-2 rounded-lg hover:bg-gray-100">
                            <i class="fas fa-chart-line"></i>
                            <span>Thống kê</span>
                        </a>
                    </li>
                    <li>
                        <a href="#" class="flex items-center space-x-3 p-2 rounded-lg hover:bg-gray-100">
                            <i class="fas fa-cog"></i>
                            <span>Cài đặt</span>
                        </a>
                    </li>
                </ul>
            </div>
            <div class="p-4 bg-blue-50 rounded-lg">
                <h3 class="font-medium text-blue-800 mb-2">Hỗ trợ</h3>
                <p class="text-sm text-gray-600 mb-3">Bạn cần trợ giúp? Liên hệ với chúng tôi qua:</p>
                <div class="flex space-x-2">
                    <a href="#" class="w-8 h-8 rounded-full bg-blue-100 text-blue-600 flex items-center justify-center">
                        <i class="fab fa-facebook-messenger"></i>
                    </a>
                    <a href="#" class="w-8 h-8 rounded-full bg-blue-100 text-blue-600 flex items-center justify-center">
                        <i class="fas fa-phone-alt"></i>
                    </a>
                    <a href="#" class="w-8 h-8 rounded-full bg-blue-100 text-blue-600 flex items-center justify-center">
                        <i class="fas fa-envelope"></i>
                    </a>
                </div>
            </div>
        </aside>

        <!-- Main Panel -->
        <div class="flex-1">
            <div class="bg-white rounded-lg shadow-md overflow-hidden">
                <!-- Panel Header -->
                <div class="border-b border-gray-200 px-6 py-4">
                    <div class="flex justify-between items-center">
                        <h2 class="text-xl font-semibold text-gray-800">Tạo buổi học mới</h2>
                        <button class="text-blue-600 hover:text-blue-800">
                            <i class="fas fa-history"></i>
                            <span class="ml-1">Lịch sử buổi học</span>
                        </button>
                    </div>
                </div>

                <!-- Form Section -->
                <div class="p-6">
                    <form id="sessionForm" action="addLesson" method="post" class="space-y-6">
                        <!-- Student Info -->
                        <div class="fade-in">
                            <h3 class="text-lg font-medium text-gray-700 mb-4 flex items-center">
                                <i class="fas fa-user-graduate mr-2 text-blue-500"></i>
                                Thông tin học viên
                            </h3>
                            <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                                <div>
                                    <label for="idStudent" class="block text-sm font-medium text-gray-700 mb-1">Mã học viên</label>
                                    <select id="idStudent" name="idStudent"
                                            class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-200 focus:border-blue-500 transition" required>
                                        <option value="" disabled selected>Chọn mã học viên</option>
                                        <c:forEach var="idst" items="${StIDList}">
                                            <option value="${idst}">${idst}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div>
                                    <label for="studentName" class="block text-sm font-medium text-gray-700 mb-1">Tên học viên</label>
                                    <input type="text" id="studentName" name="studentName"
                                           class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-200 focus:border-blue-500 transition"
                                           placeholder="Tên học viên" readonly>
                                </div>
                            </div>
                        </div>

                        <!-- Session Details -->
                        <div class="fade-in">
                            <h3 class="text-lg font-medium text-gray-700 mb-4 flex items-center">
                                <i class="fas fa-book-open mr-2 text-blue-500"></i>
                                Thông tin buổi học
                            </h3>
                            <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                                <div>
                                    <label for="subject" class="block text-sm font-medium text-gray-700 mb-1">Mã Khóa học</label>
                                    <select id="course_id" name="course_id"
                                            class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-200 focus:border-blue-500 transition" required>
                                        <option value="" disabled selected>Chọn môn học</option>
                                        <c:forEach var="course" items="${idCourse}">
                                            <option value="${course.getId()}">${course.getId()}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div>
                                    <label for="duration" class="block text-sm font-medium text-gray-700 mb-1">Tên môn học</label>
                                    <input type="text" id="subject" name="subject"
                                           class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-200 focus:border-blue-500 transition" required>
                                </div>
                            </div>
                        </div>

                        <!-- Date & Time -->
                        <div class="fade-in">
                            <h3 class="text-lg font-medium text-gray-700 mb-4 flex items-center">
                                <i class="far fa-clock mr-2 text-blue-500"></i>
                                Thời gian học
                            </h3>
                            <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                                <div>
                                    <label for="date" class="block text-sm font-medium text-gray-700 mb-1">Ngày học</label>
                                    <input type="date" id="date" name="date"
                                           class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-200 focus:border-blue-500 transition" required>
                                </div>
                                <div>
                                    <label for="time" class="block text-sm font-medium text-gray-700 mb-1">Giờ học</label>
                                    <input type="time" id="time" name="time"
                                           class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-200 focus:border-blue-500 transition" required>
                                </div>
                            </div>
                        </div>

                        <!-- Additional Notes -->
                        <div class="fade-in">
                            <h3 class="text-lg font-medium text-gray-700 mb-4 flex items-center">
                                <i class="fas fa-sticky-note mr-2 text-blue-500"></i>
                                Ghi chú
                            </h3>
                            <textarea id="notes" name="notes" rows="3"
                                      class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-200 focus:border-blue-500 transition"
                                      placeholder="Nhập ghi chú về buổi học (nếu có)"></textarea>
                        </div>

                        <!-- Form Actions -->
                        <div class="flex justify-end space-x-4 pt-4">
                            <button type="reset" class="px-6 py-2 border border-gray-300 rounded-lg text-gray-700 hover:bg-gray-100 transition">
                                <i class="fas fa-redo mr-2"></i>Nhập lại
                            </button>
                            <button type="submit" class="px-6 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition flex items-center">
                                <i class="fas fa-save mr-2"></i>Lưu buổi học
                            </button>
                        </div>
                    </form>
                </div>
            </div>

            <!-- Recent Sessions -->
            <div class="mt-8">
                <h3 class="text-lg font-semibold text-gray-800 mb-4 flex items-center">
                    <i class="fas fa-history mr-2 text-blue-500"></i>
                    Buổi học gần đây
                </h3>
                <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4" id="recentSessions">
                    <!-- Sessions will be added here by JavaScript -->
                </div>
            </div>
        </div>
    </div>
</main>

<!-- Floating Action Button -->
<button id="quickAddBtn" class="fixed bottom-8 right-8 w-14 h-14 rounded-full gradient-bg text-white flex items-center justify-center floating-btn shadow-lg transition hover:scale-110">
    <i class="fas fa-plus text-xl"></i>
</button>

<!-- Modal -->
<div id="successModal" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 hidden">
    <div class="bg-white rounded-lg p-6 max-w-md w-full mx-4 transform transition-all fade-in">
        <div class="text-center">
            <div class="w-16 h-16 bg-green-100 rounded-full flex items-center justify-center mx-auto mb-4">
                <i class="fas fa-check text-green-600 text-2xl"></i>
            </div>
            <h3 class="text-xl font-semibold text-gray-800 mb-2">Thành công!</h3>
            <p class="text-gray-600 mb-6" id="modalMessage">Buổi học đã được tạo thành công.</p>
            <button id="closeModal" class="px-6 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition">
                Đóng
            </button>
        </div>
    </div>
</div>

<!-- JavaScript -->
<script>
    // 1. Xử lý chọn học viên
    document.getElementById('idStudent').addEventListener('change', function () {
        const studentId = this.value;
        if (studentId) {
            console.log('Sending AJAX request for studentId: ' + studentId);
            fetch('${pageContext.request.contextPath}/lesson', {
                method: 'POST',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                body: 'studentId=' + encodeURIComponent(studentId)
            })
                .then(response => {
                    console.log('Response status: ' + response.status);
                    if (!response.ok) throw new Error('Network response was not ok: ' + response.statusText);
                    return response.json();
                })
                .then(data => {
                    console.log('Received data: ', data);
                    if (data.status === 'success') {
                        document.getElementById('studentName').value = data.studentName || '';
                        document.getElementById('subject').value = data.subject || '';
                    } else {
                        alert('Không tìm thấy thông tin học viên: ' + data.message);
                        document.getElementById('studentName').value = '';
                        document.getElementById('subject').value = '';
                    }
                })
                .catch(error => {
                    console.error('Error fetching student info:', error);
                    alert('Đã xảy ra lỗi khi lấy thông tin học viên: ' + error.message);
                    document.getElementById('studentName').value = '';
                    document.getElementById('subject').value = '';
                });
        } else {
            document.getElementById('studentName').value = '';
            document.getElementById('subject').value = '';
        }
    });

    // 2. Xử lý chọn khóa học
    document.getElementById('course_id').addEventListener('change', function () {
        const course_id = this.value;
        if (course_id) {
            console.log('Sending AJAX request for course: ' + course_id);
            fetch('${pageContext.request.contextPath}/lesson2', {
                method: 'POST',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                body: 'course_id=' + encodeURIComponent(course_id)
            })
                .then(response => {
                    console.log('Response status: ' + response.status);
                    if (!response.ok) throw new Error('Network response was not ok: ' + response.statusText);
                    return response.json();
                })
                .then(data => {
                    console.log('Received data: ', data);
                    if (data.status === 'success') {
                        document.getElementById('subject').value = data.subject || '';
                    } else {
                        alert('Không tìm thấy thông tin khóa học: ' + data.message);
                        document.getElementById('course_id').value = '';
                    }
                })
                .catch(error => {
                    console.error('Error fetching course info:', error);
                    alert('Đã xảy ra lỗi khi lấy thông tin khóa học: ' + error.message);
                    document.getElementById('subject').value = '';
                });
        } else {
            document.getElementById('subject').value = '';
        }
    });

    // 3. Xử lý ngày và giờ hiện tại
    const now = new Date();
    now.setMinutes(now.getMinutes() - now.getTimezoneOffset() + 7 * 60); // Điều chỉnh múi giờ +07:00
    const today = now.toISOString().split('T')[0];
    const currentTime = now.toISOString().split('T')[1].substring(0, 5);

    document.getElementById('date').setAttribute('min', today);

    // 4. Kiểm tra ngày
    document.getElementById('date').addEventListener('change', function () {
        const selectedDate = new Date(this.value);
        const todayDate = new Date(today);
        if (selectedDate < todayDate) {
            alert('Không thể chọn ngày trong quá khứ!');
            this.value = today;
            document.getElementById('time').value = '';
            document.getElementById('time').removeAttribute('min');
        } else if (selectedDate.toDateString() === todayDate.toDateString()) {
            document.getElementById('time').setAttribute('min', currentTime);
        } else {
            document.getElementById('time').removeAttribute('min');
        }
    });

    // 5. Kiểm tra giờ
    document.getElementById('time').addEventListener('change', function () {
        const selectedDate = new Date(document.getElementById('date').value);
        const todayDate = new Date(today);
        if (selectedDate.toDateString() === todayDate.toDateString()) {
            const selectedTime = this.value;
            if (selectedTime < currentTime) {
                alert('Không thể chọn giờ trong quá khứ!');
                this.value = currentTime;
            }
        }
    });

    // 6. Xử lý submit form
    document.getElementById('sessionForm').addEventListener('submit', function(event) {
        event.preventDefault();
        const formData = new FormData(this);
        const datetimeStr = formData.get('date') + ' ' + formData.get('time') + ':00';

        fetch('${pageContext.request.contextPath}/addLesson', {
            method: 'POST',
            body: new URLSearchParams(formData).toString(),
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
        })
            .then(response => {
                if (!response.ok) throw new Error('Network response was not ok');
                return response.json();
            })
            .then(data => {
                console.log('Response data: ', data);
                if (data.status === 'success') {
                    document.getElementById('modalMessage').textContent = data.message;
                    document.getElementById('successModal').classList.remove('hidden');
                    setTimeout(() => {
                        document.getElementById('successModal').classList.add('hidden');
                        this.reset();
                        window.location.href = 'schedule';
                    }, 2000);
                } else {
                    alert(data.message);
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('Đã xảy ra lỗi khi tạo buổi học: ' + error.message);
            });
    });

    // 7. Đóng modal
    document.getElementById('closeModal').addEventListener('click', function() {
        document.getElementById('successModal').classList.add('hidden');
    });
</script>
</body>
</html>