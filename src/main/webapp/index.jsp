<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="vi">


<head>
    <meta charset="utf-8">
    <title>GIASUTOT</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <meta content="" name="keywords">
    <meta content="" name="description">

    <!-- Favicon -->
    <link href="img/favicon.ico" rel="icon">

    <!-- Google Web Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Heebo:wght@400;500;600&family=Nunito:wght@600;700;800&display=swap" rel="stylesheet">

    <!-- Icon Font Stylesheet -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">

    <!-- Libraries Stylesheet -->
    <link href="libs/animate/animate.min.css" rel="stylesheet">
    <link href="libs/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">

    <!-- Customized Bootstrap Stylesheet -->
    <link href="css/bootstrap.min.css" rel="stylesheet">


    <!-- Template Stylesheet -->
    <link href="css/style.css" rel="stylesheet">
</head>

<body>
<!-- Header Start -->
<%@ include file="header.jsp" %>
<!-- Header End -->

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<!-- Navbar End -->


<!-- Carousel Start -->
<div class="container-fluid p-0 mb-5">
    <div class="owl-carousel header-carousel position-relative">
        <div class="owl-carousel-item position-relative">
            <img class="img-fluid" src="img/carousel-1.jpg" alt="">
            <div class="position-absolute top-0 start-0 w-100 h-100 d-flex align-items-center" style="background: rgba(24, 29, 56, .7);">
                <div class="container">
                    <div class="row justify-content-start">
                        <div class="col-sm-10 col-lg-8">
                            <h5 class="text-primary text-uppercase mb-3 animated slideInDown">Khóa học trực tuyến tốt nhất</h5>
                            <h1 class="display-3 text-white animated slideInDown">Nền tảng học trực tuyến hàng đầu</h1>
                            <p class="fs-5 text-white mb-4 pb-2">Khám phá các khóa học chất lượng cao với nội dung phong phú. Học mọi lúc, mọi nơi với sự hỗ trợ tận tình từ đội ngũ giảng viên.</p>
                            <a href="" class="btn btn-primary py-md-3 px-md-5 me-3 animated slideInLeft">Tìm hiểu thêm</a>
                            <a href="" class="btn btn-light py-md-3 px-md-5 animated slideInRight">Tham gia ngay</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="owl-carousel-item position-relative">
            <img class="img-fluid" src="img/carousel-2.jpg" alt="">
            <div class="position-absolute top-0 start-0 w-100 h-100 d-flex align-items-center" style="background: rgba(24, 29, 56, .7);">
                <div class="container">
                    <div class="row justify-content-start">
                        <div class="col-sm-10 col-lg-8">
                            <h5 class="text-primary text-uppercase mb-3 animated slideInDown">Khóa học trực tuyến tốt nhất</h5>
                            <h1 class="display-3 text-white animated slideInDown">Học trực tuyến ngay tại nhà</h1>
                            <p class="fs-5 text-white mb-4 pb-2">Tham gia các khóa học trực tuyến của chúng tôi để nâng cao kỹ năng và kiến thức mà không cần rời khỏi nhà.</p>
                            <a href="" class="btn btn-primary py-md-3 px-md-5 me-3 animated slideInLeft">Tìm hiểu thêm</a>
                            <a href="" class="btn btn-light py-md-3 px-md-5 animated slideInRight">Tham gia ngay</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Carousel End -->


