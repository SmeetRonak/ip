package storage;

import taskmanager.Task;
import taskmanager.Todo;
import taskmanager.Deadline;
import taskmanager.Event;
import exceptions.storage.StorageException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles loading and saving tasks to a file.
 */
public class Storage {

    private final String filePath;

    /**
     * Constructor. Uses default file path if none provided.
     */

    public Storage() {
        filePath = "./data/friday.txt";
    }

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from file.
     * If file or folder does not exist, returns an empty list.
     */
    public List<Task> load() throws StorageException {
        List<Task> tasks = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            // folder may exist, file missing
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                boolean created = parentDir.mkdirs();
                if (!created) {
                    System.out.println("Warning: Could not create directory " + parentDir.getPath());
                }
            }
            return tasks; // return empty list
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Task task = parseTask(line);
                if (task != null) tasks.add(task);
                else System.out.println("Skipped corrupted line in save file: " + line);
            }
        } catch (IOException e) {
            throw new StorageException("Error reading from file: " + e.getMessage(), e);
        }

        return tasks;
    }

    /**
     * Saves tasks to file. Creates file if missing.
     */
    public void save(List<Task> tasks) throws StorageException {
        File file = new File(filePath);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            boolean created = parentDir.mkdirs();
            if (!created) {
                System.out.println("Warning: Could not create directory " + parentDir.getPath());
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Task task : tasks) {
                writer.write(serializeTask(task));
                writer.newLine();
            }
        } catch (IOException e) {
            throw new StorageException("Error writing to file: " + e.getMessage(), e);
        }
    }

    // ---------- Helper methods ----------

    private Task parseTask(String line) {
        try {
            String[] parts = line.split("\\|");
            for (int i = 0; i < parts.length; i++) parts[i] = parts[i].trim();
            String type = parts[0];
            boolean isDone = parts[1].equals("1");
            String desc = parts[2];

            switch (type) {
            case "T":
                Todo todo = new Todo(desc);
                todo.setCompleted(isDone);
                return todo;
            case "D":
                String by = parts[3];
                Deadline deadline = new Deadline(desc, by);
                deadline.setCompleted(isDone);
                return deadline;
            case "E":
                String from = parts[3];
                String to = parts[4];
                Event event = new Event(desc, from, to);
                event.setCompleted(isDone);
                return event;
            default:
                return null;
            }
        } catch (Exception e) {
            return null; // skip malformed line
        }
    }

    private String serializeTask(Task task) {
        String done = task.getCompleted() ? "1" : "0";
        if (task instanceof Todo) {
            return "T | " + done + " | " + task.getDescription();
        } else if (task instanceof Deadline d) {
            return "D | " + done + " | " + d.getDescription() + " | " + d.getBy();
        } else if (task instanceof Event e) {
            return "E | " + done + " | " + e.getDescription() + " | " + e.getFrom() + " | " + e.getTo();
        } else {
            return "";
        }
    }
}