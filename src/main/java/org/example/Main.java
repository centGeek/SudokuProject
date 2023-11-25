//
//
//package org.example;
//
//
//public class Main {
//    public static void main(String[] args) {
//        SudokuSolver sudokuSolver = new BacktrackingSudokuSolver();
//        SudokuBoard sudokuBoard = new SudokuBoard(sudokuSolver);
//        sudokuBoard.solveGame();
//        SudokuBoardDaoFactory sudokuBoardDaoFactory = new SudokuBoardDaoFactory();
//        Dao<SudokuBoard> fileDao = sudokuBoardDaoFactory.getFileDao("files/sudokuBoard.txt");
//        fileDao.write(sudokuBoard);
//        System.out.println(fileDao.read());
//    }
//}
//
