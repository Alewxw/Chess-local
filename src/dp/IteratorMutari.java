package dp;

import moves.Mutare;
import java.util.*;

public class IteratorMutari implements Iterator<Mutare>{
    private List<Mutare> mutari;
    private int index;

    public IteratorMutari(List<Mutare> mutari)
    {
        this.mutari = mutari;
    }

    @Override
    public boolean hasNext()
    {
        return index < mutari.size();
    }

    @Override
    public Mutare next()
    {
        return mutari.get(index++);
    }

    @Override
    public void remove()
    {

    }

}
