/**
 * 
 */


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.attribute.AclEntry.Builder;
import java.util.ArrayList;

/**
 * @author lenovo1
 * 
 */
public class LexicalScanner {

	/**
	 * @param args
	 */
	// STDIN reader
	private Reader in;
	// Parentheses Counter
	private int open_p_cnt;
	private int closing_p_cnt;
	// Atom List : Numeric and Literal
	private int numeric_sum;
	ArrayList<String> literalAtomSeq;
	ArrayList<String> numericAtomSeq; 
	// Parentheses Checker
	ParenthesisChecker checker;
	private int charInt;
	// current token
	private String curToken;
	
	public LexicalScanner() throws IOException {
		this.in = new BufferedReader(new InputStreamReader(System.in)); // STDIN reader
		this.numeric_sum = 0;
		this.open_p_cnt = 0;
		this.closing_p_cnt = 0;
		this.charInt = this.in.read();
		this.checker = new ParenthesisChecker();
		this.literalAtomSeq = new ArrayList<String>(); // remember the ASCII sequences
		this.numericAtomSeq = new ArrayList<String>(); // remember the ASCII sequences
		this.curToken = null;
	}
	
	public void init(){
		
	}
	
	
	protected String getNextToken(){
		/**
		 * 	keep counters for how many literal atoms, numeric atoms, 
		 * 	open parentheses, and closing parentheses were found 
		 * 	in the input file. 
		 *  @return  
		 */
		StringBuilder builder = new StringBuilder();
		char c;
		boolean isTokenFind = false;
		
		while (this.charInt != -1){
//			System.out.printf("scn.charInt: {%c}\n", (char)this.charInt);
			c = (char) this.charInt;
			
			// Letter & Number
			if ((c >= 'A' && c <= 'Z') ||(c >= '0' && c <= '9')){
				builder.append(c);
			}
			// white space and cariage return and line feed
			else if (c == ' ' || c == '\n' || c == '\r'){
				if (builder.length() == 0) ; // go to next char
				else isTokenFind = true; // output the current token
			}
			// open parentheses and closing parentheses
			else if (c == '(' || c == ')'){
				if (builder.length() == 0) {
					builder.append(c);
					isTokenFind = true; // output the token
				}
				else break; // output the current token & keep this parentheses
			}
			// other character
			else { 
				System.out.printf("Illegal Character: '%c'\n", c);
				return null;
			}
			
			// Read next char
			try {
				this.charInt = this.in.read();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// if token have been found, break and output it
			if (isTokenFind == true) break;
		}
		
		// get token from string builder
		if (this.charInt == -1 && builder.length() == 0) return "EOF"; // no token available
		// get token
		String token = builder.toString();
		// check parenthesis validity of current token
		if (token.equals("(") || token.equals(")")){
			if (! this.checker.isParenthesesValid(token)) {
				System.out.println("Error, Parenthesis Does not match!");
				System.exit(-1);
			}
		}
		// count the token
		if ( token.equals("(") ) {
			this.open_p_cnt ++;
		}
		else if ( token.equals( ")" ) ) this.closing_p_cnt++;
		else if (this.isNumericAtom(token)) {
			this.numericAtomSeq.add(token);
			this.numeric_sum += Integer.parseInt(token);
		}
		else this.literalAtomSeq.add(token);
		// return token
		return token;
	}
	
	private boolean isNumericAtom(String token){
		// check is Numeric Atom
		if (token.length() == 0) return false;
		char c;
		for (int i = 0; i < token.length(); i++){
			c = token.charAt(i);
			if (! (c >= '0' && c <= '9')) return false; 
		}
		return true;
	}
	

	public static String getAtomsString(ArrayList<String> arrayList){
		StringBuilder builder = new StringBuilder();
		for (String token: arrayList){
			builder.append(", " + token);
		}
		return builder.toString();
	}
	
	public String getScannerOutput(){
		StringBuilder builder = new StringBuilder();
		builder.append(String.format("LITERAL ATOMS: %d%s\n", this.literalAtomSeq.size(), LexicalScanner.getAtomsString(this.literalAtomSeq)));
		builder.append(String.format( "NUMERIC ATOMS: %d, %d\n" , this.numericAtomSeq.size(), this.numeric_sum ));
		builder.append(String.format("OPEN PARENTHESES: %d\n", this.open_p_cnt));
		builder.append(String.format("CLOSING PARENTHESES: %d\n", this.closing_p_cnt));
		return builder.toString();
	}
	
	public void parseFromSTDIN(){
		/**
		 * Parse and get all tokens from the input stream. 
		 * Get Prepared for Proj#1 output.
		 */
		String token = this.getNextToken();
		while (!token.equals("EOF")){
			token = this.getNextToken();
		}
		// check parentheses of whole stream.
		if (! this.checker.isWholeStreamValid()) {
			System.out.println("Parentheses in the whole stream is not valid!");
			System.exit(-1);
		}
	}
	
	
	public static void main(String[] args) throws IOException {
		/**
		 *  Unit Test
		 */
		// create stdin from file
		InputStream is = null;
		is = new FileInputStream(new File("ScannerInput.txt"));
		System.setIn(is);
		
//		// read stdin & print test file
//		int cInt = System.in.read();
//		while (cInt != -1){
//			System.out.println( (char) cInt );
//			cInt = System.in.read();
//		}

		LexicalScanner scn = new LexicalScanner();
		
		// isNumericAtom
		boolean ret = scn.isNumericAtom("asdfasd");
		
		// isValidParentheses

		// getNextToken()
		System.out.println(scn.literalAtomSeq.toString());
		String token = scn.getNextToken();
		while (!token.equals("EOF")){
			System.out.printf("-----token: <%s>\n", token);
			token = scn.getNextToken();
		}
		
		System.out.println(scn.literalAtomSeq.toString());
		System.out.println(" --- Get All Tokens finished.  ---");
		System.out.printf("LITERAL ATOMS: %d%s\n", scn.literalAtomSeq.size(), LexicalScanner.getAtomsString(scn.literalAtomSeq));
		System.out.printf("NUMERIC ATOMS: %d, %d\n" , scn.numericAtomSeq.size(), scn.numeric_sum);
		System.out.printf("OPEN PARENTHESES: %d\n", scn.open_p_cnt);
		System.out.printf("CLOSING PARENTHESES: %d\n", scn.closing_p_cnt);
		System.out.println(" --- Test getNextToken() finished.  ---");
	
		// getScannerOutput
		System.out.println(scn.getScannerOutput());
		
		// parentheses check 1 - invalid
		StringBuilder builder = new StringBuilder();
		builder.append(scn.checker.isParenthesesValid(")"));
		builder.append(scn.checker.isParenthesesValid("("));
		builder.append(scn.checker.isParenthesesValid("("));
		System.out.println(builder.toString() + scn.checker.isWholeStreamValid());
		
		// parentheses check 1 - invalid
		builder = new StringBuilder();
		scn = new LexicalScanner();
		builder.append(scn.checker.isParenthesesValid("("));
		builder.append(scn.checker.isParenthesesValid(")"));
		builder.append(scn.checker.isParenthesesValid(")"));
		builder.append(scn.checker.isParenthesesValid(")"));
		System.out.println(builder.toString() + scn.checker.isWholeStreamValid());
		
		System.exit(0);
	}

	class ParenthesisChecker{
		/**
		 * Description: a class for checking whether the parenthesis is valid or not.
		 */
		ArrayList<String> stack = new ArrayList<>();
		private int open_cnt = 0;
		private boolean isValid = true;
		
		public boolean isParenthesesValid(String p){
			if (p.equals("(")) open_cnt ++ ;
			else if (p.equals(")")){
				if (open_cnt == 0) isValid = false;
				else open_cnt --;
			}
			else{ // illegal input
				System.out.println("Input should be parentheses!");
			}
			return isValid;
		};
		
		public boolean isWholeStreamValid(){
			if (open_cnt != 0 || isValid == false) return false;
			else return true;
		}
		
	}
	
}



