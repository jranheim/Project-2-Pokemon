// --==  CS400 Pokemon  ==--
// Name: Jacob Ranheim
// Email: jranheim@wisc.edu
// Team: KG blue
// Role: Data Wrangler
// TA: Karen Chen
// Lecturer: Gary Dahl
// Notes to Grader:

public class Pokemon implements Comparable<Pokemon> {
    private Pokemon left;
    private Pokemon right;
    private Pokemon parent;
    private String name;
    public int lvl;
    public String type;

    /**
     * Contructor to set name level and type of Pokemon
     * @param name - name of pokemon
     * @param lvl - level of pokemon
     * @param type - type of pokemon
     */
    Pokemon(String name, int lvl, String type)  {
        this.lvl = lvl;
        this.name = name;
	this.type = type;
    }

    /**
     * Pokemon to the left in tree
     */
    public void left(Pokemon l) throws NullPointerException {
        if(l != null) this.left = l;
        else throw new NullPointerException("Left node is null!");
    }

    /**
     * Pokemon to the right in tree
     */
    public void right(Pokemon r) throws NullPointerException {
        if(r != null) this.right = r;
        else throw new NullPointerException("Right node is null!");
    }

    /**
     * parent of the pokemon in tree
     */
    public void parent(Pokemon p) throws NullPointerException {
        if(p != null) this.parent = p;
        else throw new NullPointerException("Parent node is null!");
    }

    /**
     * gets the level of the pokemon called on
     * @return level of pokemon 
     */
    public int getLvl()  { return this.lvl; }

    /**
     * gets the name of the pokemon called on
     * @return name of pokemon 
     */
    public String getName() { return this.name; }

    /**
     * gets the type of the pokemon called on
     * @return type of pokemon
     */
    public String getType() { return this.type; }

    /**
     * compare pokemon to this pokemon and return value associated with the level
     * comparison
     *
     * @param o - pokemon to compare to 
     * @return 1 if greater than param, 0 if equals, and -1 if smaller
     */
    public int compareTo(Pokemon o) throws NullPointerException, ClassCastException {
        if(o == null)  { throw new NullPointerException("Parameter is null!"); }
        if(!(o instanceof Pokemon))  { throw new ClassCastException("Object is not a Pokemon!"); }
        if(this.lvl > o.lvl)  return 1;
        if(this.lvl == o.lvl) return 0;
        return -1;
    }
}
