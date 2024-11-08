package org.example.postgresql_project;

public class InvalidLengthException extends Exception {
    public InvalidLengthException(String message) {
        super(message);
    }
}