package dp;

import game.Jucator;

public interface StrategieJoc {

    int getTimpJucator();
    String getNumeStrategie();
    boolean esteJocTerminat(Jucator j1, Jucator j2);
}
