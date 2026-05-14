package ui;

import javax.swing.*;
import java.awt.*;

public class MainMenu {
    public JFrame frame = new JFrame("Joc Nou");

    JButton buton1 = new JButton("Joc nou");
    JButton buton2 = new JButton("Incarca joc");
    JButton buton3 = new JButton("Iesire");

    public MainMenu() {
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new GridLayout(3, 1)); // 3 randuri, 1 coloana

        frame.add(buton1);
        frame.add(buton2);
        frame.add(buton3);


        buton3.addActionListener(e -> System.exit(0));

        frame.setVisible(true);

        buton1.addActionListener(e -> new JocNou(frame));
    }
}
