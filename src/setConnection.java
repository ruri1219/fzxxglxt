import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 设置连接
 *
 * @author tohka
 * @date 2022/12/28
 */
public class setConnection {
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        String url = "jdbc:mysql://localhost:3306/fz";
        String user = "root";
        String password = "tohka0410";

        Connection connection = DriverManager.getConnection(url,user,password);
        return connection;
    }
}
