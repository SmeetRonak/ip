import java.util.Scanner;

import taskmanager.Task;
import taskmanager.TaskList;
import taskmanager.exceptions.TaskListEmptyException;
import taskmanager.exceptions.TaskListFullException;
import taskmanager.exceptions.TaskListIndexOutOfBoundsException;

public class Friday {
    //initialize static variables
    private static final int lineLength = 100;

    //helper function to print horizontal lines
    private static void printLine() {
        System.out.println("-".repeat(lineLength));
    }

    // helper method to parse and execute user input
    private static void parseInput(TaskList taskList, String inputLine) {
        inputLine = inputLine.trim();

        if (inputLine.equals("list")) {
            printLine();
            System.out.println("Here are the tasks in your list:");
            taskList.printTaskList(); // Task.toString() should show [X] or [ ]
            printLine();
        }
        else if (inputLine.startsWith("mark ")) {
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
        }
        else if (inputLine.startsWith("unmark ")) {
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
        }
        else { // assume adding a new task
            try {
                printLine();
                Task newTask = new Task(inputLine);
                taskList.addTask(newTask);
                System.out.println("added: " + inputLine);
                printLine();
            } catch (TaskListFullException e) {
                System.out.println(e.getMessage());
            }
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


