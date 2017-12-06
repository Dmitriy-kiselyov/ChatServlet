package ru.tanya_dima.chat;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class Message {

    private final User sender;
    private final String text;
    private final long time;

    public Message(User sender, String text) {
        this.sender = sender;
        this.text = text;
        time = System.currentTimeMillis();
    }

    public Message(User sender, String text, long time) {
        this.sender = sender;
        this.text = text;
        this.time = time;
    }

    public User getSender() {
        return sender;
    }

    public String getText() {
        return text;
    }

    public long getTime() {
        return time;
    }

    public JSONObject toJSON() {
        try {
            JSONObject json = new JSONObject();
            json.put("user", sender.getLogin());
            json.put("message", text);
            json.put("time", time);
            return json;
        }
        catch (JSONException e) {
            return new JSONObject();
        }
    }
}
