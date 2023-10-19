package org.example;

public class SudokuBoard {
    private final int number = 9;
    private final int[][] board = new int[number][number];



    public int getNumber(int x, int y) {
        return board[x][y];
    }
    public void setNumber(int x, int y, int value) {
        board[x][y] = value;
    }
    public void fillBoard() {
        SudokuSolver sudokuSolver = new TypeSudokuSolver();
        sudokuSolver.solve(this);
    }




    public void printSudoku() {
        for (int row = 0; row < 9; row++) {
            String redText = "\u001B[31m" + "%s" + "\u001B[0m";
            if (row % 3 == 0) {
                System.out.println();
            }
            for (int column = 0; column < 9; column++) {
                if (column % 3 == 0) {
                    System.out.print("  ");
                }
                if (column > 2 && column < 6) {
                    System.out.printf(redText, board[row][column] + " ");
                    continue;
                }
                if (row > 2 && row < 6) {
                    System.out.printf(redText, board[row][column] + " ");
                } else {
                    System.out.print(board[row][column] + " ");
                }
            }
            System.out.println();
        }
    }
}
