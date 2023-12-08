package org.example;

public interface SudokuSolver extends Cloneable {
    void solve(SudokuBoard board);
    SudokuSolver clone();
}
