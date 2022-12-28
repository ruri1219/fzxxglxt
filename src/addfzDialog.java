import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 添加服装信息对话框
 *
 * @author tohka
 * @date 2022/12/28
 */
public class addfzDialog extends JDialog {
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
     * 添加服装信息对话框
     *
     * @param jf       界面
     * @param title    标题
     * @param isModel  true or flase
     * @param listener 监听器
     */
    public addfzDialog(JFrame jf,String title,boolean isModel,ActionDoneListener listener){
        super(jf,title,isModel);
        this.listener=listener;
        //组装视图
        this.setBounds(600,400,WIDTH,HEIGTH);
        Box vBox=Box.createVerticalBox();

        //组装服装名称
        Box fznameBox=Box.createHorizontalBox();
        JLabel nameLabel=new JLabel("服装名称：");
        JTextField nameField=new JTextField(15);
        fznameBox.add(nameLabel);
        fznameBox.add(Box.createHorizontalStrut(20));
        fznameBox.add(nameField);

        //组装图书类型
        Box fztypeBox=Box.createHorizontalBox();
        JLabel typeLabel=new JLabel("服装类型：");
        JTextField typeField=new JTextField(15);
        fztypeBox.add(typeLabel);
        fztypeBox.add(Box.createHorizontalStrut(20));
        fztypeBox.add(typeField);

        //组装服装尺寸
        Box fzsizeBox=Box.createHorizontalBox();
        JLabel sizeLabel=new JLabel("服装尺寸：");
        JTextField sizeField=new JTextField(15);
        fzsizeBox.add(sizeLabel);
        fzsizeBox.add(Box.createHorizontalStrut(20));
        fzsizeBox.add(sizeField);
        //组装服装颜色
        Box fzcolorBox=Box.createHorizontalBox();
        JLabel colorLabel=new JLabel("服装颜色：");
        JTextField colorField=new JTextField(15);
        fzcolorBox.add(colorLabel);
        fzcolorBox.add(Box.createHorizontalStrut(20));
        fzcolorBox.add(colorField);
        //组装进价
        Box inpriceBox=Box.createHorizontalBox();
        JLabel inpriceLabel=new JLabel("进    价：");
        JTextField inpriceField=new JTextField(15);
        inpriceBox.add(inpriceLabel);
        inpriceBox.add(Box.createHorizontalStrut(20));
        inpriceBox.add(inpriceField);
        //组装库存
        Box kucunBox=Box.createHorizontalBox();
        JLabel kucunLabel=new JLabel("库    存：");
        JTextField kucunField=new JTextField(15);
        kucunBox.add(kucunLabel);
        kucunBox.add(Box.createHorizontalStrut(20));
        kucunBox.add(kucunField);
        //组装供应商编号
        Box gysidBox=Box.createHorizontalBox();
        JLabel gysidLabel=new JLabel("供应商编号:");
        JTextField gysidField=new JTextField(15);
        gysidBox.add(gysidLabel);
        gysidBox.add(Box.createHorizontalStrut(20));
        gysidBox.add(gysidField);
        //组装按钮
        Box btnBox=Box.createHorizontalBox();
        JButton addBtn=new JButton("添加");
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //获取管理员录入的信息
                String fzname=nameField.getText().trim();
                String fztype=typeField.getText().trim();
                String fzsize=sizeField.getText().trim();
                String fzcolor=colorField.getText().trim();
                String fzinprice=inpriceField.getText().trim();
                String fzkucun=kucunField.getText().trim();
                String fzgysid=gysidField.getText().trim();
                if(!fzname.equals("") && !fztype.equals("") && !fzsize.equals("")&& !fzcolor.equals("") && !fzinprice.equals("") && !fzkucun.equals("") && !fzgysid.equals("")){
                    //将管理员录入的信息存到数据库中
                    String sql0="select id from isyou order by id desc limit 1";
                    try {
                        //取得服装编号最大值
                        PreparedStatement statement=setConnection.getConnection().prepareStatement(sql0);
                        ResultSet rs=statement.executeQuery();
                        int id=0;
                        if (rs.next()){
                            id=rs.getInt("id")+1;
                            //添加管理员输入的服装信息到数据库中
                            String sql = "insert into isyou (id,fzname,fztype,fzsize,color,inprice,kucun,gysid) values(?,?,?,?,?,?,?,?)";
                            PreparedStatement preparedStatement=setConnection.getConnection().prepareStatement(sql);
                            preparedStatement.setInt(1,id);
                            preparedStatement.setString(2,fzname);
                            preparedStatement.setString(3,fztype);
                            preparedStatement.setString(4,fzsize);
                            preparedStatement.setString(5,fzcolor);
                            preparedStatement.setString(6,fzinprice);
                            preparedStatement.setString(7,fzkucun);
                            preparedStatement.setString(8,fzgysid);
                            int i=preparedStatement.executeUpdate();
                            if (i>0){
                                //查询新添加的服装的进价
                                double price=0.0;
                                String sql2="select inprice from isyou where id=?";
                                PreparedStatement ps=setConnection.getConnection().prepareStatement(sql2);
                                ps.setInt(1,id);
                                ResultSet rs1=ps.executeQuery();
                                if(rs1.next()){
                                    price=rs1.getDouble("inprice")*1.1;
                                    //将售价修改为进价的1.1倍
                                    String sql1="update isyou set outprice=round(?,0) where id=?";
                                    PreparedStatement stmt=setConnection.getConnection().prepareStatement(sql1);
                                    stmt.setDouble(1,price);
                                    stmt.setInt(2,id);
                                    int j=stmt.executeUpdate();
                                    if(j>0){
                                        JOptionPane.showMessageDialog(jf,"添加成功，请刷新界面");
                                        listener.done(null);
                                        preparedStatement.close();
                                        dispose();
                                    }
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
        vBox.add(fznameBox);
        vBox.add(Box.createVerticalStrut(10));
        vBox.add(fztypeBox);
        vBox.add(Box.createVerticalStrut(10));
        vBox.add(fzsizeBox);
        vBox.add(Box.createVerticalStrut(10));
        vBox.add(fzcolorBox);
        vBox.add(Box.createVerticalStrut(10));
        vBox.add(inpriceBox);
        vBox.add(Box.createVerticalStrut(10));
        vBox.add(kucunBox);
        vBox.add(Box.createVerticalStrut(10));
        vBox.add(gysidBox);
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