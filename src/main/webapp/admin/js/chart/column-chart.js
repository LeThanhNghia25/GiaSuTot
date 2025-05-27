function number_format(number, decimals, dec_point, thousands_sep) {
    number = (number + '').replace(',', '').replace(' ', '');
    var n = !isFinite(+number) ? 0 : +number,
        prec = !isFinite(+decimals) ? 0 : Math.abs(decimals),
        sep = (typeof thousands_sep === 'undefined') ? ',' : thousands_sep,
        dec = (typeof dec_point === 'undefined') ? '.' : dec_point,
        s = '',
        toFixedFix = function(n, prec) {
            var k = Math.pow(10, prec);
            return '' + Math.round(n * k) / k;
        };
    s = (prec ? toFixedFix(n, prec) : '' + Math.round(n)).split('.');
    if (s[0].length > 3) {
        s[0] = s[0].replace(/\B(?=(?:\d{3})+(?!\d))/g, sep);
    }
    if ((s[1] || '').length < prec) {
        s[1] = s[1] || '';
        s[1] += new Array(prec - s[1].length + 1).join('0');
    }
    return s.join(dec);
}

// Khởi tạo biểu đồ cột
var ctx = document.getElementById("myBarChart").getContext('2d');
var myBarChart;

function updateChart(year) {
    // Gửi yêu cầu AJAX để lấy dữ liệu doanh thu theo năm
    $.ajax({
        url: contextPath + '/revenue-data',
        type: 'GET',
        data: { year: year },
        dataType: 'json',
        success: function(data) {
            var revenueData = Array(12).fill(0);
            for (var month in data) {
                var monthIndex = parseInt(month) - 1; // Tháng từ 1-12, mảng từ 0-11
                revenueData[monthIndex] = data[month] || 0;
            }

            if (myBarChart) {
                myBarChart.destroy(); // Xóa biểu đồ cũ nếu tồn tại
            }

            myBarChart = new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: ["Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12"],
                    datasets: [{
                        label: "Doanh thu",
                        backgroundColor: "rgba(78, 115, 223, 0.8)",
                        hoverBackgroundColor: "rgba(78, 115, 223, 1)",
                        borderColor: "rgba(78, 115, 223, 1)",
                        borderWidth: 1,
                        data: revenueData,
                    }],
                },
                options: {
                    maintainAspectRatio: false,
                    layout: {
                        padding: {
                            left: 10,
                            right: 25,
                            top: 25,
                            bottom: 0
                        }
                    },
                    scales: {
                        x: {
                            grid: {
                                display: false,
                                drawBorder: false
                            },
                            ticks: {
                                maxTicksLimit: 12
                            }
                        },
                        y: {
                            beginAtZero: true,
                            ticks: {
                                maxTicksLimit: 5,
                                padding: 10,
                                callback: function(value) {
                                    return number_format(value) + ' VNĐ';
                                }
                            },
                            grid: {
                                color: "rgb(234, 236, 244)",
                                borderColor: "rgb(234, 236, 244)",
                                drawBorder: false,
                                borderDash: [2]
                            }
                        }
                    },
                    plugins: {
                        legend: {
                            display: false
                        },
                        tooltip: {
                            backgroundColor: "rgb(255,255,255)",
                            bodyFont: { color: "#858796" },
                            titleMarginBottom: 10,
                            titleFont: { color: '#6e707e', size: 14 },
                            borderColor: '#dddfeb',
                            borderWidth: 1,
                            padding: 15,
                            displayColors: false,
                            mode: 'index',
                            caretPadding: 10,
                            callbacks: {
                                label: function(tooltipItem) {
                                    var datasetLabel = tooltipItem.dataset.label || '';
                                    return datasetLabel + ': ' + number_format(tooltipItem.raw) + ' VNĐ';
                                }
                            }
                        }
                    }
                }
            });
        },
        error: function(xhr, status, error) {
            console.error('Lỗi khi tải dữ liệu: ' + error);
        }
    });
}

// Khởi tạo biểu đồ khi trang tải
$(document).ready(function() {
    var defaultYear = $("#yearFilter").val();
    updateChart(defaultYear);

    // Cập nhật biểu đồ khi chọn năm
    $("#yearFilter").on('change', function() {
        var selectedYear = $(this).val();
        updateChart(selectedYear);
    });
});