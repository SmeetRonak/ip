package exceptions.taskmanager;

public class EmptyDescriptionException extends FridayException {
    public EmptyDescriptionException(String taskType) {
        super("OOPS!!! The description of a " + taskType + " cannot be empty.");
    }
}
