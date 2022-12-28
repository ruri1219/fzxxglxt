import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

/**
 * 采购信息查询结果
 *
 * @author tohka
 * @date 2022/12/28
 */
public class cgSelectResult extends JDialog {
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
     * 服装编号
     */
    int id=0;

    /**
     * cg选择结果
     *
     * @param jf       界面
     * @param title    标题
     * @param isModel  true or flase
     * @param id       服装编号
     * @param listener 监听器
     * @throws SQLException           sqlexception异常
     * @throws ClassNotFoundException 类没有发现异常
     */
    public cgSelectResult(JFrame jf, String title, boolean isModel,int id, ActionDoneListener listener) throws SQLException, ClassNotFoundException {
        super(jf,title,isModel);
        this.listener=listener;
        this.id=id;

        //组装试图
        this.setBounds(600,400,WIDTH,HEIGTH);
        //组装表格
        String[] ts = {"采购单号", "供应商编号", "服装编号", "采购数量", "采购单价", "总花费"};
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
        //设置只能选中一行
        /*table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);*/
        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane);
        cgselectrequerstData();
    }

    /**
     * 请求采购信息查询结果数据
     *
     * @throws SQLException           sqlexception异常
     * @throws ClassNotFoundException 类没有发现异常
     *///请求数据
    public void cgselectrequerstData() throws SQLException, ClassNotFoundException {
        tableData.clear();
        Vector<Vector> data = cgselectgetInformationInterface();
        for (Vector vector : data) {
            tableData.add(vector);
        }
    }

    /**
     * 获取采购信息查询结果
     *
     * @return {@link Vector}<{@link Vector}>
     * @throws SQLException           sqlexception异常
     * @throws ClassNotFoundException 类没有发现异常
     *///从数据库中获取到查询的类型的销售信息
    public Vector<Vector> cgselectgetInformationInterface() throws SQLException, ClassNotFoundException {
        String sql = "select * from purchase where id=?";
        Vector<Vector> data = new Vector<>();
        PreparedStatement preparedStatement = setConnection.getConnection().prepareStatement(sql);
        preparedStatement.setInt(1,id);
        ResultSet rs = preparedStatement.executeQuery();
        String cgdh, gysid, id, purchasecount, price, money= null;
        while (rs.next()) {
            Vector information = new Vector<>();
            cgdh = rs.getString("cgdh");
            gysid = rs.getString("gysid");
            id = rs.getString("id");
            purchasecount = rs.getString("purchasecount");
            price = rs.getString("price");
            money = rs.getString("money");
            information.add(cgdh);
            information.add(gysid);
            information.add(id);
            information.add(purchasecount);
            information.add(price);
            information.add(money);
            data.add(information);
        }
        preparedStatement.close();
        rs.close();
        return data;
    }
}
