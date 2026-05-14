package piese;

import java.util.ArrayList;
import java.util.List;

public class Pion extends Piesa{

    public Pion ( String culoare, String tip )
    {
        super(culoare, tip);
    }

    public Pion ( String culoare, String tip, int rand, int coloana )
    {
        super(culoare, tip, rand, coloana);
    }

    public List<int[]> getMutariValid (Piesa[][] tabla )
    {
        List<int[]> mutari = new ArrayList<int[]>();

        // mutari normale
        int dir = getCuloare().equals("Alb") ? -1 : 1;
        int r = getRand() + dir;
        if ( r >= 0 && r < 8 && tabla[r][getColoana()] == null ) mutari.add(new int[] {r, getColoana()});

        //mutarile de inceput
        int randStart = getCuloare().equals("Alb") ? 6 : 1;
        if ( randStart == getRand() && tabla[getRand()+dir][getColoana()] == null && tabla[getRand()+2*dir][getColoana()] == null )
        {
            mutari.add(new int[] {getRand()+2*dir, getColoana()});
        }

        //capturi
        if ( getColoana() > 0 && tabla[r][getColoana()-1] != null && !tabla[r][getColoana()-1].getCuloare().equals(getCuloare()) )
        {
            mutari.add(new int[] {r, getColoana()-1});
        }

        if ( getColoana() < 7 && tabla[r][getColoana()+1] != null && !tabla[r][getColoana()+1].getCuloare().equals(getCuloare()) )
        {
            mutari.add(new int[] {r, getColoana()+1});
        }

        return mutari;
    }

    public boolean esteValidaMutarea ( int rand, int coloana )
    {
        int dir = getCuloare().equals("Alb") ? -1 : 1;
        int randStart = getCuloare().equals("Alb") ? 6 : 1;

        boolean unPas = rand == getRand() + dir && coloana == getColoana();
        boolean doiPasi = rand == getRand() + 2 * dir && coloana == getColoana() && getRand() == randStart;
        boolean captura = rand == getRand() + dir && Math.abs(coloana - getColoana()) == 1;

        return unPas || doiPasi || captura;
    }
}
