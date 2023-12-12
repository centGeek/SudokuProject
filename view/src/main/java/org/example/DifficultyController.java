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
import java.util.Random;

public class DifficultyController {

    @FXML
    private void handleGenerateButton(ActionEvent event) {

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


    public SudokuBoard copySudokuBoard() {
        SudokuSolver sudokuSolver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(sudokuSolver);
        sudokuBoard.solveGame();
        return sudokuBoard.clone();
    }

    public SudokuBoard deleteRandomNumbers(SudokuBoard sudokuBoard, String buttonName) {
        int amount = howManyDeletedFields(buttonName);
        Random random = new Random();

        int deleted = 0;
        while (deleted < amount) {
            int row = random.nextInt(9);
            int col = random.nextInt(9);

            if (sudokuBoard.get(row, col) != 0) {
                sudokuBoard.set(row, col, 0);
                deleted++;
            }
        }

        return sudokuBoard;
    }

    private int howManyDeletedFields(String buttonName) {
        return switch (buttonName) {
            case "Easy" -> DifficultyLevel.EASY.getValue();
            case "Normal" -> DifficultyLevel.MEDIUM.getValue();
            case "Hard" -> DifficultyLevel.HARD.getValue();
            default -> throw new RuntimeException("Something unexpected happened");
        };
    }
}