<!-- Service Start -->
<div class="container-xxl py-5">
    <div class="container">
        <div class="row g-4">
            <div class="col-lg-3 col-sm-6 wow fadeInUp" data-wow-delay="0.1s">
                <div class="service-item text-center pt-3">
                    <div class="p-4">
                        <i class="fa fa-3x fa-graduation-cap text-primary mb-4"></i>
                        <h5 class="mb-3">Giảng viên chất lượng</h5>
                        <p>Đội ngũ giảng viên giàu kinh nghiệm, tận tâm, luôn sẵn sàng hỗ trợ bạn trong hành trình học tập.</p>
                    </div>
                </div>
            </div>
            <div class="col-lg-3 col-sm-6 wow fadeInUp" data-wow-delay="0.3s">
                <div class="service-item text-center pt-3">
                    <div class="p-4">
                        <i class="fa fa-3x fa-globe text-primary mb-4"></i>
                        <h5 class="mb-3">Lớp học trực tuyến</h5>
                        <p>Học mọi lúc, mọi nơi với các lớp học trực tuyến linh hoạt và tiện lợi.</p>
                    </div>
                </div>
            </div>
            <div class="col-lg-3 col-sm-6 wow fadeInUp" data-wow-delay="0.5s">
                <div class="service-item text-center pt-3">
                    <div class="p-4">
                        <i class="fa fa-3x fa-home text-primary mb-4"></i>
                        <h5 class="mb-3">Dự án tại nhà</h5>
                        <p>Thực hành qua các dự án thực tế ngay tại nhà để nâng cao kỹ năng.</p>
                    </div>
                </div>
            </div>
            <div class="col-lg-3 col-sm-6 wow fadeInUp" data-wow-delay="0.7s">
                <div class="service-item text-center pt-3">
                    <div class="p-4">
                        <i class="fa fa-3x fa-book-open text-primary mb-4"></i>
                        <h5 class="mb-3">Thư viện sách</h5>
                        <p>Truy cập kho tài liệu và sách phong phú để hỗ trợ việc học tập của bạn.</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Service End -->


<!-- About Start -->
<div class="container-xxl py-5">
    <div class="container">
        <div class="row g-5">
            <div class="col-lg-6 wow fadeInUp" data-wow-delay="0.1s" style="min-height: 400px;">
                <div class="position-relative h-100">
                    <img class="img-fluid position-absolute w-100 h-100" src="img/about.jpg" alt="" style="object-fit: cover;">
                </div>
            </div>
            <div class="col-lg-6 wow fadeInUp" data-wow-delay="0.3s">
                <h6 class="section-title bg-white text-start text-primary pe-3">Về chúng tôi</h6>
                <h1 class="mb-4">Chào mừng đến với GIASUTOT</h1>
                <p class="mb-4">Chúng tôi cung cấp các khóa học trực tuyến chất lượng cao, giúp bạn học tập hiệu quả và linh hoạt.</p>
                <p class="mb-4">Với đội ngũ giảng viên giàu kinh nghiệm và hệ thống học tập hiện đại, GIASUTOT cam kết mang đến trải nghiệm học tập tốt nhất cho bạn.</p>
                <div class="row gy-2 gx-4 mb-4">
                    <div class="col-sm-6">
                        <p class="mb-0"><i class="fa fa-arrow-right text-primary me-2"></i>Giảng viên chất lượng</p>
                    </div>
                    <div class="col-sm-6">
                        <p class="mb-0"><i class="fa fa-arrow-right text-primary me-2"></i>Lớp học trực tuyến</p>
                    </div>
                    <div class="col-sm-6">
                        <p class="mb-0"><i class="fa fa-arrow-right text-primary me-2"></i>Chứng chỉ quốc tế</p>
                    </div>
                    <div class="col-sm-6">
                        <p class="mb-0"><i class="fa fa-arrow-right text-primary me-2"></i>Giảng viên chất lượng</p>
                    </div>
                    <div class="col-sm-6">
                        <p class="mb-0"><i class="fa fa-arrow-right text-primary me-2"></i>Lớp học trực tuyến</p>
                    </div>
                    <div class="col-sm-6">
                        <p class="mb-0"><i class="fa fa-arrow-right text-primary me-2"></i>Chứng chỉ quốc tế</p>
                    </div>
                </div>
                <a class="btn btn-primary py-3 px-5 mt-2" href="">Tìm hiểu thêm</a>
            </div>
        </div>
    </div>
</div>
<!-- About End -->


