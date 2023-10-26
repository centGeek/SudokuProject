package org.example;

public class SudokuBoard {
    private final int number = 9;
    private final int[][] board = new int[number][number];
    private SudokuSolver sudokuSolver;

    public SudokuBoard(SudokuSolver sudokuSolver) {
        this.sudokuSolver = sudokuSolver;
    }

    public void solveGame() {
        sudokuSolver.solve(this);
    }

    public int get(int x, int y) {
        return board[x][y];
    }

    public void set(int x, int y, int value) {
        board[x][y] = value;
    }

    boolean isValidMove(SudokuBoard board, int row, int column, int num) {
        for (int i = 0; i < 9; i++) {
            if (board.get(row, i) == num || board.get(i, column) == num) {
                return false;
            }
        }

        int startRow = (row / 3) * 3;
        int startCol = (column / 3) * 3;
        for (int i = startRow; i < startRow + 3; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                if (board.get(i, j) == num) {
                    return false;
                }
            }
        }
        return true;
    }

    /*public void printSudoku() {
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
    }*/
}
