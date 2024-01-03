package org.example;

import org.example.exceptions.FileDaoException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Locale;

public class FileDaoDecoratorTest {
    @Test
    public void testFileDaoDecorator() throws FileDaoException {

        FileDaoDecorator fileDaoDecorator = new FileDaoDecorator();
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        sudokuBoard.solveGame();
        SudokuBoard clone = sudokuBoard.clone();
        fileDaoDecorator.saveOriginalAndCopy(sudokuBoard, clone, new Locale("en"));
        SudokuBoard original = fileDaoDecorator.readOriginal(Locale.getDefault());
        SudokuBoard copy = fileDaoDecorator.readCopy(Locale.getDefault());
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                Assertions.assertEquals(original.get(row, column), sudokuBoard.get(row, column));
                Assertions.assertEquals(copy.get(row, column), clone.get(row, column));
            }
        }

    }
}
