package moves;

import piese.Piesa;

public class Mutare {
    private int randStart, colStart, randFinal, colFinal;
    private Piesa piesa;

    public Mutare(int randStart, int colStart, int randFinal, int colFinal)
    {
        this.randStart = randStart;
        this.colStart = colStart;
        this.randFinal = randFinal;
        this.colFinal = colFinal;
    }

    public Mutare ( int randStart, int colStart, int randFinal, int colFinal, Piesa piesa )
    {
        this.randStart = randStart;
        this.colStart = colStart;
        this.randFinal = randFinal;
        this.colFinal = colFinal;
        this.piesa = piesa;
    }


    public int getRandStart() {
        return randStart;
    }

    public void setRandStart(int randStart) {
        this.randStart = randStart;
    }

    public int getColStart() {
        return colStart;
    }

    public void setColStart(int colStart) {
        this.colStart = colStart;
    }

    public int getRandFinal() {
        return randFinal;
    }

    public void setRandFinal(int randFinal) {
        this.randFinal = randFinal;
    }

    public int getColFinal() {
        return colFinal;
    }

    public void setColFinal(int colFinal) {
        this.colFinal = colFinal;
    }

    public Piesa getPiesa() {
        return piesa;
    }

    public void setPiesa(Piesa piesa) {
        this.piesa = piesa;
    }
}

