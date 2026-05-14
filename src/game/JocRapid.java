package game;

public class JocRapid extends TipJoc{

    public JocRapid ( int durata, String numeJoc )
    {
        super(durata, numeJoc);
    }

    public JocRapid ()
    {
        super(3, "");
    }

    @Override
    public String getReguli ()
    {
        String reguli = "Joc rapid - 3 minute";
        return reguli;
    }
}
