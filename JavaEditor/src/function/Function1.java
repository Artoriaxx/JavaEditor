package function;


import function.prework.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.*;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.Socket;
import java.util.*;


class OccurenceWindow extends JFrame implements ActionListener {
    private JPanel jpl = new JPanel();
    private JPanel jpl1 = new JPanel();
    private JButton confirm = new MyButton("确定");
    private JButton save = new MyButton("保存");
    String[] option = {
            "桐原亮司", "西本雪穗", "笹垣润三", "桐原洋介", "秋吉雄一", "筱冢康晴", "古贺刑事", "川岛江利子", "今枝直巳", "桐原弥生子"
    };
    MyCharactor[] ch = new MyCharactor[option.length];
    private JCheckBox[] jcb = new JCheckBox[option.length];

    public OccurenceWindow() {


        jpl1.setLayout(new GridLayout(5, 2));

        for (int i = 0; i < option.length; i++) {
            ch[i] = new MyCharactor(option[i]);
            ch[i].count(Read.text);
            //System.out.println(ch[i].searchName);
            //Read.map.put(option[i], i);
        }
        Arrays.sort(ch, new Comparator<MyCharactor>() {
            @Override
            public int compare(MyCharactor o1, MyCharactor o2) {
                return o1.occurence - o2.occurence;
            }
        });
        for (int i = 0; i < jcb.length; i++) {
            jcb[i] = new JCheckBox(ch[i].name, true);
            jpl1.add(jcb[i]);
        }
        jpl.setLayout(null);

        jpl1.setSize(300, 450);
        JLabel jl = new JLabel("选择要显示存在感的人物", JLabel.CENTER);
        jl.setSize(300, 50);
        jl.setFont(new Font("楷体", Font.BOLD, 15));
        jpl.add(jl);
        jl.setLocation(50, 10);
        jpl.add(jpl1);
        jpl1.setLocation(75, 50);

        confirm.setSize(100,50);
        save.setSize(100, 50);
        confirm.addActionListener(this);
        save.addActionListener(this);
        jpl.add(confirm);
        jpl.add(save);
        confirm.setLocation(80,500);
        save.setLocation(200, 500);
        this.add(jpl);
        this.setSize(400, 600);
        jpl1.setOpaque(false);
        jpl.setOpaque(false);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
    }
    public CategoryDataset createDataset() {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < jcb.length; i++) {
            if (jcb[i].isSelected()) {
                dataset.addValue(ch[i].occurence, ch[i].name, "");
            }
        }
        return dataset;
    }
    public JFreeChart createChart() {
        StandardChartTheme sct = new StandardChartTheme("CN");
        sct.setExtraLargeFont(new Font("楷体", Font.BOLD, 20));
        sct.setRegularFont(new Font("楷体", Font.PLAIN, 15));
        sct.setLargeFont(new Font("楷体", Font.PLAIN, 15));
        ChartFactory.setChartTheme(sct);
        JFreeChart chart = ChartFactory.createBarChart3D(
                "存在感",
                "人物名称",
                "存在感",
                createDataset(),
                PlotOrientation.VERTICAL,
                true,
                false,
                false
        );

        return chart;
    }
    public void actionPerformed(ActionEvent e) {
        JFreeChart chart = createChart();
        ChartFrame cf = new ChartFrame("存在感排名", chart);
        if (e.getActionCommand().equals("确定")) {
            cf.pack();
            cf.setVisible(true);
        }
        else {
            try {
                Socket s = new Socket("127.0.0.1", 9999);
                OutputStream os = s.getOutputStream();
                PrintStream ps = new PrintStream(os);
                ps.println("Img1.jpg");
                ChartUtilities.writeChartAsJPEG(os, chart, 1000, 800);
            } catch (Exception a){}
        }
    }
}

public class Function1 implements ActionListener {
    private OccurenceWindow ow;
    public void actionPerformed(ActionEvent a) {
        if (Read.text == null) {
            JOptionPane.showMessageDialog(null,"请先载入文本");
        }
        else {
            if (ow != null) ow.setVisible(true);
            else {
                ow = new OccurenceWindow();
                ow.setVisible(true);
            }
        }
    }

}
