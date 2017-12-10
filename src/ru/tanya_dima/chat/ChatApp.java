package ru.tanya_dima.chat;

import org.json.JSONArray;

import java.util.ArrayList;

public class ChatApp {

    public ChatApp() {
        Database.init();
        Database.preset();
    }

    public void addMessage(UserMessage message) {
        Database.addMessage(message);
    }

    public ArrayList<UserMessage> getMessages() {
        return Database.getMessages();
    }

    public JSONArray getJSONMessages() {
        JSONArray arr = new JSONArray();
        for (UserMessage m : Database.getMessages())
            arr.put(m.toJSON());

        return arr;
    }

    public User getUser(String login) {
        return Database.getUser(login);
    }

    public boolean registerUser(User user) throws DatabaseException {
        User userFound = Database.getUser(user.getLogin());
        if (userFound == null) {
            Database.addUser(user);
            return true;
        } else if (!userFound.getPassword().equals(user.getPassword())) {
            throw new DatabaseException("Пользователь с таким именем уже существует / неверный пароль");
        }

        return false;
    }

}
