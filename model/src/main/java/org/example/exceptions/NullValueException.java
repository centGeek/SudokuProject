package org.example.exceptions;

import org.example.Messages;

import java.util.Locale;

public class NullValueException extends NullPointerException {
    private final String message;

    public NullValueException(String message) {
        this.message = message;
    }

    public String getLocalizedMessage(Locale locale) {
        return Messages.getMessage(message, locale);
    }
}