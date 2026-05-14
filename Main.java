/**
 * Main.java
 * Role Owner: Error Handling & Testing Engineer
 * Entry point. Displays the menu and routes user choices to CRUD operations.
 */
public class Main {

    public static void main(String[] args) {

        // Test the database connection before showing the menu
        DBConnection.testConnection();

        boolean running = true;

        while (running) {
            printMenu();
            int choice = InputHelper.readIntInRange("Enter your choice: ", 0, 6);

            switch (choice) {
                case 1 -> handleAddStudent();
                case 2 -> CRUDOperations.viewAllStudents();
                case 3 -> handleSearchStudent();
                case 4 -> handleUpdateStudent();
                case 5 -> handleDeleteStudent();
                case 0 -> {
                    System.out.println("\nGoodbye! Exiting the system...");
                    running = false;
                }
                default -> System.out.println("[ERROR] Invalid option.");
            }
        }
    }

    // ─────────────────────────────────────────────────────────────────────
    // Menu display
    // ─────────────────────────────────────────────────────────────────────

    private static void printMenu() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║   STUDENT RECORD MANAGEMENT SYSTEM   ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("║  [1] Add New Student                 ║");
        System.out.println("║  [2] View All Students               ║");
        System.out.println("║  [3] Search Student by ID            ║");
        System.out.println("║  [4] Update Student Record           ║");
        System.out.println("║  [5] Delete Student Record           ║");
        System.out.println("║  [0] Exit                            ║");
        System.out.println("╚══════════════════════════════════════╝");
    }

    // ─────────────────────────────────────────────────────────────────────
    // Handlers — gather input then call CRUDOperations
    // ─────────────────────────────────────────────────────────────────────

    private static void handleAddStudent() {
        System.out.println("\n--- ADD NEW STUDENT ---");

        String firstName = InputHelper.readString("First Name   : ");
        String lastName  = InputHelper.readString("Last Name    : ");
        String email     = InputHelper.readEmail ("Email        : ");
        String course    = InputHelper.readString("Course       : ");
        int    yearLevel = InputHelper.readIntInRange("Year Level (1-5) : ", 1, 5);
        double gpa       = InputHelper.readDoubleInRange("GPA (0.00-4.00)  : ", 0.00, 4.00);

        CRUDOperations.addStudent(firstName, lastName, email, course, yearLevel, gpa);
    }

    private static void handleSearchStudent() {
        System.out.println("\n--- SEARCH STUDENT ---");
        int id = InputHelper.readInt("Enter Student ID: ");
        CRUDOperations.searchStudentById(id);
    }

    private static void handleUpdateStudent() {
        System.out.println("\n--- UPDATE STUDENT RECORD ---");

        int id = InputHelper.readInt("Enter Student ID to update: ");

        // Show current record first so the user knows what they're editing
        CRUDOperations.searchStudentById(id);

        System.out.println("\nEnter new details (press Enter to keep current value is NOT supported — fill all fields):");
        String firstName = InputHelper.readString("New First Name   : ");
        String lastName  = InputHelper.readString("New Last Name    : ");
        String email     = InputHelper.readEmail ("New Email        : ");
        String course    = InputHelper.readString("New Course       : ");
        int    yearLevel = InputHelper.readIntInRange("New Year Level (1-5) : ", 1, 5);
        double gpa       = InputHelper.readDoubleInRange("New GPA (0.00-4.00)  : ", 0.00, 4.00);

        if (InputHelper.confirm("Confirm update?")) {
            CRUDOperations.updateStudent(id, firstName, lastName, email, course, yearLevel, gpa);
        } else {
            System.out.println("[INFO] Update cancelled.");
        }
    }

    private static void handleDeleteStudent() {
        System.out.println("\n--- DELETE STUDENT RECORD ---");

        int id = InputHelper.readInt("Enter Student ID to delete: ");

        // Show the record so the user knows what they're deleting
        CRUDOperations.searchStudentById(id);

        if (InputHelper.confirm("\nAre you sure you want to delete this record?")) {
            CRUDOperations.deleteStudent(id);
        } else {
            System.out.println("[INFO] Deletion cancelled.");
        }
    }
}
// Testing Engineer: Gel 
