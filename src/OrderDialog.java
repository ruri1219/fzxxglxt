import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 排序命令对话框
 *
 * @author tohka
 * @date 2022/12/28
 */
public class OrderDialog extends JDialog {
    final int WIDTH=500;
    final int HEIGTH=400;
    private ActionDoneListener listener;

    /**
     * 排序命令对话框
     *
     * @param jf       摩根富林明
     * @param title    标题
     * @param isModel  是模型
     * @param listener 侦听器
     */
    public OrderDialog(JFrame jf,String title,boolean isModel,ActionDoneListener listener){
        super(jf,title,isModel);
        this.listener=listener;
        //组装视图
        this.setBounds(600,400,WIDTH,HEIGTH);
        Box vBox=Box.createVerticalBox();

        //组装按钮
        Box btnBox=Box.createHorizontalBox();
        JButton ascBtn=new JButton("按售价升序排序");
        JButton descBtn=new JButton("按售价降序排序");
        ascBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new ascResult(jf, "按售价升序排序", true, new ActionDoneListener() {
                        @Override
                        public void done(Object o) {
                        }
                    }).setVisible(true);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                listener.done(null);
                dispose();
            }
        });
        descBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new descResult(jf, "按售价降序排序", true, new ActionDoneListener() {
                        @Override
                        public void done(Object o) {
                        }
                    }).setVisible(true);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                listener.done(null);
                dispose();
            }
        });
        btnBox.add(ascBtn);
        btnBox.add(Box.createVerticalStrut(20));
        btnBox.add(descBtn);

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