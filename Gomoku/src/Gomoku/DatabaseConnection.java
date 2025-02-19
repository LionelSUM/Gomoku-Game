package Gomoku;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String DB_URL = "jdbc:sqlite:D:\\Course_Material\\Graduate\\Java\\Final Project\\Gomoku\\GOMOKU_database.db";

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL);
            System.out.println("Connect with database successfully！");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
}