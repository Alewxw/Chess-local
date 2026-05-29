package ui;

import dp.StrategieJoc;
import game.Joc;
import game.Jucator;
import game.Tabla;

import javax.swing.*;
import java.awt.*;

public class JocNou {
    JDialog jd;

    JTextField nume1 = new JTextField();
    JTextField nume2 = new JTextField();

    JComboBox alegere = new JComboBox();

    JButton start =  new JButton("Start");

    public JocNou(JFrame parinte)
    {
        jd = new JDialog(parinte, "Joc Nou", true);
        jd.setSize(300, 300);
        jd.setResizable(false);
        jd.setLocationRelativeTo(parinte);


        jd.getContentPane().setBackground(new Color(30, 30, 30));
        jd.setLayout(new GridLayout(4, 2, 10, 10));

        JLabel label1 = new JLabel("Jucator 1:");
        label1.setForeground(Color.WHITE);
        JLabel label2 = new JLabel("Jucator 2:");
        label2.setForeground(Color.WHITE);
        JLabel label3 = new JLabel("Tip joc:");
        label3.setForeground(Color.WHITE);

        nume1.setBackground(new Color(50, 50, 50));
        nume1.setForeground(Color.WHITE);
        nume2.setBackground(new Color(50, 50, 50));
        nume2.setForeground(Color.WHITE);

        start.setBackground(new Color(70, 130, 80));
        start.setForeground(Color.WHITE);
        start.setFocusPainted(false);
        start.setBorderPainted(false);
        start.setCursor(new Cursor(Cursor.HAND_CURSOR));

        jd.add(label1);
        jd.add(nume1);
        jd.add(label2);
        jd.add(nume2);
        jd.add(label3);
        jd.add(alegere);
        jd.add(new JLabel());
        jd.add(start);

        alegere.addItem("Rapid");
        alegere.addItem("Clasic");

        alegere.setBackground(new Color(50, 50, 50));
        alegere.setForeground(Color.WHITE);

        start.addActionListener(e -> {
           String num1 = nume1.getText();
           String num2 = nume2.getText();

           if (num1.isEmpty() || num2.isEmpty() )
           {
               JOptionPane.showMessageDialog(null, "Nu ai pus numele!", "Error", JOptionPane.ERROR_MESSAGE);
               return ;
           }

           Jucator j1 = new Jucator(num1, "Alb");
           Jucator j2 = new Jucator(num2, "Negru");

           StrategieJoc strategie = alegere.getSelectedItem().equals("Rapid") ? new dp.StrategieRapid() : new dp.StrategieClasic();
           Tabla tabla = new Tabla();
           Joc joc = new Joc(tabla, strategie, j1, j2);

            joc.initTabla();

            int timp = strategie.getTimpJucator() * 60;
            j1.setTimer(new game.Timer(timp, false));
            j2.setTimer(new game.Timer(timp, false));

            jd.dispose();
            new TablaPanel(joc, parinte, false);
        });

        jd.setVisible(true);
    }

}
