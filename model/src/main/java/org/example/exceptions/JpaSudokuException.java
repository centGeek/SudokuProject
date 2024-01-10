package org.example.exceptions;

import org.hibernate.HibernateException;

public class JpaSudokuException extends HibernateException {
    public JpaSudokuException(String message) {
        super(message);
    }
}
