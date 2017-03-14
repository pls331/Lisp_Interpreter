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

    void testEval(){
        /*
                 1: *
                /  \
           2:Plus   3: *
                   /  \
                4: 2   5: *
                      /   \
                    6: 1  7: NIL


         */

        // Test Plus
        TreeNode n1 = new TreeNode()
        TreeNode n3 = new TreeNode()
        TreeNode n5 = new TreeNode()
        TreeNode n4 = new TreeNode(new Token("2", TokenType.NUMERIC_ATOM))
        TreeNode n6 = new TreeNode(new Token("1", TokenType.NUMERIC_ATOM))
        TreeNode n2 = new TreeNode(new Token("PLUS", TokenType.LITERAL_ATOM))
        TreeNode n7 = new TreeNode(new Token("NIL", TokenType.LITERAL_ATOM))
        TreeNode Nil = new TreeNode(new Token("NIL", TokenType.LITERAL_ATOM))
        TreeNode T = new TreeNode(new Token("T", TokenType.LITERAL_ATOM))
        n1.setLeft(n2)
        n1.setRight(n3)
        n3.setLeft(n4)
        n3.setRight(n5)
        n5.setLeft(n6)
        n5.setRight(n7)
        println TreeUtil.printDotNotation(n1)
        MyClass obj = new MyClass()
        println obj.Eval(n1)
        assertTrue(obj.Eval(n1).toString().equals("[null <- 3 -> null]"))
//        assertSame("[null <- 3 -> null]", obj.Eval(n1).toString())
        n2.setToken(new Token("MINUS", TokenType.LITERAL_ATOM))
        println TreeUtil.printDotNotation(n1)
        println obj.Eval(n1)
        assertTrue(obj.Eval(n1).toString().equals("[null <- 1 -> null]"))
        n2.setToken(new Token("TIMES", TokenType.LITERAL_ATOM))
        println TreeUtil.printDotNotation(n1)
        assertTrue(obj.Eval(n1).equals(n4))  // 2 * 1

        n2.setToken(new Token("GREATER", TokenType.LITERAL_ATOM))
        assertTrue(obj.Eval(n1).equals(T))
        n6.setToken(new Token("3", TokenType.NUMERIC_ATOM))
        assertTrue(obj.Eval(n1).equals(Nil)) // 2 > 3
        n6.setToken(new Token("0", TokenType.NUMERIC_ATOM))
        assertTrue(obj.Eval(n1).equals(T)) // 2 > 0

        n2.setToken(new Token("LESS", TokenType.LITERAL_ATOM))
        n6.setToken(new Token("3", TokenType.NUMERIC_ATOM))
        assertTrue(obj.Eval(n1).equals(T)) // 2 < 3
        n6.setToken(new Token("0", TokenType.NUMERIC_ATOM))
        assertTrue(obj.Eval(n1).equals(Nil)) // 2 < 0
        n6.setToken(new Token("1", TokenType.NUMERIC_ATOM))

        n2.setToken(new Token("EQ", TokenType.LITERAL_ATOM))
        n6.setToken(new Token("3", TokenType.NUMERIC_ATOM))
        assertTrue(obj.Eval(n1).equals(Nil)) // 2 == 3
        n6.setToken(new Token("2", TokenType.NUMERIC_ATOM))
        assertTrue(obj.Eval(n1).equals(T)) // 2 < 2
        n6.setToken(new Token("1", TokenType.NUMERIC_ATOM))


    }


    void testSingleParam(){
        /*
                         1: *
                        /  \
                   2:ATOM   3: *
                           /  \
                        4: 2   5: NIL
         */
        // Test Plus
        TreeNode n1 = new TreeNode()
        TreeNode n3 = new TreeNode()
        TreeNode n5 = new TreeNode(new Token("NIL", TokenType.LITERAL_ATOM))
        TreeNode n4 = new TreeNode(new Token("2", TokenType.NUMERIC_ATOM))
        TreeNode n2 = new TreeNode(new Token("ATOM", TokenType.LITERAL_ATOM))
        TreeNode Nil = new TreeNode(new Token("NIL", TokenType.LITERAL_ATOM))
        TreeNode T = new TreeNode(new Token("T", TokenType.LITERAL_ATOM))
        n1.setLeft(n2)
        n1.setRight(n3)
        n3.setLeft(n4)
        n3.setRight(n5)

        println TreeUtil.printDotNotation(n1)
        MyClass obj = new MyClass()

        n2.setToken(new Token("ATOM", TokenType.LITERAL_ATOM))
        n4.setToken(new Token("33", TokenType.NUMERIC_ATOM))
        assertTrue(obj.Eval(n1).equals(T)) // 33 is ATOM
        n4.setToken(new Token("22", TokenType.NUMERIC_ATOM))
        assertTrue(obj.Eval(n1).equals(T)) // 22 is ATOM
        n4.setToken(new Token("NIL", TokenType.LITERAL_ATOM))
        assertTrue(obj.Eval(n1).equals(T)) // NIL is ATOM
        n4.setToken(new Token("T", TokenType.LITERAL_ATOM))
        assertTrue(obj.Eval(n1).equals(T)) // T is ATOM
        n4.setToken(new Token("2", TokenType.LITERAL_ATOM))


        n2.setToken(new Token("INT", TokenType.LITERAL_ATOM))
        n4.setToken(new Token("33", TokenType.NUMERIC_ATOM))
        assertTrue(obj.Eval(n1).equals(T)) // 33 is ATOM
        n4.setToken(new Token("22", TokenType.NUMERIC_ATOM))
        assertTrue(obj.Eval(n1).equals(T)) // 22 is ATOM
        n4.setToken(new Token("NIL", TokenType.LITERAL_ATOM))
        assertTrue(obj.Eval(n1).equals(Nil)) // NIL is ATOM
        n4.setToken(new Token("T", TokenType.LITERAL_ATOM))
        assertTrue(obj.Eval(n1).equals(Nil)) // T is ATOM
        n4.setToken(new Token("2", TokenType.LITERAL_ATOM))

        n2.setToken(new Token("NULL", TokenType.LITERAL_ATOM))
        n4.setToken(new Token("33", TokenType.NUMERIC_ATOM))
        assertTrue(obj.Eval(n1).equals(Nil)) // 33 is ATOM
        n4.setToken(new Token("22", TokenType.NUMERIC_ATOM))
        assertTrue(obj.Eval(n1).equals(Nil)) // 22 is ATOM
        n4.setToken(new Token("NIL", TokenType.LITERAL_ATOM))
        assertTrue(obj.Eval(n1).equals(T)) // NIL is ATOM
        n4.setToken(new Token("T", TokenType.LITERAL_ATOM))
        assertTrue(obj.Eval(n1).equals(Nil)) // T is ATOM
        n4.setToken(new Token("2", TokenType.LITERAL_ATOM))

    }

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
        assertTrue(nil.equals(obj.Less(node3, node2)))
        assertTrue(T.equals(obj.Greater(node3, node2)))
        assertTrue(nil.equals(obj.Greater(node2, node3)))
        obj.aList.put("PLS", new Token("ABC", TokenType.LITERAL_ATOM))
        println obj.aList.get("PLS")
        println obj.aList.get("PPP")
        println obj.aList.containsKey("PLS")
        println obj.aList.containsKey("slx")

    }
}
