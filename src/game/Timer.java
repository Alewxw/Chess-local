package game;

public class Timer {
    private int timpRamas;
    private boolean activ;

    public Timer ( int timpRamas, boolean activ )
    {
        this.timpRamas = timpRamas;
        this.activ = activ;
    }

    public Timer ()
    {
        this.timpRamas = 0;
        this.activ = false;
    }

    public void start ()
    {
        activ = true;
    }

    public void stop ()
    {
        activ = false;
    }

    public void reset ()
    {
        timpRamas = 0;
        activ = false;

    }

    public int getTimpRamas() {
        return timpRamas;
    }

    public void setTimpRamas(int timpRamas) {
        this.timpRamas = timpRamas;
    }

    public boolean isActiv() {
        return activ;
    }

    public void setActiv(boolean activ) {
        this.activ = activ;
    }
}
