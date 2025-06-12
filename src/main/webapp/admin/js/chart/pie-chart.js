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

$(document).ready(function() {
    console.log('Document ready - Khởi tạo biểu đồ tròn');

    // Khởi tạo biểu đồ tròn
    var ctxPie = document.getElementById("myPieChart");
    if (!ctxPie) {
        console.error('Không tìm thấy element myPieChart');
        return;
    }
    console.log('ctxPie:', ctxPie);

    var myPieChart;

    function updatePieChart(year) {
        console.log('Gọi updatePieChart với year:', year);
        console.log('contextPath:', contextPath);
        if (!contextPath) {
            console.error('contextPath không được định nghĩa');
            return;
        }
        $.ajax({
            url: contextPath + '/revenue-data',
            type: 'GET',
            data: { type: 'subject', year: year },
            dataType: 'json',
            success: function(data) {
                console.log('Dữ liệu nhận được:', data);
                var labels = Object.keys(data);
                var values = Object.values(data);

                if (labels.length === 0) {
                    console.log('Không có dữ liệu để hiển thị biểu đồ tròn');
                    labels = ['Không có dữ liệu'];
                    values = [0];
                }

                if (myPieChart) {
                    myPieChart.destroy();
                    console.log('Biểu đồ cũ đã được destroy');
                }

                try {
                    myPieChart = new Chart(ctxPie.getContext('2d'), {
                        type: 'pie',
                        data: {
                            labels: labels,
                            datasets: [{
                                data: values,
                                backgroundColor: [
                                    'rgba(78, 115, 223, 0.8)',
                                    'rgba(255, 99, 132, 0.8)',
                                    'rgba(54, 162, 235, 0.8)',
                                    'rgba(255, 206, 86, 0.8)',
                                    'rgba(75, 192, 192, 0.8)'
                                ],
                                borderWidth: 1,
                                borderColor: '#ffffff'
                            }]
                        },
                        options: {
                            maintainAspectRatio: false,
                            plugins: {
                                legend: {
                                    position: 'top',
                                    labels: {
                                        font: { size: 14 },
                                        color: '#858796'
                                    }
                                },
                                tooltip: {
                                    backgroundColor: "rgba(0, 0, 0, 0.8)", // Đổi nền thành màu đen mờ
                                    bodyFont: { color: "#ffffff" }, // Đổi màu chữ thành trắng
                                    titleFont: { color: '#ffffff', size: 14 }, // Đổi màu tiêu đề thành trắng
                                    borderColor: '#dddfeb',
                                    borderWidth: 1,
                                    padding: 15,
                                    callbacks: {
                                        label: function(tooltipItem) {
                                            var label = labels[tooltipItem.dataIndex] || '';
                                            var value = number_format(values[tooltipItem.dataIndex]) + ' VNĐ';
                                            return label + ': ' + value + ' (Năm ' + year + ')';
                                        }
                                    }
                                }
                            }
                        }
                    });
                    console.log('Biểu đồ tròn đã được tạo');
                } catch (e) {
                    console.error('Lỗi khi tạo biểu đồ:', e);
                }
            },
            error: function(xhr, status, error) {
                console.error('Lỗi khi tải dữ liệu cho biểu đồ tròn:', error);
                console.log('Status:', status);
                console.log('Response:', xhr.responseText);
            }
        });
    }

    var defaultYear = $("#pieChartYearFilter").val();
    updatePieChart(defaultYear);

    $("#pieChartYearFilter").on('change', function() {
        var selectedYear = $(this).val();
        updatePieChart(selectedYear);
        $("#pieChartYear").text(selectedYear);
    });
});