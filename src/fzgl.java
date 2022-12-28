import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.*;

/**
 * 登录成功后进入的服装管理界面
 *
 * @author tohka
 * @date 2022/12/28
 */
public class fzgl {
    JFrame jf=new JFrame("吉大服装，您是管理员");

    /**
     * 初始化
     *
     * @throws IOException            ioexception
     * @throws SQLException           sqlexception异常
     * @throws ClassNotFoundException 类没有发现异常
     */
    public void init() throws IOException, SQLException, ClassNotFoundException {
        jf.setBounds(300,200,1280,720);
        jf.setResizable(false);
        jf.setIconImage(ImageIO.read(new File("image/mizugilunalogo.png")));
        //设置菜单栏
        JMenuBar jmb=new JMenuBar();
        JMenu jMenu=new JMenu("设置");
        JMenuItem m1=new JMenuItem("退出登录");
        m1.addActionListener(new ActionListener() {
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
        jMenu.add(m1);
        jmb.add(jMenu);
        jf.setJMenuBar(jmb);
        //设置分割面板
        JSplitPane sp=new JSplitPane();
        //支持连续布局
        sp.setContinuousLayout(true);
        sp.setDividerLocation(150);
        sp.setDividerSize(5);
        //设置左侧内容
        DefaultMutableTreeNode root=new DefaultMutableTreeNode("系统管理");
        DefaultMutableTreeNode jbxx=new DefaultMutableTreeNode("基本信息管理");
        DefaultMutableTreeNode kc=new DefaultMutableTreeNode("库存管理");
        DefaultMutableTreeNode xsxx=new DefaultMutableTreeNode("销售信息管理");
        DefaultMutableTreeNode cg=new DefaultMutableTreeNode("采购管理");
        root.add(jbxx);
        root.add(kc);
        root.add(xsxx);
        root.add(cg);
        Color color=new Color(255,255,255);
        JTree tree=new JTree(root);
        MyRenderer myRenderer=new MyRenderer();
        myRenderer.setBackgroundNonSelectionColor(color);
        myRenderer.setBackgroundSelectionColor(new Color(205,205,205));
        tree.setCellRenderer(myRenderer);
        tree.setBackground(color);
        //设置当前tree默认选中基本信息管理
        tree.setSelectionRow(1);
        tree.addTreeSelectionListener(new TreeSelectionListener() {
            //当条目选中变化后，执行这个方法
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                //得到当前选中的节点对象
                Object lastPathComponent=e.getNewLeadSelectionPath().getLastPathComponent();

                if (jbxx.equals(lastPathComponent)){
                    try {
                        sp.setRightComponent(new fzjbxxComponent(jf));
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                    sp.setDividerLocation(150);
                }else if (kc.equals(lastPathComponent)){
                    try {
                        sp.setRightComponent(new fzkcComponent(jf));
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                    sp.setDividerLocation(150);
                }else if (xsxx.equals(lastPathComponent)){
                    try {
                        sp.setRightComponent(new xsxxComponent(jf));
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                    sp.setDividerLocation(150);
                }else if (cg.equals(lastPathComponent)){
                    try {
                        sp.setRightComponent(new cgxxComponent(jf));
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                    sp.setDividerLocation(150);
                }

            }
        });


        sp.setRightComponent(new fzjbxxComponent(jf));


        sp.setLeftComponent(tree);
        jf.add(sp);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * 我的渲染器
     *
     * @author tohka
     * @date 2022/12/28
     *///自定义节点绘制器
    private class MyRenderer extends DefaultTreeCellRenderer{
        private ImageIcon rootIcon=null;
        private ImageIcon jbxxIcon=null;
        private ImageIcon kcIcon=null;
        private ImageIcon xsxxIcon=null;
        private ImageIcon cgIcon=null;
        public MyRenderer() {
            rootIcon=new ImageIcon("image/lilunlunalogo.png");
            jbxxIcon=new ImageIcon("image/feliasyoulunalogo.png");
            kcIcon=new ImageIcon("image/moonlunalogo.png");
            xsxxIcon=new ImageIcon("image/manqyesutelunalogo.png");
            cgIcon=new ImageIcon("image/nekolunalogo2.png");
        }

        /**
         * 让树细胞渲染器组件
         *
         * @param tree     树
         * @param value    价值
         * @param sel      选取
         * @param expanded 扩大
         * @param leaf     叶
         * @param row      行
         * @param hasFocus 有焦点
         * @return {@link Component}
         *///当绘制树的每个节点时，都会调用这个方法
        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
            //使用默认绘制
            super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
            ImageIcon image=null;
            switch (row){
                case 0:
                    image=rootIcon;
                    break;
                case 1:
                    image=jbxxIcon;
                    break;
                case 2:
                    image=kcIcon;
                    break;
                case 3:
                    image=xsxxIcon;
                    break;
                case 4:
                    image=cgIcon;
                    break;
            }
            this.setIcon(image);
            return this;
        }
    }
}
