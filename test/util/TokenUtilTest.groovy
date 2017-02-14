package util
/**
 * Created by lenovo1 on 2017/2/7.
 */
class TokenUtilTest extends GroovyTestCase {
    void testIsNumericAtom() {
        assertTrue("String", TokenUtil.isNumericAtom("12345"))
        assertTrue("String", TokenUtil.isNumericAtom("1234567890"))
        assertTrue("String", TokenUtil.isNumericAtom("1"))
        assertFalse("String", TokenUtil.isNumericAtom("L123"))
        assertFalse("String", TokenUtil.isNumericAtom("12a4A5"))
        assertFalse("String", TokenUtil.isNumericAtom("12z345Z"))
        assertFalse("String", TokenUtil.isNumericAtom("LDJKFLSKasdfasdf"))
        assertFalse("String", TokenUtil.isNumericAtom(";"))
    }

    void testIsNIL() {
        assertTrue(TokenUtil.isNIL("NIL"))
        assertFalse(TokenUtil.isNIL("NILa"))
        assertFalse(TokenUtil.isNIL("asdf"))
        assertFalse(TokenUtil.isNIL("1232"))
        assertFalse(TokenUtil.isNIL("asd34"))
        assertFalse(TokenUtil.isNIL("NIaL"))
    }
}

