import util.TreeNode;
import util.TreeUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class Interpreter {
	private LexicalScanner scanner;
	private Parser parser;
	
	private Interpreter(InputStream in){
		// Initialize Scanner
		try {
			scanner = new LexicalScanner(in);
			scanner.init(); // get the first token in this.curToken
		} catch (IOException e) {
			System.out.print("Failure during Scanner Initialization.");
			e.printStackTrace();
		}
		// Initialize Parser
		parser = new Parser(scanner);
	}

	public static void main(String[] args) throws IOException {
		Interpreter interpreter = new Interpreter(System.in);

//		System.out.println("#### Project2: Parser PrettyPrint ####");
		interpreter.parse( false ); // Project 2 output
		interpreter.eval(true, false);
//		System.out.println("\n#### Project1: Input Stream Statistics ####");
//		System.out.println(interpreter.getScannerOutput());  // Output Parsing result as Project1 Required
//		System.out.println("__Interpreter Finished Excecution.__");
		System.exit(0);
	}
	
	/**
	 * Print the result as the Proj#1 required.
	 */
	public String getScannerOutput(){
		return this.scanner.getScannerOutput();
	}
	
	/*
	 * parse and pretty print at the same time.
	 */
	
	/**
	 * Parse and get all tokens from the input stream.
	 * Get Prepared for Proj#1 output.
	 */
	public void parseStdinForProj1(){
		this.scanner.parseStdinForProj1();
	}

	public void parse(boolean isPrint){
        this.parser.parseStart(isPrint);
	}

	public void eval(boolean isPrint, boolean isTesting){
            this.parser.eval(isPrint, isTesting);
    }

    public ArrayList<String> getResult(){
		ArrayList<String> resList = new ArrayList<>();
		for(TreeNode t : this.parser.resultList){
			resList.add(TreeUtil.getListNotation(t));
		}
		return resList;
	}

	public ArrayList<String> getExceptionMsgList(){
		return (ArrayList<String>)this.parser.exMsgList.clone();
	}

}
