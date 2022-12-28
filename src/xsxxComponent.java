import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Vector;

/**
 * 销售信息组件
 *
 * @author tohka
 * @date 2022/12/28
 */
public class xsxxComponent extends Box {
    final int WIDTH = 850;
    final int HEIGHT = 600;
    JFrame jf = null;

    private JTable table;
    private Vector<String> titles;
    private Vector<Vector> tableData;
    private TableModel tableModel;

    /**
     * 销售信息组件
     *
     * @param jf 界面
     * @throws SQLException           sqlexception异常
     * @throws ClassNotFoundException 类没有发现异常
     */
    public xsxxComponent(JFrame jf) throws SQLException, ClassNotFoundException {
        //垂直布局
        super(BoxLayout.Y_AXIS);
        this.jf = jf;
        //组装视图
        JPanel btnPanel = new JPanel();
        Color color = new Color(255, 255, 255);
        btnPanel.setBackground(color);
        btnPanel.setMaximumSize(new Dimension(WIDTH, HEIGHT));
        btnPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton addBtn = new JButton("添加");
        JButton selectBtn = new JButton("查询");

        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //弹出一个对话框，让管理员输入服装的基本信息
                new addxsxxDialog(jf, "添加销售信息", true, new ActionDoneListener() {
                    @Override
                    public void done(Object o) {
                        try {
                            requerstData();
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        } catch (ClassNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }).setVisible(true);
            }
        });

        selectBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new xsselectDialog(jf, "查询服装信息", true, new ActionDoneListener() {
                    @Override
                    public void done(Object o) {
                        try {
                            requerstData();
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        } catch (ClassNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }).setVisible(true);
            }
        });
        btnPanel.add(addBtn);
        btnPanel.add(selectBtn);
//        btnPanel.add(updateBtn);
//        btnPanel.add(deleteBtn);
        this.add(btnPanel);
        //组装表格
        String[] ts = {"服装编号", "销售单号", "客户购买数量", "支付方式", "客户享受折扣", "应收款", "实收款"};
        titles = new Vector<>();
        for (String title : ts) {
            titles.add(title);
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
        requerstData();

    }

    /**
     * 请求数据
     *
     * @throws SQLException           sqlexception异常
     * @throws ClassNotFoundException 类没有发现异常
     *///请求数据
    public void requerstData() throws SQLException, ClassNotFoundException {
        tableData.clear();
        Vector<Vector> data = getInformationInterface();
        for (Vector vector : data) {
            tableData.add(vector);
        }
        //刷新表格
        table.removeAll();
    }

    /**
     * 获取信息界面
     *
     * @return {@link Vector}<{@link Vector}>
     * @throws SQLException           sqlexception异常
     * @throws ClassNotFoundException 类没有发现异常
     *///从数据库得到全部服装信息
    public Vector<Vector> getInformationInterface() throws SQLException, ClassNotFoundException {
        String sql = "select * from xiaoshou ";
        Vector<Vector> data = new Vector<>();
        PreparedStatement preparedStatement = setConnection.getConnection().prepareStatement(sql);
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
