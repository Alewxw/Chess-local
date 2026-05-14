package piese;

import java.util.*;

public class Nebun extends Piesa{

    public Nebun  ( String culoare, String tip, int rand, int coloana )
    {
        super(culoare, tip, rand, coloana);
    }

    public Nebun ( String culoare, String tip )
    {
        super(culoare, tip);
    }

    public List<int[]> getMutariValid (Piesa[][] tabla )
    {
        List<int[]> mutari = new ArrayList<>();
        int[][] dir = { {-1, 1}, {1, 1}, {-1, -1}, {1, -1}};

        for ( int[] d : dir )
        {
            int r = getRand() + d[0];
            int c = getColoana() + d[1];

            while ( r >= 0 && r < 8 && c >= 0 && c < 8 )
            {
                if ( tabla[r][c] == null )
                {
                    mutari.add(new int[]{r, c});
                }
                else
                {
                    if ( !tabla[r][c].getCuloare().equals(getCuloare()) )
                    {
                        mutari.add(new int[]{r, c});
                    }

                    break;
                }


                r += d[0];
                c += d[1];
            }

        }

        return mutari;


    }
    public boolean esteValidaMutarea ( int rand, int coloana )
    {
        return Math.abs(rand - getRand()) == Math.abs(coloana - getColoana());
    }
}
