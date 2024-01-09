package org.example.database;

import org.example.BacktrackingSudokuSolver;
import org.example.Dao;
import org.example.SudokuBoard;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

public class JpaSudokuBoardDao implements Dao<SudokuBoard>, AutoCloseable {
    private String sbName;

    private Connection con = null;
    private Statement statement = null;

    public JpaSudokuBoardDao(String sbName) {
        this.sbName = sbName;
    }
    @Override
    public SudokuBoard read() {
        try (SessionFactory sessionFactory = createSessionFactory();
             Session session = sessionFactory.openSession()) {

            String hqlSelectData = "SELECT sf.row, sf.column, sf.fieldValue FROM SudokuFieldEntity sf " +
                    "WHERE sf.sudokuBoardEntity.boardName = :boardName " +
                    "ORDER BY sf.sudokuFieldId";

            List<Object[]> results = session.createQuery(hqlSelectData, Object[].class)
                    .setParameter("boardName", sbName)
                    .list();

            BacktrackingSudokuSolver solver = new BacktrackingSudokuSolver();
            SudokuBoard board = new SudokuBoard(solver);

            for (Object[] result : results) {
                int row = (int) result[0];
                int col = (int) result[1];
                int fieldValue = (int) result[2];
                board.set(row, col, fieldValue);
            }

            return board;

        } catch (HibernateException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void write(SudokuBoard obj) {
        try (
                SessionFactory sessionFactory = createSessionFactory();
                Session session = sessionFactory.openSession()
        ) {
            session.beginTransaction();
            SudokuBoardEntity sudokuBoardEntity = new SudokuBoardEntity();
            sudokuBoardEntity.setBoardName(sbName);
            session.persist(sudokuBoardEntity);
            for (int row = 0; row < 9; row++) {
                for (int column = 0; column < 9; column++) {
                    SudokuFieldEntity sudokuFieldEntity = new SudokuFieldEntity();
                    sudokuFieldEntity.setRow(row);
                    sudokuFieldEntity.setColumn(column);
                    sudokuFieldEntity.setFieldValue(obj.get(row, column));
                    sudokuFieldEntity.setSudokuBoardEntity(sudokuBoardEntity);
                    session.persist(sudokuFieldEntity);
                }
            }
            sudokuBoardEntity.setBoardName(sbName);
            session.getTransaction().commit();
        }
    }

    private static SessionFactory createSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.configure("/META-INF/hibernate.cfg.xml");
        return configuration.buildSessionFactory();
    }

    @Override
    public void close() throws Exception {
        try {
            con.close();
            statement.close();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
}