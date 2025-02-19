package Gomoku;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GameDatabase {
    private Connection conn;

    public GameDatabase() {
        conn = DatabaseConnection.connect();
    }

    public void recordMatchResult(String username, String opponent, String result) {
        String tableName = "user_" + username;
        String insertSql = "INSERT INTO " + tableName + " (result, opponent) VALUES(?, ?)";
        String updateStatsSql = "UPDATE " + tableName + " SET win_count = ?, win_rate = ? WHERE match_id = (SELECT MAX(match_id) FROM " + tableName + ")";

        try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
            insertStmt.setString(1, result);
            insertStmt.setString(2, opponent);
            insertStmt.executeUpdate();
            System.out.println("Record " + username + "'s game data：" + result + " Oponent：" + opponent);

            int winCount = getWinCount(tableName);
            int totalCount = getTotalMatchCount(tableName);
            double winRate = totalCount > 0 ? (double) winCount / totalCount * 100 : 0;

            try (PreparedStatement updateStmt = conn.prepareStatement(updateStatsSql)) {
                updateStmt.setInt(1, winCount);
                updateStmt.setDouble(2, winRate);
                updateStmt.executeUpdate();
                System.out.println(username + "'s record has been stored！");
            }

        } catch (SQLException e) {
            System.out.println("Fail to record game: " + e.getMessage());
        }
    }
    
    private int getWinCount(String tableName) throws SQLException {
        String winQuery = "SELECT COUNT(*) FROM " + tableName + " WHERE result = 'WIN'";
        try (PreparedStatement winStmt = conn.prepareStatement(winQuery);
             ResultSet winRs = winStmt.executeQuery()) {
            return winRs.next() ? winRs.getInt(1) : 0;
        }
    }
    private int getTotalMatchCount(String tableName) throws SQLException {
        String totalQuery = "SELECT COUNT(*) FROM " + tableName;
        try (PreparedStatement totalStmt = conn.prepareStatement(totalQuery);
             ResultSet totalRs = totalStmt.executeQuery()) {
            return totalRs.next() ? totalRs.getInt(1) : 0;
        }
    }
}