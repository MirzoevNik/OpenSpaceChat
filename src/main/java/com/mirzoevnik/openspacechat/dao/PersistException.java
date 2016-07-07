package com.mirzoevnik.openspacechat.dao;

/**
 * @author MirzoevNik
 */
public class PersistException extends Exception {

    /**
     * @param message the reason of exception (String)
     */
    public PersistException(String message) {
        super(message);
    }

    /**
     * @param cause the reason of exception (Throwable)
     */
    public PersistException(Throwable cause) {
        super(cause);
    }
}