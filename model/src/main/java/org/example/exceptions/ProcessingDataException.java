package org.example.exceptions;

import java.util.Objects;

public class ProcessingDataException extends RuntimeException{
    public ProcessingDataException(Object message) {
        super((String) message);
    }
}
