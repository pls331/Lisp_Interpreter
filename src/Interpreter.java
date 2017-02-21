import exception.UndefinedBehaviorException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;


public class Interpreter {
	private LexicalScanner scanner;
	private Parser parser;
	
	private Interpreter(InputStream in){
		// Initialize Scanner
		try {
			scanner = new LexicalScanner(in);
			scanner.init(); // get the first token in this.curToken
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.print("Failure during Scanner Initialization.");
			e.printStackTrace();
		}
		// Initialize Parser
		parser = new Parser(scanner);
	}

	public static void main(String[] args) throws IOException {
		//region create STDIN from file
              InputStream is = null;
              is = new FileInputStream(new File("EvaluateTest.txt"));
              System.setIn(is);
		//endregion

		//region read stdin & print test file
//              int cInt = System.in.read();
//              while (cInt != -1){
//                      System.out.println( (char) cInt );
//                      cInt = System.in.read();
//              }
		//endregion
		Interpreter interpreter = new Interpreter(System.in);

		System.out.println("#### Project2: Parser PrettyPrint ####");
		interpreter.parse( false ); // Project 2 output
		interpreter.eval(true);
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

	public void eval(boolean isPrint){
        try {
            this.parser.eval(isPrint);
         } catch (ClassNotFoundException | NoSuchMethodException  |
                    IllegalAccessException  |InvocationTargetException e)
        {
            e.printStackTrace();
        }catch (UndefinedBehaviorException udbe){
            System.out.println(String.format("ERROR: %s", udbe.getMessage()));
        }catch (NullPointerException npe){
			System.out.println(String.format("ERROR: %s", npe.getMessage()));
		}catch(Exception e){
        	e.printStackTrace();
		}
		finally {
            System.exit(-2);
        }

    }
	
}
