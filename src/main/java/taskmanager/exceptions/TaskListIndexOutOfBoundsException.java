package taskmanager.exceptions;

public class TaskListIndexOutOfBoundsException extends TaskListException {
    public TaskListIndexOutOfBoundsException() {
        super("invalid task number! Please select one of the existing tasks");
    }
}
