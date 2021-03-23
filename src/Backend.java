// --== CS400 File Header Information ==--
// Name: Jake Schroeder
// Email: schroeder22@wisc.edu
// Team: KG Blue
// Role: Backend Developer
// TA: Keren Chen
// Lecturer: Florian Heimerl
// Notes to Grader:

import java.util.List;
import java.util.ArrayList;
import java.io.StringReader;
import java.io.Reader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.nio.charset.StandardCharsets;
import java.io.File;
import java.util.Scanner;


public class Backend implements BackendInterface {

	private List<String> types = new ArrayList<String>(); // types chosen to filter by
	
	private List<String> allTypes = new ArrayList<String>(); // all types available to filter by
	
	private List<Pokemon> currTeam = new ArrayList<Pokemon>(); // current team
	
	private RedBlackTree<Pokemon> allPokemon = new RedBlackTree<Pokemon>(); // tree of all pokemon from the provided file
	
	/**
	 * constructor to get list of Pokemon from CSV file passed in by user.
	 * 
	 * @param pokeFilePath - file path to make string reader to pass to data 
	 * wrangler to get list of available Pokemon.
	 */
	public Backend(String pokeFilePath) throws FileNotFoundException, IOException {
		
		String data = "";
		
		try {
			File file = new File(pokeFilePath);
			Scanner myReader = new Scanner(file);
			
			while (myReader.hasNextLine()) {
				data += myReader.nextLine() + "\n";
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		
		List<Pokemon> newPokemon = new ArrayList<Pokemon>();
		
		String[] test = data.split("\n");
		for (String s : test) {
			String[] array = s.split(",");
			Pokemon P = new Pokemon(array[0], Integer.parseInt(array[1]), array[2]);
			newPokemon.add(P);
		}
		
		for (Pokemon pokemon : newPokemon) {
			if (!allTypes.contains(pokemon.type)) {
				allTypes.add(pokemon.type); // add pokemon's type if it isn't already added
			}
			allPokemon.insert(pokemon); // insert into red black tree
		}
		
	}
	
	/*
	 * Adds a type filter provided by user to the types list,
	 * so availablePokemon shows only pokemon of that type.
	 * 
	 * @param type - name of type user wants to filter by
	 */
	@Override
	public void addType(String type) {
		types.add(type);
		
	}
	
	/*
	 * Removes the type filter provided by user from types list 
	 * so filteredPokemon is no longer filtered by that type.
	 * 
	 * @param type - name of type user no longer wants to filter by
	 */
	@Override
	public void removeType(String type) {
		if (types.indexOf(type) < 0) {
			System.out.println("Type not found.");
		} else {
			int index = types.indexOf(type);
			types.remove(index);
		}
	}
	
	/*
	 * Returns the current type(s) being used to filter 
	 * availablePokemon.
	 * 
	 * @return List of types that filteredPokemon is being filtered by
	 */
	@Override
	public List<String> currTypes() {
		return types;
	}

	/*
	 * Gets and returns String list of types available to filter by
	 * 
	 * @return String list of all available types to filter by
	 */
	@Override
	public List<String> getAllTypes() {
		return allTypes;
	}

	/*
	 * Gets and returns list of available Pokemon objects.
	 * If no type has been provided, then all pokemon are available.
	 * If type has been provided, available pokemon will be filtered 
	 * to show only pokemon of that type.
	 * 
	 * @return List of filtered pokemon
	 */
	@Override
	public RedBlackTree<Pokemon> getPokemon() {
		// if no types are chosen to filter by, return all pokemon
		if (types.size() == 0) {
			return allPokemon;
		} 
		
		RedBlackTree<Pokemon> filteredPokemon = new RedBlackTree<Pokemon>();
		
		// if types has elements, iterate through allPokemon and add pokemon of the
		// filtered type to availablePokemon, then return availablePokemon.
		for (Pokemon pokemon : allPokemon) {
			if (types.contains(pokemon.type)) {
				filteredPokemon.insert(pokemon);
			}
		}		
		return filteredPokemon;
	}
	
	/*
	 * Adds given Pokemon object to the current team. 
	 * If pokemon is already in the current team, message
	 * is displayed saying so, and object is not added again.
	 * If the max team size of 6 is exceeded, Pokemon is not
	 * added and a message is displayed.
	 * 
	 * @param pokemon - Pokemon object to be added
	 */
	@Override
	public void addPokemon(Pokemon pokemon) {
		if (teamSize() > 5) {
			System.out.println("Team is already full! You must remove a "
					+ "Pokemon before adding another.");
			return;
		}
		
		if (currTeam.indexOf(pokemon) < 0) {
			currTeam.add(pokemon);
		} else {
			System.out.println("Pokemon already in current team.");
		}
	}
	
	/*
	 * Removes given Pokemon object from the current team.
	 * If Pokemon object is not found in current team, message is 
	 * displayed.
	 * 
	 * @param pokemon - pokemon object to be removed
	 */
	@Override
	public void removePokemon(Pokemon pokemon) {
		if (currTeam.indexOf(pokemon) < 0) {
			System.out.println("Pokemon not in current team.");
		} else {
			int index = currTeam.indexOf(pokemon);
			currTeam.remove(index);
		}		
	}

	/*
	 * get and return list of Pokemon objects that are in
	 * the current team.
	 * 
	 * @return list of pokemon in team
	 */
	@Override
	public List<Pokemon> getTeam() {
		return currTeam;
	}

	/*
	 * returns number of pokemon in current team
	 * 
	 * @return number of pokemon in current team
	 */
	@Override
	public int teamSize() {
		return currTeam.size();
	}
	
}
