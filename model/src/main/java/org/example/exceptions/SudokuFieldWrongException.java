package org.example.exceptions;

public class SudokuFieldWrongException extends WrongValueException {
    public SudokuFieldWrongException(String message) {
        super(message);
    }
}