package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class SudokuPartTest {
    @Test
    public void thatFieldsAreSetCorrectly() {
        SudokuSolver sudokuSolver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(sudokuSolver);
        SudokuField sudokuField1 = new SudokuField(sudokuBoard);
        SudokuField sudokuField2 = new SudokuField(sudokuBoard);
        SudokuField sudokuField3 = new SudokuField(sudokuBoard);
        SudokuField sudokuField4 = new SudokuField(sudokuBoard);

        sudokuField1.setFieldValue(2);
        sudokuField2.setFieldValue(3);
        sudokuField3.setFieldValue(5);
        sudokuField4.setFieldValue(1);

        SudokuPart sudokuPart = new SudokuRow();
        sudokuPart.setSudokuFields(List.of(sudokuField1, sudokuField2, sudokuField3, sudokuField4));

        Assertions.assertEquals(2, sudokuField1.getFieldValue());
        Assertions.assertEquals(3, sudokuField2.getFieldValue());
        Assertions.assertEquals(5, sudokuField3.getFieldValue());
        Assertions.assertEquals(1, sudokuField4.getFieldValue());

        Assertions.assertTrue(sudokuPart.verify());
    }

    @Test
    public void thatVerifyWorksCorrectly() {
        SudokuSolver sudokuSolver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(sudokuSolver);
        SudokuPart sudokuColumn = new SudokuColumn();
        SudokuField sudokuField1 = new SudokuField(sudokuBoard);
        SudokuField sudokuField2 = new SudokuField(sudokuBoard);
        SudokuField sudokuField3 = new SudokuField(sudokuBoard);
        SudokuField sudokuField4 = new SudokuField(sudokuBoard);

        sudokuField1.setFieldValue(2);
        sudokuField2.setFieldValue(3);
        sudokuField3.setFieldValue(5);
        sudokuField4.setFieldValue(5);

        sudokuColumn.setSudokuFields(List.of(sudokuField1, sudokuField2, sudokuField3, sudokuField4));
        Assertions.assertFalse(sudokuColumn.verify());

        sudokuColumn.setSudokuFields(List.of(sudokuField1, sudokuField2, sudokuField3));
        Assertions.assertTrue(sudokuColumn.verify());

        sudokuField1.setFieldValue(0);
        sudokuField2.setFieldValue(0);

        sudokuColumn.setSudokuFields(List.of(sudokuField1, sudokuField2));
        Assertions.assertTrue(sudokuColumn.verify());
    }

}
