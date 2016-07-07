package com.mirzoevnik.openspacechat.entities;

/**
 * @author MirzoevNik
 */
public class Message {

    private User autor;
    private String message;

    public Message() {
        message = "";
    }

    public void setAutor(User autor) {
        this.autor = autor;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getAutor() {
        return autor;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "" + autor.getLogin() + ": " + message + "\n";
    }
}
