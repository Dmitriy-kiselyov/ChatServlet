package ru.tanya_dima.chat;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class UserMessage extends Message {

    private final String text;

    public UserMessage(User sender, String text) {
        super(sender);
        this.text = text;
    }

    public UserMessage(User sender, String text, long time) {
        super(sender, time);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public JSONObject toJSON() {
        try {
            JSONObject json = super.toJSON();
            json.put("message", text);
            return json;
        }
        catch (JSONException e) {
            return new JSONObject();
        }
    }
}
