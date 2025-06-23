<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>--%>
<%@ page import="DAO.TutorDAO, model.Tutor, java.util.List" %>
<%
    TutorDAO dao = new TutorDAO();
    List<Tutor> tutors = dao.getAllTutors();
    request.setAttribute("tutors", tutors); // gán vào request như servlet
%>
<style>
    .tutor-card {
        display: none;
    }
    .tutor-card.visible {
        display: block;
    }
</style>


<!-- Team Start -->
<div class="container-xxl py-5">
    <div class="container">
        <div class="text-center wow fadeInUp" data-wow-delay="0.1s">
            <h6 class="section-title bg-white text-center text-primary px-3">Giảng viên</h6>
            <h1 class="mb-5">Giảng viên chuyên môn cao</h1>
        </div>
        <div class="row g-4">
            <c:forEach var="tutor" items="${tutors}" varStatus="loop">
            <div class="col-lg-3 col-md-6 wow fadeInUp tutor-card" data-wow-delay="0.1s">
                <div class="team-item bg-light">
                    <div class="overflow-hidden">
                        <img class="img-fluid" src="img/img1.jpg" alt="">
                    </div>
                    <div class="position-relative d-flex justify-content-center" style="margin-top: -23px;">
                        <div class="bg-light d-flex justify-content-center pt-2 px-1">
                            <a class="btn btn-sm-square btn-primary mx-1" href="https://facebook.com"><i class="fab fa-facebook-f"></i></a>
                            <a class="btn btn-sm-square btn-primary mx-1" href="https://twitter.com"><i class="fab fa-twitter"></i></a>
                            <a class="btn btn-sm-square btn-primary mx-1" href="https://instagram.com"><i class="fab fa-instagram"></i></a>
                        </div>
                    </div>
                    <div class="text-center p-4">
                        <h5 class="mb-0">${tutor.name}</h5>
                        <small>${tutor.specialization}</small>
                        <!-- Nút trái tim "Quan tâm" -->
                        <div class="mt-2">
                            <button class="btn btn-outline-primary btn-sm btn-like"
                                data-tutor-id="${tutor.id}">
                                <i class="fas fa-heart"></i> Quan tâm
                            </button>
                        </div>

                    </div>
                </div>
            </div>
            </c:forEach>
        </div>
        <div class="text-center mt-4">
            <button id="showMoreBtn" class="btn btn-primary" onclick="showMoreTutors()">Xem thêm</button>
        </div>

    </div>
</div>
<!-- Team End -->
<script>
    document.addEventListener("DOMContentLoaded", function () {
        document.querySelectorAll(".btn-like").forEach(function (btn) {
            btn.addEventListener("click", function () {
                const tutorId = btn.getAttribute("data-tutor-id");

                fetch("add-interest", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/x-www-form-urlencoded"
                    },
                    body: "tutorId=" + encodeURIComponent(tutorId)
                })
                    .then(res => res.text())
                    .then(data => {
                        if (data === 'success') {
                            // Đổi màu nút
                            btn.classList.remove("btn-outline-primary");
                            btn.classList.add("btn-primary");
                            btn.innerHTML = '<i class="fas fa-heart"></i> Đã quan tâm';
                        } else if (data === 'not_logged_in') {
                            alert("Vui lòng đăng nhập để sử dụng chức năng này.");
                        } else {
                            alert("Có lỗi xảy ra khi lưu quan tâm.");
                        }
                    });
            });
        });
    });
</script>

<script>
    let currentVisible = 0;
    const batchSize = 4;

    function showMoreTutors() {
        const cards = document.querySelectorAll('.tutor-card');
        let shown = 0;
        for (let i = currentVisible; i < cards.length && shown < batchSize; i++) {
            cards[i].classList.add('visible');
            shown++;
            currentVisible++;
        }
        if (currentVisible >= cards.length) {
            document.getElementById('showMoreBtn').style.display = 'none';
        }
    }

    // Gọi tự động khi load xong
    document.addEventListener('DOMContentLoaded', () => {
        showMoreTutors();
    });
</script>

