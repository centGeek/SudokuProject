package org.example;

import org.example.exceptions.SudokuFieldNullValueException;
import org.example.exceptions.SudokuFieldWrongException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

        assertEquals(2, sudokuField1.getFieldValue());
        assertEquals(3, sudokuField2.getFieldValue());
        assertEquals(5, sudokuField3.getFieldValue());
        assertEquals(1, sudokuField4.getFieldValue());

        Assertions.assertThrows(SudokuFieldWrongException.class, () -> sudokuField4.setFieldValue(-3));
        Assertions.assertThrows(SudokuFieldWrongException.class, () -> sudokuField4.setFieldValue(10));
        Assertions.assertTrue(sudokuPart.verify());
    }

    @Test
    public void thatFieldsEqualsCorrectly() {
        SudokuSolver sudokuSolver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(sudokuSolver);
        SudokuField sudokuField1 = new SudokuField(sudokuBoard);
        SudokuField sudokuField2 = new SudokuField(sudokuBoard);
        assertEquals(sudokuField1, sudokuField2);
        assertEquals(sudokuField1.hashCode(), sudokuField2.hashCode());

        sudokuField1.setFieldValue(1);

        Assertions.assertNotEquals(sudokuField1, sudokuField2);

        Assertions.assertNotEquals(sudokuField2, null);
        Assertions.assertNotEquals(sudokuField1, sudokuBoard);

        sudokuField1 = sudokuField2;

        assertEquals(sudokuField1, sudokuField2);
    }

    @Test
    public void thatComparableWorksCorrectlyForSudokuField() {
        SudokuSolver sudokuSolver = new BacktrackingSudokuSolver();
        SudokuObserver sudokuObserver = new SudokuBoard(sudokuSolver);
        SudokuField sudokuField1 = new SudokuField(sudokuObserver);
        sudokuField1.setFieldValue(4);
        SudokuField sudokuField2 = new SudokuField(sudokuObserver);
        sudokuField2.setFieldValue(7);
        SudokuField sudokuField3 = new SudokuField(sudokuObserver);
        sudokuField3.setFieldValue(3);
        SudokuField sudokuField4 = new SudokuField(sudokuObserver);
        sudokuField4.setFieldValue(3);
        List<SudokuField> sudokuFieldList = new LinkedList<>(List.of(sudokuField1, sudokuField2,
                sudokuField3, sudokuField4));
        Collections.sort(sudokuFieldList);

        assertEquals(sudokuField3, sudokuFieldList.get(0));
        Assertions.assertTrue(sudokuField1.compareTo(sudokuField2) < 0);
        Assertions.assertTrue(sudokuField1.compareTo(sudokuField3) > 0);
        assertEquals(0, sudokuField3.compareTo(sudokuField4));


        SudokuFieldNullValueException sudokuFieldNullValueException =
                assertThrows(SudokuFieldNullValueException.class, () -> sudokuField3.compareTo(null));
        Assertions.assertEquals("Can't compare null obj",
                sudokuFieldNullValueException.getLocalizedMessage(new Locale("en")));
        Assertions.assertEquals("Nie mozna porownac obiektu null",
                sudokuFieldNullValueException.getLocalizedMessage(new Locale("pl")));
    }

    @Test
    void setBadPositiveValueTest() {
        final int value1 = 10;
        final int value2 = -1;

        SudokuObserver sudokuObserver = new SudokuBoard(new BacktrackingSudokuSolver());
        SudokuField field = new SudokuField(sudokuObserver);
        SudokuFieldWrongException exception1 =
                assertThrows(SudokuFieldWrongException.class, () -> field.setFieldValue(value1));
        Assertions.assertEquals("Value is too big(1-9)",
                exception1.getLocalizedMessage(new Locale("en")));
        Assertions.assertEquals("Wartosc jest zbyt duza(1-9)",
                exception1.getLocalizedMessage(new Locale("pl")));

        assertThrows(SudokuFieldWrongException.class, () -> field.setFieldValue(value2));
        SudokuFieldWrongException exception2 =
                assertThrows(SudokuFieldWrongException.class, () -> field.setFieldValue(value2));
        Assertions.assertEquals("Value is too small(1-9)",
                exception2.getLocalizedMessage(new Locale("en")));
        Assertions.assertEquals("Wartosc jest zbyt mala(1-9)",
                exception2.getLocalizedMessage(new Locale("pl")));

        field.setFieldValue(1);
        assertEquals(1, field.getFieldValue());
    }


    @Test
    public void thatSudokuFieldsCloneWorksCorrectly() {
        SudokuSolver sudokuSolver = new BacktrackingSudokuSolver();
        SudokuObserver sudokuObserver = new SudokuBoard(sudokuSolver);
        SudokuField sudokuField = new SudokuField(sudokuObserver);

        SudokuField clone = sudokuField.clone();

        assertEquals(clone, sudokuField);
        Assertions.assertNotSame(clone, sudokuField);
    }

}
