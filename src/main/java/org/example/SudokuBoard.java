package org.example;

import java.lang.*;

public class SudokuBoard {
    private final int number = 9;
    private final int squareRoot = 3;
    private final int[][] board = new int[number][number];

    public void fillBoard() {
        fillDiagonal();
        fillRemaining(0, squareRoot);
    }

    public int getNumber(int x, int y) {
        return board[x][y];
    }

    private void fillDiagonal() {
        for (int i = 0; i < number; i = i + squareRoot) {
            int num;
            for (int x = 0; x < squareRoot; x++) {
                for (int y = 0; y < squareRoot; y++) {
                    do {
                        num = randomGenerator(number);
                    } while (!checkBox(i, i, num));
                    board[i + x][i + y] = num;
                }
            }
        }
    }

    private boolean fillRemaining(int i, int j) {
        if (j >= number && i < number - 1) {
            i = i + 1;
            j = 0;
        }
        if (i >= number && j >= number) {
            return true;
        }
        if (i < squareRoot) {
            if (j < squareRoot) {
                j = squareRoot;
            }
        } else if (i < number - squareRoot) {
            if (j == (int) (i / squareRoot) * squareRoot) {
                j = j + squareRoot;
            }
        } else {
            if (j == number - squareRoot) {
                i = i + 1;
                j = 0;
                if (i >= number) {
                    return true;
                }
            }
        }
        for (int num = 1; num <= number; num++) {
            if (isSafe(i, j, num)) {
                board[i][j] = num;
                if (fillRemaining(i, j + 1)) {
                    return true;
                }
                board[i][j] = 0;
            }
        }
        return false;
    }

    private int randomGenerator(int num) {
        return (int) Math.floor((Math.random() * num + 1));
    }

    private boolean isSafe(int i, int j, int num) {
        return (checkRow(i, num) && checkCol(j, num) && checkBox(i - i % squareRoot, j - j % squareRoot, num));
    }

    private boolean checkBox(int rowStart, int colStart, int num) {
        for (int i = 0; i < squareRoot; i++) {
            for (int j = 0; j < squareRoot; j++) {
                if (board[rowStart + i][colStart + j] == num) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkRow(int i, int num) {
        for (int j = 0; j < number; j++) {
            if (board[i][j] == num) {
                return false;
            }
        }
        return true;
    }

    private boolean checkCol(int j, int num) {
        for (int i = 0; i < number; i++) {
            if (board[i][j] == num) {
                return false;
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
