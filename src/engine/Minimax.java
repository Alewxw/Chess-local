package engine;

import game.Joc;
import game.Tabla;
import moves.Mutare;
import piese.Piesa;

import java.util.*;

public class Minimax {
        Evaluator evaluator;
        int depth;

        public Minimax(Evaluator evaluator, int depth) {
            this.evaluator = evaluator;
            this.depth = depth;
        }

    public int getBestScore(Piesa[][] tabla) {
        int adancime = depth;
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;

        boolean esteMaximizator = true;

        return minimax(tabla, adancime, alpha, beta, esteMaximizator);
    }

    private int minimax(Piesa[][] tabla, int adancime, int alpha, int beta, boolean esteMaximizator) {
        if (adancime == 0) return evaluator.evalueaza(tabla);

        if (esteMaximizator) {
            int maxEval = Integer.MIN_VALUE;
            for (int[] mutare : getMutariValide(tabla, "Alb")) {
                Piesa piesaMutata = tabla[mutare[0]][mutare[1]];
                Piesa piesaCaptata = tabla[mutare[2]][mutare[3]];

                tabla[mutare[2]][mutare[3]] = piesaMutata;
                tabla[mutare[0]][mutare[1]] = null;

                int eval = minimax(tabla, adancime - 1, alpha, beta, false);
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);

                tabla[mutare[0]][mutare[1]] = piesaMutata;
                tabla[mutare[2]][mutare[3]] = piesaCaptata;

                if (beta <= alpha) break;
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (int[] mutare : getMutariValide(tabla, "Negru")) {
                Piesa piesaMutata = tabla[mutare[0]][mutare[1]];
                Piesa piesaCaptata = tabla[mutare[2]][mutare[3]];

                tabla[mutare[2]][mutare[3]] = piesaMutata;
                tabla[mutare[0]][mutare[1]] = null;

                int eval = minimax(tabla, adancime - 1, alpha, beta, true);
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);

                tabla[mutare[0]][mutare[1]] = piesaMutata;
                tabla[mutare[2]][mutare[3]] = piesaCaptata;

                if (beta <= alpha) break;
            }
            return minEval;
        }
    }

    public List<int[]> getMutariValide(Piesa[][] tabla, String culoare) {
        List<int[]> mutari = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (tabla[i][j] != null && tabla[i][j].getCuloare().equals(culoare)) {
                    List<int[]> mutariPiesa = tabla[i][j].getMutariValid(tabla);
                    for (int[] mutare : mutariPiesa) {
                        mutari.add(new int[]{i, j, mutare[0], mutare[1]});
                    }
                }
            }
        }
        return mutari;
    }

    public List<Integer> calculeazaScoruriIstoric(Joc joc) {

        Tabla tabelaNoua = new Tabla();
        Joc jocTemp = new Joc(tabelaNoua, joc.getStrategie(), joc.getJucator1(), joc.getJucator2());
        jocTemp.initTabla();

        List<Integer> scoruri = new ArrayList<>();
        List<Mutare> mutari = joc.getTabla().getIstoricMutari();

        for (Mutare mutare : mutari) {
            aplicaMutare(jocTemp.getTabla().getTabla(), mutare);
            scoruri.add(getBestScore(jocTemp.getTabla().getTabla()));
        }

        return scoruri;
    }

    private void aplicaMutare(Piesa[][] tabla, Mutare mutare) {
        Piesa piesa = tabla[mutare.getRandStart()][mutare.getColStart()];
        tabla[mutare.getRandFinal()][mutare.getColFinal()] = piesa;
        tabla[mutare.getRandStart()][mutare.getColStart()] = null;
    }

    private Piesa[][] copiazaTabla(Piesa[][] original) {
        Piesa[][] copie = new Piesa[8][8];
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                copie[i][j] = original[i][j];
        return copie;
    }
}
