package org.example.database;

import jakarta.persistence.*;

@Entity
@Table(name = "sudokuField")
public class SudokuFieldEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer sudokuFieldId;

    @Column(name = "col", nullable = false)
    private int col;

    @Column(name = "row", nullable = false)
    private int row;

    @Column(name = "field_value", nullable = false)
    private int fieldValue;

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private SudokuBoardEntity sudokuBoardEntity;

    public void setCol(int col) {
        this.col = col;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setSudokuBoardEntity(SudokuBoardEntity sudokuBoardEntity) {
        this.sudokuBoardEntity = sudokuBoardEntity;
    }

    public void setFieldValue(int fieldValue) {
        this.fieldValue = fieldValue;
    }

}
