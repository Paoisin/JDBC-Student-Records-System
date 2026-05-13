import java.util.Scanner;

/**
 * InputHelper.java
 * Role Owner: Input & Validation Engineer
 * Provides safe, validated input methods to prevent crashes and bad data.
 */
public class InputHelper {

    private static final Scanner scanner = new Scanner(System.in);

    // ─────────────────────────────────────────────────────────────────────
    // Integer input
    // ─────────────────────────────────────────────────────────────────────

    /**
     * Repeatedly prompts the user until they enter a valid integer.
     */
    public static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("[INVALID] Please enter a whole number.");
            }
        }
    }

    /**
     * Reads an integer that must fall within [min, max].
     */
    public static int readIntInRange(String prompt, int min, int max) {
        while (true) {
            int value = readInt(prompt);
            if (value >= min && value <= max) {
                return value;
            }
            System.out.println("[INVALID] Please enter a number between " + min + " and " + max + ".");
        }
    }

    // ─────────────────────────────────────────────────────────────────────
    // Double input
    // ─────────────────────────────────────────────────────────────────────

    /**
     * Reads a decimal number within [min, max].
     */
    public static double readDoubleInRange(String prompt, double min, double max) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                double value = Double.parseDouble(input);
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.println("[INVALID] Please enter a value between " + min + " and " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("[INVALID] Please enter a valid decimal number (e.g. 1.75).");
            }
        }
    }

    // ─────────────────────────────────────────────────────────────────────
    // String input
    // ─────────────────────────────────────────────────────────────────────

    /**
     * Reads a non-empty string. Rejects blank/whitespace-only input.
     */
    public static String readString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("[INVALID] This field cannot be empty.");
        }
    }

    // ─────────────────────────────────────────────────────────────────────
    // Email input
    // ─────────────────────────────────────────────────────────────────────

    /**
     * Reads and validates an email address (must contain @ and a dot after @).
     */
    public static String readEmail(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (isValidEmail(input)) {
                return input;
            }
            System.out.println("[INVALID] Please enter a valid email address (e.g. juan@school.edu).");
        }
    }

    private static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) return false;
        int atIndex = email.indexOf('@');
        if (atIndex < 1) return false;                        // must have chars before @
        String domain = email.substring(atIndex + 1);
        return domain.contains(".");                          // must have a dot after @
    }

    // ─────────────────────────────────────────────────────────────────────
    // Confirmation
    // ─────────────────────────────────────────────────────────────────────

    /**
     * Asks a yes/no question. Returns true if the user types 'y' or 'yes'.
     */
    public static boolean confirm(String prompt) {
        System.out.print(prompt + " (y/n): ");
        String input = scanner.nextLine().trim().toLowerCase();
        return input.equals("y") || input.equals("yes");
    }
}
