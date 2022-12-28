import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

/**
 * 查询结果
 *
 * @author tohka
 * @date 2022/12/28
 */
public class selectResult extends JDialog {
    final int WIDTH = 850;
    final int HEIGTH = 600;

    private JTable table;
    private Vector<String> titles;
    private Vector<Vector> tableData;
    private TableModel tableModel;
    private ActionDoneListener listener;
    String fztype=null;

    /**
     * 查询结果
     *
     * @param jf       界面
     * @param title    标题
     * @param isModel  true or flase
     * @param fztype   服装类型
     * @param listener 监听器
     * @throws SQLException           sqlexception异常
     * @throws ClassNotFoundException 类没有发现异常
     */
    public selectResult(JFrame jf, String title, boolean isModel, String fztype, ActionDoneListener listener) throws SQLException, ClassNotFoundException {
        super(jf,title,isModel);
        this.listener=listener;
        this.fztype=fztype;

        //组装试图
        this.setBounds(600,400,WIDTH,HEIGTH);
        //组装表格
        String[] ts = {"服装编号", "服装名称", "服装类型", "服装尺寸", "服装颜色", "进价", "售价", "库存", "供应商编号"};
        titles = new Vector<>();
        for (String title0 : ts) {
            titles.add(title0);
        }
        tableData = new Vector<>();
        tableModel = new DefaultTableModel(tableData, titles);
        table = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane);
        selectrequerstData();
    }

    /**
     * 请求数据
     *
     * @throws SQLException           sqlexception异常
     * @throws ClassNotFoundException 类没有发现异常
     *///请求数据
    public void selectrequerstData() throws SQLException, ClassNotFoundException {
        tableData.clear();
        Vector<Vector> data = selectgetInformationInterface();
        for (Vector vector : data) {
            tableData.add(vector);
        }
    }

    /**
     * 获取查询结果信息界面
     *
     * @return {@link Vector}<{@link Vector}>
     * @throws SQLException           sqlexception异常
     * @throws ClassNotFoundException 类没有发现异常
     *///从数据库中获取到查询的类型的服装信息
    public Vector<Vector> selectgetInformationInterface() throws SQLException, ClassNotFoundException {
        String sql="select * from isyou where fztype=?";
        Vector<Vector> data = new Vector<>();
        PreparedStatement preparedStatement = setConnection.getConnection().prepareStatement(sql);
        preparedStatement.setString(1,fztype);
        ResultSet rs = preparedStatement.executeQuery();
        String id, fzname, fztype, fzsize, color, inprice, outprice, kucun, gysid = null;
        while (rs.next()) {
            Vector information = new Vector<>();
            id = rs.getString("id");
            fzname = rs.getString("fzname");
            fztype = rs.getString("fztype");
            fzsize = rs.getString("fzsize");
            color = rs.getString("color");
            inprice = rs.getString("inprice");
            outprice = rs.getString("outprice");
            kucun = rs.getString("kucun");
            gysid = rs.getString("gysid");
            information.add(id);
            information.add(fzname);
            information.add(fztype);
            information.add(fzsize);
            information.add(color);
            information.add(inprice);
            information.add(outprice);
            information.add(kucun);
            information.add(gysid);
            data.add(information);
        }
        preparedStatement.close();
        rs.close();
        return data;
    }
}
