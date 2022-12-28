import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 删除服装信息对话框
 *
 * @author tohka
 * @date 2022/12/28
 */
public class fzdeleteDialog extends JDialog {
    final int WIDTH=400;
    final int HEIGTH=300;
    private ActionDoneListener listener;

    /**
     * 删除对话框
     *
     * @param jf       界面
     * @param title    标题
     * @param isModel  true or flase
     * @param listener 监听器
     */
    public fzdeleteDialog(JFrame jf, String title, boolean isModel, ActionDoneListener listener){
        super(jf,title,isModel);
        this.listener=listener;
        //组装视图
        this.setBounds(600,400,WIDTH,HEIGTH);
        Box vBox=Box.createVerticalBox();

        //选择要修改的编号
        Box idBox=Box.createHorizontalBox();
        JLabel idLabel=new JLabel("请输入需要删除的服装的编号：");
        JTextField idField=new JTextField(15);
        idBox.add(idLabel);
        idBox.add(Box.createHorizontalStrut(50));
        idBox.add(idField);

        //组装按钮
        Box btnBox=Box.createHorizontalBox();
        JButton deleteBtn=new JButton("删除");
        deleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //获取管理员需要删除的服装的编号
                String fzid=idField.getText().trim();
                if(!fzid.equals("")){
                    int id=Integer.valueOf(fzid);
                String sql="delete from isyou where id=?";
                    try {
                        PreparedStatement statement=setConnection.getConnection().prepareStatement(sql);
                        statement.setInt(1,id);
                        int i=statement.executeUpdate();
                        if(i>0){
                            JOptionPane.showMessageDialog(jf,"删除成功，请刷新界面");
                            listener.done(null);
                            statement.close();
                            dispose();
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }else
                    JOptionPane.showMessageDialog(jf,"删除失败，请按要求输入数据");
            }
        });

        btnBox.add(deleteBtn);
        vBox.add(Box.createVerticalStrut(40));
        vBox.add(idBox);
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