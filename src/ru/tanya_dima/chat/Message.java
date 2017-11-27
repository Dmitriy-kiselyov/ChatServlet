package ru.tanya_dima.chat;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class Message {

    private final User sender;
    private final String text;
    private final Date date;

    public Message(User sender, String text) {
        this.sender = sender;
        this.text = text;
        date = new Date();
    }

    public User getSender() {
        return sender;
    }

    public String getText() {
        return text;
    }

    public Date getDate() {
        return date;
    }

    public JSONObject toJSON() {
        try {
            JSONObject json = new JSONObject();
            json.put("user", sender.getLogin());
            json.put("message", text);
            json.put("time", date.getTime());
            return json;
        }
        catch (JSONException e) {
            return new JSONObject();
        }
    }
}
