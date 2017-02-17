package util

import org.junit.Before

/**
 * Created by lenovo1 on 2017/2/15.
 */
class TreeNodeTest extends GroovyTestCase {
    @Before
    void testGetLexicalVal() {
        TreeNode node = new TreeNode()
        assertSame(node.getLexicalVal(), "*")
        assertSame(node.getToken(), null)
        TreeNode node2 = new TreeNode(new Token("adfa", TokenType.LITERAL_ATOM))
        assertSame(node2.getToken().getType(), TokenType.LITERAL_ATOM)
        assertSame(node2.getLexicalVal(), "adfa")
    }

    void testToString_isLeaf() {
        TreeNode node = new TreeNode()
        TreeNode node2 = new TreeNode(new Token("adfa", TokenType.LITERAL_ATOM))
        TreeNode node3 = new TreeNode(new Token("1234", TokenType.NUMERIC_ATOM))
        TreeNode node4 = new TreeNode(new Token("NIL", TokenType.LITERAL_ATOM))
        node.setLeft(node2)
        node.setRight(node4)
        node2.setLeft(node3)
        System.out.println(node.toString())
        System.out.println(node2.toString())
        System.out.println(node4.toString())

        assertTrue(node4.isLeaf())
        assertTrue(node3.isLeaf())
        assertFalse(node.isLeaf())
        assertFalse(node2.isLeaf())
    }

    void testGetLeft() {

    }

    void testSetLeft() {

    }

    void testGetRight() {

    }

    void testSetRight() {

    }
}
