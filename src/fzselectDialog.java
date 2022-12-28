import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 * 查询服装信息对话框
 *
 * @author tohka
 * @date 2022/12/28
 */
public class fzselectDialog extends JDialog {
    final int WIDTH=400;
    final int HEIGTH=200;
    private ActionDoneListener listener;

    /**
     * 查询服装信息对话框
     *
     * @param jf       界面
     * @param title    标题
     * @param isModel  true or flase
     * @param listener 监听器
     */
    public fzselectDialog(JFrame jf, String title, boolean isModel, ActionDoneListener listener){
        super(jf,title,isModel);
        this.listener=listener;
        //组装视图
        this.setBounds(600,400,WIDTH,HEIGTH);
        Box vBox=Box.createVerticalBox();

        //选择要查询的类型
        Box typeBox=Box.createHorizontalBox();
        JLabel typeLabel=new JLabel("请输入需要查询的服装的类型：");
        JTextField typeField=new JTextField(15);
        typeBox.add(typeLabel);
        typeBox.add(Box.createHorizontalStrut(50));
        typeBox.add(typeField);

        //组装按钮
        Box btnBox=Box.createHorizontalBox();
        JButton selectBtn=new JButton("查询");
        selectBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //获取管理员需要查询的服装类型
                String fztype=typeField.getText().trim();
                if(!fztype.equals("") ){
                    try {
                        new selectResult(jf, "查询结果", true, fztype, new ActionDoneListener() {
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

                }else{
                    JOptionPane.showMessageDialog(jf,"查询失败，请按要求输入数据");
                }
            }
        });

        btnBox.add(selectBtn);
        vBox.add(Box.createVerticalStrut(40));
        vBox.add(typeBox);
        vBox.add(Box.createVerticalStrut(40));
        vBox.add(btnBox);
        //为了左右有间距，在vBox外层封装一个水平的Box，添加间隔
        Box hBox=Box.createHorizontalBox();
        hBox.add(Box.createHorizontalStrut(30));
        hBox.add(vBox);
        hBox.add(Box.createHorizontalStrut(30));
        this.add(hBox);
    }
}