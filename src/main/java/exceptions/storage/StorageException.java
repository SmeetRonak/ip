package exceptions.storage;

import exceptions.FridayException;
/**
 * Exception thrown for errors related to storage (file I/O).
 */
public class StorageException extends FridayException {
    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}