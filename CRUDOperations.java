import java.sql.*;

/**
 * CRUDOperations.java
 * Role Owner: Backend Logic Developer
 * Contains all Create, Read, Update, Delete operations using JDBC.
 */
public class CRUDOperations {

    // ─────────────────────────────────────────────────────────────────────
    // CREATE
    // ─────────────────────────────────────────────────────────────────────

    /**
     * Inserts a new student record into the database.
     * Uses a transaction so changes are rolled back on failure.
     */
    public static void addStudent(String firstName, String lastName,
                                  String email, String course,
                                  int yearLevel, double gpa) {
        String sql = "INSERT INTO students (first_name, last_name, email, course, year_level, gpa) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";

        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);                          // begin transaction

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, email);
            ps.setString(4, course);
            ps.setInt   (5, yearLevel);
            ps.setDouble(6, gpa);

            int rows = ps.executeUpdate();
            conn.commit();                                      // commit transaction

            if (rows > 0) {
                System.out.println("\n[SUCCESS] Student added successfully!");
            }
            ps.close();

        } catch (SQLIntegrityConstraintViolationException e) {
            rollback(conn);
            System.out.println("[ERROR] Email already exists. Please use a different email.");
        } catch (SQLException e) {
            rollback(conn);
            System.out.println("[ERROR] Failed to add student: " + e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

    // ─────────────────────────────────────────────────────────────────────
    // READ – view all
    // ─────────────────────────────────────────────────────────────────────

    /** Prints all student records in a formatted table. */
    public static void viewAllStudents() {
        String sql = "SELECT * FROM students ORDER BY student_id";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n" + "=".repeat(95));
            System.out.printf("%-5s %-15s %-15s %-30s %-10s %-6s %-5s%n",
                    "ID", "First Name", "Last Name", "Email", "Course", "Year", "GPA");
            System.out.println("=".repeat(95));

            boolean hasRecords = false;
            while (rs.next()) {
                hasRecords = true;
                System.out.printf("%-5d %-15s %-15s %-30s %-10s %-6d %-5.2f%n",
                        rs.getInt   ("student_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("course"),
                        rs.getInt   ("year_level"),
                        rs.getDouble("gpa"));
            }

            if (!hasRecords) {
                System.out.println("  No student records found.");
            }
            System.out.println("=".repeat(95));

        } catch (SQLException e) {
            System.out.println("[ERROR] Failed to retrieve records: " + e.getMessage());
        }
    }

    // ─────────────────────────────────────────────────────────────────────
    // READ – search by ID
    // ─────────────────────────────────────────────────────────────────────

    /** Searches for and prints a single student by their ID. */
    public static void searchStudentById(int id) {
        String sql = "SELECT * FROM students WHERE student_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("\n--- Student Found ---");
                System.out.println("ID         : " + rs.getInt   ("student_id"));
                System.out.println("First Name : " + rs.getString("first_name"));
                System.out.println("Last Name  : " + rs.getString("last_name"));
                System.out.println("Email      : " + rs.getString("email"));
                System.out.println("Course     : " + rs.getString("course"));
                System.out.println("Year Level : " + rs.getInt   ("year_level"));
                System.out.println("GPA        : " + rs.getDouble("gpa"));
            } else {
                System.out.println("[INFO] No student found with ID " + id + ".");
            }
            rs.close();

        } catch (SQLException e) {
            System.out.println("[ERROR] Search failed: " + e.getMessage());
        }
    }

    // ─────────────────────────────────────────────────────────────────────
    // UPDATE
    // ─────────────────────────────────────────────────────────────────────

    /**
     * Updates an existing student record.
     * Uses a transaction so changes are rolled back on failure.
     */
    public static void updateStudent(int id, String firstName, String lastName,
                                     String email, String course,
                                     int yearLevel, double gpa) {
        String sql = "UPDATE students SET first_name=?, last_name=?, email=?, "
                   + "course=?, year_level=?, gpa=? WHERE student_id=?";

        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);                          // begin transaction

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, email);
            ps.setString(4, course);
            ps.setInt   (5, yearLevel);
            ps.setDouble(6, gpa);
            ps.setInt   (7, id);

            int rows = ps.executeUpdate();
            conn.commit();                                      // commit transaction

            if (rows > 0) {
                System.out.println("\n[SUCCESS] Student record updated successfully!");
            } else {
                System.out.println("[INFO] No student found with ID " + id + ".");
            }
            ps.close();

        } catch (SQLIntegrityConstraintViolationException e) {
            rollback(conn);
            System.out.println("[ERROR] Email already exists. Please use a different email.");
        } catch (SQLException e) {
            rollback(conn);
            System.out.println("[ERROR] Update failed: " + e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

    // ─────────────────────────────────────────────────────────────────────
    // DELETE
    // ─────────────────────────────────────────────────────────────────────

    /**
     * Deletes a student record by ID.
     * Uses a transaction so changes are rolled back on failure.
     */
    public static void deleteStudent(int id) {
        String sql = "DELETE FROM students WHERE student_id = ?";

        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);                          // begin transaction

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            int rows = ps.executeUpdate();
            conn.commit();                                      // commit transaction

            if (rows > 0) {
                System.out.println("\n[SUCCESS] Student with ID " + id + " deleted successfully!");
            } else {
                System.out.println("[INFO] No student found with ID " + id + ".");
            }
            ps.close();

        } catch (SQLException e) {
            rollback(conn);
            System.out.println("[ERROR] Delete failed: " + e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

    // ─────────────────────────────────────────────────────────────────────
    // Helpers (private)
    // ─────────────────────────────────────────────────────────────────────

    private static void rollback(Connection conn) {
        if (conn != null) {
            try {
                conn.rollback();
                System.out.println("[INFO] Transaction rolled back.");
            } catch (SQLException e) {
                System.out.println("[ERROR] Rollback failed: " + e.getMessage());
            }
        }
    }

    private static void closeConnection(Connection conn) {
        if (conn != null) {
            try { conn.close(); } catch (SQLException ignored) {}
        }
    }
}
