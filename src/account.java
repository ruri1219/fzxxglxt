import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * 账户
 *
 * @author tohka
 * @date 2022/12/28
 */
public class account {
    /**
     * 添加账户
     *
     * @param username 用户名
     * @param password 密码
     * @param phone    电话
     * @return int 返回被修改的数据条数
     * @throws SQLException           sqlexception异常
     * @throws ClassNotFoundException 类没有发现异常
     */
    public static int addAccount(String username,String password,String phone) throws SQLException, ClassNotFoundException {
        String sql="insert into zh(username,password,phone) values(?,?,?)";
        PreparedStatement stmt=setConnection.getConnection().prepareStatement(sql);
        stmt.setString(1,username);
        stmt.setString(2,password);
        stmt.setString(3,phone);
        int count=stmt.executeUpdate();
        return count;
    }

    /**
     * 检查帐户是否注册过
     *
     * @param username 用户名
     * @param password 密码
     * @return int 返回1为注册过 登陆成功，返回0为未注册登录失败
     * @throws SQLException           sqlexception异常
     * @throws ClassNotFoundException 类没有发现异常
     */
    public static int checkAccount(String username,String password) throws SQLException, ClassNotFoundException {
        String sql="select * from zh where username=? and password=? ";
        PreparedStatement stmt=setConnection.getConnection().prepareStatement(sql);
        stmt.setString(1,username);
        stmt.setString(2,password);
        ResultSet rs=stmt.executeQuery();
        if (rs.next())
            return 1;
        else
            return 0;
    }
}
