package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.Objects;

import static org.example.DifficultyLevel.copySudokuBoard;
import static org.example.DifficultyLevel.deleteRandomNumbers;

public class DifficultyController {

    @FXML
    public void handleGenerateButton(ActionEvent event) {

        try {
            Button clickedButton = (Button) event.getSource();
            String buttonName = clickedButton.getText();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Board.fxml")));
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            GridPane gridPane = (GridPane) root.lookup("#gridPane");
            stage.setScene(new Scene(root, 600, 600));
            SudokuBoard sudokuBoardCopied = copySudokuBoard();
            SudokuBoard sudokuBoard = deleteRandomNumbers(sudokuBoardCopied, buttonName);
            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                    int value = sudokuBoard.get(row, col);
                    Label label = new Label(value != 0 ? String.valueOf(value) : "");
                    label.setMaxWidth(Double.MAX_VALUE - 10.0);
                    label.setMaxHeight(Double.MAX_VALUE - 10.0);
                    label.setAlignment(Pos.CENTER);
                    gridPane.add(label, col, row);
                }
            }

            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
