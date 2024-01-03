package org.example.exceptions;

import org.example.Messages;

import java.util.Locale;
import java.util.logging.Logger;

public class WrongValueException extends IllegalArgumentException {
    private final String message;
    Logger log = Logger.getLogger(String.valueOf(WrongValueException.class));

    public WrongValueException(String message) {
        this.message = message;
    }

    public String getLocalizedMessage(Locale locale) {
        return Messages.getMessage(message, locale);
    }
}
