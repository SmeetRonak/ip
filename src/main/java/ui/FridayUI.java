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
        Storage storage = new Storage("./data/friday.txt"); // default path

        this.taskHandler = new TaskHandler(taskList, storage);
        this.parser = new CommandParser();
        this.scanner = new Scanner(System.in);

        // load tasks from file
        try {
            Task[] loadedTasks = storage.load().toArray(new Task[0]);
            for (Task t : loadedTasks) {
                taskList.addTask(t);
            }
        } catch (Exception e) {
            System.out.println("Could not load saved tasks: " + e.getMessage());
        }
    }

    public void start() {
        printLogo();

        String inputLine = scanner.nextLine();
        while (!inputLine.equals("bye")) {
            try {
                String command = parser.getCommand(inputLine);
                String arguments = parser.getArguments(inputLine);

                switch (command) {
                case "list" -> taskHandler.printTasks();
                case "mark" -> taskHandler.mark(arguments);
                case "unmark" -> taskHandler.unmark(arguments);
                case "todo" -> taskHandler.addTodo(arguments);
                case "deadline" -> taskHandler.addDeadline(arguments);
                case "event" -> taskHandler.addEvent(arguments);
                default -> throw new FridayException("Unknown command!");
                }
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
