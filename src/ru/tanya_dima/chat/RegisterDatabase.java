package ru.tanya_dima.chat;

import java.util.HashMap;
import java.util.Map;

public class RegisterDatabase {

    private static Map<String, User> users = new HashMap<>();

    public static boolean register(User user) throws DatabaseException {
        User userFound = users.get(user.getLogin());
        if (userFound == null) {
            users.put(user.getLogin(), user);
            return true;
        } else if (!userFound.getPassword().equals(user.getPassword())) {
            throw new DatabaseException("Пользователь с таким именем уже существует / неверный пароль");
        }

        return false;
    }

    public static User getUser(String login) {
        return users.get(login);
    }

}
