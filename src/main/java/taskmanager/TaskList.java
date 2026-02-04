package taskmanager;

import taskmanager.exceptions.TaskListEmptyException;
import taskmanager.exceptions.TaskListFullException;
import taskmanager.exceptions.TaskListIndexOutOfBoundsException;

public class TaskList {
    private static final int CAPACITY = 100;

    private Task[] tasks;
    private int len;

    public TaskList() {
        tasks = new Task[CAPACITY];
        len = 0;
    }

    public void addTask(Task task) throws TaskListFullException {
        if (len >= CAPACITY) {
            throw new TaskListFullException();
        }
        tasks[len++] = task;
    }


    public void markTask(int index) throws TaskListIndexOutOfBoundsException {
        if (index < 0 || index >= len) {
            throw new TaskListIndexOutOfBoundsException();
        }
        tasks[index].setIsCompleted(true);
    }

    public void unmarkTask(int index) throws TaskListEmptyException, TaskListIndexOutOfBoundsException {
        if (index < 0) {
            throw new TaskListEmptyException();
        } else if (index >= len) {
            throw new TaskListIndexOutOfBoundsException();
        } else {
            tasks[index].setIsCompleted(false);
        }
    }

    public void printTaskList() {
        for (int i = 0; i < len; i++) {
            System.out.printf("%d. %s%n", i + 1, tasks[i].toString());
        }
    }
}
