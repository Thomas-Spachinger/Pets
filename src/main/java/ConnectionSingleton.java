import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionSingleton {
    private static Connection con;

    private static Connection connect() {
        String conn = "jdbc:mysql://127.0.0.1:3306/household?IntegratedSecurity=true";
        try  {
            System.out.println("Connected");
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(conn, "root", "");
        }
        catch (SQLException e) {
            System.out.println("Error");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public static Connection getInstance() {
        try {
            if (con == null || con.isClosed()) {
                return connect();
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return con;
    }
}
