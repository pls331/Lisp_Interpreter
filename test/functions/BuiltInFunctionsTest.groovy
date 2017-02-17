package functions

import exception.UndefinedBehaviorException
import util.Token
import util.TokenType
import util.TreeNode
import util.TreeUtil

/**
 * Created by lenovo1 on 2017/2/16.
 */

class MyClass implements BuiltInFunctions{

}

class BuiltInFunctionsTest extends GroovyTestCase {


    void testPlus_Eq() {
        TreeNode node = new TreeNode()
        TreeNode node2 = new TreeNode(new Token("0000", TokenType.NUMERIC_ATOM))
        TreeNode node3 = new TreeNode(new Token("1234", TokenType.NUMERIC_ATOM))
        TreeNode node4 = new TreeNode(new Token("NIL", TokenType.LITERAL_ATOM))
        TreeNode nil = node4
        TreeNode T = new TreeNode(new Token("T", TokenType.LITERAL_ATOM))
        MyClass obj = new MyClass()
        TreeNode node5 = obj.Plus(node2, node3)
        assertTrue(node5.getToken().equals(node3.getToken()))
        assertTrue(TreeUtil.isEqual(obj.Plus(node2, node3), node3))
        assertTrue(TreeUtil.isEqual(node3, obj.Plus(node2, node3)))
        try{
            obj.Plus(node, node2)
        }catch (Exception e){
            println node
            println node2
            assertSame((new UndefinedBehaviorException("").getClass()), e.getClass())
        }
        assertTrue(nil.equals(obj.Less(node3, node2)))
        assertTrue(T.equals(obj.Less(node2, node3)))
    }
}
