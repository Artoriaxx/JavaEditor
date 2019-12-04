package function;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class Read implements ActionListener {
    public static String text = null;
    static HashMap<String, Integer> map = new HashMap<String, Integer>();
    public void actionPerformed(ActionEvent e) {
        if (text != null) {
            JOptionPane.showMessageDialog(null, "已经载入成功，无需再次载入");
        }
        else {
            try {
                Socket s = new Socket("127.0.0.1",9999);
                BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream(), StandardCharsets.UTF_8));
                String str = null;
                while ((str = in.readLine()) != null) {
                    text = text + str + "\n";
                    if (text.contains("http://Www.Qisuu.Com")) break;
                    //System.out.println(str);
                }
                s.close();
                in.close();
                JOptionPane.showMessageDialog(null, "载入成功");
            } catch (Exception a) {}
        }

    }
}
