package taskmanager;

public class Task {
    private final String name;
    private Boolean isCompleted;

    public Task(String name) {
        this.name = name;
        isCompleted = false;
    }

    public Task(String name, Boolean isCompleted) {
        this.name = name;
        this.isCompleted = isCompleted;
    }

    public String getName() {
        return name;
    }

    public Boolean getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public String toString() {
        if (isCompleted) {
            return "[X] " + name;
        }
        return "[ ] " + name;
    }
}
