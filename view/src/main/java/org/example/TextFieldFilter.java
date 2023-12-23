package org.example;

import javafx.scene.control.TextFormatter;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class TextFieldFilter implements UnaryOperator<TextFormatter.Change> {
    @Override
    public TextFormatter.Change apply(TextFormatter.Change change) {
        Pattern pattern = Pattern.compile("[1-9 ]?");
        if (pattern.matcher(change.getControlNewText()).matches()) {
            return change;
        } else {
            return null;
        }
    }
}
