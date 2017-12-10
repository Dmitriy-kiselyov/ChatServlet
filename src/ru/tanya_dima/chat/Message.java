package ru.tanya_dima.chat;

import org.json.JSONException;
import org.json.JSONObject;

public class Message {

    private final User sender;
    private final long time;

    public Message(User sender) {
        this.sender = sender;
        time = System.currentTimeMillis();
    }

    public Message(User sender, long time) {
        this.sender = sender;
        this.time = time;
    }

    public User getSender() {
        return sender;
    }

    public long getTime() {
        return time;
    }

    public JSONObject toJSON() {
        try {
            JSONObject json = new JSONObject();
            json.put("user", sender.getLogin());
            json.put("time", time);
            return json;
        }
        catch (JSONException e) {
            return new JSONObject();
        }
    }

}
