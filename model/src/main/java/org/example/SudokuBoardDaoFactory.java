package org.example;

import org.example.database.JpaSudokuBoardDao;

public class SudokuBoardDaoFactory {
    private SudokuBoardDaoFactory() {
    }

    public static Dao<SudokuBoard> getFileDao(String fileName) {
        return new FileSudokuBoardDao(fileName);
    }
    public static Dao<SudokuBoard> getDatabaseDao(String sudokuBoardName) {
        return new JpaSudokuBoardDao(sudokuBoardName);
    }
}
