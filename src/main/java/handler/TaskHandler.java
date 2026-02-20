package handler;

import java.util.List;
import java.util.Arrays;  // needed if you use Arrays.asList()

import exceptions.taskmanager.EmptyDescriptionException;
import exceptions.FridayException;
import exceptions.taskmanager.InvalidFormatException;
import exceptions.taskmanager.TaskListIndexOutOfBoundsException;
import taskmanager.*;

import storage.Storage;
import exceptions.storage.StorageException;

public class TaskHandler {
    private final TaskList taskList;
    private final Storage storage;

    public TaskHandler(TaskList taskList, Storage storage) {
        this.taskList = taskList;
        this.storage = storage;
    }

    public void printTasks() {
        System.out.println("Here are the tasks in your list:");
        taskList.printTaskList();
    }

    public void mark(String args) throws FridayException {
        int index = parseIndex(args);
        taskList.markTask(index);
        System.out.println("Nice! I've marked this task as done");
        saveTasks();
    }

    public void unmark(String args) throws FridayException {
        int index = parseIndex(args);
        taskList.unmarkTask(index);
        System.out.println("OK, I've marked this task as not done yet");
        saveTasks();
    }

    public void addTodo(String args) throws FridayException {
        if (args.isEmpty()) throw new EmptyDescriptionException("todo");
        createAndAddTask(new Todo(args));
    }

    public void addDeadline(String args) throws FridayException {
        String[] parts = args.split("/by");
        if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
            throw new InvalidFormatException("deadline <description> /by <time>");
        }
        createAndAddTask(new Deadline(parts[0].trim(), parts[1].trim()));
    }

    public void addEvent(String args) throws FridayException {
        String[] firstSplit = args.split("/from");
        if (firstSplit.length < 2 || firstSplit[0].trim().isEmpty()) {
            throw new InvalidFormatException("event <description> /from <start_time> /to <end_time>");
        }

        String desc = firstSplit[0].trim();
        String[] secondSplit = firstSplit[1].split("/to");
        if (secondSplit.length < 2 || secondSplit[0].trim().isEmpty() || secondSplit[1].trim().isEmpty()) {
            throw new InvalidFormatException("event <description> /from <start_time> /to <end_time>");
        }

        createAndAddTask(new Event(desc, secondSplit[0].trim(), secondSplit[1].trim()));
    }

    public void delete(String args) throws FridayException {
        int index = parseIndex(args);

        Task removedTask;
        try {
            removedTask = taskList.deleteTask(index);
        } catch (TaskListIndexOutOfBoundsException e) {
            throw new FridayException(e.getMessage());
        }

        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + removedTask);
        System.out.println("Now you have " + taskList.getLength() + " tasks in the list.");
    }

    // ---------- Helper Methods ----------

    private void createAndAddTask(Task task) {
        taskList.addTask(task);

        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskList.getLength() + " tasks in the list.");
        saveTasks();
    }

    private int parseIndex(String arg) throws FridayException {
        try {
            return Integer.parseInt(arg.trim()) - 1;
        } catch (NumberFormatException e) {
            throw new FridayException("Invalid task number format!");
        }
    }

    private void saveTasks() {
        try {
            Task[] tasksArray = taskList.getAllTasks();          // your array
            storage.save(Arrays.asList(tasksArray));            // wrap as List<Task>
        } catch (StorageException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    public void loadTasks() {
        try {
            List<Task> loadedTasks = storage.load();     // returns a List<Task>
            for (Task t : loadedTasks) {
                taskList.addTask(t);                      // just add directly
            }
        } catch (StorageException e) {
            System.out.println("Warning: Could not load previous tasks: " + e.getMessage());
        }
    }
}
