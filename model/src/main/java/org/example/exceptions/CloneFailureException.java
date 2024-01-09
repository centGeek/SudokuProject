package org.example.exceptions;

public class CloneFailureException extends SudokuException {

    String message;

    public CloneFailureException(String message) {
        super(message);
    }

}