package Gomoku;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDatabase {
    private Connection conn;

    public UserDatabase(Connection conn) {
        this.conn = conn;
        createUsersTable();
    }

    private void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users ("
                   + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                   + "username TEXT NOT NULL UNIQUE, "
                   + "password TEXT NOT NULL);";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.execute();
            System.out.println("Main sheet has been created！");
        } catch (SQLException e) {
            System.out.println("Fail to create sheet: " + e.getMessage());
        }
    }

    public void registerUser(String username, String password) {
        String sql = "INSERT INTO users(username, password) VALUES(?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.executeUpdate();
            System.out.println("User registered successfully！");

            createUserStatsTable(username);
        } catch (SQLException e) {
            System.out.println("Fail to register: " + e.getMessage());
        }
    }

    private void createUserStatsTable(String username) {
    	String tableName = "user_" + username;
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " ("
                   + "match_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                   + "result TEXT NOT NULL, "
                   + "opponent TEXT NOT NULL, "
                   + "win_count INTEGER DEFAULT 0, "
                   + "win_rate REAL DEFAULT 0.0 "
                   + ");";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.execute();
            System.out.println("User " + username + "'s data sheet has been created！");
        } catch (SQLException e) {
            System.out.println("Fail to create data sheet for user: " + e.getMessage());
        }
    }
}