document.addEventListener("DOMContentLoaded", function () {
    const registerForm = document.getElementById("register-form");

    if (registerForm) {
        const emailError = document.getElementById("email-error");
        const nameError = document.getElementById("name-error");
        const birthError = document.getElementById("birth-error");
        const descriptionError = document.getElementById("description-error");
        const passwordError = document.getElementById("password-error");

        registerForm.addEventListener("submit", function (event) {
            console.log("Đã submit");
            event.preventDefault();

            if (emailError) emailError.textContent = "";
            if (nameError) nameError.textContent = "";
            if (birthError) birthError.textContent = "";
            if (descriptionError) descriptionError.textContent = "";
            if (passwordError) passwordError.textContent = "";

            const emailInput = document.getElementById("register-email");
            const nameInput = document.getElementById("register-name");

            let isValid = true;

            if (emailInput && emailInput.value.trim() === "") {
                if (emailError) emailError.textContent = "Email không được để trống.";
                isValid = false;
            }

            if (nameInput && nameInput.value.trim().length < 3) {
                if (nameError) nameError.textContent = "Tên phải từ 3 ký tự trở lên.";
                isValid = false;
            }

            if (isValid) {
                registerForm.submit();
            }
        });
    }
});
