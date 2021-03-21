
public class PokemonDummy implements Comparable<PokemonDummy> {
    private PokemonDummy left;
    private PokemonDummy right;
    private PokemonDummy parent;
    private String name;
    public int lvl;

    PokemonDummy(int lvl, String name)  {
        this.lvl = lvl;
        this.name = name;
    }

    public void left(PokemonDummy l) throws NullPointerException {
        if(l != null) this.left = l;
        else throw new NullPointerException("Left node is null!");
    }

    public void right(PokemonDummy r) throws NullPointerException {
        if(r != null) this.right = r;
        else throw new NullPointerException("Right node is null!");
    }

    public void parent(PokemonDummy p) throws NullPointerException {
        if(p != null) this.parent = p;
        else throw new NullPointerException("Parent node is null!");
    }

    public int getLvl()  { return this.lvl; }

    public String getName() { return this.name; }


    public int compareTo(PokemonDummy o) throws NullPointerException, ClassCastException {
        if(o == null)  { throw new NullPointerException("Parameter is null!"); }
        if(!(o instanceof PokemonDummy))  { throw new ClassCastException("Object is not a Pokemon!"); }
        if(this.lvl > o.lvl)  return 1;
        if(this.lvl == o.lvl) return 0;
        return -1;
    }
}
