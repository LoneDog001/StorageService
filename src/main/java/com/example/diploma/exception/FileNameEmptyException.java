package com.example.diploma.exception;

public class FileNameEmptyException extends RuntimeException {
    public FileNameEmptyException(String message) {
        super(message);
    }
}
