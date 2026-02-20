package ui;

import taskmanager.Task;

import java.util.Scanner;
import handler.CommandParser;
import handler.TaskHandler;
import taskmanager.TaskList;
import storage.Storage;
import exceptions.FridayException;

public class FridayUI {
    private static final int LINE_LENGTH = 100;
    private final TaskHandler taskHandler;
    private final CommandParser parser;
    private final Scanner scanner;

    public FridayUI() {
        TaskList taskList = new TaskList();
        Storage storage = new Storage("./data/friday.txt");

        this.taskHandler = new TaskHandler(taskList, storage);
        this.parser = new CommandParser();
        this.scanner = new Scanner(System.in);

        // Load previous tasks using TaskHandler helper
        taskHandler.loadTasks();
    }

    /**
     * Starts the chatbot interaction loop.
     * Continuously reads user input, parses it into commands and arguments,
     * and dispatches the command to the appropriate handler.
     * The loop terminates when the user enters "bye".
     *
     */
    public void start() {
        printLogo();

        String inputLine = scanner.nextLine();
        while (!inputLine.equals("bye")) {
            try {
                String command = parser.getCommand(inputLine);
                String arguments = parser.getArguments(inputLine);

                switch (command) {
                case "list":
                    taskHandler.printTasks();
                    break;

                case "mark":
                    taskHandler.mark(arguments);
                    break;

                case "unmark":
                    taskHandler.unmark(arguments);
                    break;

                case "todo":
                    taskHandler.addTodo(arguments);
                    break;

                case "deadline":
                    taskHandler.addDeadline(arguments);
                    break;

                case "event":
                    taskHandler.addEvent(arguments);
                    break;

                case "delete":
                    taskHandler.delete(arguments);
                    break;

                default:
                    throw new FridayException("Unknown command!");
                }

                printLine();

            } catch (FridayException e) {
                printLine();
                System.out.println(" " + e.getMessage());
                printLine();
            }
            inputLine = scanner.nextLine();
        }

        System.out.println("Bye. Hope to see you soon!");
        printLine();
    }

    public void printLine() {
        System.out.println("-".repeat(LINE_LENGTH));
    }

    private void printLogo() {
        printLine();
        String logo = """
                  _____ ____  ___ ____    _ __   __
                 |  ___|  _ \\|_ _|  _ \\  / \\ \\ \\ / /
                 | |_  | |_) || || | | |/ _ \\ \\ V /
                 |  _| |  _ < | || |_| / ___  \\| |
                 |_|   |_| \\_\\___|____/_/    \\_\\_|
                """;
        System.out.println(logo);
        System.out.println("Hello, I'm Friday!");
        System.out.println("What can I do for you?");
        printLine();
    }
}
