public class Friday {
    //initialize static variables
    private static final int lineLength = 100;

    //helper function to print horizontal lines
    private static void printLine() {
        System.out.println("-".repeat(lineLength));
    }

    public static void main(String[] args) {
        String logo = """
                  _____ ____  ___ ____    _ __   __
                 |  ___|  _ \\|_ _|  _ \\  / \\\\ \\ / /
                 | |_  | |_) || || | | |/ _ \\\\ V /
                 |  _| |  _ < | || |_| / ___ \\| |
                 |_|   |_| \\_\\___|____/_/   \\_\\_|
                """;
        printLine();
        System.out.println(logo);
        System.out.println("Hello, I'm Friday!");
        System.out.println("What can I do for you?");
        printLine();
        System.out.println("Bye. Hope to see you soon!");
        printLine();
    }
}


