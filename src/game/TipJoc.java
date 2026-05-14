package game;

public abstract class TipJoc {
    private int durata;
    private String numeJoc;

    public abstract String getReguli();

    public TipJoc(int durata, String numejoc)
    {
        this.durata = durata;
        this.numeJoc = numejoc;
    }

    public TipJoc()
    {
        this.durata = 0;
        this.numeJoc = "";
    }

    public int getDurata() {
        return durata;
    }

    public void setDurata(int durata) {
        this.durata = durata;
    }

    public String getNumeJoc() {
        return numeJoc;
    }

    public void setNumeJoc(String numeJoc) {
        this.numeJoc = numeJoc;
    }
}
