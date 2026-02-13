package handler;

/**
 * Parses user input into commands and arguments.
 */
public class CommandParser {

    public String getCommand(String inputLine) {
        inputLine = inputLine.trim();
        String[] tokens = inputLine.split("\\s+", 2);
        return tokens[0].toLowerCase();
    }

    public String getArguments(String inputLine) {
        inputLine = inputLine.trim();
        String[] tokens = inputLine.split("\\s+", 2);
        return tokens.length > 1 ? tokens[1].trim() : "";
    }
}
