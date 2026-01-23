package fr.baptiiiiste.game.renderers;

public class LogRenderer {

    private static final String RESET = "\u001B[0m";
    private static final String BLUE = "\u001B[34m";
    private static final String YELLOW = "\u001B[33m";
    private static final String RED = "\u001B[31m";
    private static final String ORANGE = "\u001B[38;5;208m";

    public static void game(String message) {
        System.out.println(YELLOW + "[GAME] " + RESET + message);
    }

    public static void info(String message) {
        System.out.println(BLUE + "[INFO] " + RESET + message);
    }

    public static void warning(String message) {
        System.out.println(ORANGE + "[WARNING] " + RESET + message);
    }

    public static void error(String message) {
        System.err.println(RED + "[ERROR] " + RESET + message);
    }
}
