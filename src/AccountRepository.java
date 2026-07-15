import java.math.BigDecimal;
import java.sql.*;
public class AccountRepository {
    private final String url = "jdbc:sqlite:trading.db";

    public void initialize() throws SQLException{
        try(Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement()){
            stmt.execute("CREATE TABLE IF NOT EXISTS accounts(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "username TEXT UNIQUE NOT NULL, " +
                    "first_name TEXT NOT NULL, " +
                    "last_name TEXT NOT NULL, " +
                    "age INTEGER NOT NULL, " +
                    "balance TEXT NOT NULL)");
            stmt.execute("CREATE TABLE IF NOT EXISTS positions (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "account_id INTEGER NOT NULL, " +
                    "ticker TEXT NOT NULL, " +
                    "shares INTEGER NOT NULL, " +
                    "avg_cost TEXT NOT NULL, " +
                    "FOREIGN KEY (account_id) REFERENCES accounts(id))");
        }
    }
    public long createAccount(String username, String first_name,
                              String last_name, int age, BigDecimal balance) throws SQLException{
        String sql = "INSERT INTO accounts (username, first_name, last_name, age, balance) " +
                "VALUES (?,?,?,?,?)";
        try(Connection conn = DriverManager.getConnection(url);
        PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, username);
            ps.setString(2, first_name);
            ps.setString(3, last_name);
            ps.setInt(4, age);
            ps.setString(5, balance.toString());
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            keys.next();
            return keys.getLong(1);
        }
    }
    public boolean usernameExists(String username) throws SQLException{
        String sql = "SELECT id FROM accounts WHERE username = ?";
        try(Connection conn = DriverManager.getConnection(url);
        PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
    }
}
