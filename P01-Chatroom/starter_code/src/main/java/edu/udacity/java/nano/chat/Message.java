package edu.udacity.java.nano.chat;

import org.json.JSONException;
import org.json.JSONObject;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import java.io.IOException;

/**
 * WebSocket message model
 */
public class Message {

    // TODO: add message model. ENTER, CHAT, LEAVE

    private String from_user;
    private String msg;
    private MessageType type;
    private int onlineCount;

    public enum MessageType {
        ENTER,
        CHAT,
        LEAVE,
        SPEAK
    }

    public Message() {}

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }


    public String getFrom_user() {
        return from_user;
    }

    public void setFrom_user(String from_user) {
        this.from_user = from_user;
    }

    public String getContent() { return msg; }

    public void setContent(String msg) { this.msg =  msg; }

    public int getOnlineCount() { return onlineCount; }

    public void setOnlineCount(int onlineCount) { this.onlineCount = onlineCount; }

    public String getParamsAsJSON() {

        JSONObject jsonObject = new JSONObject();

        try{

            jsonObject.put("username", from_user);
            jsonObject.put("msg", msg);
            jsonObject.put("type", type);
            jsonObject.put("onlineCount", onlineCount);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }
}

