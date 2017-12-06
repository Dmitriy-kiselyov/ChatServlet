package ru.tanya_dima.chat;

public interface ChatConstants {

    String LOGIN = "ru.tanya_dima.chat.userLogin";
    String APP = "ru.tanya_dima.chat.applicationState";

    String API_ACTION = "action";
    String API_REGISTER = "register";
    String API_MESSAGES = "getMessages";
    String API_POST_MESSAGE = "postMessage";

    int ERR_UNKNOWN_PARAM = 1;
    int ERR_LOGIN_FAIL = 2;
    int ERR_AUTH_FAIL = 3;
    int ERR_NO_PARAMS = 4;
    int ERR_DEFAULT = 100;
}
