package org.example.exceptions;

public class SudokuFieldCloneFailureException extends CloneFailureException {

    public SudokuFieldCloneFailureException() {
        super("fieldCloneFailure");
    }

}