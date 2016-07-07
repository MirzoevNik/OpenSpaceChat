package com.mirzoevnik.openspacechat.websocket;

import com.mirzoevnik.openspacechat.entities.Message;
import javax.websocket.Session;
import java.util.*;

/**
 * @author MirzoevNik
 */
public class SessionManager {

    private final List<Session> sessions = Collections.synchronizedList(new ArrayList<Session>());
    private final List<Message> messages = Collections.synchronizedList(new ArrayList<Message>());

    /**
     * Singleton
     */
    private static SessionManager instance;

    private SessionManager() {
    }

    /**
     * @return manager of session
     */
    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }

        return instance;
    }

    /**
     * @param session new client's session
     */
    public void addSession(Session session) {
        sessions.add(session);
        synchronized (session) {
            for (Message message : messages) {
                sendToSession(session, message);
            }
        }
    }

    /**
     * @param session client's session
     */
    public void removeSession(Session session) {
        sessions.remove(session);
    }

    /**
     * @return list of all messages in chat
     */
    public List<Message> getMessages() {
        return messages;
    }

    /**
     * @param message new message
     */
    public void addMessage(Message message) {
        messages.add(message);
        sendToAllConnectedSessions(message);
    }

    /**
     * @param message sent message
     */
    private void sendToAllConnectedSessions(Message message) {
        synchronized (message) {
            for (Session session : sessions) {
                sendToSession(session, message);
            }
        }
    }

    /**
     * @param session client's session
     * @param message sent message to client
     */
    private void sendToSession(Session session, Message message) {
        synchronized (session) {
            try {
                session.getBasicRemote().sendText(message.toString());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}