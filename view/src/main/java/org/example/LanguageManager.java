package org.example;

import java.io.*;
import java.util.Properties;

public class LanguageManager {
    private static final String CONFIG_FILE = "config.properties";
    private static final String LANGUAGE_KEY = "language";

    private Properties properties;

    public LanguageManager() {
        properties = new Properties();
        loadConfig();
    }

    public String getLanguage() {
        return properties.getProperty(LANGUAGE_KEY, "english");
    }

    public void setLanguage(String language) {
        properties.setProperty(LANGUAGE_KEY, language);
    }

    private void loadConfig() {
        try (InputStream input = new FileInputStream(CONFIG_FILE)) {
            properties.load(input);
        } catch (IOException e) {
            createConfigFile();
        }
    }

    private void createConfigFile() {
        try (OutputStream output = new FileOutputStream(CONFIG_FILE)) {
            properties.setProperty(LANGUAGE_KEY, "english");
            properties.store(output, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
