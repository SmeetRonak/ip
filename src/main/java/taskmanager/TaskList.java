package taskmanager;

import java.util.ArrayList;

//import taskmanager.exceptions.TaskListEmptyException;
//import taskmanager.exceptions.TaskListFullException;
import taskmanager.exceptions.TaskListIndexOutOfBoundsException;

public class TaskList {
    private final ArrayList<Task> tasks = new ArrayList<>();

    public TaskList() {
        //Initialisation moved to field declarations
    }

    //getter
    public int getLength() {
        return tasks.size();
    }

    /**
     * Adds a task to the list.
     *
     * @param task The task to be added.
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Marks the task at the specified index as completed.
     *
     * @param index The 0-based index of the task.
     * @throws TaskListIndexOutOfBoundsException If index is invalid.
     */
    public void markTask(int index) throws TaskListIndexOutOfBoundsException {
        if (index < 0 || index >= tasks.size()) {
            throw new TaskListIndexOutOfBoundsException();
        }
        tasks.get(index).setCompleted(true);
    }

    /**
     * Marks the task at the specified index as incomplete.
     *
     * @param index The 0-based index of the task.
     * @throws TaskListIndexOutOfBoundsException If index exceeds current length.
     */
    public void unmarkTask(int index) throws TaskListIndexOutOfBoundsException {
        if (index < 0 || index >= tasks.size()) {
            throw new TaskListIndexOutOfBoundsException();
        }
        tasks.get(index).setCompleted(false);
    }

    /**
     * Returns an array of all current tasks in the list.
     * The returned array has length equal to the number of tasks in the list.
     */
    public Task[] getAllTasks() {
        Task[] activeTasks = new Task[length];
        for (int i = 0; i < length; i++) {
            activeTasks[i] = tasks[i];
        }
        return activeTasks;
    }

    /**
     * Prints all tasks currently in the list to the console.
     */
    public void printTaskList() {
        for (int i = 0; i < tasks.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, tasks.get(i));
        }
    }

    /**
     * Deletes the task at the specified index and returns it.
     *
     * @param index The 0-based index of the task to delete.
     * @return The task that was removed from the list.
     * @throws TaskListIndexOutOfBoundsException If the index is invalid.
     */
    public Task deleteTask(int index) throws TaskListIndexOutOfBoundsException {
        if (index < 0 || index >= tasks.size()) {
            throw new TaskListIndexOutOfBoundsException();
        }
        return tasks.remove(index);
    }
}
