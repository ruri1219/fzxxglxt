import javax.swing.*;
import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

/**
 * 导出信息
 *
 * @author tohka
 * @date 2022/12/28
 */
public class daochu {
    private Vector<String> titles;
    private Vector<Vector> tableData=new Vector<>();
    private JFrame jf;

    /**
     * 导出服装信息
     *
     * @param jf 界面
     * @throws SQLException           sqlexception异常
     * @throws ClassNotFoundException 类没有发现异常
     * @throws IOException            ioexception
     */
    public void fzxxdaochu(JFrame jf) throws SQLException, ClassNotFoundException, IOException {
        this.jf=jf;
        BufferedWriter bw=new BufferedWriter(new FileWriter("daochu/fzjbxx.txt"));
        tableData.clear();
        Vector<Vector> data=new Vector<>();
        data=getInformationInterface2();
        bw.write("服装编号             服装名称             服装类型           服装尺寸               服装颜色            进   价              售   价            库   存             供应商编号");
        bw.newLine();
        bw.flush();
       for(Vector<String> v:data) {
           for(String x:v) {
               bw.write(x);
               int cur=0;
               for(int i=0;i<x.length();i++) {
                   if(((int)(x.charAt(i))>=65&&(int)(x.charAt(i))<=90)||((int)(x.charAt(i))>=97&&(int)(x.charAt(i))<=122)||((int)(x.charAt(i))>=48&&(int)(x.charAt(i))<=57)||(x.charAt(i)=='.'))
                       cur++;
                   else
                       cur+=2;
               }
               for(int i=0;i<20-cur;i++) {
                   bw.write(" ");
               }
               bw.flush();
           }
           bw.newLine();
       }
        bw.close();
        JOptionPane.showMessageDialog(jf,"导出成功");
    }
    public void xsxxdaochu(JFrame jf) throws IOException, SQLException, ClassNotFoundException {
        this.jf=jf;
        BufferedWriter bw=new BufferedWriter(new FileWriter("daochu/xsxx.txt"));
        tableData.clear();
        Vector<Vector> data=new Vector<>();
        data=getInformationInterface3();
        bw.write("服装编号                销售单号        客户购买数量             支付方式           客户享受折扣             应收款              实收款");
        bw.newLine();
        bw.flush();
        for(Vector<String> v:data) {
            for(String x:v) {
                bw.write(x);
                int cur=0;
                for(int i=0;i<x.length();i++) {
                    if(((int)(x.charAt(i))>=65&&(int)(x.charAt(i))<=90)||((int)(x.charAt(i))>=97&&(int)(x.charAt(i))<=122)||((int)(x.charAt(i))>=48&&(int)(x.charAt(i))<=57)||(x.charAt(i)=='.'))
                        cur++;
                    else
                        cur+=2;
                }
                for(int i=0;i<20-cur;i++) {
                    bw.write(" ");
                }
                bw.flush();
            }
            bw.newLine();
        }
        bw.close();
        JOptionPane.showMessageDialog(jf,"导出成功");
    }
    public void cgxxdaochu(JFrame jf) throws IOException, SQLException, ClassNotFoundException {
        this.jf=jf;
        BufferedWriter bw=new BufferedWriter(new FileWriter("daochu/cgxx.txt"));
        tableData.clear();
        Vector<Vector> data=new Vector<>();
        data=getInformationInterface4();
        bw.write("采购单号            供应商编号           服装编号               采购数量               采购单价              总花费");
        bw.newLine();
        bw.flush();
        for(Vector<String> v:data) {
            for(String x:v) {
                bw.write(x);
                int cur=0;
                for(int i=0;i<x.length();i++) {
                    if(((int)(x.charAt(i))>=65&&(int)(x.charAt(i))<=90)||((int)(x.charAt(i))>=97&&(int)(x.charAt(i))<=122)||((int)(x.charAt(i))>=48&&(int)(x.charAt(i))<=57)||(x.charAt(i)=='.'))
                        cur++;
                    else
                        cur+=2;
                }
                for(int i=0;i<20-cur;i++) {
                    bw.write(" ");
                }
                bw.flush();
            }
            bw.newLine();
        }
        bw.close();
        JOptionPane.showMessageDialog(jf,"导出成功");
    }

    /**
     * 获取服装信息
     *
     * @return {@link Vector}<{@link Vector}>
     * @throws SQLException           sqlexception异常
     * @throws ClassNotFoundException 类没有发现异常
     */
    public Vector<Vector> getInformationInterface2() throws SQLException, ClassNotFoundException {
        String sql = "select * from isyou ";
        Vector<Vector> data = new Vector<>();
        PreparedStatement preparedStatement = setConnection.getConnection().prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();
        String id, fzname, fztype, fzsize, color, inprice, outprice, kucun, gysid = null;
        while (rs.next()) {
            Vector information = new Vector<>();
            id = rs.getString("id");
            fzname = rs.getString("fzname");
            fztype = rs.getString("fztype");
            fzsize = rs.getString("fzsize");
            color = rs.getString("color");
            inprice = rs.getString("inprice");
            outprice = rs.getString("outprice");
            kucun = rs.getString("kucun");
            gysid = rs.getString("gysid");
            information.add(id);
            information.add(fzname);
            information.add(fztype);
            information.add(fzsize);
            information.add(color);
            information.add(inprice);
            information.add(outprice);
            information.add(kucun);
            information.add(gysid);
            data.add(information);
        }
        preparedStatement.close();
        rs.close();
        return data;
    }
    public Vector<Vector> getInformationInterface3() throws SQLException, ClassNotFoundException {
        String sql = "select * from xiaoshou ";
        Vector<Vector> data = new Vector<>();
        PreparedStatement preparedStatement = setConnection.getConnection().prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();
        String id, salescode, salescount, paytype, rebeat, receivable, total= null;
        while (rs.next()) {
            Vector information = new Vector<>();
            id = rs.getString("id");
            salescode = rs.getString("salescode");
            salescount = rs.getString("salescount");
            paytype = rs.getString("paytype");
            rebeat = rs.getString("rebeat");
            receivable = rs.getString("receivable");
            total = rs.getString("total");
            information.add(id);
            information.add(salescode);
            information.add(salescount);
            information.add(paytype);
            information.add(rebeat);
            information.add(receivable);
            information.add(total);
            data.add(information);
        }
        preparedStatement.close();
        rs.close();
        return data;
    }
    public Vector<Vector> getInformationInterface4() throws SQLException, ClassNotFoundException {
        String sql = "select * from purchase ";
        Vector<Vector> data = new Vector<>();
        PreparedStatement preparedStatement = setConnection.getConnection().prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();
        String cgdh, gysid, id, purchasecount, price, money= null;
        while (rs.next()) {
            Vector information = new Vector<>();
            cgdh = rs.getString("cgdh");
            gysid = rs.getString("gysid");
            id = rs.getString("id");
            purchasecount = rs.getString("purchasecount");
            price = rs.getString("price");
            money = rs.getString("money");
            information.add(cgdh);
            information.add(gysid);
            information.add(id);
            information.add(purchasecount);
            information.add(price);
            information.add(money);
            data.add(information);
        }
        preparedStatement.close();
        rs.close();
        return data;
    }
}
