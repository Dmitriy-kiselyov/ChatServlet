function placeholderAutoHide(element) {
    var placeholder = element.attr("placeholder");

    function hide() {
        element.removeAttr("placeholder");
    }

    function show() {
        element.attr("placeholder", placeholder);
    }

    element.on("focus", hide);
    element.on("blur", show);
}

function validate(element, validateFunc) {
    element.val(element.val().trim());
    var messageField = element.next();

    var message = validateFunc(element.val());
    if (message) {
        element.addClass("login__field-warning");
        messageField.html(message);
        return false;
    } else {
        element.removeClass("login__field-warning");
        messageField.html("&nbsp;");
        return true;
    }
}

function validateLogin() {
    var loginPattern = /^[a-zA-Z][a-zA-Z0-9]{2,19}$/;
    var loginField = $("[name='login']");

    return validate(loginField, function (login) {
        if (login.length == 0)
            return "Заполните логин";
        if (login.length < 3 || login.length > 20)
            return "Логин должен состоять от 3 до 20 символов";
        if (!login.match(loginPattern))
            return "Логин должен состоять только из латинских букв и цифр, и не должен начинаться с цифры";
    });
}

function validatePassword() {
    var passwordPattern = /^[a-zA-Z0-9_]{4,29}$/;
    var passwordField = $("[name='password']");

    return validate(passwordField, function (pass) {
        if (pass.length == 0)
            return "Заполните пароль";
        if (pass.length < 5 || pass.length > 30)
            return "Пароль должен состоять от 5 до 30 символов";
        if (!pass.match(passwordPattern))
            return "Пароль должен состоять только из латинских букв, цифр и нижнего подчеркивания";
    });
}

function submitForm() {
    var form = $("#register");

    var loginOk = validateLogin();
    var passOk = validatePassword();
    if (passOk && loginOk) {
        $.ajax({
            url: "ChatServlet?action=register",
            type: "POST",
            data: form.serialize(),
            success: function (data) {
                if (data.response)
                    window.location.replace(data.response.url);
                if (data.error)
                    showMessage($("#server_message"), data.error.error_msg);
            }
        });
    }
}

function showMessage(el, message) {
    el.text(message);
    el.slideDown("fast", "swing", function () {
        setTimeout(function () {
            el.slideUp("fast", "swing");
        }, 5000);
    });
}

$(function () {
    var loginField = $("[name='login']");
    var passwordField = $("[name='password']");
    placeholderAutoHide(loginField);
    placeholderAutoHide(passwordField);

    //Проверка форм
    loginField.on("blur", validateLogin);
    passwordField.on("blur", validatePassword);

    //Отправка формы
    $("#register").off("submit").on("submit", function () {
        submitForm();
        return false;
    });
});