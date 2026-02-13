import java.util.Scanner;

import taskmanager.Task;
import taskmanager.Todo;
import taskmanager.Event;
import taskmanager.Deadline;
import taskmanager.TaskList;

import taskmanager.exceptions.FridayException;
import taskmanager.exceptions.TaskListEmptyException;
import taskmanager.exceptions.TaskListFullException;
import taskmanager.exceptions.TaskListIndexOutOfBoundsException;
import taskmanager.exceptions.EmptyDescriptionException;
import taskmanager.exceptions.InvalidFormatException;
import taskmanager.exceptions.UnknownCommandException;

/**
 * Main entry point for the Friday task management application.
 * Handles the user interface and command execution logic.
 */
public class Friday {
    // Initialize static variables
    private static final int LINE_LENGTH = 100;

    /**
     * Parses the user input and executes the corresponding task command.
     *
     * @param taskList The list of tasks to be modified.
     * @param inputLine The raw string input from the user.
     */
    private static void parseInput(TaskList taskList, String inputLine) {
        inputLine = inputLine.trim();

        // Split input into command and arguments
        String[] tokens = inputLine.split("\\s+", 2); // splits into [command, rest]
        String command = tokens[0].toLowerCase();    // normalize command to lowercase
        String arguments = tokens.length > 1 ? tokens[1] : "";

        try {
            switch (command) {
            case "list" -> {
                printLine();
                System.out.println("Here are the tasks in your list:");
                taskList.printTaskList();
                printLine();
            }
            case "mark" -> handleMark(taskList, "mark " + arguments, true);
            case "unmark" -> handleMark(taskList, "unmark " + arguments, false);
            case "todo" -> handleTodo(taskList, "todo " + arguments);
            case "deadline" -> handleDeadline(taskList, "deadline " + arguments);
            case "event" -> handleEvent(taskList, "event " + arguments);
            default -> throw new UnknownCommandException();
            }

        } catch (FridayException e) {
            printLine();
            System.out.println(" " + e.getMessage());
            printLine();
        }
    }

    // ---------- Handlers ----------

    /**
     * Handles marking or unmarking a task in the task list.
     *
     * @param taskList The list of tasks to modify.
     * @param inputLine The raw input string from the user.
     * @param isMark True if marking a task as done, false if unmarking.
     * @throws FridayException If the task index is invalid, the list is empty,
     *                       or the task number format is incorrect.
     */
    private static void handleMark(TaskList taskList, String inputLine, boolean isMark) throws FridayException {
        String[] parts = inputLine.split(" ");
        if (parts.length < 2) throw new InvalidFormatException(isMark ? "mark <task_number>" : "unmark <task_number>");

        try {
            int index = Integer.parseInt(parts[1]) - 1;
            if (isMark) {
                taskList.markTask(index);
                printLine();
                System.out.println("Nice! I've marked this task as done");
                printLine();
            } else {
                taskList.unmarkTask(index);
                printLine();
                System.out.println("OK, I've marked this task as not done yet");
                printLine();
            }
        } catch (NumberFormatException e) {
            throw new FridayException("Invalid task number format!");
        } catch (TaskListIndexOutOfBoundsException | TaskListEmptyException e) {
            throw new FridayException(e.getMessage());
        }
    }

    /**
     * Handles creating and adding a Todo task to the task list.
     *
     * @param taskList The list of tasks to modify.
     * @param inputLine The raw input string from the user.
     * @throws FridayException If the task description is empty or the task list is full.
     */
    private static void handleTodo(TaskList taskList, String inputLine) throws FridayException {
        String desc = inputLine.substring(4).trim();
        if (desc.isEmpty()) throw new EmptyDescriptionException("todo");

        createAndAddTask(taskList, new Todo(desc));
    }

    /**
     * Handles creating and adding a Deadline task to the task list.
     *
     * @param taskList The list of tasks to modify.
     * @param inputLine The raw input string from the user.
     * @throws FridayException If the description or time is missing, the format is invalid,
     *                       or the task list is full.
     */
    private static void handleDeadline(TaskList taskList, String inputLine) throws FridayException {
        String body = inputLine.substring(8).trim();
        if (body.isEmpty()) throw new EmptyDescriptionException("deadline");

        String[] parts = body.split("/by");
        if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
            throw new InvalidFormatException("deadline <description> /by <time>");
        }

        createAndAddTask(taskList, new Deadline(parts[0].trim(), parts[1].trim()));
    }

    /**
     * Handles creating and adding an Event task to the task list.
     *
     * @param taskList The list of tasks to modify.
     * @param inputLine The raw input string from the user.
     * @throws FridayException If the description, start time, or end time is missing,
     *                       the format is invalid, or the task list is full.
     */
    private static void handleEvent(TaskList taskList, String inputLine) throws FridayException {
        String body = inputLine.substring(5).trim();
        if (body.isEmpty()) throw new EmptyDescriptionException("event");

        String[] firstSplit = body.split("/from");
        if (firstSplit.length < 2 || firstSplit[0].trim().isEmpty()) {
            throw new InvalidFormatException("event <description> /from <start_time> /to <end_time>");
        }

        String desc = firstSplit[0].trim();
        String[] secondSplit = firstSplit[1].split("/to");
        if (secondSplit.length < 2 || secondSplit[0].trim().isEmpty() || secondSplit[1].trim().isEmpty()) {
            throw new InvalidFormatException("event <description> /from <start_time> /to <end_time>");
        }

        createAndAddTask(taskList, new Event(desc, secondSplit[0].trim(), secondSplit[1].trim()));
    }

    // ---------- Helper Methods ----------

    /**
     * Adds a task to the task list, prints the standard task-added message, and returns the task.
     *
     * @param taskList The task list to add the task to.
     * @param task The task to be added.
     * @throws FridayException If the task list is full.
     */
    private static void createAndAddTask(TaskList taskList, Task task) throws FridayException {
        try {
            taskList.addTask(task);
        } catch (TaskListFullException e) {
            throw new FridayException(e.getMessage());
        }
        printTaskAdded(taskList, task);
    }

    /**
     * Prints a standardized message when a task is added to the list.
     *
     * @param taskList The task list containing tasks.
     * @param newTask The task that was just added.
     */
    private static void printTaskAdded(TaskList taskList, Task newTask) {
        printLine();
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + newTask);
        System.out.println("Now you have " + taskList.getLength() + " tasks in the list.");
        printLine();
    }

    /**
     * Prints a horizontal line separator to the console.
     */
    private static void printLine() {
        System.out.println("-".repeat(LINE_LENGTH));
    }

    // ---------- Main ----------

    public static void main(String[] args) {
        String logo = """
                  _____ ____  ___ ____    _ __   __
                 |  ___|  _ \\|_ _|  _ \\  / \\ \\ \\ / /
                 | |_  | |_) || || | | |/ _ \\ \\ V /
                 |  _| |  _ < | || |_| / ___  \\| |
                 |_|   |_| \\_\\___|____/_/    \\_\\_|
                """;

        printLine();
        System.out.println(logo);
        System.out.println("Hello, I'm Friday!");
        System.out.println("What can I do for you?");
        printLine();

        Scanner input = new Scanner(System.in);
        TaskList taskList = new TaskList();
        String inputLine = input.nextLine();

        while (!inputLine.equals("bye")) {
            parseInput(taskList, inputLine);
            inputLine = input.nextLine();
        }

        System.out.println("Bye. Hope to see you soon!");
        printLine();
    }
}
