package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class Main extends Application {

    private final Locale localePL = Locale.getDefault();
    private final Locale localeEN = new Locale("en", "EN");
    private final ResourceBundle langText = ResourceBundle.getBundle("MenuText", localeEN);
    @Override
    public void start(Stage stage) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Main.fxml")));
            Button button1 = (Button) root.lookup("#button1");
            Button button2 = (Button) root.lookup("#button2");
            Button button3 = (Button) root.lookup("#button3");
            Label label = (Label) root.lookup("#label");
            Label label1 = (Label) root.lookup("#label1");
            VBox myVBox = new VBox(10);

            button1.setText(langText.getString("button1"));
            button2.setText(langText.getString("button2"));
            button3.setText(langText.getString("button3"));

            label.setFont(Font.font("Arial", FontWeight.BOLD, 16));
            label1.setFont(Font.font("Arial", FontWeight.BOLD, 20));
            myVBox.getChildren().addAll(label1, label, button1, button2, button3);
            myVBox.setAlignment(Pos.CENTER);
            myVBox.setSpacing(10);

            Scene scene = new Scene(myVBox, 400, 400);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
