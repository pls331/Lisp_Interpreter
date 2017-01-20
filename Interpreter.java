

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


public class Interpreter {
	private LexicalScanner scanner;
	private Parser parser;
	
	public Interpreter(){
		// Initialize Scanner
		try {
			scanner = new LexicalScanner();
			scanner.init(); // get the first token in this.curToken
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.print("Failure during Scanner Initialization.");
			e.printStackTrace();
		}
		// Initialize Parser
		parser = new Parser(scanner);
	}

	/**
	 * Print the result as the Proj#1 required.
	 */
	public String getScannerOutput(){
		return this.scanner.getScannerOutput();
	}
	
	/**
	 * Parse and get all tokens from the input stream. 
	 * Get Prepared for Proj#1 output.
	 */
	public void parseStdinForProj1(){
		this.scanner.parseStdinForProj1();
	}
	
	/*
	 * parse and pretty print at the same time.
	 */
	
	public void parse(boolean isPrint){
		this.parser.parseStart(isPrint);
	}
	
	
	
	public static void main(String[] args) throws IOException {
		// create STDIN from file
		InputStream is = null;
		is = new FileInputStream(new File("ParserTest.txt"));
		System.setIn(is);
		
//		// read stdin & print test file
//		int cInt = System.in.read();
//		while (cInt != -1){
//			System.out.println( (char) cInt );
//			cInt = System.in.read();
//		}
		Interpreter interpreter = new Interpreter();
		
		System.out.println("#### Project2: Parser PrettyPrint ####");
		interpreter.parse( true ); // Project 2 output

		System.out.println("\n#### Project1: Input Stream Statistics ####");
		System.out.println(interpreter.getScannerOutput());  // Output Parsing result as Project1 Required
//		System.out.println("__Interpreter Finished Excecution.__");	
	}
	
}
