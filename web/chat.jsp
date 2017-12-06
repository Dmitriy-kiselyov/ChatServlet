<%@page import="ru.tanya_dima.chat.ChatConstants" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Чатик</title>
    <link rel="stylesheet" href="css/common.css"/>
    <link rel="stylesheet" href="css/chat.css">
    <link rel="stylesheet" href="css/fonts.css"/>
    <script src="js/jquery-3.2.1.min.js"></script>
    <script src="js/chat.js"></script>
</head>

<%
    if (session.getAttribute(ChatConstants.LOGIN) == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
    }
%>

<body>

<div class="content chat">
    <div class="chat__header">Чатик :*</div>
    <div class="chat__main" id="chat"></div>
    <form class="chat__footer" autocomplete="off" id="send_form">
        <div class="chat__message_wrapper">
            <input type="text" name="message" class="chat__input" placeholder="Сообщение..." id="send_input">
            <input type="submit" class="chat__send" value="">
        </div>
    </form>
</div>

</body>

</html>