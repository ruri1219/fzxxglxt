import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

/**
 * 升序排序结果
 *
 * @author tohka
 * @date 2022/12/28
 */
public class ascResult extends JDialog {
    /**
     * 宽度
     */
    final int WIDTH = 850;
    /**
     * 高度
     */
    final int HEIGTH = 600;

    /**
     * 表格
     */
    private JTable table;
    /**
     * 标题
     */
    private Vector<String> titles;
    /**
     * 表数据
     */
    private Vector<Vector> tableData;
    /**
     * 表格模型
     */
    private TableModel tableModel;
    /**
     * 监听器
     */
    private ActionDoneListener listener;

    /**
     * 升序排序结果
     *
     * @param jf       界面
     * @param title    标题
     * @param isModel  true or flase
     * @param listener 监听器
     * @throws SQLException           sqlexception异常
     * @throws ClassNotFoundException 类没有发现异常
     */
    public ascResult(JFrame jf, String title, boolean isModel, ActionDoneListener listener) throws SQLException, ClassNotFoundException {
        super(jf,title,isModel);
        this.listener=listener;

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
     * 请求查询结果数据
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
     * 获取查询结果信息
     *
     * @return {@link Vector}<{@link Vector}>
     * @throws SQLException           sqlexception异常
     * @throws ClassNotFoundException 类没有发现异常
     *///从数据库中获取到查询的类型的服装信息
    public Vector<Vector> selectgetInformationInterface() throws SQLException, ClassNotFoundException {
        String sql="select * from isyou order by outprice asc";
        Vector<Vector> data = new Vector<>();
        PreparedStatement preparedStatement = setConnection.getConnection().prepareStatement(sql);
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
