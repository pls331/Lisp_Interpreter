package util
/**
 * Created by lenovo1 on 2017/2/16.
 */
class TreeUtilTest extends GroovyTestCase {

    void testPrettyPrint() {
        TreeNode nodeR1 = new TreeNode(new Token("0", TokenType.NUMERIC_ATOM))
        TreeNode node1 = new TreeNode(new Token("1", TokenType.NUMERIC_ATOM))
        TreeNode nodeR2 = new TreeNode()
        TreeNode node2 = new TreeNode(new Token("2", TokenType.NUMERIC_ATOM))
        TreeNode nodeR3 = new TreeNode()
        TreeNode node3 = new TreeNode(new Token("3", TokenType.NUMERIC_ATOM))
        TreeNode node4 = TreeUtil.nodeNIL
        println TreeUtil.getListNotation(nodeR1)
        nodeR1.setLeft(node1)
        nodeR1.setRight(nodeR2)
        nodeR2.setLeft(node2)
        nodeR2.setRight(nodeR3)
        nodeR3.setLeft(node3)
        nodeR3.setRight(node3)
        println TreeUtil.getListNotation(nodeR1)
    }


}
