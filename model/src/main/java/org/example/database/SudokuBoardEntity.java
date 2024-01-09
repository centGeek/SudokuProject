package org.example.database;

import jakarta.persistence.*;

@Entity
@Table(name = "sudokuBoard")
public class SudokuBoardEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "board_name")
    private String boardName;

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

}
