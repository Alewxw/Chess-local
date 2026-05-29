package engine;

import piese.Piesa;

public class Evaluator {
    private static final int PION = 100;
    private static final int CAL = 300;
    private static final int NEBUN = 300;
    private static final int TURA = 500;
    private static final int REGINA = 900;
    private static final int REGE = 10000;

    private static final int[][] PionMatrice = {
            { 0, 0, 0, 0, 0, 0, 0, 0},
            { 50, 50, 50, 50, 50, 50, 50, 50},
            { 20, 20, 30, 40, 40, 30, 20, 20},
            { 15, 15, 20, 30, 30, 20, 15, 15},
            { 10, 10, 10, 20, 20, 10, 10, 10},
            { 5, 5, 5, 10, 10, 5, 5, 5},
            { 0, 0, 0, 0, 0, 0, 0, 0},
            { 0, 0, 0, 0, 0, 0, 0, 0},
    };
    private static final int[][] CalMatrice = {
            { -50, -40, -30, -30, -30, -30, -40, -50 },
            { -40, -20,   0,   0,   0,   0, -20, -40 },
            { -30,   0,  10,  15,  15,  10,   0, -30 },
            { -30,   5,  15,  20,  20,  15,   5, -30 },
            { -30,   5,  15,  20,  20,  15,   5, -30 },
            { -30,   0,  10,  15,  15,  10,   0, -30 },
            { -40, -20,   0,   0,   0,   0, -20, -40 },
            { -50, -40, -30, -30, -30, -30, -40, -50 },
    };
    private static final int[][] NebunMatrice = {
            { -20, -10, -10, -10, -10, -10, -10, -20 },
            { -10,   5,   0,   0,   0,   0,   5, -10 },
            { -10,  10,  10,  10,  10,  10,  10, -10 },
            { -10,   0,  10,  15,  15,  10,   0, -10 },
            { -10,   0,  10,  15,  15,  10,   0, -10 },
            { -10,  10,  10,  10,  10,  10,  10, -10 },
            { -10,   5,   0,   0,   0,   0,   5, -10 },
            { -20, -10, -10, -10, -10, -10, -10, -20 },
    };
    private static final int[][] TuraMatrice = {
            {  0,  0,  0,  0,  0,  0,  0,  0 },
            { 20, 20, 20, 20, 20, 20, 20, 20 },
            {  0,  0,  0,  0,  0,  0,  0,  0 },
            {  0,  0,  0,  0,  0,  0,  0,  0 },
            {  0,  0,  0,  0,  0,  0,  0,  0 },
            {  0,  0,  0,  0,  0,  0,  0,  0 },
            {  0,  0,  0,  0,  0,  0,  0,  0 },
            {  0,  0,  0,  5,  5,  0,  0,  0 },
    };

    private static final int[][] ReginaMatrice = {
            { -20, -10, -10,  -5,  -5, -10, -10, -20 },
            { -10,   0,   0,   0,   0,   0,   0, -10 },
            { -10,   0,   5,   5,   5,   5,   0, -10 },
            {  -5,   0,   5,   5,   5,   5,   0,  -5 },
            {  -5,   0,   5,   5,   5,   5,   0,  -5 },
            { -10,   0,   5,   5,   5,   5,   0, -10 },
            { -10,   0,   0,   0,   0,   0,   0, -10 },
            { -20, -10, -10,  -5,  -5, -10, -10, -20 },
    };

    private static final int[][] RegeMid = {
            { 10, 20, 10, 0, 0, 10, 20, 10},
            { -10, -10, -10, -10, -10, -10, -10, -10},
            { -20, -20, -20, -20, -20, -20, -20, -20},
            { -50, -50, -50, -50, -50, -50, -50, -50},
            { -50, -50, -50, -50, -50, -50, -50, -50},
            { -20, -20, -20, -20, -20, -20, -20, -20},
            { -10, -10, -10, -10, -10, -10, -10, -10},
            { 10, 20, 10, 0, 0, 10, 20, 10},

    };

    private static final int[][] RegeEnd = {
            { -50, -30, -20, -20, -20, -20, -30, -50 },
            { -30, -10,  10,  10,  10,  10, -10, -30 },
            { -20,  10,  20,  25,  25,  20,  10, -20 },
            { -20,  10,  25,  30,  30,  25,  10, -20 },
            { -20,  10,  25,  30,  30,  25,  10, -20 },
            { -20,  10,  20,  25,  25,  20,  10, -20 },
            { -30, -10,  10,  10,  10,  10, -10, -30 },
            { -50, -30, -20, -20, -20, -20, -30, -50 },
    };

    public int valoare (Piesa piesa) {

        switch ( piesa.getTip() )
        {
            case "Pion": return PION;
            case "Cal": return CAL;
            case "Nebun": return NEBUN;
            case "Tura": return TURA;
            case "Regina": return REGINA;

        }

        return REGE;
    }

    public boolean esteEndGame ( Piesa[][] tabla )
    {
        int nrAlb = 0, nrNegru = 0;

        for ( int i = 0 ; i < 8 ; i ++ )
            for ( int j = 0 ; j < 8 ; j ++ )
                if ( tabla[i][j] != null && !tabla[i][j].getTip().equals("Rege") && !tabla[i][j].getTip().equals("Pion") )
                {
                    if ( tabla[i][j].getCuloare().equals("Alb") )
                        nrAlb ++;
                    else
                        nrNegru ++;
                }

        return nrAlb <= 3 || nrNegru <= 3;

    }

    public int bonusPozitional ( Piesa piesa, boolean endgame )
    {
        int rand = piesa.getRand();
        int col = piesa.getColoana();

        if ( piesa.getCuloare().equals("Negru") )
            rand = 7 - rand;

        switch ( piesa.getTip() )
        {
            case "Pion": return PionMatrice[rand][col];
            case "Cal": return CalMatrice[rand][col];
            case "Nebun": return NebunMatrice[rand][col];
            case "Tura": return TuraMatrice[rand][col];
            case "Regina": return ReginaMatrice[rand][col];
            case "Rege": return endgame ? RegeEnd[rand][col] : RegeMid[rand][col];
        }

        return 0;
    }

    public int evalueaza ( Piesa[][] tabla )
    {
        int scor = 0;
        boolean endgame = esteEndGame(tabla);

        for ( int i = 0 ; i < 8 ; i ++ )
            for ( int j = 0 ; j < 8 ; j ++ )
                if ( tabla[i][j] != null )
                {
                    if ( tabla[i][j].getCuloare().equals("Alb") )
                        scor += bonusPozitional( tabla[i][j], endgame ) + valoare(tabla[i][j]);
                    else
                        scor -= bonusPozitional( tabla[i][j], endgame ) + valoare(tabla[i][j]);
                }

        return scor;
    }


}
