package game;

import dp.PiesaFactory;
import dp.StrategieJoc;
import exceptions.JocTerminatException;
import exceptions.MutareInvalidaException;
import exceptions.PromovareException;
import exceptions.RegeSahException;
import interfata.Salvabil;
import moves.Mutare;
import piese.Piesa;
import java.io.*;

import java.util.List;

public class Joc implements Salvabil {
    Tabla tabla;
    Jucator jucator1, jucator2;
    StrategieJoc strategie;
    Jucator jucatorCurent;

    private int enPassantCol = -1;
    private int enPassantRand = -1;

    boolean jocActiv;

    public Joc ( Tabla tabla, StrategieJoc strategie, Jucator jucator1, Jucator jucator2 )
    {
        this.tabla = tabla;
        this.strategie = strategie;
        this.jucator1 = jucator1;
        this.jucator2 = jucator2;
        this.jocActiv = true;
        this.jucatorCurent = jucator1;
    }

    public void initTabla ( )
    {
        String[] ordine= {"Tura", "Cal", "Nebun", "Regina", "Rege", "Nebun", "Cal", "Tura"};

        for ( int i = 0 ; i < 8 ; i ++ )
        {
            piese.Piesa p = PiesaFactory.creeazaPiesa(ordine[i], "Alb", 7, i);
            piese.Piesa l = PiesaFactory.creeazaPiesa(ordine[i], "Negru", 0, i);
            piese.Piesa m = PiesaFactory.creeazaPiesa("Pion", "Alb", 6, i);
            piese.Piesa o = PiesaFactory.creeazaPiesa("Pion", "Negru", 1, i);

            tabla.setPiesa(7, i, p);
            tabla.setPiesa(0, i, l);
            tabla.setPiesa(6, i, m);
            tabla.setPiesa(1, i, o);
        }

    }

