package org.example.jpa.Exception;

public class MethodArgumentTypeException extends RuntimeException {

    //issue: Name not valid
    //issue: Phone not valid
    //issue: Email not valid


    public MethodArgumentTypeException(String message) {
        super(message);
    }
}