<!-- Categories Start -->
<div class="container-xxl py-5 category">
    <div class="container">
        <div class="text-center wow fadeInUp" data-wow-delay="0.1s">
            <h6 class="section-title bg-white text-center text-primary px-3">Danh mục</h6>
            <h1 class="mb-5">Danh mục khóa học</h1>
        </div>
        <div class="row g-3">
            <div class="col-lg-7 col-md-6">
                <div class="row g-3">
                    <div class="col-lg-12 col-md-12 wow zoomIn" data-wow-delay="0.1s">
                        <a class="position-relative d-block overflow-hidden" href="">
                            <img class="img-fluid" src="img/cat-1.jpg" alt="">
                            <div class="bg-white text-center position-absolute bottom-0 end-0 py-2 px-3" style="margin: 1px;">
                                <h5 class="m-0">Thiết kế web</h5>
                                <small class="text-primary">49 Khóa học</small>
                            </div>
                        </a>
                    </div>
                    <div class="col-lg-6 col-md-12 wow zoomIn" data-wow-delay="0.3s">
                        <a class="position-relative d-block overflow-hidden" href="">
                            <img class="img-fluid" src="img/cat-2.jpg" alt="">
                            <div class="bg-white text-center position-absolute bottom-0 end-0 py-2 px-3" style="margin: 1px;">
                                <h5 class="m-0">Thiết kế đồ họa</h5>
                                <small class="text-primary">49 Khóa học</small>
                            </div>
                        </a>
                    </div>
                    <div class="col-lg-6 col-md-12 wow zoomIn" data-wow-delay="0.5s">
                        <a class="position-relative d-block overflow-hidden" href="">
                            <img class="img-fluid" src="img/cat-3.jpg" alt="">
                            <div class="bg-white text-center position-absolute bottom-0 end-0 py-2 px-3" style="margin: 1px;">
                                <h5 class="m-0">Biên tập video</h5>
                                <small class="text-primary">49 Khóa học</small>
                            </div>
                        </a>
                    </div>
                </div>
            </div>
            <div class="col-lg-5 col-md-6 wow zoomIn" data-wow-delay="0.7s" style="min-height: 350px;">
                <a class="position-relative d-block h-100 overflow-hidden" href="">
                    <img class="img-fluid position-absolute w-100 h-100" src="img/cat-4.jpg" alt="" style="object-fit: cover;">
                    <div class="bg-white text-center position-absolute bottom-0 end-0 py-2 px-3" style="margin: 1px;">
                        <h5 class="m-0">Tiếp thị trực tuyến</h5>
                        <small class="text-primary">49 Khóa học</small>
                    </div>
                </a>
            </div>
        </div>
    </div>
</div>
<!-- Categories Start -->


