package org.example;

import javafx.util.converter.NumberStringConverter;

public class Converter extends NumberStringConverter {
    @Override
    public Number fromString(String s) {
        if (s.isEmpty()) {
            return 0;
        }
        if (s.length() == 1) {
            return Integer.parseInt(s);
        }

        return super.fromString(s);
    }

    @Override
    public String toString(Number number) {
        if (number.intValue() == 0) {
            return "";
        }
        if (number.intValue() > 0 && number.intValue() <= 9) {
            return number.toString();
        }
        return super.toString(number);
    }
}
