package dp;

import exceptions.PozitieInvalidaException;
import piese.*;

public class PiesaFactory {

    public static Piesa creeazaPiesa (String tip, String culoare, int rand, int coloana )
    {
        switch ( tip )
        {
            case "Tura" :{
                Tura tura = new Tura(culoare, tip, rand, coloana);
                return tura;
            }

            case "Nebun" :{
                Nebun nebun = new Nebun(culoare, tip, rand, coloana);
                return nebun;
            }

            case "Regina" :{
                Regina regina = new Regina(culoare, tip, rand, coloana);
                return regina;
            }

            case "Rege" :{
                Rege rege = new Rege(culoare, tip, rand, coloana);
                return rege;
            }

            case "Cal" :{
                Cal cal = new Cal(culoare, tip, rand, coloana);
                return cal;
            }

            case "Pion" :{
                Pion pion = new Pion(culoare, tip, rand, coloana);
                return pion;
            }

            default: throw new PozitieInvalidaException("Tip de piesa invalid: " + tip);

        }
    }
}
