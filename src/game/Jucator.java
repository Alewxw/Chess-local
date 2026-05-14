package game;

public class Jucator {
    private String nume;
    private String culoare;
    private Timer timer;
    private int scorTotal;

    public Jucator ( String nume, String culoare )
    {
        this.nume = nume;
        this.culoare = culoare;
    }

    public Jucator ( String nume, String culoare, Timer timer, int scorTotal )
    {
        this.nume = nume;
        this.culoare = culoare;
        this.timer = timer;
        this.scorTotal = scorTotal;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public int getScorTotal() {
        return scorTotal;
    }

    public void setScorTotal(int scorTotal) {
        this.scorTotal = scorTotal;
    }

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public String getCuloare() {
        return culoare;
    }

    public void setCuloare(String culoare) {
        this.culoare = culoare;
    }
}
