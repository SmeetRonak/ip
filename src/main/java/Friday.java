import java.util.Scanner;

import taskmanager.Task;
import taskmanager.Todo;
import taskmanager.Event;
import taskmanager.Deadline;
import taskmanager.TaskList;
import taskmanager.exceptions.TaskListEmptyException;
import taskmanager.exceptions.TaskListFullException;
import taskmanager.exceptions.TaskListIndexOutOfBoundsException;


/**
 * Main entry point for the Friday task management application.
 * Handles the user interface and command execution logic.
 */
public class Friday {
    //initialize static variables
    private static final int LINE_LENGTH = 100;

    /**
     * Prints a horizontal line separator to the console.
     */
    private static void printLine() {
        System.out.println("-".repeat(LINE_LENGTH));
    }


    /**
     * Parses the user input and executes the corresponding task command.
     *
     * @param taskList The list of tasks to be modified.
     * @param inputLine The raw string input from the user.
     */
    private static void parseInput(TaskList taskList, String inputLine) {
        inputLine = inputLine.trim();

        if (inputLine.equals("list")) {
            printLine();
            System.out.println("Here are the tasks in your list:");
            taskList.printTaskList(); // Task.toString() should show [X] or [ ]
            printLine();
        } else if (inputLine.startsWith("mark ")) {
            try {
                int index = Integer.parseInt(inputLine.split(" ")[1]) - 1; // user uses 1-based
                taskList.markTask(index);
                printLine();
                System.out.println("Nice! I've marked this task as done");
                printLine();
            } catch (TaskListIndexOutOfBoundsException e) {
                System.out.println(e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Invalid task number format!");
            }
        } else if (inputLine.startsWith("unmark ")) {
            try {
                int index = Integer.parseInt(inputLine.split(" ")[1]) - 1;
                taskList.unmarkTask(index);
                printLine();
                System.out.println("OK, I've marked this task as not done yet");
                printLine();
            } catch (TaskListEmptyException | TaskListIndexOutOfBoundsException e) {
                System.out.println(e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Invalid task number format!");
            }
        } else if (inputLine.startsWith("todo ")) {
            try {
                String desc = inputLine.substring(5).trim();
                Task newTask = new Todo(desc);
                taskList.addTask(newTask);

                printLine();
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + newTask);
                System.out.println("Now you have " + taskList.getLength() + " tasks in the list.");
                printLine();
            } catch (TaskListFullException e) {
                System.out.println(e.getMessage());
            }

        } else if (inputLine.startsWith("deadline ")) {
            try {
                String body = inputLine.substring(9).trim();
                String[] parts = body.split("/by");

                String desc = parts[0].trim();
                String by = parts[1].trim();

                Task newTask = new Deadline(desc, by);
                taskList.addTask(newTask);

                printLine();
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + newTask);
                System.out.println("Now you have " + taskList.getLength() + " tasks in the list.");
                printLine();
            } catch (Exception e) {
                System.out.println("Invalid deadline format! Use: deadline <desc> /by <time>");
            }

        } else if (inputLine.startsWith("event ")) {
            try {
                String body = inputLine.substring(6).trim();

                String[] firstSplit = body.split("/from");
                String desc = firstSplit[0].trim();

                String[] secondSplit = firstSplit[1].split("/to");
                String from = secondSplit[0].trim();
                String to = secondSplit[1].trim();

                Task newTask = new Event(desc, from, to);
                taskList.addTask(newTask);

                printLine();
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + newTask);
                System.out.println("Now you have " + taskList.getLength() + " tasks in the list.");
                printLine();
            } catch (Exception e) {
                System.out.println("Invalid event format! Use: event <desc> /from <time> /to <time>");
            }

        } else {
            System.out.println("Unknown command!");
        }
    }


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