    public void executaMutare(int randStart, int colStart, int randFinal, int colFinal) {

        if (!jocActiv)
            throw new JocTerminatException("Jocul nu e activ");

        Piesa piesa = tabla.getPiesa(randStart, colStart);

        if (piesa == null)
            throw new MutareInvalidaException("Nu e o piesa acolo");

        if (!piesa.getCuloare().equals(jucatorCurent.getCuloare()))
            throw new MutareInvalidaException("Piesa nu apartine jucatorului curent");

        List<int[]> mutariValide = piesa.getMutariValid(tabla.getTabla());

        boolean gasit = false;

        for ( int[] d : mutariValide )
        {
            if (d[0] == randFinal && d[1] == colFinal)
            {
                gasit = true;
                break;
            }
        }

        boolean esteEnPassant = false;

        if (piesa.getTip().equals("Pion") && colFinal == enPassantCol &&
                randStart == enPassantRand && Math.abs(colFinal - colStart) == 1) {
            esteEnPassant = true;
            gasit = true;
        }

        if (!gasit)
            throw new MutareInvalidaException("Mutarea nu este valida pentru aceasta piesa");

        Piesa dest = tabla.getPiesa(randFinal, colFinal);
        boolean aMutatOriginal = piesa.isaMutat();

        tabla.setPiesa(randFinal, colFinal, piesa);
        tabla.setPiesa(randStart, colStart, null);

        piesa.setRand(randFinal);
        piesa.setColoana(colFinal);
        piesa.setaMutat(true);

        if (esteEnPassant) {
            tabla.setPiesa(enPassantRand, enPassantCol, null);
        }

        if (piesa.getTip().equals("Pion") && Math.abs(randFinal - randStart) == 2) {
            enPassantCol = colFinal;
            enPassantRand = randFinal;
        } else {
            enPassantCol = -1;
            enPassantRand = -1;
        }

        if (piesa.getTip().equals("Rege") && Math.abs(colFinal - colStart) == 2) {
            if (colFinal == 6) {
                Piesa tura = tabla.getPiesa(randStart, 7);
                tabla.setPiesa(randStart, 5, tura);
                tabla.setPiesa(randStart, 7, null);
                tura.setColoana(5);
                tura.setaMutat(true);

            }
            else {
                Piesa tura = tabla.getPiesa(randStart, 0);
                tabla.setPiesa(randStart, 3, tura);
                tabla.setPiesa(randStart, 0, null);
                tura.setColoana(3);
                tura.setaMutat(true);
            }
        }

        if ( esteSah(piesa.getCuloare()) )
        {
            tabla.setPiesa(randStart, colStart, piesa);
            tabla.setPiesa(randFinal, colFinal, dest);

            piesa.setRand(randStart);
            piesa.setColoana(colStart);
            piesa.setaMutat(aMutatOriginal);

            if (piesa.getTip().equals("Rege") && Math.abs(colFinal - colStart) == 2) {
                if (colFinal == 6) {
                    Piesa tura = tabla.getPiesa(randStart, 5);
                    tabla.setPiesa(randStart, 7, tura);
                    tabla.setPiesa(randStart, 5, null);
                    tura.setColoana(7);
                    tura.setaMutat(false);
                } else {
                    Piesa tura = tabla.getPiesa(randStart, 3);
                    tabla.setPiesa(randStart, 0, tura);
                    tabla.setPiesa(randStart, 3, null);
                    tura.setColoana(0);
                    tura.setaMutat(false);
                }
            }

            throw new MutareInvalidaException("Mutarea te pune in sah");
        }

        tabla.adaugaMutare(new Mutare(randStart, colStart, randFinal, colFinal, piesa));

        jucatorCurent = (jucatorCurent == jucator1) ? jucator2 : jucator1;

        if ( tabla.getPiesa(randFinal, colFinal).getTip().equals("Pion") && (randFinal == 0 || randFinal == 7) )
        {
            throw new PromovareException("Ai reusit sa promovezi un pion!");
        }

        if ( esteSah(jucatorCurent.getCuloare()) )
        {
            if (!areMutariLegale(jucatorCurent.getCuloare()))
            {
                jocActiv = false;
                throw new JocTerminatException("Mat! " + (jucatorCurent == jucator1 ? jucator2.getNume() : jucator1.getNume()) + " a castigat!");
            }
            throw new RegeSahException("Regele lui " + jucatorCurent.getNume() + " e in sah!");
        }
    }

    public boolean areMutariLegale ( String culoare )
    {
        for ( int i = 0 ; i < 8 ; i ++ )
            for ( int j = 0 ; j < 8 ; j ++ )
            {
                if ( tabla.getPiesa(i, j) != null && tabla.getPiesa(i, j).getCuloare().equals(culoare) )
                {
                    List<int[]> mutariValide = tabla.getPiesa(i, j).getMutariValid(tabla.getTabla());

                    for ( int[] d : mutariValide )
                    {
                        Piesa piesaCurenta = tabla.getPiesa(i, j);
                        Piesa dest = tabla.getPiesa(d[0], d[1]);

                        tabla.setPiesa(d[0], d[1], piesaCurenta);
                        tabla.setPiesa(i, j, null);
                        piesaCurenta.setRand(d[0]);
                        piesaCurenta.setColoana(d[1]);

                        if (!esteSah(culoare)) {

                            tabla.setPiesa(i, j, piesaCurenta);
                            tabla.setPiesa(d[0], d[1], dest);

                            piesaCurenta.setRand(i);
                            piesaCurenta.setColoana(j);

                            return true;
                        }

                        tabla.setPiesa(i, j, piesaCurenta);
                        tabla.setPiesa(d[0], d[1], dest);

                        piesaCurenta.setRand(i);
                        piesaCurenta.setColoana(j);
                    }
                }
            }

        return false;
    }

    public void esteJocTerminat ()
    {
        if (strategie.esteJocTerminat(jucator1, jucator2))
            jocActiv = false;
    }

