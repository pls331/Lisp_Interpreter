package util
/**
 * Created by lenovo1 on 2017/2/16.
 */
class TreeUtilTest extends GroovyTestCase {

    void testPrettyPrint() {
        TreeNode node = new TreeNode()
        TreeNode node2 = new TreeNode(new Token("adfa", TokenType.LITERAL_ATOM))
        TreeNode node3 = new TreeNode(new Token("1234", TokenType.NUMERIC_ATOM))
        TreeNode node4 = new TreeNode(new Token("NIL", TokenType.LITERAL_ATOM))
        node.setLeft(node2)
        node.setRight(node4)
        node2.setLeft(node3)
        println TreeUtil.printDotNotation(node)
    }


}
