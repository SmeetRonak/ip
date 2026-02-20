package exceptions.taskmanager;

public class TaskListFullException extends TaskListException {
    public TaskListFullException() {
        super("Task list is full! Cannot add more tasks.");
    }
}
