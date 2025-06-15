

document.addEventListener('DOMContentLoaded', function () {

    const registerForm = document.getElementById('register-form');
    const emailInput = document.getElementById('register-email');
    const usernameInput = document.getElementById('register-username');
    const birthInput = document.getElementById('register-birth');
    const describeInput = document.getElementById('register-describe');
    const passwordInput = document.getElementById('register-password');

    const emailError = document.getElementById('email-error');
    const usernameError = document.getElementById('username-error');
    const birthError = document.getElementById('birth-error');
    const describeError = document.getElementById('describeSt-error');
    const passwordError = document.getElementById('password-error');

    // Hàm kiểm tra định dạng email
    function validateEmail(email) {
        const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
        return emailPattern.test(email);
    }

    registerForm.addEventListener('submit', function (event) {
        event.preventDefault();

        // Xóa các lỗi cũ trước khi kiểm tra
        emailError.textContent = '';
        usernameError.textContent = '';
        birthError.textContent = '';
        describeError.textContent = '';
        passwordError.textContent = '';

        let valid = true;

        // Kiểm tra email
        if (!emailInput.value) {
            emailError.textContent = 'Vui lòng nhập email';
            valid = false;
        } else if (!validateEmail(emailInput.value)) {
            emailError.textContent = 'Email không đúng định dạng';
            valid = false;
        }

        // Kiểm tra tên đăng nhập: 5-10 ký tự, không ký tự đặc biệt
        const usernamePattern = /^[a-zA-Z0-9]{5,10}$/;
        if (!usernameInput.value) {
            usernameError.textContent = 'Vui lòng nhập tên đăng nhập';
            valid = false;
        } else if (!usernamePattern.test(usernameInput.value)) {
            usernameError.textContent = 'Tên đăng nhập phải từ 5-10 ký tự và không chứa ký tự đặc biệt';
            valid = false;
        }

        // Kiểm tra ngày sinh (birth): không được để trống và phải là ngày hợp lệ
        if (!birthInput.value) {
            birthError.textContent = 'Vui lòng nhập ngày sinh';
            valid = false;
        } else {
            const birthDate = new Date(birthInput.value);
            if (isNaN(birthDate.getTime())) {
                birthError.textContent = 'Ngày sinh không hợp lệ';
                valid = false;
            }
        }

        // Kiểm tra mô tả (describe): không được để trống, tối thiểu 5 ký tự
        if (!describeInput.value) {
            describeError.textContent = 'Vui lòng nhập mô tả';
            valid = false;
        } else if (describeInput.value.length < 5) {
            describeError.textContent = 'Mô tả phải có ít nhất 5 ký tự';
            valid = false;
        }

        // Kiểm tra mật khẩu: 5-10 ký tự, không ký tự đặc biệt
        const passwordPattern = /^[a-zA-Z0-9]{5,10}$/;
        if (!passwordInput.value) {
            passwordError.textContent = 'Vui lòng nhập mật khẩu';
            valid = false;
        } else if (!passwordPattern.test(passwordInput.value)) {
            passwordError.textContent = 'Mật khẩu phải từ 5-10 ký tự và không chứa ký tự đặc biệt';
            valid = false;
        }

        // Nếu tất cả hợp lệ thì submit form
        if (valid) {
            registerForm.submit();
        }
    });
});
