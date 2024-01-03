package org.example;

import java.util.ListResourceBundle;

public class Authors extends ListResourceBundle {

    private final Object[][] authors = {
            {"author1", "Łukasz Centkowski"},
            {"author2", "Maciej Zielak"}
    };

    @Override
    protected Object[][] getContents() {
        return authors;
    }
}