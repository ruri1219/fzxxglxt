import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Vector;

/**
 * 服装库存组件
 *
 * @author tohka
 * @date 2022/12/28
 */
public class fzkcComponent extends Box {
    final int WIDTH = 850;
    final int HEIGHT = 600;
    JFrame jf = null;

    private JTable table;
    private Vector<String> titles;
    private Vector<Vector> tableData;
    private TableModel tableModel;


    /**
     * 服装库存组件
     *
     * @param jf jframe
     * @throws SQLException           sqlexception异常
     * @throws ClassNotFoundException 类没有发现异常
     */
    public fzkcComponent(JFrame jf) throws SQLException, ClassNotFoundException {
        //垂直布局
        super(BoxLayout.Y_AXIS);
        this.jf = jf;
        //组装视图
        JPanel btnPanel = new JPanel();
        Color color = new Color(255, 255, 255);
        btnPanel.setBackground(color);
        btnPanel.setMaximumSize(new Dimension(WIDTH, HEIGHT));
        btnPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton updateBtn = new JButton("修改");

        updateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new kcupdateDialog(jf, "修改库存", true, new ActionDoneListener() {
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
        btnPanel.add(updateBtn);
//        btnPanel.add(deleteBtn);
        this.add(btnPanel);
        //组装表格
        String[] ts = {"服装编号", "服装名称", "服装类型", "服装尺寸", "服装颜色", "进价", "售价", "库存", "供应商编号"};
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
        String sql = "select * from isyou ";
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
