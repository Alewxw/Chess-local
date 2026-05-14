package interfata;

import piese.Piesa;
import java.util.*;

public interface Miscabil {
    List<int[]> getMutariValid(Piesa[][] tabla);
    boolean esteValidaMutarea(int rand, int coloana);
}
