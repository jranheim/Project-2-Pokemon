// --==  CS400 PokemonDataReader  ==--
// Name: Jacob Ranheim
// Email: jranheim@wisc.edu
// Team: KG blue
// Role: Data Wrangler
// TA: Karen Chen
// Lecturer: Gary Dahl
// Notes to Grader:

import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;

public class PokemonDataReader implements PokemonReaderInterface{
        
    @Override
    public List<Pokemon> readDataSet(BufferedReader inputFileReader) throws DataFormatException, IOException {
	//list to hold created pokemon        	
	List<Pokemon> list = new ArrayList<Pokemon>();

	try{
	    String line = inputFileReader.readLine();
	    //continue if the next line is not null
	    while(line != null){
		//split each line by ',' to split name, level, type
		String[] pok = line.split(",");
		//if there are more than 3 pokemon attributes per line, throw error
		if(pok.length > 3){
		       throw new DataFormatException();
		}	       
		String name = pok[0];
		int level = Integer.parseInt(pok[1]);
		String type = pok[2];
		    
		Pokemon p = new Pokemon(name, level, type);
		list.add(p);
		line = inputFileReader.readLine();
	    }
	}
	catch(DataFormatException e){
	    System.out.println("Data in file not int correct format");
	}
	catch(IOException i){
	    i.printStackTrace();
	}
	return list;
    }
}

