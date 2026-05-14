import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database Engineer: Paulo
 * Database Connection Setup and MySQL Configuration
 */
public class DBConnection {

    // ── Change these three values to match your setup ──────────────────────
    private static final String URL      = "jdbc:mysql://localhost:3306/student_db";
    private static final String USERNAME = "root";      // your MySQL username
    private static final String PASSWORD = "";          // your MySQL password
    // ───────────────────────────────────────────────────────────────────────

    /**
     * Opens and returns a new Connection to the database.
     * The caller is responsible for closing it.
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Load the MySQL JDBC driver (required for older driver versions)
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("[ERROR] MySQL JDBC Driver not found.");
            System.out.println("        Download mysql-connector-j and add it to your classpath.");
            throw new SQLException("Driver not found", e);
        }
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    /**
     * Quick test — prints OK or an error message.
     */
    public static void testConnection() {
        System.out.print("Testing database connection... ");
        try (Connection conn = getConnection()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("OK");
            }
        } catch (SQLException e) {
            System.out.println("FAILED");
            System.out.println("[ERROR] " + e.getMessage());
        }
    }
}
