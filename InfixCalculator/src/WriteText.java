import java.io.File;
import java.util.*;

public class WriteText {
	
	private Formatter y;
	
	public void writeFile(String[] a, File outputFile) {
		try {
			y = new Formatter(outputFile);
		}
		catch(Exception e) {
			System.out.println("Error");
		}

		for (int i = 0; i < a.length - 1; i++) { 
			y.format("%s\n", a[i]);
        }
		y.format("%s", a[a.length - 1]);
		
		y.close();
	}
}
