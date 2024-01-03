package org.example.exceptions;


public class ProcessingDataException extends RuntimeException {
    public ProcessingDataException(Object message) {
        super((String) message);
    }
}
