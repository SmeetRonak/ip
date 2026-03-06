package storage;

import taskmanager.Task;
import taskmanager.Todo;
import taskmanager.Deadline;
import taskmanager.Event;
import exceptions.storage.StorageException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Handles loading and saving tasks to a file.
 */
public class Storage {

    private final String filePath;

    public Storage() {
        filePath = "./data/friday.txt";
    }

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the storage file.
     * If the file or its parent directory does not exist, the directory
     * is created (if needed) and an empty task list is returned.
     *
     * @return List of tasks loaded from the file.
     * @throws StorageException If an error occurs while reading from the file.
     */
    public List<Task> load() throws StorageException {
        List<Task> tasks = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                boolean created = parentDir.mkdirs();
                if (!created) {
                    System.out.println("Warning: Could not create directory " + parentDir.getPath());
                }
            }
            return tasks;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                Task task = parseTask(line);

                if (task != null) {
                    tasks.add(task);
                } else {
                    System.out.println("Skipped corrupted line in save file: " + line);
                }
            }

        } catch (IOException e) {
            throw new StorageException("Error reading from file: " + e.getMessage(), e);
        }

        return tasks;
    }

    /**
     * Saves the given list of tasks to the storage file.
     * The parent directory is created if it does not exist.
     *
     * @param tasks List of tasks to be saved.
     * @throws StorageException If an error occurs while writing to the file.
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

    /**
     * Parses a single line from the save file and converts it into a Task object.
     * The line is expected to follow the format used by {@link #serializeTask(Task)}.

     * Supported formats:
     * T | done | description
     * D | done | description | byDate
     * E | done | description | fromDateTime | toDateTime

     * If the line is malformed or cannot be parsed, null is returned.
     *
     * @param line A line read from the save file.
     * @return The corresponding Task object, or null if parsing fails.
     */
    private Task parseTask(String line) {

        try {

            String[] parts = line.split("\\|");

            for (int i = 0; i < parts.length; i++) {
                parts[i] = parts[i].trim();
            }

            String type = parts[0];
            boolean isDone = parts[1].equals("1");
            String desc = parts[2];

            switch (type) {

            case "T":
                Todo todo = new Todo(desc);
                todo.setCompleted(isDone);
                return todo;

            case "D":
                LocalDate by = LocalDate.parse(parts[3]);
                Deadline deadline = new Deadline(desc, by);
                deadline.setCompleted(isDone);
                return deadline;

            case "E":
                LocalDateTime from = LocalDateTime.parse(parts[3]);
                LocalDateTime to = LocalDateTime.parse(parts[4]);
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


    /**
     * Converts a Task object into a string representation suitable for saving to file.
     * The format depends on the task type:

     * Todo:     T | done | description
     * Deadline: D | done | description | byDate
     * Event:    E | done | description | fromDateTime | toDateTime

     * Dates and times are stored using ISO-8601 format as produced by
     * {@link LocalDate#toString()} and {@link LocalDateTime#toString()}.
     *
     * @param task Task to serialize.
     * @return A string representation of the task for file storage.
     */
    private String serializeTask(Task task) {

        String done = task.getCompleted() ? "1" : "0";

        if (task instanceof Todo) {

            return "T | " + done + " | " + task.getDescription();

        } else if (task instanceof Deadline d) {

            return "D | " + done + " | " + d.getDescription()
                    + " | " + d.getBy();   // LocalDate → ISO string

        } else if (task instanceof Event e) {

            return "E | " + done + " | " + e.getDescription()
                    + " | " + e.getFrom()  // LocalDateTime → ISO string
                    + " | " + e.getTo();

        } else {
            return "";
        }
    }
}