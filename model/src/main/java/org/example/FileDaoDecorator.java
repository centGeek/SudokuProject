
package org.example;

import org.example.exceptions.FileDaoException;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class FileDaoDecorator implements DaoDecorator {
    private static final Logger logger = Logger.getLogger(FileDaoException.class.getName());

    private final Dao<SudokuBoard> daoCopy = new FileSudokuBoardDao("sudokuCopy.txt");
    private final Dao<SudokuBoard> daoOriginal = new FileSudokuBoardDao("sudokuOriginal.txt");

    public void saveOriginalAndCopy(SudokuBoard sudokuBoardCopy, SudokuBoard sudokuBoardOriginal, Locale locale)
            throws FileDaoException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("logger", locale);

        daoCopy.write(sudokuBoardCopy);
        daoOriginal.write(sudokuBoardOriginal);

        logger.info(resourceBundle.getString("originalSaved") + sudokuBoardOriginal);
        logger.info(resourceBundle.getString("copySaved") + sudokuBoardCopy);

    }

    public SudokuBoard readOriginal(Locale locale) throws FileDaoException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("logger", locale);
        logger.info(resourceBundle.getString("originalRead") + daoOriginal.read());
        return daoOriginal.read();
    }

    public SudokuBoard readCopy(Locale locale) throws FileDaoException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("logger", locale);
        logger.info(resourceBundle.getString("copyRead") + daoCopy.read());
        return daoCopy.read();
    }

}
