import util.Token
import util.TokenType

/**
 * Created by lenovo1 on 2017/2/15.
 */
class LexicalScannerTest extends GroovyTestCase {
    void testGetCurrentToken() {
        /**
         *  Unit Test
         */
        // create stdin from file
        InputStream is = null
        is = new FileInputStream(new File("ScannerInput.txt"))
        System.setIn(is)

//		// read stdin & print test file
//		int cInt = System.in.read();
//		while (cInt != -1){
//			System.out.println( (char) cInt );
//			cInt = System.in.read();
//		}

        LexicalScanner scn = new LexicalScanner()

        // isNumericAtom
        boolean ret = scn.isNumericAtom("asdfasd")

        // isValidParentheses

        // getNextToken()
        System.out.println(scn.literalAtomSeq.toString())
        Token token = scn.getNextToken()
        while (token.getType() != TokenType.EOF){
            System.out.printf("-----token: %s\n", token.toString())
            token = scn.getNextToken()
        }

        System.out.println(scn.literalAtomSeq.toString())
        System.out.println(" --- Get All Tokens finished.  ---")
        System.out.printf("LITERAL ATOMS: %d%s\n", scn.literalAtomSeq.size(), LexicalScanner.getAtomsString(scn.literalAtomSeq))
        System.out.printf("NUMERIC ATOMS: %d, %d\n" , scn.numericAtomSeq.size(), scn.numeric_sum)
        System.out.printf("OPEN PARENTHESES: %d\n", scn.open_p_cnt)
        System.out.printf("CLOSING PARENTHESES: %d\n", scn.closing_p_cnt)
        System.out.println(" --- Test getNextToken() finished.  ---")

        // getScannerOutput
        System.out.println(scn.getScannerOutput())

        // parentheses check 1 - invalid
        scn = new LexicalScanner(is)
        assertTrue(scn.checker.isParenthesesValid("("))
        assertTrue(scn.checker.isParenthesesValid(")"))

        assertFalse(scn.checker.isParenthesesValid(")"))
        assertFalse(scn.checker.isParenthesesValid(")"))
        // Test init()
        is = new FileInputStream(new File("ScannerInput.txt"))
        System.setIn(is)
        scn = new LexicalScanner()
        System.out.println(scn.curToken)
        scn.init()
        assertTrue(scn.getCurrentToken().getType() == TokenType.OPEN_PARENTHESIS)
        System.out.printf("( == %s\n", scn.curToken)
        // getCurrent + moveToNext()
        assertTrue(scn.getCurrentToken().getLexval().equals("("))
        assertTrue(scn.getCurrentToken().getType() == TokenType.OPEN_PARENTHESIS)
        scn.moveToNextToken()
        assertTrue(scn.getCurrentToken().getLexval().equals("DEFUN"))
        assertTrue(scn.getCurrentToken().getType() == TokenType.LITERAL_ATOM)
        while( scn.getCurrentToken().getType() != TokenType.EOF){
            System.out.printf("%s\n", scn.getCurrentToken())
            scn.moveToNextToken()
        }
        System.out.println("\nInit(), getCurrent(), moveToNext() tested result is above this line ______")
        System.exit(0)
    }

}
