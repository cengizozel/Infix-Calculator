import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class InfixCalculator {
	public static void main(String[] args) throws FileNotFoundException {
		
		// Run this at the very beginning in order to direct all the console output into a text file.
		// As a result, there is not console output.
		PrintStream out = new PrintStream(new FileOutputStream("OUTPUT.txt"));
		System.setOut(out);
		
		System.out.println("Name: Cengiz Ozel            |");
		System.out.println("Netid: cozel                 |");
		System.out.println("Email: cozel@u.rochester.edu |");
		System.out.println("Assignment: Project 1        |");
		System.out.println("_____________________________|\n\n");
		
		ReadText r = new ReadText();
		Algorithm s = new Algorithm();
		
		// First argument, refers to the name of text file with all the infix expressions.
		String input = null;
		
		// Second argument, refers to the name of text file where all the answers will be saved.
		String output = null;
		
		// The next two refers to the actual text file and not only the name.
		File inputFile = null;
		File outputFile = null;
		
		// First argument must be provided...
		try {
			input = args[0];
		}
		catch (Exception e) {
			System.out.println("You must name the input file as a commandline argument.");
		}
		
		
		// However, if the user does not enter a preferred output file, then all the answers will be saved in a default text file.
		// Thus, one program argument is sufficient to run the program though a second argument lets the user choose/create the output file.
		try {
			output = args[1];
		}
		catch (Exception e) {
			System.out.println("You can name the output file as a commandline argument.");
			System.out.println("It will be \"my_eval.txt\" by default.\n");
			outputFile = new File("my_eval.txt");
		}
		
		inputFile = new File(input);
		
		try {
			outputFile = new File(output);
			if (outputFile.createNewFile()) {
				System.out.println("File created: " + outputFile.getName());
			}
			else {
				//System.out.println("File already exists.");
			}
		}
		catch (Exception e) {
		}

		
		// Reads and stores the input file first, so it can be sent to the class where the algorithm happens.
		r.readFile(inputFile);
		String[] inputArray = r.expressions;
		s.shuntingYard(inputArray, outputFile);
		
	}
}
