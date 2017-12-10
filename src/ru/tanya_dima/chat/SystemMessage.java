package ru.tanya_dima.chat;

import org.json.JSONException;
import org.json.JSONObject;

public class SystemMessage extends Message {

    private final Status status;

    public SystemMessage(User sender, Status status) {
        super(sender);
        this.status = status;
    }

    public SystemMessage(User sender, Status status, long time) {
        super(sender, time);
        this.status = status;
    }

    public JSONObject toJSON() {
        try {
            JSONObject json = super.toJSON();
            json.put("status", status.name().toLowerCase());
            return json;
        }
        catch (JSONException e) {
            return new JSONObject();
        }
    }

    public static enum Status {ENTER, LEFT}

}
