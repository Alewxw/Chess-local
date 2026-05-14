package piese;

import java.util.ArrayList;
import java.util.List;

public class Tura extends Piesa{
    public Tura ( String culoare, String tip, int rand, int coloana )
    {
       super(culoare, tip, rand, coloana);
    }

    public Tura ( String culoare, String tip )
    {
        super(culoare, tip);
    }

    public List<int[]> getMutariValid (Piesa[][] tabla )
    {
        List<int[]> mutari = new ArrayList<int[]>();

        int dir[][] = {{-1, 0}, {1, 0}, {0, 1}, {0, -1}};

        for ( int[] d  : dir )
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
        return rand == this.getRand() || coloana == this.getColoana();
    }
}
