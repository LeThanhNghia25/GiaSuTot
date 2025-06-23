<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="model.Tutor" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gia Sư Tốt - Thống kê Doanh thu</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <style>
        .gradient-bg {
            background: linear-gradient(135deg, #6B73FF 0%, #000DFF 100%);
        }
        .chart-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
        }
        .floating-btn {
            box-shadow: 0 4px 15px rgba(0, 13, 255, 0.3);
        }
        input:focus, select:focus {
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
    Tutor tutor = (Tutor) request.getSession().getAttribute("tutor");
    if (tutor == null) {
        response.sendRedirect(request.getContextPath() + "/account?action=login");
        return;
    }
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
                        <a href="${pageContext.request.contextPath}/lesson" class="flex items-center space-x-3 p-2 rounded-lg hover:bg-blue-50 text-blue-600">
                            <i class="fas fa-plus-circle"></i>
                            <span>Tạo buổi học</span>
                        </a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/schedule" class="flex items-center space-x-3 p-2 rounded-lg hover:bg-gray-100">
                            <i class="fas fa-calendar-alt"></i>
                            <span>Lịch dạy</span>
                        </a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/students" class="flex items-center space-x-3 p-2 rounded-lg hover:bg-gray-100">
                            <i class="fas fa-users"></i>
                            <span>Học viên</span>
                        </a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/tutor-revenue" class="flex items-center space-x-3 p-2 rounded-lg bg-blue-50 text-blue-600">
                            <i class="fas fa-chart-line"></i>
                            <span>Thống kê</span>
                        </a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/settings" class="flex items-center space-x-3 p-2 rounded-lg hover:bg-gray-100">
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
            <div class="bg-white rounded-lg shadow-md overflow-hidden chart-card fade-in">
                <!-- Panel Header -->
                <div class="border-b border-gray-200 px-6 py-4">
                    <div class="flex justify-between items-center">
                        <h2 class="text-xl font-semibold text-gray-800">Thống kê Doanh thu</h2>
                        <div class="flex items-center space-x-4">
                            <form action="${pageContext.request.contextPath}/tutor-revenue" method="get">
                                <label for="yearFilter" class="text-sm font-medium text-gray-700">Năm:</label>
                                <select id="yearFilter" name="year" class="px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-200 focus:border-blue-500 transition" onchange="this.form.submit()">
                                    <option value="2023" ${param.year == '2023' ? 'selected' : ''}>2023</option>
                                    <option value="2024" ${param.year == '2024' ? 'selected' : ''}>2024</option>
                                    <option value="2025" ${param.year == '2025' ? 'selected' : ''} selected>2025</option>
                                </select>
                            </form>
                        </div>
                    </div>
                </div>

                <!-- Chart Section -->
                <div class="p-6">
                    <div class="relative h-96">
                        <canvas id="myBarChart"></canvas>
                    </div>
                    <script>
                        var monthlyRevenue = ${monthlyRevenue};
                        var revenueData = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
                        for (var month in monthlyRevenue) {
                            var monthIndex = parseInt(month) - 1;
                            revenueData[monthIndex] = monthlyRevenue[month] || 0;
                        }

                        var ctx = document.getElementById('myBarChart').getContext('2d');
                        new Chart(ctx, {
                            type: 'bar',
                            data: {
                                labels: ["Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12"],
                                datasets: [{
                                    label: "Doanh thu (VNĐ)",
                                    backgroundColor: "rgba(78, 115, 223, 0.8)",
                                    hoverBackgroundColor: "rgba(78, 115, 223, 1)",
                                    borderColor: "rgba(78, 115, 223, 1)",
                                    borderWidth: 1,
                                    data: revenueData,
                                }]
                            },
                            options: {
                                maintainAspectRatio: false,
                                scales: {
                                    y: {
                                        beginAtZero: true,
                                        ticks: {
                                            callback: function(value) { return value.toLocaleString() + ' VNĐ'; }
                                        }
                                    }
                                }
                            }
                        });
                    </script>
                </div>
            </div>
        </div>
    </div>
</main>
</body>
</html>