package taskmanager;

public class Task {

    private final String description;
    private boolean isCompleted = false;

    /**
     * Initializes a task with a null name and incomplete status.
     */
    public Task() {
        description = null;
    }

    /**
     * Initializes a task with the given name and sets status to incomplete.
     *
     * @param name The description of the task.
     */
    public Task(String name) {
        this.description = name;
    }

    /**
     * Initializes a task with a name and a specific completion status.
     *
     * @param name The description of the task.
     * @param isCompleted The initial completion status.
     */
    public Task(String name, boolean isCompleted) {
        this.description = name;
        this.isCompleted = isCompleted;
    }

    public String getDescription() {
        return description;
    }

    public boolean getCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    @Override
    public String toString() {
        if (isCompleted) {
            return "[X] " + description;
        }
        return "[ ] " + description;
    }
}
