package exceptions.taskmanager;

public class UnknownCommandException extends FridayException {
    public UnknownCommandException() {
        super("OOPS!!! I'm sorry, I don't know what you're asking me to do!");
    }
}
