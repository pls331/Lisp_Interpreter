package functions
/**
 * Created by lenovo1 on 2017/3/14.
 */

class ReservedNameTest extends GroovyTestCase implements ReservedName {
    void testContains(){
         assertTrue(ReservedName.Function.contain("CAR"))
         assertTrue(ReservedName.Function.contain("CONS"))
         assertTrue(ReservedName.Function.contain("CDR"))
         assertTrue(ReservedName.Function.contain("CONS"))
         assertTrue(ReservedName.Function.contain("ATOM"))
         assertTrue(ReservedName.Function.contain("EQ"))
         assertTrue(ReservedName.Function.contain("NULL"))
         assertTrue(ReservedName.Function.contain("INT"))
         assertTrue(ReservedName.Function.contain("PLUS"))
         assertTrue(ReservedName.Function.contain("MINUS"))
         assertTrue(ReservedName.Function.contain("TIMES"))
         assertTrue(ReservedName.Function.contain("LESS"))
         assertTrue(ReservedName.Function.contain("GREATER"))
         assertTrue(ReservedName.Function.contain("COND"))
         assertTrue(ReservedName.Function.contain("QUOTE"))
         assertTrue(ReservedName.Function.contain("DEFUN"))
         assertTrue(ReservedName.Atom.contain("NIL"))
         assertTrue(ReservedName.Atom.contain("T"))
        assertFalse(ReservedName.Function.contain("F"))
        assertFalse(ReservedName.Function.contain("T"))
        assertFalse(ReservedName.Atom.contain("F"))
        assertFalse(ReservedName.Atom.contain("A"))
        assertFalse(ReservedName.Atom.contain("E"))


    }
}
