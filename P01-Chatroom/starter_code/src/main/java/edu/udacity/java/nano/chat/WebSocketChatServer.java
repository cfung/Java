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
        //TODO: add send message method.
        System.out.println("starting sendMessageToAll..");
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
        //TODO: add on open connection.
        System.out.println("onOpen (username).." + username);

        this.session = session;
        //onlineSessions.put(session);
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
        //TODO: add send message.
        //jsonStr.(users.get(session.getId()));
        /*

        lo Mouhamadou: In Messages: Create String method with the parameters: type, username, message, onlineCount,
        return the parameters as a new Message using the JSON.toJSONString method (import com.alibaba.fastjson.JSON).
        In WebSocketChatServer: Create a Message instance and use JSON.parseObject method to add arguments String method and Message class.
        Use SendMessageToAll method to add the Message String method and parameters(ex. method.getusername()).
         */


        System.out.println("onMessage.." + jsonStr);
        Message message = new Message();

        try {
            org.json.JSONObject jsonObject = new JSONObject(jsonStr);
            System.out.println("onMessage username: " + jsonObject.getString("username"));
            System.out.println("session ID: " + session.getId());
            String user = jsonObject.getString("username");
            String content = jsonObject.getString("msg");


            message.setFrom_user(user);
            message.setContent(content);
            message.setType(Message.MessageType.SPEAK);
            message.setOnlineCount(users.size());


        } catch (JSONException e) {
            e.printStackTrace();
        }

        // {"username":"test","msg":"message", "type":"SPEAK", "onlineCount":users.size()}
        System.out.println("message to be sent..." + message.getParamsAsJSON());
        sendMessageToAll(message.getParamsAsJSON());
    }

    /**
     * Used to decorate a Java method that is called by the container when the WebSocket connection closes
     * Close connection, 1) remove session, 2) update user.
     */
    @OnClose
    public void onClose(Session session) {
        //TODO: add close connection.
        chatServers.remove(this);
        // TODO: update user
        System.out.println("num of users before removal...: " + users.size());
        users.remove(session.getId());
        System.out.println("num of users after removal...: " + users.size());
    }

    /**
     * Print exception.
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("throwing error...");
        error.printStackTrace();
    }

    private void sendMessage(String message) {
        System.out.println("inside sendMessage...");
        try {
            this.session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
