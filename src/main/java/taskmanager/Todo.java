package taskmanager;

public class Todo extends Task {

    //no additional variables required

    public Todo(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
