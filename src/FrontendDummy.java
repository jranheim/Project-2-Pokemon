import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

/*
 * Dummy frontend to test Backend
 */
public class FrontendDummy {

	public static void main (String[] args) throws IOException {
		boolean quit = false;
		Backend backend = new Backend("Pokemon.csv");
		while(!quit) {
			System.out.println("~ ~ ~ ~ ~ ~ ~ ! Pokemon Sort ! ~ ~ ~ ~ ~ ~ ~");
			System.out.println("Create your very own team of 6 unique Pokemon!");
			System.out.println("Enter the number of a type to filter by, or press");
			System.out.println("ENTER to see an unfiltered list of Pokemon sorted by level!");
			System.out.println("To quit, input q. To remove a pokemon from your team, input r.");
			System.out.println("YOUR TEAM: " + backend.getTeam());
			int i = 1;
			for (String type : backend.getAllTypes()) {
				System.out.print(i + ")" + type + " ");
				i++;
			}
			System.out.print("\nInput: ");
			Scanner scnr = new Scanner(System.in);
			String input = scnr.nextLine();
			if (input.equals("")) {
				printPokemon(backend, scnr);
			} else if (input.equals("q")) {
				quit = true;
			} else if (input.equals("r")) {
				System.out.println("Which Pokemon would you like to remove? Press ENTER to go back.");
				int l = 1;
				for (Pokemon pokemon : backend.getTeam()) {
					System.out.println(l + ")" + pokemon.toString() + " ");
					l++;
				}
				System.out.print("Input: ");
				String choice = scnr.nextLine();
				if (choice.equals("")) {
					continue;
				} else {
					backend.removePokemon(backend.getTeam().get(Integer.parseInt(choice) - 1));
				}
			} else {
				backend.addType(backend.getAllTypes().get(Integer.parseInt(input) - 1));
				printPokemon(backend, scnr);
				backend.removeType(backend.getAllTypes().get(Integer.parseInt(input) - 1));

			}
		}
		System.out.println("Session Ended.");
		
	}
	
	private static void printPokemon(Backend backend, Scanner scnr) {
		int f = 1;
		System.out.println("To add a pokemon to your team, enter the number next to their name.");
		System.out.println("To return back to the main menu, press ENTER");
		for (Pokemon pokemon : backend.getPokemon()) {
			System.out.println(f + ")" + pokemon.toString() + " ");
			f++;
		}
		System.out.print("\nInput: ");
		String input = scnr.nextLine();
		int i = 1;
		for (Pokemon pokemon : backend.getPokemon()) {
			if (Integer.parseInt(input) == i) {
				backend.addPokemon(pokemon);
				return;
			}
			i++;
		}
		if (input.equals("")) {
			return;
		} else {
			
		}
	}

}
