package taskmanager.exceptions;

public class UnknownCommandException extends FridayException {
    public UnknownCommandException() {
        super("OOPS!!! I'm sorry, but I don't know what that means :-(");
    }
}
