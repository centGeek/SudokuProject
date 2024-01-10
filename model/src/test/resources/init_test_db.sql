CREATE TABLE sudokuBoard
(
    id         serial PRIMARY KEY,
    board_name VARCHAR(255) NOT NULL
);

CREATE TABLE sudokuField
(
    id          serial,
    board_id    INT NOT NULL,
    row         INT NOT NULL,
    col         INT NOT NULL,
    field_value INT NOT NULL,
    primary key (id),
    FOREIGN KEY (board_id) REFERENCES sudokuBoard (id)
);

