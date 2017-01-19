

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


public class Interpreter {
	private LexicalScanner scanner;
	
	public Interpreter(){
		// Initialize Scanner
		try {
			scanner = new LexicalScanner();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.print("Failure during Scanner Initialization.");
			e.printStackTrace();
		}
	}


	public static void main(String[] args) throws IOException {
		// create STDIN from file
		InputStream is = null;
		is = new FileInputStream(new File("ScannerInput.txt"));
		System.setIn(is);
		
//		// read stdin & print test file
//		int cInt = System.in.read();
//		while (cInt != -1){
//			System.out.println( (char) cInt );
//			cInt = System.in.read();
//		}
		Interpreter interpreter = new Interpreter();
		
		// Parse the STDIN stream
		interpreter.parseFromSTDIN();
		// Output Parsing result as Project1 Required
		System.out.println(interpreter.getScannerOutput());
		
//		System.out.println("__Interpreter Finished Excecution.__");		
	}
	
	public String getScannerOutput(){
		/**
		 * Print the result as the Proj#1 required.
		 */
		return this.scanner.getScannerOutput();
	}
	
	public void parseFromSTDIN(){
		/**
		 * Parse and get all tokens from the input stream. 
		 * Get Prepared for Proj#1 output.
		 */
		this.scanner.parseFromSTDIN();
	}

}
