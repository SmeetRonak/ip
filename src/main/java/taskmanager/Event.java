package taskmanager;

public class Event extends Task {

    //keeping variables private, use getters and setters to access
    private String from;
    private String to;

    public Event(String name, String from, String to) {
        super(name);
        this.from = from;
        this.to = to;
    }

    //getters
    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    //setters
    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + ", to: " + to + ")";
    }
}