<!-- Courses Start -->
<div class="container-xxl py-5">
    <div class="container">
        <div class="text-center wow fadeInUp" data-wow-delay="0.1s">
            <h6 class="section-title bg-white text-center text-primary px-3">Khóa học</h6>
            <h1 class="mb-5">Khóa học phổ biến</h1>
        </div>
        <div class="row g-4 justify-content-center">
            <div class="col-lg-4 col-md-6 wow fadeInUp" data-wow-delay="0.1s">
                <div class="course-item bg-light">
                    <div class="position-relative overflow-hidden">
                        <img class="img-fluid" src="img/course-1.jpg" alt="">
                        <div class="w-100 d-flex justify-content-center position-absolute bottom-0 start-0 mb-4">
                            <a href="#" class="flex-shrink-0 btn btn-sm btn-primary px-3 border-end" style="border-radius: 30px 0 0 30px;">Tìm hiểu thêm</a>
                            <a href="#" class="flex-shrink-0 btn btn-sm btn-primary px-3" style="border-radius: 0 30px 30px 0;">Tham gia ngay</a>
                        </div>
                    </div>
                    <div class="text-center p-4 pb-0">
                        <h3 class="mb-0">3.500.000 VNĐ</h3>
                        <div class="mb-3">
                            <small class="fa fa-star text-primary"></small>
                            <small class="fa fa-star text-primary"></small>
                            <small class="fa fa-star text-primary"></small>
                            <small class="fa fa-star text-primary"></small>
                            <small class="fa fa-star text-primary"></small>
                            <small>(123)</small>
                        </div>
                        <h5 class="mb-4">Khóa học thiết kế & phát triển web cho người mới bắt đầu</h5>
                    </div>
                    <div class="d-flex border-top">
                        <small class="flex-fill text-center border-end py-2"><i class="fa fa-user-tie text-primary me-2"></i>Nguyễn Văn A</small>
                        <small class="flex-fill text-center border-end py-2"><i class="fa fa-clock text-primary me-2"></i>1.49 Giờ</small>
                        <small class="flex-fill text-center py-2"><i class="fa fa-user text-primary me-2"></i>30 Học viên</small>
                    </div>
                </div>
            </div>
            <div class="col-lg-4 col-md-6 wow fadeInUp" data-wow-delay="0.3s">
                <div class="course-item bg-light">
                    <div class="position-relative overflow-hidden">
                        <img class="img-fluid" src="img/course-2.jpg" alt="">
                        <div class="w-100 d-flex justify-content-center position-absolute bottom-0 start-0 mb-4">
                            <a href="#" class="flex-shrink-0 btn btn-sm btn-primary px-3 border-end" style="border-radius: 30px 0 0 30px;">Tìm hiểu thêm</a>
                            <a href="#" class="flex-shrink-0 btn btn-sm btn-primary px-3" style="border-radius: 0 30px 30px 0;">Tham gia ngay</a>
                        </div>
                    </div>
                    <div class="text-center p-4 pb-0">
                        <h3 class="mb-0">4.200.000 VNĐ</h3>
                        <div class="mb-3">
                            <small class="fa fa-star text-primary"></small>
                            <small class="fa fa-star text-primary"></small>
                            <small class="fa fa-star text-primary"></small>
                            <small class="fa fa-star text-primary"></small>
                            <small class="fa fa-star-half-alt text-primary"></small>
                            <small>(98)</small>
                        </div>
                        <h5 class="mb-4">Khóa học thiết kế đồ họa chuyên nghiệp</h5>
                    </div>
                    <div class="d-flex border-top">
                        <small class="flex-fill text-center border-end py-2"><i class="fa fa-user-tie text-primary me-2"></i>Trần Thị B</small>
                        <small class="flex-fill text-center border-end py-2"><i class="fa fa-clock text-primary me-2"></i>2.5 Giờ</small>
                        <small class="flex-fill text-center py-2"><i class="fa fa-user text-primary me-2"></i>45 Học viên</small>
                    </div>
                </div>
            </div>
            <div class="col-lg-4 col-md-6 wow fadeInUp" data-wow-delay="0.5s">
                <div class="course-item bg-light">
                    <div class="position-relative overflow-hidden">
                        <img class="img-fluid" src="img/course-3.jpg" alt="">
                        <div class="w-100 d-flex justify-content-center position-absolute bottom-0 start-0 mb-4">
                            <a href="#" class="flex-shrink-0 btn btn-sm btn-primary px-3 border-end" style="border-radius: 30px 0 0 30px;">Tìm hiểu thêm</a>
                            <a href="#" class="flex-shrink-0 btn btn-sm btn-primary px-3" style="border-radius: 0 30px 30px 0;">Tham gia ngay</a>
                        </div>
                    </div>
                    <div class="text-center p-4 pb-0">
                        <h3 class="mb-0">2.800.000 VNĐ</h3>
                        <div class="mb-3">
                            <small class="fa fa-star text-primary"></small>
                            <small class="fa fa-star text-primary"></small>
                            <small class="fa fa-star text-primary"></small>
                            <small class="fa fa-star text-primary"></small>
                            <small class="fa fa-star text-primary"></small>
                            <small>(150)</small>
                        </div>
                        <h5 class="mb-4">Khóa học biên tập video cơ bản</h5>
                    </div>
                    <div class="d-flex border-top">
                        <small class="flex-fill text-center border-end py-2"><i class="fa fa-user-tie text-primary me-2"></i>Lê Văn C</small>
                        <small class="flex-fill text-center border-end py-2"><i class="fa fa-clock text-primary me-2"></i>1.2 Giờ</small>
                        <small class="flex-fill text-center py-2"><i class="fa fa-user text-primary me-2"></i>25 Học viên</small>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Courses End -->

