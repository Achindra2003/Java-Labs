package exception;

// CHECKED: Forces handling of File I/O, Traversal, or Missing Directory errors
public class PharmacyStorageException extends Exception {
    public PharmacyStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}