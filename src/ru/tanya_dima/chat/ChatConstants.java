package ru.tanya_dima.chat;

public interface ChatConstants {

    static public final String LOGIN = "ru.tanya_dima.chat.userLogin";
    static public final String APP = "ru.tanya_dima.chat.applicationState";

    static public final String API_ACTION = "action";
    static public final String API_REGISTER = "register";
    static public final String API_MESSAGES = "getMessages";

    static public final int ERR_UNKNOWN_PARAM = 1;
    static public final int ERR_LOGIN_FAIL = 2;
    static public final int ERR_AUTH_FAIL = 3;
    static public final int ERR_NO_PARAMS = 4;
    static public final int ERR_DEFAULT = 100;
}
