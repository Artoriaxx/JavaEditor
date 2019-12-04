import function.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;
import java.util.HashMap;


class MyButton extends JButton {
    MyButton(String s) {
        this.setFont(new Font("楷体", Font.BOLD, 14));
        this.setBorderPainted(false);
        this.setLabel(s);
    }
}

class Choose extends JFrame implements ActionListener {
    String name = null;
    Choose(String s) {
        this.name = s;
        JButton jbt = new MyButton("白夜行");
        jbt.setSize(200, 100);
        jbt.addActionListener(this);
        this.add(jbt);
        this.setSize(200,100);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }


    public void actionPerformed(ActionEvent e) {
        this.dispose();
        new Client(name);
    }
}



public class Client extends JFrame {
    String name = null;
    private JPanel jpl = new JPanel();
    private JButton load = new MyButton("载入");
    private JButton fun1 = new MyButton("存在感");
    private JButton fun2 = new MyButton("密度");
    private JButton fun3 = new MyButton("关系");

    Read r = new Read();
    Function1 f1 = new Function1();
    Function2 f2 = new Function2();
    Function3 f3 = new Function3();
    public void setBack(){
        ((JPanel)this.getContentPane()).setOpaque(false);
        ImageIcon img = new ImageIcon("background.jpg"); //添加图片
        JLabel background = new JLabel(img);
        this.getLayeredPane().add(background, new Integer(Integer.MIN_VALUE));
        background.setBounds(-20, -120, img.getIconWidth(), img.getIconHeight());
    }
    public Client(String name) {
        this.name = name;
        setBack();
        JLabel jl = new JLabel("欢迎, 用户" + name, JLabel.CENTER);
        jl.setFont(new Font("楷体", Font.BOLD, 20));
        load.addActionListener(r);
        fun1.addActionListener(f1);
        fun2.addActionListener(f2);
        fun3.addActionListener(f3);


        jl.setSize(300, 50);
        load.setSize(80, 50);
        fun1.setSize(80, 50);
        fun2.setSize(80, 50);
        fun3.setSize(80, 50);

        jpl.setLayout(null);
        jpl.setOpaque(false);
        jpl.add(jl);
        jpl.add(load);
        jpl.add(fun1);
        jpl.add(fun2);
        jpl.add(fun3);

        jl.setLocation(50, 20);
        load.setLocation(20, 100);
        fun1.setLocation(110, 100);
        fun2.setLocation(200, 100);
        fun3.setLocation(290, 100);

        this.add(jpl);

        this.setVisible(true);
        this.setSize(400, 600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
    }
    public static void main(String[] args) {
        String s = null;
        s = JOptionPane.showInputDialog("请输入昵称:");
        while (s == null || s.length() == 0) {
            s = JOptionPane.showInputDialog("请重新输入昵称");
        }
        new Choose(s);
    }

}
