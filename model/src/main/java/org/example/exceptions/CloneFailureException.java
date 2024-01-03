package org.example.exceptions;

import org.example.Messages;

import java.util.Locale;

public class CloneFailureException extends RuntimeException {

    String message;

    public CloneFailureException(String message) {
        this.message = message;
    }

}