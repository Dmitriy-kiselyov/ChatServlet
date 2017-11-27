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
</head>

<%
    if (session.getAttribute(ChatConstants.LOGIN) == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
    }
%>

<body>

<div class="content chat">
    <div class="chat__header">Чатик :*</div>
    <div class="chat__main">
        <div class="chat__message">
            <div class="chat__message__header">
                <span class="chat__message__name">Tanya_Orlova</span>
                <span class="chat__message__time">15:45</span>
            </div>
            <div class="chat__message__text">Привет, зайка, как дела?</div>
        </div>
        <div class="chat__message">
            <div class="chat__message__header">
                <span class="chat__message__name">Kiselyov_Dima</span>
                <span class="chat__message__time">16:48</span>
            </div>
            <div class="chat__message__text">
                Отлично, а ты как??? Отлично, а ты как??? Отлично, а ты как??? Отлично, а ты как??? Отлично, а ты как???
                Отлично, а ты как??? Отлично, а ты как??? Отлично, а ты как??? Отлично, а ты как???
            </div>
        </div>
        <div class="chat__message">
            <div class="chat__message__header">
                <span class="chat__message__name">Tanya_Orlova</span>
                <span class="chat__message__time">15:45</span>
            </div>
            <div class="chat__message__text">Привет, зайка, как дела?</div>
        </div>
        <div class="chat__message">
            <div class="chat__message__header">
                <span class="chat__message__name">Kiselyov_Dima</span>
                <span class="chat__message__time">16:48</span>
            </div>
            <div class="chat__message__text">
                Отлично, а ты как??? Отлично, а ты как??? Отлично, а ты как??? Отлично, а ты как??? Отлично, а ты как???
                Отлично, а ты как??? Отлично, а ты как??? Отлично, а ты как??? Отлично, а ты как???
            </div>
        </div>
        <div class="chat__message">
            <div class="chat__message__header">
                <span class="chat__message__name">Kiselyov_Dima</span>
                <span class="chat__message__time">16:48</span>
            </div>
            <div class="chat__message__text">
                Отлично, а ты как??? Отлично, а ты как??? Отлично, а ты как??? Отлично, а ты как??? Отлично, а ты как???
                Отлично, а ты как??? Отлично, а ты как??? Отлично, а ты как??? Отлично, а ты как???
            </div>
        </div>
        <div class="chat__message">
            <div class="chat__message__header">
                <span class="chat__message__name">Kiselyov_Dima</span>
                <span class="chat__message__time">16:48</span>
            </div>
            <div class="chat__message__text">
                Отлично, а ты как??? Отлично, а ты как??? Отлично, а ты как??? Отлично, а ты как??? Отлично, а ты как???
                Отлично, а ты как??? Отлично, а ты как??? Отлично, а ты как??? Отлично, а ты как???
            </div>
        </div>
        <div class="chat__message">
            <div class="chat__message__header">
                <span class="chat__message__name">Kiselyov_Dima</span>
                <span class="chat__message__time">16:48</span>
            </div>
            <div class="chat__message__text">
                Отлично, а ты как??? Отлично, а ты как??? Отлично, а ты как??? Отлично, а ты как??? Отлично, а ты как???
                Отлично, а ты как??? Отлично, а ты как??? Отлично, а ты как??? Отлично, а ты как???
            </div>
        </div>
        <div class="chat__message">
            <div class="chat__message__header">
                <span class="chat__message__name">Kiselyov_Dima</span>
                <span class="chat__message__time">16:48</span>
            </div>
            <div class="chat__message__text">
                Отлично, а ты как??? Отлично, а ты как??? Отлично, а ты как??? Отлично, а ты как??? Отлично, а ты как???
                Отлично, а ты как??? Отлично, а ты как??? Отлично, а ты как??? Отлично, а ты как???
            </div>
        </div>
        <div class="chat__message">
            <div class="chat__message__header">
                <span class="chat__message__name">Kiselyov_Dima</span>
                <span class="chat__message__time">16:48</span>
            </div>
            <div class="chat__message__text">
                Отлично, а ты как??? Отлично, а ты как??? Отлично, а ты как??? Отлично, а ты как??? Отлично, а ты как???
                Отлично, а ты как??? Отлично, а ты как??? Отлично, а ты как??? Отлично, а ты как???
            </div>
        </div>
    </div>
    <form class="chat__footer" autocomplete="off">
        <div class="chat__message_wrapper">
            <input type="text" name="message" class="chat__input" placeholder="Сообщение...">
            <input type="submit" class="chat__send" value="">
        </div>
    </form>
</div>

</body>

</html>