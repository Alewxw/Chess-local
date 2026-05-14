package ui;

import dp.StrategieJoc;
import game.Joc;
import game.Jucator;
import game.Tabla;

import javax.swing.*;
import java.awt.*;

public class JocNou {
    JDialog jd = new JDialog();

    JTextField nume1 = new JTextField();
    JTextField nume2 = new JTextField();

    JComboBox alegere = new JComboBox();

    JButton start =  new JButton("Start");

    public JocNou(JFrame parinte)
    {
        jd.setSize(300, 300);
        jd.setResizable(false);
        jd.setLayout(new GridLayout(4, 2));

        jd.setLocationRelativeTo(parinte);

        jd.add(new JLabel("Jucator 1:"));
        jd.add(nume1);

        jd.add(new JLabel("Jucator 2:"));
        jd.add(nume2);

        jd.add(new JLabel("Tip joc:"));
        jd.add(alegere);

        jd.add(new JLabel());
        jd.add(start);

        alegere.addItem("Rapid");
        alegere.addItem("Clasic");

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
            jd.dispose();
            new TablaPanel(joc, parinte);
        });

        jd.setVisible(true);
    }

}
