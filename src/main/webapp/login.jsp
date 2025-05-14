<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>eLEARNING - eLearning HTML Template</title>
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
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <style>
        body {
            background-color: #f8f9fa;
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
        }

        .auth-container {
            max-width: 1000px;
            margin: 60px auto;
            display: flex;
            background: #fff;
            border-radius: 8px;
            overflow: hidden;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
            transition: all 0.4s ease;
        }

        /* Section hiển thị Register hoặc Login */
        .form-section {
            flex: 1;
            padding: 40px;
            box-sizing: border-box;
        }

        /* Toggle giữa 2 form */
        .toggle-section {
            flex: 1;
            background-color: #0097a7;
            color: white;
            display: flex;
            justify-content: center;
            align-items: center;
            flex-direction: column;
            cursor: pointer;
            transition: background 0.3s ease;
        }

        .toggle-section:hover {
            background-color: #007c91;
        }

        .form-section h2 {
            font-weight: bold;
            margin-bottom: 10px;
        }

        .form-section p {
            margin-bottom: 30px;
        }

        .form-section label {
            font-weight: 600;
            margin-bottom: 5px;
            display: block;
        }

        .form-section input {
            width: 100%;
            padding: 10px;
            margin-bottom: 20px;
            border: 1px solid #ccc;
            border-radius: 6px;
        }

        .btn-auth {
            padding: 10px 20px;
            border-radius: 6px;
            font-weight: bold;
            margin-right: 10px;
            border: none;
            cursor: pointer;
        }

        .btn-cancel {
            background-color: #fff;
            border: 1px solid #000;
        }

        .btn-submit {
            background-color: #06BBCCFF;
            color: #fff;
        }

        .hidden {
            display: none;
        }

        /* Khi hiển thị Login, form login nằm bên phải, toggle Register bên trái */
        .container-login .auth-container {
            flex-direction: row;
        }

        .container-login #login-section {
            order: 2;
        }

        .container-login #toggle-register {
            order: 1;
        }

        /* Khi hiển thị Register, form register nằm bên trái, toggle Login bên phải */
        .container-register .auth-container {
            flex-direction: row;
        }

        .container-register #register-section {
            order: 1;
        }

        .container-register #toggle-login {
            order: 2;
        }

        footer {
            background: #343a40;
            color: white;
            text-align: center;
            padding: 16px 0;
            margin-top: 50px;
        }

        @media (max-width: 768px) {
            .auth-container {
                flex-direction: column;
            }

            .form-section,
            .toggle-section {
                width: 100%;
            }
        }
    </style>
</head>
<body>
<!-- Spinner Start -->
<div id="spinner" class="show bg-white position-fixed translate-middle w-100 vh-100 top-50 start-50 d-flex align-items-center justify-content-center">
    <div class="spinner-border text-primary" style="width: 3rem; height: 3rem;" role="status">
        <span class="sr-only"z>Loading...</span>
    </div>
</div>
<!-- Navbar -->
<nav class="navbar navbar-expand-lg bg-white navbar-light shadow sticky-top p-0">
    <a href="index.jsp" class="navbar-brand d-flex align-items-center px-4 px-lg-5">
        <h2 class="m-0 text-primary"><i class="fa fa-book me-3"></i>eLEARNING</h2>
    </a>
    <button type="button" class="navbar-toggler me-4" data-bs-toggle="collapse" data-bs-target="#navbarCollapse">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarCollapse">
        <div class="navbar-nav ms-auto p-4 p-lg-0">
            <a href="index.jsp" class="nav-item nav-link">Home</a>
            <a href="about.jsp" class="nav-item nav-link">About</a>
            <a href="courses.jsp" class="nav-item nav-link">Courses</a>
            <div class="nav-item dropdown">
                <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown">Pages</a>
                <div class="dropdown-menu fade-down m-0">
                    <a href="team.jsp" class="dropdown-item">Our Team</a>
                    <a href="testimonial.jsp" class="dropdown-item">Testimonial</a>
                    <a href="404.jsp" class="dropdown-item">404 Page</a>
                </div>
            </div>
            <a href="contact.jsp" class="nav-item nav-link active">Contact</a>
        </div>
        <a href="login.jsp" class="btn btn-primary py-4 px-lg-5 d-none d-lg-block">Join Now<i class="fa fa-arrow-right ms-3"></i></a>
    </div>
