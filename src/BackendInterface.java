// --== CS400 File Header Information ==--
// Name: Jake Schroeder
// Email: schroeder22@wisc.edu
// Team: KG Blue
// Role: Backend Developer
// TA: Keren Chen
// Lecturer: Florian Heimerl
// Notes to Grader:

import java.util.List;

public interface BackendInterface {
	
	public void addType(String type); // adds type to filter pokemon by
	public void removeType(String type); // removes type to filter pokemon by
	
	public List<String> currTypes(); // lists current types pokemon are filtered by
	
	public List<String> getAllTypes(); // lists all types of pokemon available to filter by
	
	public RedBlackTree<Pokemon> getPokemon(); // returns tree of available pokemon, sorted by level
	
	public void addPokemon(Pokemon pokemon); // adds pokemon to team
	public void removePokemon(Pokemon pokemon); // removes pokemon from team
	
	public List<Pokemon> getTeam(); // returns list of pokemon currently in team
	
	public int teamSize(); // returns number of pokemon in current team
}
