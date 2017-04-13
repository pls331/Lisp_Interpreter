package util

/**
 * Created by lenovo1 on 2017/4/12.
 */
class TypeSystemTest extends GroovyTestCase {
    void testTypeCreation(){
        println TypeSystem.AnyType
        println TypeSystem.BOOL
        println TypeSystem.LIST_NAT
        println TypeSystem.NAT
        assertFalse(TypeSystem.AnyType.equals(TypeSystem.BOOL))
        assertFalse(TypeSystem.NAT.equals(TypeSystem.BOOL))
        assertFalse(TypeSystem.LIST_NAT.equals(TypeSystem.NAT))
        assertTrue(TypeSystem.NAT.equals(TypeSystem.NAT))
        assertTrue(TypeSystem.NAT == TypeSystem.NAT)
        assertTrue(! TypeSystem.AnyType.isOfType(TypeSystem.NAT))
        assertTrue(! TypeSystem.AnyType.isOfType(TypeSystem.LIST_NAT))
        assertTrue(TypeSystem.NAT.isOfType(TypeSystem.AnyType))
        assertTrue(TypeSystem.NAT.isOfType(TypeSystem.NAT))
        assertTrue(TypeSystem.BOOL.isOfType(TypeSystem.AnyType))
        assertTrue(TypeSystem.LIST_NAT.isOfType(TypeSystem.AnyType))
        assertTrue(TypeSystem.AnyType.isOfType(TypeSystem.AnyType))
        assertFalse(TypeSystem.LIST_NAT.isOfType(TypeSystem.BOOL))
    }
}
