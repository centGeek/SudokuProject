package org.example.database;

import org.example.BacktrackingSudokuSolver;
import org.example.Dao;
import org.example.SudokuBoard;
import org.example.exceptions.JpaSudokuException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class JpaSudokuBoardDao implements Dao<SudokuBoard>, AutoCloseable {
    private String sbName;
    private static final Logger logger = LoggerFactory.getLogger(JpaSudokuBoardDao.class);

    public static String path = "/META-INF/hibernate.cfg.xml";

    public void setPath(String path) {
        JpaSudokuBoardDao.path = path;
    }

    public JpaSudokuBoardDao(String sbName) {
        this.sbName = sbName;
    }

    @Override
    public SudokuBoard read() {
        try (SessionFactory sessionFactory = createSessionFactory(); Session session = sessionFactory.openSession()) {

            String hqlSelectData = "SELECT sf.row, sf.col, sf.fieldValue FROM SudokuFieldEntity sf "
                    + "JOIN sf.sudokuBoardEntity sb "
                    + "WHERE sb.boardName = :boardName ";


            List<Object[]> results = session
                    .createQuery(hqlSelectData, Object[].class)
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
            logger.info("SudokuBoard read from database: " + board + "\n" + "SudokuBoard name: " + sbName);
            return board;

        } catch (JpaSudokuException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void write(SudokuBoard obj) {
        try (SessionFactory sessionFactory = createSessionFactory(); Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            SudokuBoardEntity sudokuBoardEntity = new SudokuBoardEntity();
            sudokuBoardEntity.setBoardName(sbName);
            session.persist(sudokuBoardEntity);
            try {
                session.createQuery("FROM SudokuBoardEntity WHERE boardName = :boardName")
                        .setParameter("boardName", sbName)
                        .uniqueResult();
            } catch (Exception e) {
                throw new JpaSudokuException("SudokuBoard with this name already exists");
            }
            for (int row = 0; row < 9; row++) {
                for (int column = 0; column < 9; column++) {
                    SudokuFieldEntity sudokuFieldEntity = new SudokuFieldEntity();
                    sudokuFieldEntity.setRow(row);
                    sudokuFieldEntity.setCol(column);
                    sudokuFieldEntity.setFieldValue(obj.get(row, column));
                    sudokuFieldEntity.setSudokuBoardEntity(sudokuBoardEntity);
                    session.persist(sudokuFieldEntity);
                }
            }
            sudokuBoardEntity.setBoardName(sbName);
            session.getTransaction().commit();
            logger.info("SudokuBoard saved to database: " + obj + "\n" + "SudokuBoard name: " + sbName);
        } catch (JpaSudokuException e) {
            e.printStackTrace();
        }
    }

    private static SessionFactory createSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.configure(path);
        return configuration.buildSessionFactory();
    }

    @Override
    public void close() {
    }

    public void delete() {
        try (SessionFactory sessionFactory = createSessionFactory(); Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createQuery("DELETE FROM SudokuFieldEntity").executeUpdate();
            session.createQuery("DELETE FROM SudokuBoardEntity").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}