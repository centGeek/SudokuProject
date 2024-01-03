package org.example.exceptions;

public class SudokuBoardCloneFailureException extends CloneFailureException {


    public SudokuBoardCloneFailureException() {
        super("boardCloneFailure");
    }
}
