import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

/**
 * 销售信息查询结果
 *
 * @author tohka
 * @date 2022/12/28
 */
public class xsSelectResult extends JDialog {
    final int WIDTH = 850;
    final int HEIGTH = 600;

    private JTable table;
    private Vector<String> titles;
    private Vector<Vector> tableData;
    private TableModel tableModel;
    private ActionDoneListener listener;
    int id=0;

    /**
     * xs选择结果
     *
     * @param jf       界面
     * @param title    标题
     * @param isModel  true or flase
     * @param id       服装编号
     * @param listener 监听器
     * @throws SQLException           sqlexception异常
     * @throws ClassNotFoundException 类没有发现异常
     */
    public xsSelectResult(JFrame jf, String title, boolean isModel,int id, ActionDoneListener listener) throws SQLException, ClassNotFoundException {
        super(jf,title,isModel);
        this.listener=listener;
        this.id=id;

        //组装试图
        this.setBounds(600,400,WIDTH,HEIGTH);
        //组装表格
        String[] ts = {"服装编号", "销售单号", "客户购买数量", "支付方式", "客户享受折扣", "应收款", "实收款"};
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
        xsselectrequerstData();
    }

    /**
     * 请求销售信息查询结果
     *
     * @throws SQLException           sqlexception异常
     * @throws ClassNotFoundException 类没有发现异常
     *///请求数据
    public void xsselectrequerstData() throws SQLException, ClassNotFoundException {
        tableData.clear();
        Vector<Vector> data = xsselectgetInformationInterface();
        for (Vector vector : data) {
            tableData.add(vector);
        }
    }

    /**
     * 销售信息查询结果信息界面
     *
     * @return {@link Vector}<{@link Vector}>
     * @throws SQLException           sqlexception异常
     * @throws ClassNotFoundException 类没有发现异常
     *///从数据库中获取到查询的类型的销售信息
    public Vector<Vector> xsselectgetInformationInterface() throws SQLException, ClassNotFoundException {
        String sql = "select * from xiaoshou where id=?";
        Vector<Vector> data = new Vector<>();
        PreparedStatement preparedStatement = setConnection.getConnection().prepareStatement(sql);
        preparedStatement.setInt(1,id);
        ResultSet rs = preparedStatement.executeQuery();
        String id, salescode, salescount, paytype, rebeat, receivable, total= null;
        while (rs.next()) {
            Vector information = new Vector<>();
            id = rs.getString("id");
            salescode = rs.getString("salescode");
            salescount = rs.getString("salescount");
            paytype = rs.getString("paytype");
            rebeat = rs.getString("rebeat");
            receivable = rs.getString("receivable");
            total = rs.getString("total");
            information.add(id);
            information.add(salescode);
            information.add(salescount);
            information.add(paytype);
            information.add(rebeat);
            information.add(receivable);
            information.add(total);
            data.add(information);
        }
        preparedStatement.close();
        rs.close();
        return data;
    }
}
