package edu.udacity.java.nano.chat;

/**
 * WebSocket message model
 */
public class Message {

    // TODO: add message model. ENTER, CHAT, LEAVE

    private String from_user;
    private String to_user;
    private MessageType type;

    private enum MessageType {
        ENTER,
        CHAT,
        LEAVE
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

    public String getTo_user() {
        return to_user;
    }

    public void setTo_user(String to_user) {
        this.to_user = to_user;
    }
}
