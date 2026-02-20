package exceptions.taskmanager;

import exceptions.FridayException;

public class InvalidFormatException extends FridayException {
    public InvalidFormatException(String correctFormat) {
        super("OOPS!!! Incorrect format. Correct format: " + correctFormat);
    }
}
