<%@ page import="ru.tanya_dima.chat.ChatConstants" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Чатик</title>
    <link rel="stylesheet" href="css/common.css"/>
    <link rel="stylesheet" href="css/fonts.css"/>
    <link rel="stylesheet" href="css/login.css"/>
    <script src="js/jquery-3.2.1.min.js"></script>
    <script src="js/login.js"></script>
</head>

<%
    if (session.getAttribute(ChatConstants.LOGIN) != null) {
        response.sendRedirect(request.getContextPath() + "/chat.jsp");
    }
%>

<body>

<form id="register" autocomplete="off" class="content login">
    <div class="login__title">Вход</div>

    <input type="text" name="login" placeholder="Логин" class="login__field">
    <p class="login__message">&nbsp;</p>

    <input type="password" name="password" placeholder="Пароль" class="login__field">
    <p class="login__message">&nbsp;</p>

    <input type="submit" name="submit" value="Войти" class="login__submit">
    <p id="server_message" class="login__message" style="text-align: center" hidden></p>
</form>

</body>
</html>