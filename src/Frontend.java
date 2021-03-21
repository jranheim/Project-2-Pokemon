import java.io.File;
import java.util.Scanner;
public class Frontend {
    private Scanner sc;
    public static void main(String[] args) {

    }

    /**
     *
     * @param s the file pathname of the csv of pokemon
     */
    private void setEnv(String s)  {
        sc = new Scanner();
        System.out.println("Welcome, trainer! Press 'p' to see the list of pokemon for your party!");
        //TODO catch for p
        System.out.println("Here are the avaialble pokemon for your party! Your part of 6 must not contain " +
                "more than 1 pokemon within the same 10 levels!");
    }
}
