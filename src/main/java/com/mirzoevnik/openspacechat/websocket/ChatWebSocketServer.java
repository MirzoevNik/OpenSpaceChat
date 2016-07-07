package com.mirzoevnik.openspacechat.websocket;

import com.mirzoevnik.openspacechat.entities.Message;
import com.mirzoevnik.openspacechat.entities.User;
import javax.json.Json;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.StringReader;

/**
 * @author MirzoevNik
 */
@ServerEndpoint("/websocket")
public class ChatWebSocketServer {

    /**
     * @param session session of client
     */
    @OnOpen
    public void onOpen(Session session) {
        SessionManager.getInstance().addSession(session);
        System.out.println("websocket is opened");
    }

    /**
     * @param session session of client
     */
    @OnClose
    public void onClose(Session session) {
        SessionManager.getInstance().removeSession(session);
        System.out.println("websocket is closed");
    }

    /**
     * @param error error of websocket
     */
    @OnError
    public void onError(Throwable error) {
        System.out.println("error websocket");
    }

    /**
     * @param message message from client
     * @param session session of client
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        try (JsonReader reader = Json.createReader(new StringReader(message))) {

            // get message in JSON structure
            JsonObject jsonMessage = reader.readObject();
            reader.close();

            // get data about autor of message
            User autor = new User();
            JsonObject autorJson = jsonMessage.getJsonObject("autor");
            autor.setId(autorJson.getInt("id"));
            autor.setLogin(autorJson.getString("login"));
            autor.setPassword(autorJson.getString("password"));
            autor.setName(autorJson.getString("name"));
            autor.setSurname(autorJson.getString("surname"));
            autor.setCountry(autorJson.getString("country"));

            // set data about message
            Message userMessage = new Message();
            userMessage.setAutor(autor);
            userMessage.setMessage(jsonMessage.getString("message"));

            // send message to all clients
            SessionManager.getInstance().addMessage(userMessage);
        }
        catch (JsonException e) {
            e.printStackTrace();
        }
    }
}
