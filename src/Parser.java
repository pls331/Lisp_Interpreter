import exception.InvalidTypeException;
import exception.UndefinedBehaviorException;
import functions.StaticChecker;
import util.*;

import java.util.ArrayList;

/**
 * 
 */

/**
 * @author lenovo1
 *
 */
//public class Parser implements BuiltInFunctions{
public class Parser implements StaticChecker{
	/**
	 * Context-free Gramma:
	 * <Start> ::= <Expr> <Start> | <Expr> eof
	 * <Expr> ::= atom | ( <List> )
	 * <List> ::= <Expr> <List> | ε
	 */
	private LexicalScanner scn;
	private ArrayList<TreeNode> rootList;
	public ArrayList<Pair<TypeSystem, Integer>> resultList;
	public ArrayList<String> exMsgList;

	public Parser(LexicalScanner scn) {
		// Initialize parser
		this.scn = scn;
		this.rootList = new ArrayList<>(); // root of parse tree
		this.resultList = new ArrayList<>(); // root of parse tree
		this.exMsgList = new ArrayList<>(); //
	}
	
	private void moveToNextToken(){
		scn.moveToNextToken();
	}

	/*
	 * Parse <Start>
	 */
	public void  parseStart(boolean isPrint) {
		if (scn.getCurrentToken().getType() == TokenType.EOF)
			return;
		
		StringBuilder builder = new StringBuilder();
		TreeNode root = null;
		
		do{
			root = parseExpression(); // parse Top level expression
			if (isPrint) builder.append(TreeUtil.printDotNotation(root));
			this.rootList.add(root);
		}while(scn.getCurrentToken().getType() != TokenType.EOF);

		if (! this.scn.checker.isWholeStreamValid()){
			System.out.println();
			System.out.printf("Error: Invalid Parenthesis. @[parseStart] INFO: %s\n", this.scn.checker.printCheckerStatus());
			System.exit(-1);
		}
		System.out.print(builder.toString());
	}


	public ArrayList<Pair<TypeSystem, Integer>> eval(boolean isPrint, boolean isTesting){
		boolean isExceptionCaught = false;
		for (TreeNode node : this.rootList){
			try{
				isExceptionCaught = false;
				System.out.println(TreeUtil.getListNotation(node));
				System.out.flush();
				// static type checking
				TypeSystem evalType = this.evalType(node);
				// static empty list checking
				Pair<TypeSystem, Integer> evalPair = this.evalEmptyList(node);
				System.out.println(String.format("%s, %s", evalType, evalPair));
				assert evalType == evalPair.getFirst();
				this.resultList.add(new Pair<>(evalType, evalPair.getSecond()));
//				if(isPrint)
//					System.out.println(TreeUtil.getListNotation(node));
			}catch (InvalidTypeException ite){
				if(!isTesting) System.out.println(String.format(ite.getMessage()));
				exMsgList.add(ite.getMessage());
				isExceptionCaught = true;
//				ite.printStackTrace();
			}
			catch (UndefinedBehaviorException udbe){
				if(!isTesting) System.out.println(String.format("TYPE ERROR: %s", udbe.getMessage()));
				exMsgList.add(udbe.getMessage());
				isExceptionCaught = true;
//				udbe.printStackTrace();
			}catch (NullPointerException npe){
				if(!isTesting) System.out.println(String.format("TYPE ERROR: %s", npe.getMessage()));
				exMsgList.add(npe.getMessage());
				isExceptionCaught = true;
//				npe.printStackTrace();
			}catch(Exception e){
				exMsgList.add(e.getMessage());
				isExceptionCaught = true;
//				e.printStackTrace();
			}finally {
				System.out.flush();
				System.err.flush();
				if(isExceptionCaught && ! isTesting) System.exit(-2);
			}

		}
		return this.resultList;
	}

	private TreeNode parseExpression(){
		TreeNode node = new TreeNode(); // node for current terminal/non-terminal
		Token curToken = scn.getCurrentToken();
		
		// <Expr> ::= (<List>)
		if (curToken.getType() == TokenType.OPEN_PARENTHESIS){
			moveToNextToken(); // consume OPEN_PARENTHESES
			TreeNode rightMost = node;
			while (scn.getCurrentToken().getType() != TokenType.CLOSING_PARENTHESIS) {
				if (scn.getCurrentToken().getType() == TokenType.EOF) {
					System.out.printf("Error: Invalid Parenthesis. @[parseExpression] INFO: %s\n", 
							this.scn.checker.printCheckerStatus());
					System.exit(-1);
				}
				
				rightMost.setLeft(parseExpression());
				if (scn.getCurrentToken().getType() == TokenType.CLOSING_PARENTHESIS){
					// reach the end of the LISP "list" -> NIL node
					rightMost.setRight(new TreeNode(new Token("NIL", TokenType.LITERAL_ATOM)));
				}
				else{
					// create inner node
					rightMost.setRight(new TreeNode());
				}
				rightMost = rightMost.getRight();
			}
			moveToNextToken(); // consume CLOSING_PARENTHESES
			if(rightMost.isLeaf()){
				rightMost.setToken(new Token("NIL", TokenType.LITERAL_ATOM));
			}
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

}
