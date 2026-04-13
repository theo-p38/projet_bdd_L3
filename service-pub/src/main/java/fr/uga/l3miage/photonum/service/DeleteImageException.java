package fr.uga.l3miage.photonum.service;

public class DeleteImageException extends Exception {
    public DeleteImageException(String message) {
        super(message);
    }

    public DeleteImageException(String message, Throwable cause) {
        super(message, cause);
    }
}
