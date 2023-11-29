package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class SudokuPartTest {


    @Test
    public void thatVerifyWorksCorrectly() {
        SudokuSolver sudokuSolver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(sudokuSolver);

        SudokuField sudokuField1 = new SudokuField(sudokuBoard);
        sudokuField1.setFieldValue(2);

        SudokuField sudokuField2 = new SudokuField(sudokuBoard);
        sudokuField2.setFieldValue(3);

        SudokuField sudokuField3 = new SudokuField(sudokuBoard);
        sudokuField3.setFieldValue(5);

        SudokuField sudokuField4 = new SudokuField(sudokuBoard);
        sudokuField4.setFieldValue(5);

        SudokuPart sudokuColumn = new SudokuColumn();
        sudokuColumn.setSudokuFields(List.of(sudokuField1, sudokuField2, sudokuField3, sudokuField4));
        Assertions.assertFalse(sudokuColumn.verify());

        sudokuColumn.setSudokuFields(List.of(sudokuField1, sudokuField2, sudokuField3));
        Assertions.assertTrue(sudokuColumn.verify());

        sudokuField1.setFieldValue(0);
        sudokuField2.setFieldValue(0);

        sudokuColumn.setSudokuFields(List.of(sudokuField1, sudokuField2));
        Assertions.assertTrue(sudokuColumn.verify());
    }

    @Test
    public void thatEqualsAndHashcodeWorksCorrectly() {
        SudokuSolver sudokuSolver = new BacktrackingSudokuSolver();
        SudokuObserver sudokuObserver = new SudokuBoard(sudokuSolver);
        SudokuField sudokuField1 = new SudokuField(sudokuObserver);
        SudokuField sudokuField2 = new SudokuField(sudokuObserver);
        Assertions.assertEquals(sudokuField1, sudokuField2);
        Assertions.assertEquals(sudokuField1.hashCode(), sudokuField2.hashCode());
        sudokuField1.setFieldValue(3);
        Assertions.assertNotEquals(sudokuField1, sudokuField2);
    }

    @Test
    public void thatToStringWorksCorrectly() {
        SudokuSolver sudokuSolver = new BacktrackingSudokuSolver();
        SudokuObserver sudokuObserver = new SudokuBoard(sudokuSolver);
        SudokuField sudokuField1 = new SudokuField(sudokuObserver);
        SudokuField sudokuField2 = new SudokuField(sudokuObserver);
        Assertions.assertEquals(sudokuField1.toString(), sudokuField2.toString());

        sudokuField1.setFieldValue(2);
        Assertions.assertNotEquals(sudokuField1.toString(), sudokuField2.toString());
    }

    @Test
    public void thatSudokuPartInheritedClassesEqualsAndHashCodeWorksCorrectly() {
        SudokuPart sudokuRow = new SudokuRow();
        SudokuPart sudokuBox = new SudokuBox();
        SudokuPart sudokuColumn1 = new SudokuColumn();
        SudokuPart sudokuColumn2 = new SudokuColumn();
        Assertions.assertNotEquals(sudokuRow, sudokuBox);
        Assertions.assertNotEquals(sudokuBox, sudokuColumn1);
        Assertions.assertEquals(sudokuColumn1, sudokuColumn2);
        Assertions.assertEquals(sudokuColumn1.hashCode(), sudokuColumn2.hashCode());
        SudokuPart sudokuColumn3 = sudokuColumn1;
        Assertions.assertEquals(sudokuColumn1, sudokuColumn3);
        Assertions.assertNotEquals(sudokuColumn1, null);
    }

    @Test
    public void thatSudokuPartInheritedClassesToStringWorksCorrectly() {
        SudokuPart sudokuRow = new SudokuRow();
        SudokuPart sudokuBox = new SudokuBox();
        SudokuPart sudokuColumn1 = new SudokuColumn();
        SudokuPart sudokuColumn2 = new SudokuColumn();
        Assertions.assertNotEquals(sudokuRow.toString(), sudokuBox.toString());
        Assertions.assertNotEquals(sudokuBox.toString(), sudokuColumn1.toString());
        Assertions.assertEquals(sudokuColumn1.toString(), sudokuColumn2.toString());
    }

}
