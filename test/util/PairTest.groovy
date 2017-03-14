package util
/**
 * Created by lenovo1 on 2017/3/14.
 */
class PairTest extends GroovyTestCase {
    void testPair(){
        Token t1 = new Token("111", TokenType.NUMERIC_ATOM);
        Token t2 = new Token("222", TokenType.NUMERIC_ATOM);
        Pair<TreeNode> pair = new Pair<>(new TreeNode(t1),
                                        new TreeNode(t2));
        assertSame(pair.getFirst().getToken(), t1)
        assertSame(pair.getSecond().getToken(), t2)

    }

}
