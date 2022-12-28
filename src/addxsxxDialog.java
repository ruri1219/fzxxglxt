import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 添加销售信息对话框
 *
 * @author tohka
 * @date 2022/12/28
 */
public class addxsxxDialog extends JDialog {
    final int WIDTH=500;
    final int HEIGTH=400;
    private ActionDoneListener listener;

    /**
     * 添加销售信息对话框
     *
     * @param jf       界面
     * @param title    标题
     * @param isModel  true or flase
     * @param listener 监听器
     */
    public addxsxxDialog(JFrame jf,String title,boolean isModel,ActionDoneListener listener){
        super(jf,title,isModel);
        this.listener=listener;
        //组装视图
        this.setBounds(600,400,WIDTH,HEIGTH);
        Box vBox=Box.createVerticalBox();
        //组装服装编号
        Box idBox=Box.createHorizontalBox();
        JLabel idLabel=new JLabel("服装编号：");
        JTextField idField=new JTextField(15);
        idBox.add(idLabel);
        idBox.add(Box.createHorizontalStrut(50));
        idBox.add(idField);
        //组装销售单号
        Box salescodeBox=Box.createHorizontalBox();
        JLabel salescodeLabel=new JLabel("销售单号(请按照表格中的格式输入)：");
        JTextField salescodeField=new JTextField(15);
        salescodeBox.add(salescodeLabel);
        salescodeBox.add(Box.createHorizontalStrut(20));
        salescodeBox.add(salescodeField);

        //组装客户购买数量
        Box salescountBox=Box.createHorizontalBox();
        JLabel salescountLabel=new JLabel("客户购买数量：");
        JTextField salescountField=new JTextField(15);
        salescountBox.add(salescountLabel);
        salescountBox.add(Box.createHorizontalStrut(20));
        salescountBox.add(salescountField);

        //组装支付方式
        Box paytypeBox=Box.createHorizontalBox();
        JLabel paytypeLabel=new JLabel("支付方式：");
        JTextField paytypeField=new JTextField(15);
        paytypeBox.add(paytypeLabel);
        paytypeBox.add(Box.createHorizontalStrut(20));
        paytypeBox.add(paytypeField);
        //组装客户享受折扣
        Box rebeatBox=Box.createHorizontalBox();
        JLabel rebeatLabel=new JLabel("客户享受折扣：");
        JTextField rebeatField=new JTextField(15);
        rebeatBox.add(rebeatLabel);
        rebeatBox.add(Box.createHorizontalStrut(20));
        rebeatBox.add(rebeatField);
        //组装按钮
        Box btnBox=Box.createHorizontalBox();
        JButton addBtn=new JButton("添加");
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //获取管理员输入的信息
                String fzid=idField.getText().trim();
                String salescode=salescodeField.getText().trim();
                String salescount=salescountField.getText().trim();
                String paytype=paytypeField.getText().trim();
                String rebeat=rebeatField.getText().trim();
                if(!fzid.equals("") && !salescode.equals("") && !salescount.equals("") && !paytype.equals("")&& !rebeat.equals("")){
                    int id=Integer.valueOf(fzid);
                    String sql0="select outprice from isyou where id=?";
                    try {
                        PreparedStatement preparedStatement=setConnection.getConnection().prepareStatement(sql0);
                        preparedStatement.setInt(1,id);
                        ResultSet rs=preparedStatement.executeQuery();
                        if(rs.next()){
                            int Salescount=Integer.valueOf(salescount);
                            Double Rebeat=Double.valueOf(rebeat);
                            Double Receivable=rs.getDouble("outprice")*Salescount;
                            Double Total=Rebeat*Receivable;
                            String sql="insert into xiaoshou (id,salescode,salescount,paytype,rebeat,receivable,total) values(?,?,?,?,?,?,?)";
                            PreparedStatement statement=setConnection.getConnection().prepareStatement(sql);
                            statement.setInt(1,id);
                            statement.setString(2,salescode);
                            statement.setInt(3,Salescount);
                            statement.setString(4,paytype);
                            statement.setDouble(5,Rebeat);
                            statement.setDouble(6,Receivable);
                            statement.setDouble(7,Total);
                            int i=statement.executeUpdate();
                            if(i>0){
                                JOptionPane.showMessageDialog(jf,"添加成功，请刷新界面");
                                listener.done(null);
                                statement.close();
                                dispose();
                            }
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        btnBox.add(addBtn);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(idBox);
        vBox.add(Box.createVerticalStrut(10));
        vBox.add(salescodeBox);
        vBox.add(Box.createVerticalStrut(10));
        vBox.add(salescountBox);
        vBox.add(Box.createVerticalStrut(10));
        vBox.add(paytypeBox);
        vBox.add(Box.createVerticalStrut(10));
        vBox.add(rebeatBox);
        vBox.add(Box.createVerticalStrut(10));
        vBox.add(btnBox);
        //为了左右有间距，在vBox外层封装一个水平的Box，添加间隔
        Box hBox=Box.createHorizontalBox();
        hBox.add(Box.createHorizontalStrut(20));
        hBox.add(vBox);
        hBox.add(Box.createHorizontalStrut(20));
        this.add(hBox);
    }
}