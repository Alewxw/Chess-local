package piese;

import interfata.Miscabil;

import java.util.*;

public abstract class Piesa implements Miscabil {
    private String culoare;
    private String tip;

    private int rand;
    private int coloana;

    public Piesa ( String culoare, String tip, int rand, int coloana ) {
        this.culoare = culoare;
        this.tip = tip;
        this.rand = rand;
        this.coloana = coloana;
    }

    public Piesa ( String culoare, String tip )
    {
        this.culoare = culoare;
        this.tip = tip;
    }

    public String getCuloare() {
        return culoare;
    }

    public void setCuloare(String culoare) {
        this.culoare = culoare;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public int getRand() {
        return rand;
    }

    public void setRand(int rand) {
        this.rand = rand;
    }

    public int getColoana() {
        return coloana;
    }

    public void setColoana(int coloana) {
        this.coloana = coloana;
    }
}
