package org.example;

import javafx.beans.property.adapter.JavaBeanIntegerProperty;
import javafx.beans.property.adapter.JavaBeanIntegerPropertyBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

import static org.example.DifficultyLevel.copySudokuBoard;
import static org.example.DifficultyLevel.deleteRandomNumbers;


public class DifficultyController {
    private static final int size = 9;
    private final Locale localePL = Locale.getDefault();
    private final Locale localeEN = new Locale("en", "EN");

    private final ResourceBundle langText = ResourceBundle.getBundle("BoardText", localeEN);

    @FXML
    public void handleGenerateButton(ActionEvent actionEvent) {
        try {
            Button clickedButton = (Button) actionEvent.getSource();
            String buttonName = clickedButton.getId();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Board.fxml")));

            Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
            Label label2 = (Label) root.lookup("#label2");

            VBox mainContainer = new VBox();
            mainContainer.setAlignment(Pos.CENTER);
            mainContainer.setMinSize(840, 650);
            mainContainer.setMaxSize(2000, 2000);

            Pane pane = (Pane) root.lookup("#pane");
            mainContainer.getChildren().add(pane);

            GridPane gridPane = (GridPane) root.lookup("#gridPane");

            SudokuBoard sudokuBoardCopied = copySudokuBoard();
            SudokuBoard sudokuBoard = deleteRandomNumbers(sudokuBoardCopied, buttonName);

            Button button = (Button) root.lookup("#check");
            button.setText(langText.getString("check"));
            button.setOnAction(e -> {
                if (validate(sudokuBoard)) {
                    label2.setText(langText.getString("win"));
                    label2.setStyle("-fx-text-fill: green");
                    label2.setFont(javafx.scene.text.Font.font("Arial", FontWeight.BOLD, 20));
                } else {
                    label2.setText(langText.getString("lose"));
                    label2.setStyle("-fx-text-fill: red");
                    label2.setFont(javafx.scene.text.Font.font("Arial", FontWeight.BOLD, 20));
                }
            });

            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                    int value = sudokuBoard.get(row, col);

                    Label label = new Label(value != 0 ? String.valueOf(value) : "");
                    label.setMaxWidth(Double.MAX_VALUE - 10.0);
                    label.setMaxHeight(Double.MAX_VALUE - 10.0);
                    label.setAlignment(Pos.CENTER);
                    TextField textField = generateTextField(value, sudokuBoardCopied, row, col);
                    gridPane.add(textField, col, row);
                }
            }
            stage.setScene(new Scene(mainContainer));
            stage.show();
        } catch (IOException |
                 NoSuchMethodException ex) {
            throw new RuntimeException(ex);
        }


    }


    private TextField generateTextField(int value, SudokuBoard sudokuBoard, int row, int col) throws NoSuchMethodException {

        JavaBeanIntegerPropertyBuilder javaBeanIntegerPropertyBuilder = JavaBeanIntegerPropertyBuilder.create();


        JavaBeanIntegerProperty bean;

        bean = javaBeanIntegerPropertyBuilder.bean(sudokuBoard.getSudokuField(row, col)).name("value")
                .setter("setFieldValue").getter("getFieldValue").build();


        StringConverter stringIntegerConverter = new IntegerStringConverter();
        TextField textField = new TextField();
        textField.textProperty().bindBidirectional(bean, stringIntegerConverter);
        TextFormatter<Number> textFormatter = new TextFormatter<>(new Converter(), 0, new TextFieldFilter());
        textField.setTextFormatter(textFormatter);
        textField.setMaxWidth(Double.MAX_VALUE - 12.0);
        textField.setMaxHeight(Double.MAX_VALUE - 12.0);
        textField.setAlignment(Pos.CENTER);


        String borderWidth = "0";
        String borderColor = "black";


        if ((row % 3 == 0) && (col % 3 == 0)) {
            borderWidth = "2 0 0 2";
        } else if ((row % 3 == 0) && (col % 3 == 2)) {
            borderWidth = "2 2 0 0";
        } else if ((row % 3 == 2) && (col % 3 == 0)) {
            borderWidth = "0 0 2 2";
        } else if ((row % 3 == 2) && (col % 3 == 2)) {
            borderWidth = "0 2 2 0";
        } else if (row % 3 == 0) {
            borderWidth = "2 0 0 0";
        } else if (row % 3 == 2) {
            borderWidth = "0 0 2 0";
        } else if (col % 3 == 0) {
            borderWidth = "0 0 0 2";
        } else if (col % 3 == 2) {
            borderWidth = "0 2 0 0";
        }

        textField.setStyle("-fx-border-width: " + borderWidth + "; -fx-border-color: " + borderColor + ";");

        if (value != 0) {
            textField.setEditable(false);
            textField.setText(Integer.toString(value));
        } else {
            textField.setCursor(Cursor.HAND);
        }

        return textField;
    }

    private boolean validate(SudokuBoard sudokuBoard) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (sudokuBoard.get(row, col) == 0) {
                    return false;
                }
                if (!sudokuBoard.verify(row, col)) {
                    return false;
                }
            }
        }
        return true;
    }

}
