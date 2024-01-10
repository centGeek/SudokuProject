package org.example.exceptions;

public class CloneFailureException extends RuntimeException {

    String message;

    public CloneFailureException(String message) {
        super(message);
    }

}