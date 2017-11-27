package ru.tanya_dima.chat;

import org.json.JSONArray;

import java.util.ArrayList;

public class ChatApp {

    private ArrayList<Message> messages;

    public ChatApp() {
        messages = new ArrayList<>();
    }

    public void addMessage(Message message) {
        messages.add(message);
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public JSONArray getJSONMessages() {
        JSONArray arr = new JSONArray();
        for (Message m : messages)
            arr.put(m.toJSON());

        return arr;
    }

}
