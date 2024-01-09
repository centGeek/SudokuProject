package org.example.database;

import jakarta.persistence.*;

@Entity
@Table(name = "sudokuField")
public class SudokuFieldEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer sudokuFieldId;

    @Column(name = "col")
    private int column;

    @Column(name = "row")
    private int row;

    @Column(name = "field_value")
    private int fieldValue;
    @ManyToOne
    @JoinColumn(name = "board_id")
    private SudokuBoardEntity sudokuBoardEntity;

    public void setColumn(int column) {
        this.column = column;
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
