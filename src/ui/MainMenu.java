package ui;

import game.Joc;
import game.Jucator;
import game.Tabla;

import javax.swing.*;
import java.awt.*;

public class MainMenu {
    public JFrame frame = new JFrame("Joc Nou");

    JButton buton1 = creeazaButon("Joc Nou");
    JButton buton2 = creeazaButon("Încarcă Joc");
    JButton buton3 = creeazaButon("Ieșire");

    public MainMenu() {
        frame.setSize(400, 300);
        frame.getContentPane().setBackground(new Color(30, 30, 30));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));


        JLabel titlu = new JLabel("♔ Joc de Șah ♔");
        titlu.setFont(new Font("Serif", Font.BOLD, 32));
        titlu.setForeground(Color.WHITE);
        titlu.setAlignmentX(Component.CENTER_ALIGNMENT);

        buton1.setAlignmentX(Component.CENTER_ALIGNMENT);
        buton2.setAlignmentX(Component.CENTER_ALIGNMENT);
        buton3.setAlignmentX(Component.CENTER_ALIGNMENT);

        buton1.setMaximumSize(new Dimension(200, 50));
        buton2.setMaximumSize(new Dimension(200, 50));
        buton3.setMaximumSize(new Dimension(200, 50));

        frame.add(Box.createVerticalGlue());
        frame.add(titlu);
        frame.add(Box.createRigidArea(new Dimension(0, 30)));
        frame.add(buton1);
        frame.add(Box.createRigidArea(new Dimension(0, 15)));
        frame.add(buton2);
        frame.add(Box.createRigidArea(new Dimension(0, 15)));
        frame.add(buton3);
        frame.add(Box.createVerticalGlue());



        buton1.addActionListener(e -> new JocNou(frame));

        buton2.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.setDialogType(JFileChooser.OPEN_DIALOG);

            if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                String cale = chooser.getSelectedFile().getAbsolutePath();

                Jucator j1 = new Jucator("Jucator1", "Alb");
                Jucator j2 = new Jucator("Jucator2", "Negru");
                Tabla tabla = new Tabla();

                Joc joc = new Joc(tabla, new dp.StrategieClasic(), j1, j2);
                joc.incarca(cale);
                j1.setTimer(new game.Timer(600, false));
                j2.setTimer(new game.Timer(600, false));
                new TablaPanel(joc, frame, true);


            }

        });

        buton3.addActionListener(e -> System.exit(0));

        frame.setVisible(true);


    }

    private JButton creeazaButon(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        btn.setBackground(new Color(70, 130, 80));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
}
