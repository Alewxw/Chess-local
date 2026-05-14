package game;

import moves.Mutare;
import piese.Piesa;

import java.util.*;

public class Tabla {
    private Piesa[][] tabla;
    private List<Mutare> istoricMutari;

    public Tabla()
    {
        this.tabla = new Piesa[8][8];
        this.istoricMutari = new ArrayList<>();
    }

    public Tabla (  Piesa[][] tabla, List<Mutare> istoricMutari )
    {
        this.tabla = tabla;
        this.istoricMutari = istoricMutari;
    }

    public void adaugaMutare ( Mutare mutare )
    {
        istoricMutari.add(mutare);
    }

    public Piesa getPiesa ( int rand, int coloana )
    {
        return tabla[rand][coloana];
    }

    public void setPiesa ( int rand, int coloana, Piesa piesa )
    {
        tabla[rand][coloana] = piesa;
    }

    public Piesa[][] getTabla() {
        return tabla;
    }

    public void setTabla(Piesa[][] tabla) {
        this.tabla = tabla;
    }

    public List<Mutare> getIstoricMutari() {
        return istoricMutari;
    }

    public void setIstoricMutari(List<Mutare> istoricMutari) {
        this.istoricMutari = istoricMutari;
    }
}
