package ui;

import game.Joc;
import game.Jucator;
import game.Tabla;

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
                new TablaPanel(joc, frame);


            }

        });

        buton3.addActionListener(e -> System.exit(0));

        frame.setVisible(true);


    }
}
