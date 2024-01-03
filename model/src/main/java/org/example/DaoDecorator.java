package org.example;

import org.example.exceptions.FileDaoException;

import java.util.Locale;

public interface DaoDecorator {
    void saveOriginalAndCopy(SudokuBoard sudokuBoardCopy, SudokuBoard sudokuBoardOriginal, Locale locale)
            throws FileDaoException;

    SudokuBoard readOriginal(Locale locale) throws FileDaoException;

    SudokuBoard readCopy(Locale locale) throws FileDaoException;

}
