<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="model.Tutor" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.Lession" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gia Sư Tốt - Lịch Dạy</title>
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
%>
<!-- Header -->
<header class="gradient-bg text-white shadow-lg">
    <div class="container mx-auto px-4 py-6">
        <div class="flex justify-between items-center">
            <div class="flex items-center space-x-2">
                <i class="fas fa-chalkboard-teacher text-3xl"></i>
                <h1 class="text-2xl font-bold">GIASUTOT</h1>
            </div>
            <nav class="hidden md:flex space-x-6">
                <a href="index.jsp" class="hover:text-blue-200 transition">Trang chủ</a>
                <a href="lessonSchedule.jsp" class="hover:text-blue-200 transition">Lịch dạy</a>
                <a href="#" class="hover:text-blue-200 transition">Học viên</a>
                <a href="#" class="hover:text-blue-200 transition"><%= tutor.getName() %></a>
            </nav>
            <div class="flex items-center space-x-4">
                <div class="relative">
                    <i class="fas fa-bell text-xl cursor-pointer hover:text-blue-200"></i>
                    <span class="absolute -top-1 -right-1 bg-red-500 text-white text-xs rounded-full h-4 w-4 flex items-center justify-center">3</span>
                </div>
                <div class="w-10 h-10 rounded-full bg-white flex items-center justify-center text-blue-600 font-bold cursor-pointer">GT</div>
            </div>
        </div>
    </div>
</header>

<!-- Main Content -->
<main class="container mx-auto px-4 py-8">
    <div class="flex flex-col md:flex-row gap-8">
        <!-- Sidebar -->
        <aside class="w-full md:w-64 bg-white rounded-lg shadow-md p-4 h-fit">
            <div class="mb-6">
                <h2 class="text-lg font-semibold text-gray-700 mb-4">Menu</h2>
                <ul class="space-y-2">
                    <li>
                        <a href="lesson" class="flex items-center space-x-3 p-2 rounded-lg hover:bg-gray-100">
                            <i class="fas fa-plus-circle"></i>
                            <span>Tạo buổi học</span>
                        </a>
                    </li>
                    <li>
                        <a href="lessonSchedule.jsp" class="flex items-center space-x-3 p-2 rounded-lg bg-blue-50 text-blue-600">
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
                        <a href="#" class="flex items-center space-x-3 p-2 rounded-lg hover:bg-gray-100">
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
                    <h2 class="text-xl font-semibold text-gray-800">Danh sách lịch dạy</h2>
                </div>

                <!-- Schedule List -->
                <div class="p-6">
                    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
                        <c:forEach var="lesson" items="${lessons}">
                            <div class="session-card bg-white p-4 rounded-lg shadow-md fade-in">
                                <p><strong>Ngày giờ:</strong> ${lesson.getFormattedTime()}</p>
                                <p><strong>Môn dạy:</strong> ${lesson.getCourse_id()}</p>
                                <p><strong>Học sinh:</strong> ${lesson.getStudent_id()}</p>
                                <div class="flex space-x-2 mt-4">
                                    <button type="button" onclick="openReviewModal('${lesson.getStudent_id()}', '${lesson.getCourse_id()}', '${lesson.getFormattedTime()}')"
                                            class="px-3 py-1 bg-green-500 text-white rounded hover:bg-green-600">Xác nhận hoàn thành</button>
                                    <form action="cancelLesson" method="post">
                                        <input type="hidden" name="studentId" value="${lesson.getStudent_id()}">
                                        <input type="hidden" name="courseId" value="${lesson.getCourse_id()}">
                                        <input type="hidden" name="time" value="${lesson.getFormattedTime()}">
                                        <button type="submit" class="px-3 py-1 bg-red-500 text-white rounded hover:bg-red-600">Hủy</button>
                                    </form>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>

<!-- Review Modal -->
<div id="reviewModal" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 hidden">
    <div class="bg-white rounded-lg p-6 max-w-md w-full mx-4 transform transition-all fade-in">
        <div class="text-center">
            <h3 class="text-xl font-semibold text-gray-800 mb-4">Đánh giá buổi học</h3>
            <form id="reviewForm" action="confirmLesson" method="post">
                <input type="hidden" name="lessonId" id="reviewLessonId">
                <input type="hidden" name="courseId" id="reviewCourseId">
                <input type="hidden" name="time" id="reviewTime">
                <div class="mb-4 text-left">
                    <label for="rating" class="block text-sm font-medium text-gray-700">Điểm đánh giá (1-5):</label>
                    <select id="rating" name="rating" class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring focus:ring-blue-500 focus:ring-opacity-50">
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                    </select>
                </div>
                <div class="mb-4 text-left">
                    <label for="comment" class="block text-sm font-medium text-gray-700">Nhận xét:</label>
                    <textarea id="comment" name="comment" rows="4" class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring focus:ring-blue-500 focus:ring-opacity-50" placeholder="Nhập nhận xét của bạn"></textarea>
                </div>
                <div class="flex justify-end space-x-2">
                    <button type="button" onclick="closeReviewModal()" class="px-4 py-2 bg-gray-300 text-gray-800 rounded hover:bg-gray-400">Hủy</button>
                    <button type="submit" href="confirmLesson"  class="px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700">Gửi đánh giá</button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- JavaScript for Modal -->
<script>
    function openReviewModal(lessonId, courseId, time) {
        document.getElementById('reviewLessonId').value = lessonId;
        document.getElementById('reviewCourseId').value = courseId;
        document.getElementById('reviewTime').value = time;
        document.getElementById('reviewModal').classList.remove('hidden');
    }

    function closeReviewModal() {
        document.getElementById('reviewModal').classList.add('hidden');
        document.getElementById('reviewForm').reset();
    }
</script>
</body>
</html>