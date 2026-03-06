package handler;

import java.util.List;
import java.util.Arrays;  // needed if you use Arrays.asList()

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

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


    /**
     * Marks a task as completed based on the provided index argument.
     *
     * @param args the task number provided by the user
     * @throws FridayException if the task index is invalid or cannot be parsed
     */
    public void mark(String args) throws FridayException {
        int index = parseIndex(args);
        taskList.markTask(index);
        System.out.println("Nice! I've marked this task as done");
        saveTasks();
    }


    /**
     * Marks a task as not completed based on the provided index argument.
     *
     * @param args the task number provided by the user
     * @throws FridayException if the task index is invalid or cannot be parsed
     */
    public void unmark(String args) throws FridayException {
        int index = parseIndex(args);
        taskList.unmarkTask(index);
        System.out.println("OK, I've marked this task as not done yet");
        saveTasks();
    }


    /**
     * Creates and adds a Todo task to the task list.
     *
     * @param args the description of the todo task
     * @throws FridayException if the description is empty
     */
    public void addTodo(String args) throws FridayException {
        if (args.isEmpty()) throw new EmptyDescriptionException("todo");
        createAndAddTask(new Todo(args));
    }


    /**
     * Creates and adds a Deadline task to the task list.
     * The expected format is: description /by yyyy-mm-dd.
     *
     * @param args the description and deadline date provided by the user
     * @throws FridayException if the format is invalid or the date cannot be parsed
     */
    public void addDeadline(String args) throws FridayException {
        String[] parts = args.split("/by");

        if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
            throw new InvalidFormatException("deadline <description> /by <yyyy-mm-dd>");
        }

        try {
            String description = parts[0].trim();
            LocalDate byDate = LocalDate.parse(parts[1].trim());

            createAndAddTask(new Deadline(description, byDate));

        } catch (DateTimeParseException e) {
            throw new FridayException("Date must be in yyyy-mm-dd format.");
        }
    }


    /**
     * Creates and adds an Event task to the task list.
     * The expected format is: description /from yyyy-mm-ddTHH:mm /to yyyy-mm-ddTHH:mm.
     *
     * @param args the description, start time, and end time of the event
     * @throws FridayException if the format is invalid or the date-time values cannot be parsed
     */
    public void addEvent(String args) throws FridayException {

        String[] firstSplit = args.split("/from");

        if (firstSplit.length < 2 || firstSplit[0].trim().isEmpty()) {
            throw new InvalidFormatException(
                    "event <description> /from <yyyy-mm-ddTHH:mm> /to <yyyy-mm-ddTHH:mm>");
        }

        String desc = firstSplit[0].trim();

        String[] secondSplit = firstSplit[1].split("/to");

        if (secondSplit.length < 2 || secondSplit[0].trim().isEmpty()
                || secondSplit[1].trim().isEmpty()) {

            throw new InvalidFormatException(
                    "event <description> /from <yyyy-mm-ddTHH:mm> /to <yyyy-mm-ddTHH:mm>");
        }

        try {

            LocalDateTime from = LocalDateTime.parse(secondSplit[0].trim());
            LocalDateTime to = LocalDateTime.parse(secondSplit[1].trim());

            createAndAddTask(new Event(desc, from, to));

        } catch (DateTimeParseException e) {
            throw new FridayException("DateTime must be yyyy-mm-ddTHH:mm");
        }
    }


    /**
     * Deletes a task from the task list based on the provided index.
     *
     * @param args the task number provided by the user
     * @throws FridayException if the index is invalid or out of bounds
     */
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


    /**
     * Searches for tasks whose descriptions contain the given keyword
     * and prints all matching tasks.

     * @param keyword the keyword used to search task descriptions
     * @throws FridayException if the keyword is empty
     */
    public void find(String keyword) throws FridayException {

        if (keyword.isEmpty()) {
            throw new EmptyDescriptionException("find");
        }

        Task[] tasks = taskList.getAllTasks();

        System.out.println("Here are the matching tasks in your list:");

        int matchIndex = 1;

        for (Task task : tasks) {
            if (task == null) continue;

            if (task.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println(matchIndex + "." + task);
                matchIndex++;
            }
        }

        if (matchIndex == 1) {
            System.out.println("No matching tasks found.");
        }
    }

    // ---------- Helper Methods ----------


    /**
     * Adds a task to the task list, prints a confirmation message,
     * and saves the updated task list to storage.
     *
     * @param task the task to be added
     */
    private void createAndAddTask(Task task) {
        taskList.addTask(task);

        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskList.getLength() + " tasks in the list.");
        saveTasks();
    }


    /**
     * Parses a user-provided task number into a zero-based index.
     *
     * @param arg the task number as a string
     * @return the corresponding zero-based index
     * @throws FridayException if the task number is not a valid integer
     */
    private int parseIndex(String arg) throws FridayException {
        try {
            return Integer.parseInt(arg.trim()) - 1;
        } catch (NumberFormatException e) {
            throw new FridayException("Invalid task number format!");
        }
    }


    /**
     * Saves the current list of tasks to persistent storage.
     * Prints an error message if saving fails.
     */
    private void saveTasks() {
        try {
            Task[] tasksArray = taskList.getAllTasks();          // your array
            storage.save(Arrays.asList(tasksArray));            // wrap as List<Task>
        } catch (StorageException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }


    /**
     * Loads tasks from storage and adds them to the task list.
     * Prints a warning message if loading fails.
     */
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
