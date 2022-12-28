import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.*;
import java.util.Vector;

/**
 * 采购信息组件
 *
 * @author tohka
 * @date 2022/12/28
 */
public class cgxxComponent extends Box {
    final int WIDTH = 850;
    final int HEIGHT = 600;
    JFrame jf = null;

    private JTable table;
    private Vector<String> titles;
    private Vector<Vector> tableData;
    private TableModel tableModel;

    /**
     * 采购信息组件
     *
     * @param jf 界面
     * @throws SQLException           sqlexception异常
     * @throws ClassNotFoundException 类没有发现异常
     */
    public cgxxComponent(JFrame jf) throws SQLException, ClassNotFoundException {
        //垂直布局
        super(BoxLayout.Y_AXIS);
        this.jf = jf;
        //组装视图
        JPanel btnPanel = new JPanel();
        Color color = new Color(255, 255, 255);
        btnPanel.setBackground(color);
        btnPanel.setMaximumSize(new Dimension(WIDTH, HEIGHT));
        btnPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton dcBtn=new JButton("导出");
        JButton addBtn = new JButton("添加");
        JButton selectBtn = new JButton("查询");

        dcBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new daochu().cgxxdaochu(jf);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //弹出一个对话框，让管理员输入服装的基本信息
                new addcgDialog(jf, "添加采购信息", true, new ActionDoneListener() {
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
                new cgSelectDialog(jf, "查询采购信息", true, new ActionDoneListener() {
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
        btnPanel.add(dcBtn);
        btnPanel.add(addBtn);
        btnPanel.add(selectBtn);
        this.add(btnPanel);
        //组装表格
        String[] ts = {"采购单号", "供应商编号", "服装编号", "采购数量", "采购单价", "总花费"};
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
        String sql = "select * from purchase ";
        Vector<Vector> data = new Vector<>();
        PreparedStatement preparedStatement = setConnection.getConnection().prepareStatement(sql);
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
