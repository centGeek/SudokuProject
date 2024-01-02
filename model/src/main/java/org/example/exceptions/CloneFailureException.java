package org.example.exceptions;

import org.example.Messages;

import java.util.Locale;

public class CloneFailureException extends RuntimeException {

    String message;

    public CloneFailureException() {
        String message = "cloneFailure";
    }

    public String getLocalizedMessage(Locale locale) {
        return Messages.getMessage(message, locale);
    }
}