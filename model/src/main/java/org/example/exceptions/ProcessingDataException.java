package org.example.exceptions;


public class ProcessingDataException extends RuntimeException {
    public ProcessingDataException(String message) {
        super(message);
    }
}
