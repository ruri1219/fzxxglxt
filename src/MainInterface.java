import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.*;

/**
 * 登录界面
 *
 * @author tohka
 * @date 2022/12/28
 */
public class MainInterface {
    JFrame jf=new JFrame("服装信息管理系统");

    /**
     * 初始化
     *
     * @throws IOException ioexception
     *///组装视图
    public void init() throws IOException {
        //设置窗口的相关属性
        jf.setBounds(300,200,1280,720);
        jf.setResizable(false);//不能拖动边框修改大小
        jf.setIconImage(ImageIO.read(new File("image/nekolunalogo.png")));
        //设置窗口的内容
        BackGroundPanel bgPanel=new BackGroundPanel(ImageIO.read(new File("image/sobyoluna.png")));
        //组装登录相关的元素
        Box vBox=Box.createVerticalBox();
        //组装用户名
        Box uBox=Box.createHorizontalBox();
        JLabel uLabel=new JLabel("用户名");
        JTextField uField=new JTextField(15);
        uBox.add(uLabel);
        uBox.add(Box.createHorizontalStrut(15));
        uBox.add(uField);
        //组装密码
        Box pBox=Box.createHorizontalBox();
        JLabel pLabel=new JLabel("密码");
        JTextField pField=new JTextField(15);
        pBox.add(pLabel);
        pBox.add(Box.createHorizontalStrut(27));
        pBox.add(pField);
        //组装按钮
        Box btnBox=Box.createHorizontalBox();
        JButton loginBtn=new JButton("登录");
        JButton regisBtn=new JButton("注册");
        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //获取用户输入的数据
                String username=uField.getText().trim();
                String password=pField.getText().trim();
                //访问登录接口
                int count=0;
                try {
                    count=account.checkAccount(username,password);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                if (count>0){
                    JOptionPane.showMessageDialog(jf,"登录成功");
                    try {
                        new fzgl().init();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    } finally {
                        jf.dispose();
                    }
                }else {
                    JOptionPane.showMessageDialog(jf,"登录失败");
                    uField.setText("");
                    pField.setText("");
                }
            }
        });
        regisBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //打开注册界面
                    new RegisterInterface().init();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }finally {
                    //当前界面消失
                    jf.dispose();
                }
            }
        });
        btnBox.add(loginBtn);
        btnBox.add(Box.createHorizontalStrut(250));
        btnBox.add(regisBtn);
        vBox.add(Box.createVerticalStrut(150));
        vBox.add(uBox);
        vBox.add(Box.createVerticalStrut(80));
        vBox.add(pBox);
        vBox.add(Box.createVerticalStrut(80));
        vBox.add(btnBox);
        bgPanel.add(vBox);
        jf.add(bgPanel);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * 打开登录界面
     *
     * @param args args
     */
    public static void main(String[] args) {
        try {
            new MainInterface().init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
