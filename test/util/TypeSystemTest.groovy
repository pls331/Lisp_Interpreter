package util

/**
 * Created by lenovo1 on 2017/4/12.
 */
class TypeSystemTest extends GroovyTestCase {
    void testTypeCreation(){
        println TypeSystem.AnyT
        println TypeSystem.BOOL
        println TypeSystem.LIST_NAT
        println TypeSystem.NAT
        assertFalse(TypeSystem.AnyT.equals(TypeSystem.BOOL))
        assertFalse(TypeSystem.NAT.equals(TypeSystem.BOOL))
        assertFalse(TypeSystem.LIST_NAT.equals(TypeSystem.NAT))
        assertTrue(TypeSystem.NAT.equals(TypeSystem.NAT))
        assertTrue(TypeSystem.NAT == TypeSystem.NAT)
        assertTrue(! TypeSystem.AnyT.isOfType(TypeSystem.NAT))
        assertTrue(! TypeSystem.AnyT.isOfType(TypeSystem.LIST_NAT))
        assertTrue(TypeSystem.NAT.isOfType(TypeSystem.AnyT))
        assertTrue(TypeSystem.NAT.isOfType(TypeSystem.NAT))
        assertTrue(TypeSystem.BOOL.isOfType(TypeSystem.AnyT))
        assertTrue(TypeSystem.LIST_NAT.isOfType(TypeSystem.AnyT))
        assertTrue(TypeSystem.AnyT.isOfType(TypeSystem.AnyT))
        assertFalse(TypeSystem.LIST_NAT.isOfType(TypeSystem.BOOL))
    }
}
