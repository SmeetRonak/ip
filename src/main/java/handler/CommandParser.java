package handler;

/**
 * Parses user input into commands and arguments.
 */
public class CommandParser {


    /**
     * Extracts the command from a line of user input.

     * The command is defined as the first word in the input, converted to lowercase.
     *
     * @param inputLine The raw line of user input.
     * @return The command string in lowercase.
     */
    public String getCommand(String inputLine) {
        inputLine = inputLine.trim();
        String[] tokens = inputLine.split("\\s+", 2);
        return tokens[0].toLowerCase();
    }


    /**
     * Extracts the arguments from a line of user input.

     * Arguments are considered everything after the first word.
     * If there are no arguments, an empty string is returned.
     *
     * @param inputLine The raw line of user input.
     * @return The arguments string, or an empty string if none exist.
     */
    public String getArguments(String inputLine) {
        inputLine = inputLine.trim();
        String[] tokens = inputLine.split("\\s+", 2);
        return tokens.length > 1 ? tokens[1].trim() : "";
    }
}
