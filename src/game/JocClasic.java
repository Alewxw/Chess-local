package game;

public class JocClasic extends TipJoc{

   public JocClasic ( int durata, String numeJoc )
   {
       super(durata, numeJoc);
   }

   public JocClasic ()
   {
       super(10, "");
   }

    @Override
    public String getReguli ()
    {
        String reguli = "Joc clasic - 10 minute";
        return reguli;
    }
}
