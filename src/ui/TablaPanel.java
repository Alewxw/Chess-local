package ui;

import dp.PiesaFactory;
import exceptions.JocTerminatException;
import exceptions.PromovareException;
import game.Joc;
import game.Jucator;
import game.Tabla;
import moves.Mutare;
import piese.Piesa;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

public class TablaPanel extends JPanel {
    private Joc joc;
    private static final int DIM_CASUTA = 80;
    private javax.swing.Timer swingTimer;

    private int randSelectat = -1;
    private int colSelectat = -1;

    private boolean modAnaliza = false;

    private int scorCurent = 0;
    private engine.Minimax minimax = new engine.Minimax(new engine.Evaluator(), 3);

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

    public TablaPanel(Joc joc, JFrame parinte, boolean modAnaliza) {
        this.joc = joc;
        this.modAnaliza = modAnaliza;

        JFrame frame = new JFrame("Sah");

        setPreferredSize(new Dimension(DIM_CASUTA * 8 + 50, DIM_CASUTA * 8 + 50));

        JMenuBar menuBar = new JMenuBar();
        JMenu meniu = new JMenu("Optiuni");
        JMenuItem salveaza = new JMenuItem("Salveaza joc");
        JMenuItem istoric = new JMenuItem("Istoric joc");

        meniu.add(salveaza);
        meniu.add(istoric);
        menuBar.add(meniu);
        frame.setJMenuBar(menuBar);

        frame.add(this);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(parinte);
        frame.setVisible(true);

        swingTimer = new javax.swing.Timer(1000, e -> {
            Jucator curent = joc.getJucatorCurent();
            game.Timer t = curent.getTimer();

            if (t.getTimpRamas() > 0) {
                t.setTimpRamas(t.getTimpRamas() - 1);
            } else {

                joc.esteJocTerminat();
                swingTimer.stop();
                JOptionPane.showMessageDialog(null, curent.getNume() + " a pierdut la timp!");
            }
            repaint();
        });
        swingTimer.start();
        recalculeazaScor();

        istoric.addActionListener( e-> {
            swingTimer.stop();

            JTextArea textArea = new JTextArea();
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);

            List<Mutare> mutari = joc.getTabla().getIstoricMutari();

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mutari.size(); i++)
            {
                Mutare m = mutari.get(i);
                sb.append((i+1) + ". " + m.getPiesa().getTip() + " " + m.getRandStart() + "," + m.getColStart() + " -> " + m.getRandFinal() + "," + m.getColFinal() + "\n");
            }

            textArea.setText(sb.toString());

            JOptionPane.showMessageDialog(frame, scrollPane, "Istoric", JOptionPane.PLAIN_MESSAGE);
            swingTimer.start();

        });

        salveaza.addActionListener(e -> {
           JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);

            if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
                String cale = fileChooser.getSelectedFile().getAbsolutePath();

                if ( !cale.endsWith(".txt") ) cale += ".txt";
                joc.salveaza(cale);


            }
        });

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
                        recalculeazaScor();

                    }
                    catch (JocTerminatException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Joc terminat", JOptionPane.INFORMATION_MESSAGE);
                        repaint();
                    }
                    catch (PromovareException ex) {
                        JPopupMenu popup = new JPopupMenu();
                        String[] simboluri = {"♕", "♖", "♗", "♘"};
                        String[] tipuri = {"Regina", "Tura", "Nebun", "Cal"};

                        String culoare = joc.getTabla().getPiesa(rand, col).getCuloare();

                        for ( int i = 0; i < tipuri.length; i++ ) {
                            final String tip = tipuri[i];
                            final String simbol = simboluri[i];

                            JButton btn = new JButton(simbol);
                            btn.setFont(new Font("Serif", Font.PLAIN, 40));

                            btn.addActionListener(ev -> {
                                Piesa noua = PiesaFactory.creeazaPiesa(tip, culoare, rand, col);
                                joc.getTabla().setPiesa(rand, col, noua);
                                randSelectat = -1;
                                colSelectat = -1;
                                popup.setVisible(false);
                                repaint();
                            });
                            popup.add(btn);
                        }

                        popup.show(TablaPanel.this, col * DIM_CASUTA, rand * DIM_CASUTA);
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

    private String formatTimp(int secunde) {
        return secunde / 60 + ":" + String.format("%02d", secunde % 60);
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

        if (modAnaliza) {
            int linie = calculeazaPozitiebara(scorCurent);
            int x = DIM_CASUTA * 8 + 5;
            int latime = 40;

            g.setColor(Color.BLACK);
            g.fillRect(x, 0, latime, linie);

            g.setColor(Color.WHITE);
            g.fillRect(x, linie, latime, DIM_CASUTA * 8 - linie);

            g.setColor(Color.GRAY);
            g.drawRect(x, 0, latime, DIM_CASUTA * 8);

            g.setFont(new Font("Arial", Font.BOLD, 12));
            g.setColor(Color.BLACK);
            String scorText = scorCurent > 0 ? "+" + (scorCurent / 100.0) : "" + (scorCurent / 100.0);
            g.drawString(scorText, x + 2, DIM_CASUTA * 8 + 20);
        }

        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.setColor(Color.BLACK);
        g.drawString(joc.getJucator1().getNume() + ": " + formatTimp(joc.getJucator1().getTimer().getTimpRamas()), 10, DIM_CASUTA * 8 + 20);
        g.drawString(joc.getJucator2().getNume() + ": " + formatTimp(joc.getJucator2().getTimer().getTimpRamas()), 10, DIM_CASUTA * 8 + 40);
    }

    private int calculeazaPozitiebara(int scor) {

        scor = Math.max(-1000, Math.min(1000, scor));

        return (int) (320 - (scor / 1000.0) * 320);
    }

    private void recalculeazaScor() {
        if (!modAnaliza) return;
        new SwingWorker<Integer, Void>() {
            @Override
            protected Integer doInBackground() {
                return minimax.getBestScore(joc.getTabla().getTabla());
            }

            @Override
            protected void done() {
                try {
                    scorCurent = get();
                    repaint();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }
}