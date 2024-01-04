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
import org.example.exceptions.FileDaoException;
import org.example.exceptions.ProcessingDataException;

import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.example.DifficultyLevel.deleteRandomNumbers;
import static org.example.DifficultyLevel.startGame;

public class DifficultyController {
    private LanguageManager languageManager;
    private ResourceBundle langText;
    public static final Logger logger = LoggerFactory.getLogger(DifficultyController.class);

    public void setLanguageManager(LanguageManager languageManager) {
        this.languageManager = languageManager;
    }

    private Locale locale;
    private SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
    private SudokuBoard sudokuBoardInGame = new SudokuBoard(new BacktrackingSudokuSolver());

    @FXML
    public void handleGenerateButton(ActionEvent actionEvent) {
        try {
            String language = languageManager.getLanguage();
            locale = getLocale(language);
            langText = ResourceBundle.getBundle("BoardText", locale);

            VBox mainContainer = new VBox();
            mainContainer.setAlignment(Pos.CENTER);
            mainContainer.setMinSize(840, 650);
            mainContainer.setMaxSize(2000, 2000);

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Board.fxml")));
            Pane pane = (Pane) root.lookup("#pane");
            mainContainer.getChildren().add(pane);

            SudokuBoard sudokuBoardCopied;

            sudokuBoard = startGame();
            sudokuBoardCopied = clone(sudokuBoard);
            Button clickedButton = (Button) actionEvent.getSource();
            String buttonName = clickedButton.getId();
            sudokuBoardInGame = deleteRandomNumbers(sudokuBoardCopied, buttonName, locale);
            GridPane gridPane = (GridPane) root.lookup("#gridPane");
            textFieldIteration(sudokuBoardInGame, gridPane);
            Label label2 = (Label) root.lookup("#label2");
            checkButtonConfigured(root, sudokuBoardInGame, label2);

            Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(mainContainer));
            stage.show();
            Button toFile = (Button) root.lookup("#toFile");
            toFile.setText(langText.getString("toFile"));
            DaoDecorator daoDecorator = new FileDaoDecorator();

            toFile.setOnAction(e -> {
                try {
                    daoDecorator.saveOriginalAndCopy(sudokuBoardInGame, sudokuBoard, locale);
                } catch (FileDaoException ex) {
                    logger.error(ex.getMessage());
                }
            });
            Button fromFile = (Button) root.lookup("#fromFile");
            fromFile.setText(langText.getString("fromFile"));
            fromFile.setOnAction(e -> {
                try {
                    daoDecorator.readOriginal(locale);
                    daoDecorator.readCopy(locale);
                } catch (FileDaoException ex) {
                    logger.error(ex.getMessage());

                }
            });

        } catch (IOException | NoSuchMethodException ex) {
            String message = ResourceBundle.getBundle("messages", locale).getString("errorBoardfxml");
            throw new ProcessingDataException(message);
        }
    }


    void textFieldIteration(SudokuBoard sudokuBoardInGame, GridPane gridPane) throws NoSuchMethodException {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                int value = sudokuBoardInGame.get(row, col);

                Label label = new Label(value != 0 ? String.valueOf(value) : "");
                label.setMaxWidth(Double.MAX_VALUE - 10.0);
                label.setMaxHeight(Double.MAX_VALUE - 10.0);
                label.setAlignment(Pos.CENTER);
                TextField textField = generateTextField(value, sudokuBoardInGame, row, col);
                gridPane.add(textField, col, row);
            }
        }
    }

    private void checkButtonConfigured(Parent root, SudokuBoard sudokuBoardInGame, Label label2) {
        Button button = (Button) root.lookup("#check");
        button.setText(langText.getString("check"));
        button.setOnAction(e -> {
            if (validate(sudokuBoardInGame)) {
                label2.setText(langText.getString("win"));
                label2.setStyle("-fx-text-fill: green");
                label2.setFont(javafx.scene.text.Font.font("Arial", FontWeight.BOLD, 20));
            } else {
                label2.setText(langText.getString("lose"));
                label2.setStyle("-fx-text-fill: red");
                label2.setFont(javafx.scene.text.Font.font("Arial", FontWeight.BOLD, 20));
            }
        });
    }

    private Locale getLocale(String language) {
        if (language.equalsIgnoreCase("english")) {
            locale = new Locale("en");
        } else if (language.equalsIgnoreCase("polish")) {
            locale = new Locale("pl");
        } else {
            locale = new Locale("en");
        }
        return locale;
    }

    private static SudokuBoard clone(SudokuBoard sudokuBoard) {
        SudokuSolver sudokuSolver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoardCopied = new SudokuBoard(sudokuSolver);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                sudokuBoardCopied.set(i, j, sudokuBoard.get(i, j));
            }
        }
        return sudokuBoardCopied;
    }


    private TextField generateTextField(int value, SudokuBoard sudokuBoardInGame, int row, int col)
            throws NoSuchMethodException {

        JavaBeanIntegerPropertyBuilder javaBeanIntegerPropertyBuilder = JavaBeanIntegerPropertyBuilder.create();


        JavaBeanIntegerProperty bean;

        bean = javaBeanIntegerPropertyBuilder.bean(sudokuBoardInGame.getSudokuField(row, col)).name("value")
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


        borderWidth = settingBorderWidth(row, col, borderWidth);

        textField.setStyle("-fx-border-width: " + borderWidth + "; -fx-border-color: " + borderColor + ";");

        if (value != 0) {
            textField.setEditable(false);
            textField.setText(Integer.toString(value));
        } else {
            textField.setCursor(Cursor.HAND);
        }

        return textField;
    }

    private String settingBorderWidth(int row, int col, String borderWidth) {
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
        return borderWidth;
    }

    boolean validate(SudokuBoard sudokuBoardInGame) {
        ResourceBundle win = ResourceBundle.getBundle("logger", locale);

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (sudokuBoardInGame.get(row, col) == 0) {
                    logger.info(win.getString("lose"));
                    return false;
                }
                if (!sudokuBoardInGame.verify(row, col)) {
                    logger.info(win.getString("lose"));
                    return false;
                }
            }
        }
        logger.info(win.getString("win"));
        return true;
    }

}
