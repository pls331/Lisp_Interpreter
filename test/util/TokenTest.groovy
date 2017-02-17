package util
/**
 * Created by lenovo1 on 2017/2/15.
 */
class TokenTest extends GroovyTestCase {
    void testIsNumericAtom() {
        assertTrue("String", Token.isNumericAtom("12345"))
        assertTrue("String", Token.isNumericAtom("1234567890"))
        assertTrue("String", Token.isNumericAtom("1"))
        assertFalse("String",Token.isNumericAtom("L123"))
        assertFalse("String",Token.isNumericAtom("12a4A5"))
        assertFalse("String",Token.isNumericAtom("12z345Z"))
        assertFalse("String",Token.isNumericAtom("LDJKFLSKasdfasdf"))
        assertFalse("String",Token.isNumericAtom(";"))
    }

    void testIsNIL() {

    }

    void testGetType() {
        Token token1 = new Token("EOF", TokenType.EOF)
        assertTrue(token1.getType() == TokenType.EOF)
        Token token2 = new Token("asdf", TokenType.LITERAL_ATOM)
        assertTrue(token2.getType() == TokenType.LITERAL_ATOM)
        assertFalse(token2.getType() == TokenType.EOF)
        Token token3 = new Token("123", TokenType.NUMERIC_ATOM)
        Token token4 = new Token("123", TokenType.NUMERIC_ATOM)
        Token token5 = new Token("12", TokenType.NUMERIC_ATOM)
        assertTrue(token3.equals(token4))
        println  token3.equals(token5)
        assertFalse(token3.equals(token5))
        assertFalse(token4.equals(token2))
//        assertFalse(token)
    }
}
