package org.example.exceptions;

import org.example.Messages;

import java.util.Locale;

public class WrongValueException extends IllegalArgumentException {
    private final String message;

    public WrongValueException(String message) {
        this.message = message;
    }

    public String getLocalizedMessage(Locale locale) {
        return Messages.getMessage(message, locale);
    }
}
