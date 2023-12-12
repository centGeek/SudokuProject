package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class SudokuFieldTest {
    @Test
    public void thatFieldsAreSetCorrectly() {
        SudokuSolver sudokuSolver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(sudokuSolver);

        SudokuField sudokuField1 = new SudokuField(sudokuBoard);
        sudokuField1.setFieldValue(2);

        SudokuField sudokuField2 = new SudokuField(sudokuBoard);
        sudokuField2.setFieldValue(3);

        SudokuField sudokuField3 = new SudokuField(sudokuBoard);
        sudokuField3.setFieldValue(5);

        SudokuField sudokuField4 = new SudokuField(sudokuBoard);
        sudokuField4.setFieldValue(1);

        SudokuPart sudokuPart = new SudokuRow();
        sudokuPart.setSudokuFields(List.of(sudokuField1, sudokuField2, sudokuField3, sudokuField4));

        Assertions.assertEquals(2, sudokuField1.getFieldValue());
        Assertions.assertEquals(3, sudokuField2.getFieldValue());
        Assertions.assertEquals(5, sudokuField3.getFieldValue());
        Assertions.assertEquals(1, sudokuField4.getFieldValue());

        sudokuField4.setFieldValue(-3);
        sudokuField3.setFieldValue(10);
        Assertions.assertNotEquals(-3, sudokuField4.getFieldValue());
        Assertions.assertNotEquals(10, sudokuField3.getFieldValue());
        Assertions.assertTrue(sudokuPart.verify());
    }

    @Test
    public void thatFieldsEqualsCorrectly() {
        SudokuSolver sudokuSolver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(sudokuSolver);
        SudokuField sudokuField1 = new SudokuField(sudokuBoard);
        SudokuField sudokuField2 = new SudokuField(sudokuBoard);
        Assertions.assertEquals(sudokuField1, sudokuField2);
        Assertions.assertEquals(sudokuField1.hashCode(), sudokuField2.hashCode());

        sudokuField1.setFieldValue(1);

        Assertions.assertNotEquals(sudokuField1, sudokuField2);

        Assertions.assertNotEquals(sudokuField2, null);
        Assertions.assertNotEquals(sudokuField1, sudokuBoard);

        sudokuField1 = sudokuField2;

        Assertions.assertEquals(sudokuField1, sudokuField2);
    }
    @Test
    public void thatComparableWorksCorrectlyForSudokuField(){
        SudokuSolver sudokuSolver = new BacktrackingSudokuSolver();
        SudokuObserver sudokuObserver = new SudokuBoard(sudokuSolver);
        SudokuField sudokuField1 = new SudokuField(sudokuObserver);
        SudokuField sudokuField2 = new SudokuField(sudokuObserver);
        SudokuField sudokuField3 = new SudokuField(sudokuObserver);
        SudokuField sudokuField4 = new SudokuField(sudokuObserver);

        sudokuField1.setFieldValue(4);
        sudokuField2.setFieldValue(7);
        sudokuField3.setFieldValue(3);
        sudokuField4.setFieldValue(3);
        List<SudokuField> sudokuFieldList = new LinkedList<>(List.of(sudokuField1, sudokuField2,
                sudokuField3, sudokuField4));
        Collections.sort(sudokuFieldList);

        Assertions.assertEquals(sudokuField3, sudokuFieldList.get(0));
        Assertions.assertTrue(sudokuField1.compareTo(sudokuField2) < 0);
        Assertions.assertTrue(sudokuField1.compareTo(sudokuField3) > 0);
        Assertions.assertEquals(0, sudokuField3.compareTo(sudokuField4));


        Assertions.assertThrows(NullPointerException.class,() -> sudokuField3.compareTo(null));
    }
    @Test
    public void thatSudokuFieldsCloneWorksCorrectly(){
        SudokuSolver sudokuSolver = new BacktrackingSudokuSolver();
        SudokuObserver sudokuObserver = new SudokuBoard(sudokuSolver);
        SudokuField sudokuField = new SudokuField(sudokuObserver);

        SudokuField clone = sudokuField.clone();

        Assertions.assertEquals(clone, sudokuField);
        Assertions.assertNotSame(clone, sudokuField);
    }

}
