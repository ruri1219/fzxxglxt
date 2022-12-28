import javax.swing.*;
import java.awt.*;

/**
 * 背景板
 *
 * @author tohka
 * @date 2022/12/28
 */
public class BackGroundPanel extends JPanel {
    /**
     * 返回图标
     *///声明图片
    private Image backIcon;

    /**
     * 背景板
     *
     * @param backIcon 返回图标
     */
    public BackGroundPanel(Image backIcon){
        this.backIcon=backIcon;
    }

    /**
     * 油漆组件
     *
     * @param g g
     */
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        //绘制背景
        g.drawImage(backIcon,0,0,1280,720,null);
    }
}
