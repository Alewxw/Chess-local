package dp;

import game.Jucator;
import game.Timer;

public class StrategieRapid implements StrategieJoc{

    public boolean esteJocTerminat(Jucator j1, Jucator j2)
    {
        Timer t1 = j1.getTimer();
        Timer t2 = j2.getTimer();

        return t1.getTimpRamas() <= 0 || t2.getTimpRamas() <= 0;

    }

    public int getTimpJucator ()
    {
        return 3;
    }

    public String getNumeStrategie ()
    {
        return "Rapid";
    }

}
