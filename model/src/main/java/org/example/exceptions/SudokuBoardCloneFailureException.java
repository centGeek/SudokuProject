package org.example.exceptions;

import java.util.ResourceBundle;

public class SudokuBoardCloneFailureException extends CloneFailureException {

    public SudokuBoardCloneFailureException() {
        ResourceBundle bundle = ResourceBundle.getBundle("messages");
        String message = bundle.getString("SudokuBoardCloneFailureException");
    }
}
