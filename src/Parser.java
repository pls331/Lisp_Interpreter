import functions.BuiltInFunctions;
import util.TreeNode;

import java.util.ArrayList;

/**
 * 
 */

/**
 * @author lenovo1
 *
 */
public class Parser implements BuiltInFunctions{

	public static final String CLOSING_PARENTHESES = ")";
	public static final String OPEN_PARENTHESES = "(";
	public static final String EOF = "EOF";
	/**
	 * Context-free Gramma:
	 * <Start> ::= <Expr> <Start> | <Expr> eof
	 * <Expr> ::= atom | ( <List> )
	 * <List> ::= <Expr> <List> | ε
	 */
	private LexicalScanner scn;
	private ArrayList<TreeNode> rootList;
	
	public Parser(LexicalScanner scn) {
		// Initialize parser
		this.scn = scn;
		this.rootList = new ArrayList<>(); // root of parse tree
	}
	
	private static boolean isValidToken(String token){
		if (token.equals("")) return true;
		return isAtom(token) || token.equals(OPEN_PARENTHESES) || token.equals(CLOSING_PARENTHESES);
	}

	private static boolean isAtom(String token){
		if (token.equals("")) return true;
		for (char c: token.toCharArray())
			// only Number and Uppercase letter is allowed
			if ( !( (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9') ) )  
				return false;
		// valid
		return true;
	}

	private String getCurrentToken(){
		return scn.getCurrentToken();
	}
	
	private void moveToNextToken(){
		scn.moveToNextToken();
	}
	
	/*
	 * Parse <Start>
	 */
	
	public void parseStart(boolean isPrint){
		if (getCurrentToken().equals(EOF)) 
			return;
		
		StringBuilder builder = new StringBuilder();
		TreeNode root = null;
		
		do{
			root = parseExpression(); // parse Top level expression
			if (isPrint) builder.append(prettyPrint(root)); 
			this.rootList.add(root);
		}while(!scn.getCurrentToken().equals(EOF));

		if (! this.scn.checker.isWholeStreamValid()){
			System.out.println();
			System.out.printf("Error: Invalid Parenthesis. @[parseStart] INFO: %s\n", this.scn.checker.printCheckerStatus());
			System.exit(-1);
		}
		System.out.print(builder.toString());
	}
	
	private TreeNode parseExpression(){
		TreeNode node = new TreeNode(); // node for current terminal/non-terminal
		String curToken = getCurrentToken();
		
		// <Expr> ::= (<List>)
		if (curToken.equals(OPEN_PARENTHESES)){
			moveToNextToken(); // consume OPEN_PARENTHESES
			TreeNode pointer = node;
			while (! getCurrentToken().equals(CLOSING_PARENTHESES)) {
				if (getCurrentToken().equals(EOF)) {
					System.out.printf("Error: Invalid Parenthesis. @[parseExpression] INFO: %s\n", 
							this.scn.checker.printCheckerStatus());
					System.exit(-1);
				}
				
				pointer.setLeft(parseExpression());
//				if (getCurrentToken().equals(CLOSING_PARENTHESES)) break; // only 1 expression in (<List>)
				pointer.setRight(new TreeNode());
				pointer = pointer.getRight();
			}
			moveToNextToken(); // consume CLOSING_PARENTHESES
		}
		// <Expr> ::= Atom
		else if(isAtom(curToken)){
			node.setLexicalVal(curToken);
			moveToNextToken(); // consume atom
		}
		// Other -> Error
		else{
			System.out.println("Error:  Not Atom, Not Open Parenthesis@[parseExpression].");
			System.exit(-1);
		}
		return node;
	}
	
	public String prettyPrint(TreeNode node){
		StringBuilder builder = new StringBuilder();
		prettyPrintHelper(node, builder);
		builder.append("\n");
		return builder.toString();
	}
	
	private void prettyPrintHelper(TreeNode node, StringBuilder builder){
		// empty tree
		if (node == null) return;
		
		// leaf node
		if (node.getLeft() == null && node.getRight() == null) {
			builder.append(node.getLexicalVal());
			return;
		}
		// print inner node
		builder.append("(");
		prettyPrintHelper(node.getLeft(), builder);
		builder.append(".");
		prettyPrintHelper(node.getRight(), builder);
		builder.append(")");		
	}

}
