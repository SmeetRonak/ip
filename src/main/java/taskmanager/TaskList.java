package taskmanager;

import exceptions.taskmanager.TaskListEmptyException;
import exceptions.taskmanager.TaskListFullException;
import exceptions.taskmanager.TaskListIndexOutOfBoundsException;

public class TaskList {
    private static final int CAPACITY = 100;

    private final Task[] tasks = new Task[CAPACITY];
    private int length = 0;

    public TaskList() {
        //Initialisation moved to field declarations
    }

    //getter
    public int getLength() {
        return length;
    }

    /**
     * Adds a task to the list.
     *
     * @param task The task to be added.
     * @throws TaskListFullException If the list has reached maximum capacity.
     */
    public void addTask(Task task) throws TaskListFullException {
        if (length >= CAPACITY) {
            throw new TaskListFullException();
        }
        tasks[length] = task;
        length++;
    }

    /**
     * Marks the task at the specified index as completed.
     *
     * @param index The 0-based index of the task.
     * @throws TaskListIndexOutOfBoundsException If index is invalid.
     */
    public void markTask(int index) throws TaskListIndexOutOfBoundsException {
        if (index < 0 || index >= length) {
            throw new TaskListIndexOutOfBoundsException();
        }
        tasks[index].setCompleted(true);
    }

    /**
     * Marks the task at the specified index as incomplete.
     *
     * @param index The 0-based index of the task.
     * @throws TaskListEmptyException If index is negative.
     * @throws TaskListIndexOutOfBoundsException If index exceeds current length.
     */
    public void unmarkTask(int index) throws TaskListEmptyException, TaskListIndexOutOfBoundsException {
        if (index < 0) {
            throw new TaskListEmptyException();
        } else if (index >= length) {
            throw new TaskListIndexOutOfBoundsException();
        } else {
            tasks[index].setCompleted(false);
        }
    }

    /**
     * Prints all tasks currently in the list to the console.
     */
    public void printTaskList() {
        for (int i = 0; i < length; i++) {
            System.out.printf("%d. %s%n", i + 1, tasks[i].toString());
        }
    }
}
