package taskmanager;

public class Deadline extends Task {

    //keeping variables private, use getters and setters to access
    private String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    //getter
    public String getBy() {
        return by;
    }

    //setter
    public void setBy(String by) {
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}
