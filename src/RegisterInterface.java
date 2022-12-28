import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 注册界面
 *
 * @author tohka
 * @date 2022/12/28
 */
public class RegisterInterface {
    JFrame jf = new JFrame("注册");
    public void init() throws IOException {
        jf.setBounds(300,200,1280,720);
        jf.setResizable(false);
        jf.setIconImage(ImageIO.read(new File("image/nekolunalogo.png")));
        //设置窗口的内容
        BackGroundPanel bgPanel=new BackGroundPanel(ImageIO.read(new File("image/sobyoluna.png")));
        bgPanel.setBounds(0,0,1280,720);
        Box vBox=Box.createVerticalBox();
        //组装用户名
        Box uBox=Box.createHorizontalBox();
        JLabel uLabel=new JLabel("用户名:");
        JTextField uField=new JTextField(20);
        uBox.add(uLabel);
        uBox.add(Box.createHorizontalStrut(20));
        uBox.add(uField);
        //组装密码
        Box pBox=Box.createHorizontalBox();
        JLabel pLabel=new JLabel("密    码:");
        JTextField pField=new JTextField(20);
        pBox.add(pLabel);
        pBox.add(Box.createHorizontalStrut(27));
        pBox.add(pField);
        jf.setVisible(true);
        //组装手机号
        Box tBox=Box.createHorizontalBox();
        JLabel tLabel=new JLabel("手机号:");
        JTextField tField=new JTextField(20);
        tBox.add(tLabel);
        tBox.add(Box.createHorizontalStrut(27));
        tBox.add(tField);
        //组装按钮
        Box btnBox=Box.createHorizontalBox();
        JButton registBtn=new JButton("注册");
        JButton backBtn=new JButton("返回登录页面");
        //获取用户录入的信息
        registBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username=uField.getText().trim();
                String password=pField.getText().trim();
                String phone=tField.getText().trim();
                if(!username.equals("")&&!password.equals("")&&!phone.equals("")){
                    int count=0;
                    String sql="select * from zh where phone=?";
                    try {
                        PreparedStatement statement=setConnection.getConnection().prepareStatement(sql);
                        statement.setString(1,phone);
                        ResultSet rs= statement.executeQuery();
                        //查询手机号是否被注册过
                        if (!rs.next()){
                            String sql1="select * from zh where username=?";
                            PreparedStatement statement1=setConnection.getConnection().prepareStatement(sql1);
                            statement1.setString(1,username);
                            ResultSet rs1=statement1.executeQuery();
                            //查询用户名是否被注册过
                            if (!rs1.next()){
                                try {
                                    count=account.addAccount(username,password,phone);
                                } catch (SQLException ex) {
                                    throw new RuntimeException(ex);
                                } catch (ClassNotFoundException ex) {
                                    throw new RuntimeException(ex);
                                }
                                if(count>0){
                                    JOptionPane.showMessageDialog(jf,"注册成功！");
                                    try {
                                        new MainInterface().init();//跳转到登录界面
                                        jf.dispose();
                                    } catch (IOException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                }
                            }else{
                                JOptionPane.showMessageDialog(jf,"注册失败，该用户名已被注册！");
                                uField.setText("");
                                pField.setText("");
                                tField.setText("");
                            }
                        }else{
                            JOptionPane.showMessageDialog(jf,"注册失败，该手机号已被注册！");
                            uField.setText("");
                            pField.setText("");
                            tField.setText("");
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //返回登录界面
                    new MainInterface().init();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }finally {
                    //当前界面消失
                    jf.dispose();
                }
            }
        });
        btnBox.add(registBtn);
        btnBox.add(Box.createHorizontalStrut(80));
        btnBox.add(backBtn);
        vBox.add(Box.createVerticalStrut(150));
        vBox.add(uBox);
        vBox.add(Box.createVerticalStrut(80));
        vBox.add(pBox);
        vBox.add(Box.createVerticalStrut(80));
        vBox.add(tBox);
        vBox.add(Box.createVerticalStrut(80));
        vBox.add(btnBox);
        bgPanel.add(vBox);
        jf.add(bgPanel);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
