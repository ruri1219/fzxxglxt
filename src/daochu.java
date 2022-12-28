import javax.swing.*;
import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class daochu {
    private Vector<String> titles;
    private Vector<Vector> tableData=new Vector<>();
    private JFrame jf;
    public void fzxxdaochu(JFrame jf) throws SQLException, ClassNotFoundException, IOException {
        this.jf=jf;
        BufferedWriter bw=new BufferedWriter(new FileWriter("daochu/fzjbxx.txt"));
        tableData.clear();
        Vector<Vector> data=new Vector<>();
        data=getInformationInterface2();
        bw.write("服装编号             服装名称             服装类型           服装尺寸               服装颜色            进   价              售   价            库   存             供应商编号");
        bw.newLine();
        bw.flush();
       for(Vector<String> v:data)
       {
           for(String x:v)
           {
               bw.write(x);
               int cur=0;
               for(int i=0;i<x.length();i++)
               {
                   if(((int)(x.charAt(i))>=65&&(int)(x.charAt(i))<=90)||((int)(x.charAt(i))>=97&&(int)(x.charAt(i))<=122)||((int)(x.charAt(i))>=48&&(int)(x.charAt(i))<=57)||(x.charAt(i)=='.')) cur++;
                   else cur+=2;
               }
               for(int i=0;i<20-cur;i++)
               {
                   bw.write(" ");
               }
               bw.flush();
           }
           bw.newLine();
       }
        bw.close();
        JOptionPane.showMessageDialog(jf,"导出成功");
    }
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
}
