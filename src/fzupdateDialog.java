import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 修改服装信息对话框
 *
 * @author tohka
 * @date 2022/12/28
 */
public class fzupdateDialog extends JDialog {
    final int WIDTH=400;
    final int HEIGTH=300;
    private ActionDoneListener listener;

    /**
     * 修改服装信息对话框
     *
     * @param jf       界面
     * @param title    标题
     * @param isModel  true or flase
     * @param listener 监听器
     */
    public fzupdateDialog(JFrame jf, String title, boolean isModel, ActionDoneListener listener){
        super(jf,title,isModel);
        this.listener=listener;
        //组装视图
        this.setBounds(600,400,WIDTH,HEIGTH);
        Box vBox=Box.createVerticalBox();

        //选择要修改的编号
        Box idBox=Box.createHorizontalBox();
        JLabel idLabel=new JLabel("请输入需要修改的服装的编号：");
        JTextField idField=new JTextField(15);
        idBox.add(idLabel);
        idBox.add(Box.createHorizontalStrut(50));
        idBox.add(idField);
        //修改售价
        Box outpriceBox=Box.createHorizontalBox();
        JLabel outLabel=new JLabel("修改新的售价   ：");
        JTextField outField=new JTextField(15);
        outpriceBox.add(outLabel);
        outpriceBox.add(Box.createHorizontalStrut(50));
        outpriceBox.add(outField);

        //组装按钮
        Box btnBox=Box.createHorizontalBox();
        JButton updateBtn=new JButton("修改");
        updateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //获取管理员修改的信息
                String fzid=idField.getText().trim();
                String outprice=outField.getText().trim();
                if(!fzid.equals("") && !outprice.equals("")){
                    int id=Integer.valueOf(fzid);
                    double out=Double.parseDouble(outprice);
                    //将管理员修改的信息应用到数据库中
                    String sql="update isyou set outprice=? where id=?";
                    try {
                        PreparedStatement ps=setConnection.getConnection().prepareStatement(sql);
                        ps.setDouble(1,out);
                        ps.setInt(2,id);
                        int i=ps.executeUpdate();
                        if(i>0){
                            JOptionPane.showMessageDialog(jf,"修改成功，请刷新界面");
                            listener.done(null);
                            ps.close();
                            dispose();
                        }
                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }else{
                    JOptionPane.showMessageDialog(jf,"修改失败，请按要求输入数据");
                }
            }
        });

        btnBox.add(updateBtn);
        vBox.add(Box.createVerticalStrut(40));
        vBox.add(idBox);
        vBox.add(Box.createVerticalStrut(40));
        vBox.add(outpriceBox);
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