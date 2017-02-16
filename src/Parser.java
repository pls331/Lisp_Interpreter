import util.Token;
import util.TokenType;
import util.TreeNode;

import java.util.ArrayList;

/**
 * 
 */

/**
 * @author lenovo1
 *
 */
//public class Parser implements BuiltInFunctions{
public class Parser {

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
	
	private Token getCurrentToken(){
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
		Token curToken = getCurrentToken();
		
		// <Expr> ::= (<List>)
		if (curToken.getType() == TokenType.OPEN_PARENTHESIS){
			moveToNextToken(); // consume OPEN_PARENTHESES
			TreeNode rightMost = node;
			while (getCurrentToken().getType() != TokenType.CLOSING_PARENTHESIS) {
				if (getCurrentToken().getType() == TokenType.EOF) {
					System.out.printf("Error: Invalid Parenthesis. @[parseExpression] INFO: %s\n", 
							this.scn.checker.printCheckerStatus());
					System.exit(-1);
				}
				
				rightMost.setLeft(parseExpression());
				if (getCurrentToken().getType() == TokenType.CLOSING_PARENTHESIS){
					// reach the end of the LISP "list" -> NIL node
					rightMost.setRight(new TreeNode(new Token("NIL", TokenType.NIL)));
				}
				else{
					// create inner node
					rightMost.setRight(new TreeNode());
				}
				rightMost = rightMost.getRight();
			}
			moveToNextToken(); // consume CLOSING_PARENTHESES
		}
		// <Expr> ::= Atom
		else if(curToken.getType() == TokenType.LITERAL_ATOM ||
					curToken.getType() == TokenType.NUMERIC_ATOM){
			node.setToken(curToken);
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
