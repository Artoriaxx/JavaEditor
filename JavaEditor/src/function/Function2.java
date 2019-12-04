package function;

import function.prework.MyCharactor;
import org.jfree.chart.*;;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;


class DensityWindow extends JFrame implements ActionListener {
    private JPanel jpl = new JPanel();
    private JPanel jpl1 = new JPanel();
    private JButton confirm = new MyButton("确定");
    private JButton save = new MyButton("保存");
    String[] option = {
            "桐原亮司", "西本雪穗", "笹垣润三", "桐原洋介", "秋吉雄一", "筱冢康晴", "古贺刑事", "川岛江利子", "今枝直巳", "桐原弥生子"
    };
    MyCharactor[] ch = new MyCharactor[option.length];
    private JRadioButton[] jrb = new JRadioButton[option.length];
    private ButtonGroup bg = new ButtonGroup();
    String now = null;
    public DensityWindow() {


        jpl1.setLayout(new GridLayout(5, 2));
        for (int i = 0; i < option.length; i++) {
            ch[i] = new MyCharactor(option[i]);
            ch[i].count(Read.text);
            //Read.map.put(option[i], i);
        }
        for (int i = 0; i < jrb.length; i++) {
            jrb[i] = new JRadioButton(ch[i].name);
            bg.add(jrb[i]);
            jpl1.add(jrb[i]);
        }
        jpl.setLayout(null);
        jpl1.setSize(300, 450);
        JLabel jl = new JLabel("选择要显示密度的人物", JLabel.CENTER);
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
        save.setLocation(200,500);
        this.add(jpl);
        this.setSize(400, 600);
        jpl.setOpaque(false);
        jpl1.setOpaque(false);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
    }
    public CategoryDataset createDataset() {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < jrb.length; i++) {
            if (jrb[i].isSelected()) {
                for (int j = 0; j < ch[i].density.length; j++) {
                    //System.out.println(ch[i].density[j]);
                    dataset.addValue(ch[i].density[j], ch[i].name, String.valueOf(j + 1));
                    now = ch[i].name;
                }
                break;
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
        JFreeChart chart = ChartFactory.createLineChart(
                "密度",
                "人物名称",
                "密度",
                createDataset(),
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        CategoryPlot cp = (CategoryPlot)chart.getPlot();
        cp.setBackgroundPaint(Color.lightGray);

        cp.setOutlinePaint(Color.BLUE);
        cp.setRangeGridlinesVisible(true);
        cp.setDomainGridlinesVisible(false);

        return chart;
    }
    public void actionPerformed(ActionEvent e) {
        JFreeChart chart = createChart();
        ChartFrame cf = new ChartFrame("密度", chart);
        if (e.getActionCommand().equals("确定")) {
            cf.pack();
            cf.setVisible(true);
        }
        else {
            try {
                Socket s = new Socket("127.0.0.1", 9999);
                OutputStream os = s.getOutputStream();
                PrintStream ps = new PrintStream(os);

                ps.println("Img2-" + now + ".jpg");
                ChartUtilities.writeChartAsJPEG(os, chart, 1000, 800);
                ps.close();
                s.close();
            } catch (Exception a){}
        }
    }

}
public class Function2 implements ActionListener {
    private DensityWindow dw;
    public void actionPerformed(ActionEvent a) {
        if (Read.text == null) {
            JOptionPane.showMessageDialog(null,"请先载入文本");
        }
        else {
            if (dw != null) {
                dw.setVisible(true);
            }
            else {
                dw = new DensityWindow();
                dw.setVisible(true);
            }
        }
    }

}
