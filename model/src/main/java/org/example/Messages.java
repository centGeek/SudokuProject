package org.example;

import java.util.Locale;
import java.util.ResourceBundle;

public class Messages {
    private Messages() {
    }

    public static String getMessage(String message, Locale locale) {
        return ResourceBundle.getBundle("messages", locale).getString(message);
    }
}
