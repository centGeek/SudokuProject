package org.example;

import org.example.exceptions.FileDaoException;

import java.io.*;

public class FileSudokuBoardDao implements Dao<SudokuBoard> {
    private final String fileName;

    public FileSudokuBoardDao(String fileName) {
        this.fileName = fileName;
    }


    @Override
    public SudokuBoard read() throws FileDaoException {
        try (FileInputStream fileIn = new FileInputStream(fileName)) {
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            Object obj = objectIn.readObject();

            return (SudokuBoard) obj;
        } catch (IOException | ClassNotFoundException ex) {
            throw new FileDaoException(ex);
        }
    }

    @Override
    public void write(SudokuBoard obj) throws FileDaoException {
        try (FileOutputStream fileOut = new FileOutputStream(fileName)) {

            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(obj);

        } catch (IOException ex) {
            throw new FileDaoException(ex);
        }
    }

    @Override
    public void close() throws Exception {
    }
}
