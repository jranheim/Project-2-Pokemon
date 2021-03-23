// --==  CS400 PokemonReaderInterface  ==--
// Name: Jacob Ranheim
// Email: jranheim@wisc.edu
// Team: KG blue
// Role: Data Wrangler
// TA: Karen Chen
// Lecturer: Gary Dahl
// Notes to Grader:

import java.util.List;
import java.util.zip.DataFormatException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.BufferedReader;

public interface PokemonReaderInterface {

        public List<Pokemon> readDataSet(BufferedReader inputFileReader) throws IOException, DataFormatException;

}
