package org.example.exceptions;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SudokuFieldCloneFailureException extends CloneFailureException {
    Logger logger = Logger.getLogger(SudokuFieldCloneFailureException.class.getName());

    public SudokuFieldCloneFailureException() {
        logger.log(Level.SEVERE, ResourceBundle.getBundle("messages").getString("fieldCloneFailure"));
    }

}