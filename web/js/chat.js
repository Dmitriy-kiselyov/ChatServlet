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

function fetchUserMessages(messages) {
    if (!Array.isArray(messages))
        messages = [messages];

    var chat = $("#chat");
    messages.forEach(function (message) {
        var header = $("<div>").addClass("chat__message__header")
            .append($("<span>").addClass("chat__message__name").text(message.user))
            .append($("<span>").addClass("chat__message__time").text(parseTime(message.time)));
        var text = $("<div>").addClass("chat__message__text").text(message.message);

        var wrapper = $("<div>").addClass("chat__message")
            .append(header)
            .append(text);
        chat.append(wrapper);
    });

    scrollChat();
}

function fetchSystemMessage(message) {
    var chat = $("#chat");

    var status = message.status == "enter" ? " зашел в беседу" : " вышел из беседы";
    var text = $("<div>").addClass("chat__message__text-system")
        .append($(document.createTextNode("Пользователь ")))
        .append($("<span>").addClass("chat__message__name").text(message.user))
        .append($(document.createTextNode(status)));

    var wrapper = $("<div>").addClass("chat__message")
        .append(text);
    chat.append(wrapper);

    scrollChat();
}

function scrollChat() {
    var chat = $("#chat");
    chat.parent().scrollTop(chat.height());
}

function getMessages() {
    $.ajax({
        url: "ChatServlet?action=getMessages",
        type: "GET",
        success: function (data) {
            fetchUserMessages(data.response);
        },
        complete: function () {
            cometMessage();
        }
    });
}

function cometMessage() {
    $.ajax({
        url: "ChatServlet?action=cometMessage",
        type: "GET",
        success: function (data) {
            var message = data.response;
            if (message.message)
                fetchUserMessages(message);
            else if (message.status)
                fetchSystemMessage(message);

            playMessage();
            cometMessage();
        },
        error: function () {
            setTimeout(cometMessage, 2000);
        }
    });
}

function playMessage() {
    var soundEl = $(".chat__header__sound");
    if (soundEl && !soundEl.hasClass("chat__header__sound-mute")) {
        var audio = playMessage.audio || (playMessage.audio = new Audio("/sound/message.mp3"));
        audio.currentTime = 0;
        audio.play();
    }
}

function sendMessage() {
    var input = $("#send_input");
    var message = input.val().trim();
    if (message.length == 0)
        return;

    $.ajax({
        url: "ChatServlet?action=sendMessage",
        method: "POST",
        data: {
            message: message
        },
        beforeSend: function () {
            input.val("").focus();
        },
        success: function (data) {
            console.log(data);
        },
        error: function () {
            console.log("POST ERROR");
        }
    });
}

function parseTime(millis) {
    var now = new Date();
    var sendTime = new Date(millis);

    var formatDate = function (t) {
        if (t < 10)
            t = "0" + t;
        return t;
    };

    //Дата
    var timeStr = "";
    if (new Date(now.getFullYear(), now.getMonth(), now.getDate()).getTime() ===
        new Date(sendTime.getFullYear(), sendTime.getMonth(), sendTime.getDate()).getTime()) {
        timeStr += "";
    } else if (new Date(now.getFullYear(), now.getMonth(), now.getDate() - 1).getTime() ===
        new Date(sendTime.getFullYear(), sendTime.getMonth(), sendTime.getDate()).getTime()) {
        timeStr += "вчера в ";
    } else {
        timeStr += formatDate(sendTime.getDate()) + "." + formatDate(sendTime.getMonth() + 1);
        if (now.getFullYear() !== sendTime.getFullYear())
            timeStr += "." + sendTime.getFullYear();
        timeStr += " в ";
    }

    //Время
    timeStr += formatDate(sendTime.getHours()) + ":" + formatDate(sendTime.getMinutes());

    return timeStr;
}

$(function () {
    placeholderAutoHide($("#send_input"));

    setTimeout(function () {
        getMessages();
    }, 10);

    var soundSwitch = $(".chat__header__sound");
    if (localStorage.getItem("sound") == "off") {
        soundSwitch.addClass("chat__header__sound-mute");
    }
    soundSwitch.click(function () {
        soundSwitch.toggleClass("chat__header__sound-mute");
        if (soundSwitch.hasClass("chat__header__sound-mute"))
            localStorage.setItem("sound", "off");
        else
            localStorage.setItem("sound", "on");
    });

    $("#send_form").on("submit", function () {
        sendMessage();
        return false;
    });
});