import java.io.File;
import java.util.*;

public class ReadText {
	
	private Scanner lines;
	private Scanner read;
	public int i = 0;
	public String[] expressions;
	
	public void readFile(File inputFile) {
		try {
			read = new Scanner(inputFile);
			lines = new Scanner(inputFile);
		}
		catch (Exception e) {
			System.out.println("ok");
		}
		
		while(lines.hasNextLine()) {
			lines.nextLine();
			i++;
		}
		
		expressions = new String[i];
		i = 0;
		
		while(read.hasNextLine()) {
			expressions[i] = read.nextLine();
			i++;
		}
		
		read.close();
	}
}
