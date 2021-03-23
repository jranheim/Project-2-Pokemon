// --==  CS400 Pokemon  ==--
// Name: Jacob Ranheim
// Email: jranheim@wisc.edu
// Team: KG blue
// Role: Data Wrangler
// TA: Karen Chen
// Lecturer: Gary Dahl
// Notes to Grader:

public interface PokemonInterface extends Comparable<Pokemon> {

        public String getname();
        public Integer getLevel();
	public String getType();

        // from super interface Comparable
        public int compareTo(MovieInterface otherMovie);

}

