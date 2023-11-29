package org.example;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileSudokuBoardDao implements Dao<SudokuBoard> {
    private final String fileName;

    public FileSudokuBoardDao(String fileName) {
        this.fileName = fileName;
    }


    @Override
    public SudokuBoard read() {
        try (FileInputStream fileIn = new FileInputStream(fileName)) {
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            Object obj = objectIn.readObject();

            return (SudokuBoard) obj;
        } catch (Exception ex) {
            throw new RuntimeException("Something unexpected happened");
        }
    }

    @Override
    public void write(SudokuBoard obj) {
        try (FileOutputStream fileOut = new FileOutputStream(fileName)) {

            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(obj);

        } catch (Exception ex) {
            throw new RuntimeException("Something unexpected happened");
        }
    }

    @Override
    public void close() throws Exception {

    }
}
