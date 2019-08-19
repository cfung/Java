package edu.udacity.java.nano.chat;

/**
 * WebSocket message model
 */
public class Message {

    // TODO: add message model. ENTER, CHAT, LEAVE
    private String type;

    public Message() {}

    public Message(String type) {this.type = type;}


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
