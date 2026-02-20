package exceptions.taskmanager;

public class InvalidFormatException extends FridayException {
    public InvalidFormatException(String correctFormat) {
        super("OOPS!!! Incorrect format. Correct format: " + correctFormat);
    }
}
