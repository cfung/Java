package edu.udacity.java.nano.chat;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * WebSocket Server
 *
 * @see ServerEndpoint WebSocket Client
 * @see Session   WebSocket Session
 */

@Component
@ServerEndpoint(value = "/chat", decoders = MessageDecoder.class, encoders = MessageEncoder.class)
public class WebSocketChatServer {

    /**
     * All chat sessions.
     */
    private static Map<String, Session> onlineSessions = new ConcurrentHashMap<>();

    private Session session;

    private static Set<WebSocketChatServer> chatServers = new CopyOnWriteArraySet<>();

    private static HashMap<String, String> users = new HashMap<>();



    private static void sendMessageToAll(String msg) {
        //Completed: add send message method.

        for (WebSocketChatServer server : chatServers) {

            server.sendMessage(msg);
        }
    }

    /**
     * invoked by the container when a new WebSocket connection is initiated
     * Open connection, 1) add session, 2) add user.
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {
        //Completed: add on open connection.
        this.session = session;

        session.getRequestParameterMap();
        chatServers.add(this);
        users.put(session.getId(), username);

        Message message = new Message();
        message.setFrom_user(users.get(session.getId()));
    }

    /**
     * receives the information from the WebSocket container when a message is sent to the endpoint
     * Send message, 1) get username and session, 2) send message to all.
     */
    @OnMessage
    public void onMessage(Session session, String jsonStr) {
        //Completed: add send message.
        Message message = new Message();

        try {
            org.json.JSONObject jsonObject = new JSONObject(jsonStr);
            String user = jsonObject.getString("username");
            String content = jsonObject.getString("msg");
            message.setFrom_user(user);
            message.setContent(content);
            message.setType(Message.MessageType.SPEAK);
            message.setOnlineCount(users.size());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        sendMessageToAll(message.getParamsAsJSON());
    }

    /**
     * Used to decorate a Java method that is called by the container when the WebSocket connection closes
     * Close connection, 1) remove session, 2) update user.
     */
    @OnClose
    public void onClose(Session session) {
        //Completed: add close connection.
        chatServers.remove(this);
        // Completed: update user
        users.remove(session.getId());
    }

    /**
     * Print exception.
     */
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    private void sendMessage(String message) {

        try {
            this.session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
