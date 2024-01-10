package org.example.database;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sudokuBoard")
public class SudokuBoardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "board_name", nullable = false)
    private String boardName;

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    @OneToMany(mappedBy = "sudokuBoardEntity", cascade = CascadeType.ALL)
    private List<SudokuFieldEntity> sudokuFieldEntities = new ArrayList<>();

    public List<SudokuFieldEntity> getSudokuFieldEntities() {
        return sudokuFieldEntities;
    }
}