<jsp:include page="tutors.jsp" />

<!-- Testimonial Start -->
<div class="container-xxl py-5 wow fadeInUp" data-wow-delay="0.1s">
    <div class="container">
        <div class="text-center">
            <h6 class="section-title bg-white text-center text-primary px-3">Phản hồi</h6>
            <h1 class="mb-5">Học viên nói gì!</h1>
        </div>
        <div class="owl-carousel testimonial-carousel position-relative">
            <div class="testimonial-item text-center">
                <img class="border rounded-circle p-2 mx-auto mb-3" src="img/testimonial-1.jpg" style="width: 80px; height: 80px;">
                <h5 class="mb-0">Nguyễn Thị An</h5>
                <p>Nhân viên Marketing</p>
                <div class="testimonial-text bg-light text-center p-4">
                    <p class="mb-0">Khóa học rất bổ ích, giảng viên nhiệt tình và nội dung dễ hiểu. Tôi đã cải thiện kỹ năng thiết kế web đáng kể!</p>
                </div>
            </div>
            <div class="testimonial-item text-center">
                <img class="border rounded-circle p-2 mx-auto mb-3" src="img/testimonial-2.jpg" style="width: 80px; height: 80px;">
                <h5 class="mb-0">Trần Văn Bình</h5>
                <p>Freelancer</p>
                <div class="testimonial-text bg-light text-center p-4">
                    <p class="mb-0">Khóa học thiết kế đồ họa giúp tôi tự tin hơn trong công việc. Hệ thống bài giảng rất bài bản và thực tế!</p>
                </div>
            </div>
            <div class="testimonial-item text-center">
                <img class="border rounded-circle p-2 mx-auto mb-3" src="img/testimonial-3.jpg" style="width: 80px; height: 80px;">
                <h5 class="mb-0">Lê Thị Cẩm</h5>
                <p>Sinh viên</p>
                <div class="testimonial-text bg-light text-center p-4">
                    <p class="mb-0">Tôi rất thích khóa học biên tập video. Nội dung dễ tiếp cận và giảng viên luôn sẵn sàng giải đáp thắc mắc!</p>
                </div>
            </div>
            <div class="testimonial-item text-center">
                <img class="border rounded-circle p-2 mx-auto mb-3" src="img/testimonial-4.jpg" style="width: 80px; height: 80px;">
                <h5 class="mb-0">Phạm Văn Dũng</h5>
                <p>Chủ doanh nghiệp nhỏ</p>
                <div class="testimonial-text bg-light text-center p-4">
                    <p class="mb-0">Khóa học tiếp thị trực tuyến đã giúp tôi xây dựng chiến lược quảng cáo hiệu quả. Rất đáng để tham gia!</p>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Testimonial End -->


<!-- Footer Start -->
<%@ include file="footer.jsp" %>
<!-- Footer End -->


<!-- Back to Top -->
<a href="#" class="btn btn-lg btn-primary btn-lg-square back-to-top"><i class="bi bi-arrow-up"></i></a>


<!-- JavaScript Libraries -->
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="libs/wow/wow.min.js"></script>
<script src="libs/easing/easing.min.js"></script>
<script src="libs/waypoints/waypoints.min.js"></script>
<script src="libs/owlcarousel/owl.carousel.min.js"></script>

<!-- Template Javascript -->
<script src="js/main.js"></script>
</body>

</html>