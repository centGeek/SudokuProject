package org.example;

import org.example.exceptions.FileDaoException;

public interface Dao<T> extends AutoCloseable {
    T read() throws FileDaoException;

    void write(T obj) throws FileDaoException;
}
