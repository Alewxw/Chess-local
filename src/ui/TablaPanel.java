package ui;

import exceptions.JocTerminatException;
import game.Joc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

public class TablaPanel extends JPanel {
    private Joc joc;
    private static final int DIM_CASUTA = 80;

    private int randSelectat = -1;
    private int colSelectat = -1;

    private static final Map<String, String> SIMBOLURI = new HashMap<>();

    static {
        SIMBOLURI.put("Rege,Alb", "♔");
        SIMBOLURI.put("Regina,Alb", "♕");
        SIMBOLURI.put("Tura,Alb", "♖");
        SIMBOLURI.put("Nebun,Alb", "♗");
        SIMBOLURI.put("Cal,Alb", "♘");
        SIMBOLURI.put("Pion,Alb", "♙");
        SIMBOLURI.put("Rege,Negru", "♚");
        SIMBOLURI.put("Regina,Negru", "♛");
        SIMBOLURI.put("Tura,Negru", "♜");
        SIMBOLURI.put("Nebun,Negru", "♝");
        SIMBOLURI.put("Cal,Negru", "♞");
        SIMBOLURI.put("Pion,Negru", "♟");
    }

    public TablaPanel(Joc joc, JFrame parinte) {
        this.joc = joc;

        JFrame frame = new JFrame("Sah");

        setPreferredSize(new Dimension(DIM_CASUTA * 8, DIM_CASUTA * 8));
        frame.add(this);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(parinte);
        frame.setVisible(true);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = e.getX() / DIM_CASUTA;
                int rand = e.getY() / DIM_CASUTA;

                if (randSelectat == -1 && colSelectat == -1) {
                    if (joc.getTabla().getPiesa(rand, col) != null) {
                        randSelectat = rand;
                        colSelectat = col;
                        repaint();
                    }
                }
                else{
                    try{
                        joc.executaMutare(randSelectat, colSelectat, rand, col);
                        randSelectat = -1;
                        colSelectat = -1;
                        repaint();
                    }
                    catch (JocTerminatException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Joc terminat", JOptionPane.INFORMATION_MESSAGE);
                        repaint();
                    }
                    catch (Exception ex) {
                        randSelectat = -1;
                        colSelectat = -1;
                        repaint();
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for ( int i = 0 ; i < 8 ; i ++ )
            for ( int j = 0 ; j < 8 ; j ++ )
            {
                if ( ( i + j) % 2 == 0 ) g.setColor(new Color(240, 217, 181));
                else {
                    g.setColor(new Color(181, 136, 99));
                }

                g.fillRect(j* DIM_CASUTA, i * DIM_CASUTA, DIM_CASUTA, DIM_CASUTA);
                if (i == randSelectat && j == colSelectat) {
                    g.setColor(new Color(0, 255, 0, 100));
                    g.fillRect(j * DIM_CASUTA, i * DIM_CASUTA, DIM_CASUTA, DIM_CASUTA);
                }
            }

        g.setFont(new Font("Serif", Font.PLAIN, 60));
        for ( int i = 0 ; i < 8 ; i ++ )
            for ( int j = 0 ; j < 8 ; j ++ )
            {
                piese.Piesa piesa = joc.getTabla().getPiesa(i, j);
                if ( piesa != null )
                {
                    String simbol = SIMBOLURI.get(piesa.getTip() + "," + piesa.getCuloare());
                    g.setColor(Color.BLACK);
                    g.drawString(simbol, j * DIM_CASUTA + 10, i * DIM_CASUTA + 65);
                }
            }

    }
}