</nav>
<!-- Navbar End -->

<div class="container container-register" id="main-container">
    <div class="auth-container">

        <!-- Toggle section để chuyển sang Login -->
        <div class="toggle-section" id="toggle-login">
            <p>Already have an account?</p>
            <h2 id="switch-to-login">Login here</h2>
        </div>

        <!-- Register form -->
        <div class="form-section hidden" id="register-section">
            <h2>Register</h2>
            <p>Create a new account</p>
            <form>
                <label for="reg-name">Name</label>
                <input type="text" id="reg-name" placeholder="Your Name">

                <label for="reg-email">Email</label>
                <input type="email" id="reg-email" placeholder="Email">

                <label for="reg-password">Password</label>
                <input type="password" id="reg-password" placeholder="Password">

                <button type="submit" class="btn-auth btn-submit">Register</button>
                <button type="reset" class="btn-auth btn-cancel">Cancel</button>
            </form>
        </div>

        <!-- Login form -->
        <div class="form-section" id="login-section">
            <h2>Login</h2>
            <p>Welcome back!</p>
            <form>
                <label for="login-email">Email</label>
                <input type="email" id="login-email" placeholder="Email">

                <label for="login-password">Password</label>
                <input type="password" id="login-password" placeholder="Password">

                <button type="submit" class="btn-auth btn-submit">Login</button>
                <button type="reset" class="btn-auth btn-cancel">Cancel</button>
            </form>
        </div>

        <!-- Toggle section để chuyển sang Register -->
        <div class="toggle-section hidden" id="toggle-register">
            <p>Don't have an account?</p>
            <h2 id="switch-to-register">Register here</h2>
        </div>

    </div>
