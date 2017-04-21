package util
/**
 * Created by lenovo1 on 2017/2/16.
 */
class TreeUtilTest extends GroovyTestCase {

    void testPrettyPrint() {

        TreeNode node0 = new TreeNode(new Token("1", TokenType.NUMERIC_ATOM))
        TreeNode node1 = new TreeNode(new Token("1", TokenType.NUMERIC_ATOM))
        TreeNode node1L = new TreeNode()
        TreeNode node2 = new TreeNode(new Token("2", TokenType.NUMERIC_ATOM))
        TreeNode node3 = new TreeNode(new Token("3", TokenType.NUMERIC_ATOM))
        TreeNode node4 = TreeUtil.nodeNIL
        println TreeUtil.getListNotation(node1)
        node.setLeft(node2)
        node.setRight(node4)
        node2.setLeft(node3)
        println TreeUtil.getListNotation(node)
    }


}
