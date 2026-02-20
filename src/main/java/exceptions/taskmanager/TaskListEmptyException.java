package exceptions.taskmanager;

public class TaskListEmptyException extends TaskListException {
    public TaskListEmptyException() {
        super("Task list is empty! No tasks to remove.");
    }
}
