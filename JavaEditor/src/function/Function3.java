package function;

import function.prework.MyCharactor;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.*;

class MyButton extends JButton {
    MyButton(String s) {
        this.setFont(new Font("楷体", Font.BOLD, 14));
        this.setBorderPainted(false);
        this.setLabel(s);
    }
}
class MyCanvas extends JPanel {
    public void paint(Graphics g) {
        g.clearRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(Color.white);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(new Color(18, 162, 153));
        g.fillOval(200, 150, 200, 200);
        g.setColor(new Color(44,164, 224));
        g.fillOval(400, 150, 200, 200);
        g.setColor(new Color(234,86,24));
        g.fillOval(300, 50, 200, 200);
        g.setColor(new Color(227, 15, 128));
        g.fillOval(300, 250, 200, 200);
        g.setColor(Color.WHITE);
        g.fillOval(350, 200, 100, 100);
        g.setColor(Color.WHITE);
        g.setFont(new Font("楷体", Font.BOLD, 20));
        int nx, ny;
        nx = 220; ny = 250;
        for (int i = 0; i < RelationWindow.num[0]; i++) {
            g.drawString(RelationWindow.group[0][i], nx, ny);
            ny += 21;
        }
        nx = 350; ny = 350;
        for (int i = 0; i < RelationWindow.num[1]; i++) {
            g.drawString(RelationWindow.group[1][i], nx, ny);
            ny += 21;
        }
        nx = 470; ny = 250;
        for (int i = 0; i < RelationWindow.num[2]; i++) {
            g.drawString(RelationWindow.group[2][i], nx, ny);
            ny += 21;
        }
        nx = 350; ny = 150;
        for (int i = 0; i < RelationWindow.num[3]; i++) {
            g.drawString(RelationWindow.group[3][i], nx, ny);
            ny += 21;
        }
        nx = 360; ny = 260;

        g.setColor(Color.black);
        g.drawString("人物关系", nx, ny);
    }
}
class RelationWindow extends JFrame implements ActionListener{
    private JPanel jpl = new JPanel();
    private JButton confirm = new JButton("确定");
    static String[] option = {
            "桐原亮司", "西本雪穗", "笹垣润三", "桐原洋介", "秋吉雄一", "筱冢康晴", "古贺刑事", "川岛江利子", "今枝直巳", "桐原弥生子"
    };
    MyCharactor[] ch = new MyCharactor[option.length];
    int[] family = new int[option.length];
    int[] pos = new int[option.length];
    int[][] rel = new int[option.length][option.length];
    static String[][] group = new String[option.length][option.length];
    static int cnt;
    static int[] num = new int[option.length];
    int find(int person) {
        return family[person] == person ? person : (family[person] = find(family[person]));
    }
    void merge(int x, int y) {
        x = find(x);
        y = find(y);
        if (x != y) {
            family[x] = y;
        }
    }
    void calcRelation() {
        for (int i = 0; i < option.length; i++) {
            family[i] = i;
        }
        for (int i = 0; i < option.length; i++) {
            for (int j = 0; j < option.length; j++) {
                rel[i][j] = 0;
            }
        }
        for (int i = 0; i < option.length; i++) {
            ch[i] = new MyCharactor(option[i]);
            //System.out.println(ch[i].searchName);
        }
        for (int i = 0; i < Read.text.length() - 2; i++) {
            for (int j = 0; j < option.length; j++) {
                if (i + ch[j].searchName.length() < Read.text.length()) {
                    if (Read.text.substring(i, i + ch[j].searchName.length()).compareTo(ch[j].searchName) == 0) {
                        pos[j] = i;
                        for (int k = 0; k < option.length; k++) {
                            if (k == j) continue;
                            if (pos[j] - pos[k] < 10 && pos[k] > 0) {
                                rel[j][k]++;
                                rel[k][j]++;
                                if (rel[j][k] > 30) merge(j, k);
                            }

                        }
                    }

                }
            }
            if (Read.text.substring(i, i + 2).compareTo("丈夫") == 0) {
                for (int k = i - 300; k < Math.min(Read.text.length(),i + 300); k++) {
                    if (Read.text.substring(k, k + 2).compareTo("太太") == 0) {
                        for (int z = i - 300; z < i + 300; z++) {
                            for (int x = 0; x < option.length; x++) {
                                if (z + ch[x].searchName.length() < Read.text.length()) {
                                    if (Read.text.substring(z, z + ch[x].searchName.length()).compareTo(ch[x].searchName) == 0) { merge(0, 4);
                                        //System.out.println(option[x]);
                                        pos[x] = z;
                                        for (int y = 0; y < option.length; y++) {
                                            if (x == 2 || y == 2) continue;
                                            if (Math.abs(pos[x] - pos[y]) < 50) merge(x, y);
                                        }
                                    }
                                }
                            }
                        }
                        break;
                    }
                }
            }
        }

        for (int i = 0; i < option.length; i++) family[i] = find(family[i]);
        int[] vis = new int[option.length];
        for (int i = 0; i < option.length; i++) vis[i] = -1;
        cnt = 0;
        for (int i = 0; i < option.length; i++) {
            if (vis[family[i]] == -1) {
                vis[family[i]] = cnt++;
            }
            group[vis[family[i]]][num[vis[family[i]]]++] = option[i];
        }
    }
    private JButton save = new MyButton("保存");
    MyCanvas mc = new MyCanvas();
    public RelationWindow() {

        calcRelation();
        this.setLayout(null);

        mc.setSize(800, 600);
        mc.setBackground(Color.white);
        this.setSize(800,600);
        this.add(mc);
        this.add(save);
        save.setSize(100, 50);
        save.setLocation(350, 500);
        save.addActionListener(this);

        this.setLocationRelativeTo(null);
        this.setResizable(false);
    }
    public void actionPerformed(ActionEvent e) {
        try {
            Socket s = new Socket("127.0.0.1", 9999);
            OutputStream os = s.getOutputStream();
            BufferedImage image = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
            Graphics g = image.createGraphics();
            mc.paint(g);
            PrintStream ps = new PrintStream(os);
            ps.println("Img3.jpg");
            ImageIO.write(image, "jpg", os);
        } catch(Exception a){}
    }
}
public class Function3 implements ActionListener {
    private RelationWindow rw;
    public void actionPerformed(ActionEvent a) {

        if (Read.text == null) {
            JOptionPane.showMessageDialog(null,"请先载入文本");
        }
        else {
            if (rw != null) {
                rw.setVisible(true);
            }
            else {
                rw = new RelationWindow();
                rw.setVisible(true);
            }
        }
    }

}
