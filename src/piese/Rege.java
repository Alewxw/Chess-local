package piese;

import java.util.ArrayList;
import java.util.List;

public class Rege extends Piesa{
    public Rege ( String culoare, String tip )
    {
        super(culoare, tip);
    }

    public Rege ( String culoare, String tip, int rand, int coloana )
    {
        super(culoare, tip, rand, coloana);
    }

    public  List<int[]> getMutariValid (Piesa[][] tabla )
    {
        List<int[]> mutari = new ArrayList<>();
        int[][] directii = {{-1,-1},{-1,0},{-1,1},{0,-1},{0,1},{1,-1},{1,0},{1,1}};
        for (int[] d : directii)
        {
            int r = getRand() + d[0];
            int c = getColoana() + d[1];

            if (r >= 0 && r < 8 && c >= 0 && c < 8 &&
                    (tabla[r][c] == null || !tabla[r][c].getCuloare().equals(getCuloare())) ) {
                mutari.add(new int[]{r, c});
            }
        }

        if ( !isaMutat() && tabla[getRand()][5] == null  && tabla[getRand()][6] == null && tabla[getRand()][7] != null && tabla[getRand()][7].getTip().equals("Tura") && !tabla[getRand()][7].isaMutat() )
        {
                mutari.add(new int[]{getRand(), 6});
        }

        if ( !isaMutat() && tabla[getRand()][1] == null  && tabla[getRand()][2] == null && tabla[getRand()][3] == null && tabla[getRand()][0] != null && tabla[getRand()][0].getTip().equals("Tura") && !tabla[getRand()][0].isaMutat() )
        {
                mutari.add(new int[]{getRand(), 2});
        }



        return mutari;
    }

    @Override
    public  boolean esteValidaMutarea ( int rand, int coloana )
    {
        return Math.abs(rand - getRand()) <= 1 && Math.abs(coloana - getColoana()) <= 1;
    }
}