</div>
<div class="container-fluid bg-dark text-light footer pt-5 mt-5 wow fadeIn" data-wow-delay="0.1s">
    <div class="container py-5">
        <div class="row g-5">
            <div class="col-lg-3 col-md-6">
                <h4 class="text-white mb-3">Quick Link</h4>
                <a class="btn btn-link" href="">About Us</a>
                <a class="btn btn-link" href="">Contact Us</a>
                <a class="btn btn-link" href="">Privacy Policy</a>
                <a class="btn btn-link" href="">Terms & Condition</a>
                <a class="btn btn-link" href="">FAQs & Help</a>
            </div>
            <div class="col-lg-3 col-md-6">
                <h4 class="text-white mb-3">Contact</h4>
                <p class="mb-2"><i class="fa fa-map-marker-alt me-3"></i>123 Street, New York, USA</p>
                <p class="mb-2"><i class="fa fa-phone-alt me-3"></i>+012 345 67890</p>
                <p class="mb-2"><i class="fa fa-envelope me-3"></i>info@example.com</p>
                <div class="d-flex pt-2">
                    <a class="btn btn-outline-light btn-social" href=""><i class="fab fa-twitter"></i></a>
                    <a class="btn btn-outline-light btn-social" href=""><i class="fab fa-facebook-f"></i></a>
                    <a class="btn btn-outline-light btn-social" href=""><i class="fab fa-youtube"></i></a>
                    <a class="btn btn-outline-light btn-social" href=""><i class="fab fa-linkedin-in"></i></a>
                </div>
            </div>
            <div class="col-lg-3 col-md-6">
                <h4 class="text-white mb-3">Gallery</h4>
                <div class="row g-2 pt-2">
                    <div class="col-4">
                        <img class="img-fluid bg-light p-1" src="img/course-1.jpg" alt="">
                    </div>
                    <div class="col-4">
                        <img class="img-fluid bg-light p-1" src="img/course-2.jpg" alt="">
                    </div>
                    <div class="col-4">
                        <img class="img-fluid bg-light p-1" src="img/course-3.jpg" alt="">
                    </div>
                    <div class="col-4">
                        <img class="img-fluid bg-light p-1" src="img/course-2.jpg" alt="">
                    </div>
                    <div class="col-4">
                        <img class="img-fluid bg-light p-1" src="img/course-3.jpg" alt="">
                    </div>
                    <div class="col-4">
                        <img class="img-fluid bg-light p-1" src="img/course-1.jpg" alt="">
                    </div>
                </div>
            </div>
            <div class="col-lg-3 col-md-6">
                <h4 class="text-white mb-3">Newsletter</h4>
                <p>Dolor amet sit justo amet elitr clita ipsum elitr est.</p>
                <div class="position-relative mx-auto" style="max-width: 400px;">
                    <input class="form-control border-0 w-100 py-3 ps-4 pe-5" type="text" placeholder="Your email">
                    <button type="button" class="btn btn-primary py-2 position-absolute top-0 end-0 mt-2 me-2">SignUp</button>
                </div>
            </div>
        </div>
    </div>
    <div class="container">
        <div class="copyright">
            <div class="row">
                <div class="col-md-6 text-center text-md-start mb-3 mb-md-0">
                    &copy; <a class="border-bottom" href="#">Your Site Name</a>, All Right Reserved.

                    <!--/*** This template is free as long as you keep the footer author’s credit link/attribution link/backlink. If you'd like to use the template without the footer author’s credit link/attribution link/backlink, you can purchase the Credit Removal License from "https://htmlcodex.com/credit-removal". Thank you for your support. ***/-->
                    Designed By <a class="border-bottom" href="https://htmlcodex.com">HTML Codex</a>
                </div>
                <div class="col-md-6 text-center text-md-end">
                    <div class="footer-menu">
                        <a href="">Home</a>
                        <a href="">Cookies</a>
                        <a href="">Help</a>
                        <a href="">FQAs</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Footer End -->
<a href="#" class="btn btn-lg btn-primary btn-lg-square back-to-top"><i class="bi bi-arrow-up"></i></a>


<!-- Scripts -->
<script>
    const mainContainer = document.getElementById("main-container");
    const loginSection = document.getElementById("login-section");
    const registerSection = document.getElementById("register-section");
    const toggleLogin = document.getElementById("toggle-login");
    const toggleRegister = document.getElementById("toggle-register");

    const switchToLogin = document.getElementById("switch-to-login");
    const switchToRegister = document.getElementById("switch-to-register");

    switchToLogin.addEventListener("click", () => {
        mainContainer.classList.remove("container-register");
        mainContainer.classList.add("container-login");
        registerSection.classList.add("hidden");
        toggleLogin.classList.add("hidden");
        loginSection.classList.remove("hidden");
        toggleRegister.classList.remove("hidden");
    });

    switchToRegister.addEventListener("click", () => {
        mainContainer.classList.remove("container-login");
        mainContainer.classList.add("container-register");
        loginSection.classList.add("hidden");
        toggleRegister.classList.add("hidden");
        registerSection.classList.remove("hidden");
        toggleLogin.classList.remove("hidden");
    });
</script>

<!-- JavaScript Libraries -->
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="libs/wow/wow.min.js"></script>
<script src="libs/easing/easing.min.js"></script>
<script src="libs/waypoints/waypoints.min.js"></script>
<script src="libs/owlcarousel/owl.carousel.min.js"></script>

<!-- Template Javascript -->
<script src="js/main.js"></script>
<!-- Font Awesome (icon sách) -->
<script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script>

</body>
</html>
