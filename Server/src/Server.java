

import java.awt.*;
import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

import javax.swing.*;
public class Server extends JFrame {
    String text = null;
    public void setBack(){
        ((JPanel)this.getContentPane()).setOpaque(false);
        ImageIcon img = new ImageIcon("background.jpg"); //添加图片
        JLabel background = new JLabel(img);
        this.getLayeredPane().add(background, new Integer(Integer.MIN_VALUE));
        background.setBounds(-20, -120, img.getIconWidth(), img.getIconHeight());
    }
    public Server() {
        setBack();
        JLabel jl = new JLabel("服务器欢迎你", JLabel.CENTER);
        this.setLayout(null);
        jl.setFont(new Font("楷体", Font.BOLD, 20));
        jl.setSize(300, 50);
        jl.setLocation(50, 100);
        this.add(jl);
        this.setSize(400, 600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.read();
        while (true) {
            this.getImg();
        }

    }
    void read() {
        String inputPath = "白夜行.txt";
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(inputPath), "gbk"));
            String line = null;
            while ((line = in.readLine()) != null) {
                text = text + line + "\n";
            }
            ServerSocket ss = new ServerSocket(9999);
            Socket s = ss.accept();
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream(), StandardCharsets.UTF_8));
            //System.out.println(1);
            out.write(text);
            out.flush();
            out.close();
            in.close();
            ss.close();
            s.close();
            //System.out.println(text);
        } catch (Exception e){}
    }
    void getImg() {
        try {
            ServerSocket ss = new ServerSocket(9999);
            Socket s = ss.accept();
            InputStream in = s.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String fileName = br.readLine();
            FileOutputStream fos = new FileOutputStream(fileName);
            byte[] buf = new byte[1024];
            int len = 0;
            while ((len = in.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }
            in.close();
            s.close();
            ss.close();
        } catch (Exception a){}
    }
    public static void main(String[] args) throws Exception {
        new Server();
    }
}
