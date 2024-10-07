package org.example.jpa.Exception;

public class MessageNotReadableException extends RuntimeException {

    public MessageNotReadableException() {
        super("Unable to read request data.");
    }
}
