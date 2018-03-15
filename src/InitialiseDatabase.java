import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
 
/**
 *
 * @author sqlitetutorial.net
 */
public class InitialiseDatabase {
 
    /**
     * Create a new table in the test database
     *
     */
    public static void createNewTable() {
        // SQLite connection string
        String url = "jdbc:sqlite:C://My Things/SQLite/secureApplication.db";
        
        // SQL statement for creating a new table
        String sqlPeople = "CREATE TABLE IF NOT EXISTS people (\n"
                + "	id varchar PRIMARY KEY,\n"
                + "	publicKey varchar NOT NULL\n"
                + ");";
        
        String sqlMessages = "CREATE TABLE IF NOT EXISTS messages (\n"
        		+ " message text PRIMARY KEY,\n"
        		+ "sender varchar NOT NULL,\n"
        		+ "recipient varchar NOT NULL,\n"
        		+ "subject varchar NOT NULL\n"
        		+ ");";
        		
        
        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sqlPeople);
            stmt.execute(sqlMessages);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
 
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        createNewTable();
    }
 
}