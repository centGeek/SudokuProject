package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class Main extends Application {

    private final LanguageManager languageManager = new LanguageManager();
    private Logger logger = Logger.getLogger(Main.class.getName());
    private ResourceBundle langText;
    private Label label;
    private Label label1;
    private Button button1;
    private Button button2;
    private Button button3;
    private Label authors;
    private Button polishButton;
    private Button englishButton;
    private Label languageLabel;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Main.fxml"));
        Parent root = loader.load();
        button1 = (Button) root.lookup("#button1");
        button2 = (Button) root.lookup("#button2");
        button3 = (Button) root.lookup("#button3");
        polishButton = new Button("polish");
        englishButton = new Button("english");
        logger.info("Application started");
        DifficultyController difficultyController = loader.getController();
        difficultyController.setLanguageManager(languageManager);


        VBox myVBox = new VBox(10);
        label = (Label) root.lookup("#label");
        label1 = (Label) root.lookup("#label1");

        String language = languageManager.getLanguage();
        langText = ResourceBundle.getBundle("MenuText", getLocaleFromLanguage(language, logger));

        label.setText(langText.getString("label"));
        label1.setText(langText.getString("label1"));

        button1.setText(langText.getString("button1"));
        button2.setText(langText.getString("button2"));
        button3.setText(langText.getString("button3"));
        languageLabel = (Label) root.lookup("#language");
        languageLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 10));
        polishButton.setOnAction(event -> {
            setLanguage("polish");
            updateLabels();
        });

        englishButton.setOnAction(event -> {
            setLanguage("english");
            updateLabels();
        });

        label.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        label1.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        Authors authorsClass = new Authors();
        Object[][] contents = authorsClass.getContents();
        authors = new Label(langText.getString("authors")+contents[0][1] + ", " + contents[1][1]);
        authors.setFont(Font.font("Arial", FontWeight.NORMAL, 10));
        authors.setAlignment(Pos.CENTER);

        myVBox.getChildren().addAll(label1, label, button1, button2, button3, authors);

        myVBox.setAlignment(Pos.CENTER);
        myVBox.setSpacing(10);

        Separator separator = new Separator();
        HBox languageButtonsBox = new HBox(languageLabel, polishButton, englishButton);
        languageButtonsBox.setAlignment(Pos.CENTER);
        languageButtonsBox.setSpacing(10);

        VBox bottomVBox = new VBox(separator, languageButtonsBox);
        bottomVBox.setAlignment(Pos.CENTER);

        BorderPane rootPane = new BorderPane();
        rootPane.setTop(myVBox);
        rootPane.setBottom(bottomVBox);

        Scene scene = new Scene(rootPane, 600, 400);
        stage.setScene(scene);
        stage.show();
    }

    static Locale getLocaleFromLanguage(String language, Logger logger) {
        Locale locale;
        if (language.equalsIgnoreCase("polish")) {
            logger.info("Język ustawiono na polski");
            locale = new Locale("pl", "PL");

        } else {
            logger.info("Language set to english");
            locale = new Locale("en", "EN");
        }
        return locale;
    }


    private void setLanguage(String language) {
        languageManager.setLanguage(language);
    }

    private void updateLabels() {
        languageLabel.setText(langText.getString("languageLabel"));
        englishButton.setText(langText.getString("languageEng"));
        polishButton.setText(langText.getString("languagePol"));
        langText = ResourceBundle.getBundle("MenuText", getLocaleFromLanguage(languageManager.getLanguage(), logger));
        label.setText(langText.getString("label"));
        label1.setText(langText.getString("label1"));
        button1.setText(langText.getString("button1"));
        button2.setText(langText.getString("button2"));
        button3.setText(langText.getString("button3"));
        Authors authorsClass = new Authors();
        Object[][] contents = authorsClass.getContents();
        authors.setText(langText.getString("authors") + contents[0][1] + ", " + contents[1][1]);

    }

    public static void main(String[] args) {
        launch(args);
    }
}