    public boolean esteSah ( String culoare )
    {
        int randRege = 0, colRege = 0;
        for ( int i = 0 ; i < 8 ; i ++ )
            for ( int j = 0 ; j < 8 ; j ++ )
            {
                Piesa piesa = tabla.getPiesa(i, j);

                if (piesa != null && piesa.getTip().equals("Rege") && piesa.getCuloare().equals(culoare)) {
                    randRege = i;
                    colRege = j;
                }
            }

        for ( int i = 0 ; i < 8 ; i ++ )
            for ( int j = 0 ; j < 8 ; j ++ )
            {
                Piesa piesa = tabla.getPiesa(i, j);

                if (piesa != null && !piesa.getCuloare().equals(culoare))
                {
                    List<int[]> mutariValide = piesa.getMutariValid(tabla.getTabla());

                    for ( int[] d : mutariValide )
                    {
                        if (d[0] == randRege && d[1] == colRege)
                        {
                            return true;
                        }
                    }
                }
            }

        return false;
    }

    @Override
    public void salveaza(String numeFisier) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(numeFisier));

            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    Piesa piesa = tabla.getPiesa(i, j);
                    if (piesa != null) {
                        writer.write(piesa.getTip() + "," + piesa.getCuloare() + "," + i + "," + j);
                        writer.newLine();
                    }
                }
            }

            writer.write("JUCATOR_CURENT:" + jucatorCurent.getCuloare());

            writer.write("EN_PASSANT:" + enPassantCol + "," + enPassantRand);
            writer.newLine();

            writer.close();

        } catch (IOException e) {
            System.out.println("Eroare la salvare: " + e.getMessage());
        }
    }

    @Override
    public void incarca(String numeFisier)
    {
        try{
            BufferedReader reader = new BufferedReader(new FileReader(numeFisier));

           String linie;
           while ( (linie = reader.readLine()) != null)
           {
               if ( linie.startsWith("JUCATOR_CURENT") )
               {
                   String culoare = linie.split(":")[1];
                   jucatorCurent = culoare.equals(jucator1.getCuloare()) ? jucator1 : jucator2;
               }
               else
               {
                   String[] data = linie.split(",");
                   Piesa piesa = PiesaFactory.creeazaPiesa(data[0], data[1],
                           Integer.parseInt(data[2]), Integer.parseInt(data[3]));
                   tabla.setPiesa(Integer.parseInt(data[2]), Integer.parseInt(data[3]), piesa);
               }

               if (linie.startsWith("EN_PASSANT")) {
                   String[] vals = linie.split(":")[1].split(",");
                   enPassantCol = Integer.parseInt(vals[0]);
                   enPassantRand = Integer.parseInt(vals[1]);
               }
           }

           reader.close();

        }
        catch ( IOException e)
        {
                System.out.println("Eroare la incarcare: " + e.getMessage());
        }
    }

    public Tabla getTabla() {
        return tabla;
    }

    public void setTabla(Tabla tabla) {
        this.tabla = tabla;
    }

    public Jucator getJucator1() {
        return jucator1;
    }

    public void setJucator1(Jucator jucator1) {
        this.jucator1 = jucator1;
    }

    public Jucator getJucator2() {
        return jucator2;
    }

    public void setJucator2(Jucator jucator2) {
        this.jucator2 = jucator2;
    }

    public StrategieJoc getStrategie() {
        return strategie;
    }

    public void setStrategie(StrategieJoc strategie) {
        this.strategie = strategie;
    }

    public Jucator getJucatorCurent() {
        return jucatorCurent;
    }

    public void setJucatorCurent(Jucator jucatorCurent) {
        this.jucatorCurent = jucatorCurent;
    }

    public boolean isJocActiv() {
        return jocActiv;
    }

    public void setJocActiv(boolean jocActiv) {
        this.jocActiv = jocActiv;
    }
}
