import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 添加采购信息对话框
 *
 * @author tohka
 * @date 2022/12/28
 */
public class addcgDialog extends JDialog {
    /**
     * 宽度
     */
    final int WIDTH=500;
    /**
     * 高度
     */
    final int HEIGTH=400;
    /**
     * 监听器
     */
    private ActionDoneListener listener;

    /**
     * 添加采购信息对话框
     *
     * @param jf       界面
     * @param title    标题
     * @param isModel  true or flase
     * @param listener 监听器
     */
    public addcgDialog(JFrame jf,String title,boolean isModel,ActionDoneListener listener){
        super(jf,title,isModel);
        this.listener=listener;
        //组装视图
        this.setBounds(600,400,WIDTH,HEIGTH);
        Box vBox=Box.createVerticalBox();

        //组装采购单号
        Box cgdhBox=Box.createHorizontalBox();
        JLabel cgdhLabel=new JLabel("采购单号：");
        JTextField cgdhField=new JTextField(15);
        cgdhBox.add(cgdhLabel);
        cgdhBox.add(Box.createHorizontalStrut(20));
        cgdhBox.add(cgdhField);

        //组装供应商编号
        Box gysidBox=Box.createHorizontalBox();
        JLabel gysidLabel=new JLabel("供应商编号:");
        JTextField gysidField=new JTextField(15);
        gysidBox.add(gysidLabel);
        gysidBox.add(Box.createHorizontalStrut(20));
        gysidBox.add(gysidField);

        //组装服装编号
        Box idBox=Box.createHorizontalBox();
        JLabel idLabel=new JLabel("服装编号：");
        JTextField idField=new JTextField(15);
        idBox.add(idLabel);
        idBox.add(Box.createHorizontalStrut(20));
        idBox.add(idField);
        //组装采购数量
        Box countBox=Box.createHorizontalBox();
        JLabel countLabel=new JLabel("采购数量：");
        JTextField countField=new JTextField(15);
        countBox.add(countLabel);
        countBox.add(Box.createHorizontalStrut(20));
        countBox.add(countField);

        //组装按钮
        Box btnBox=Box.createHorizontalBox();
        JButton addBtn=new JButton("添加");
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //获取管理员录入的信息
                String cgdh=cgdhField.getText().trim();
                String gysid=gysidField.getText().trim();
                String id=idField.getText().trim();
                String count=countField.getText().trim();
                if(!cgdh.equals("") && !gysid.equals("") && !id.equals("")&& !count.equals("")){
                    int gysid0=Integer.valueOf(gysid);
                    int id0=Integer.valueOf(id);
                    int count0=Integer.valueOf(count);
                    double price0=0.0;
                    Double money0=0.0;
                    try {
                        //添加管理员输入的采购信息到数据库中
                        String sql = "insert into purchase (cgdh,gysid,id,purchasecount) values(?,?,?,?)";
                        PreparedStatement preparedStatement=setConnection.getConnection().prepareStatement(sql);
                        preparedStatement.setString(1,cgdh);
                        preparedStatement.setInt(2,gysid0);
                        preparedStatement.setInt(3,id0);
                        preparedStatement.setInt(4,count0);
                        int i=preparedStatement.executeUpdate();
                        if (i>0){
                            //查询新添加的采购的服装的进价
                            double inprice=0.0;
                            String sql2="select inprice from isyou where id=?";
                            PreparedStatement ps=setConnection.getConnection().prepareStatement(sql2);
                            ps.setInt(1,id0);
                            ResultSet rs1=ps.executeQuery();
                            if(rs1.next()){
                                price0=rs1.getDouble("inprice");
                                money0=price0*count0;
                                //修改实付款
                                String sql1="update purchase set price=?,money=round(?,0) where cgdh=?";
                                PreparedStatement stmt=setConnection.getConnection().prepareStatement(sql1);
                                stmt.setDouble(1,price0);
                                stmt.setDouble(2,money0);
                                stmt.setString(3,cgdh);
                                int j=stmt.executeUpdate();
                                if(j>0){
                                    JOptionPane.showMessageDialog(jf,"添加成功，请刷新界面");
                                    listener.done(null);
                                    preparedStatement.close();
                                    dispose();
                                }
                            }
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }else{
                    JOptionPane.showMessageDialog(jf,"添加失败，请按要求输入信息");
                }
            }
        });
        btnBox.add(addBtn);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(cgdhBox);
        vBox.add(Box.createVerticalStrut(10));
        vBox.add(gysidBox);
        vBox.add(Box.createVerticalStrut(10));
        vBox.add(idBox);
        vBox.add(Box.createVerticalStrut(10));
        vBox.add(countBox);
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