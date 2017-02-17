package util;

/**
 * Created by lenovo1 on 2017/2/16.
 */
public class TreeUtil {
    public static TreeNode nodeNIL = new TreeNode(new Token("NIL", TokenType.LITERAL_ATOM));
    public static TreeNode nodeT = new TreeNode(new Token("T", TokenType.LITERAL_ATOM));

    public static boolean isNIL(TreeNode node){
        if(node == null)
            throw new NullPointerException();
        return node.getToken().getType() == TokenType.LITERAL_ATOM && node.getLexicalVal().equals("NIL");
    }

    public static boolean isNumeric (TreeNode node)
        throws NullPointerException {

        if(node == null)
            throw new NullPointerException( "TreeUtil.isNumeric: input must be TreeNode" );
        if(node.getToken() == null)
            return false;

        return node.getToken().getType() == TokenType.NUMERIC_ATOM;
    }

    public static boolean isLiteral(TreeNode node)
            throws NullPointerException{

        if(node == null)
            throw new NullPointerException();
        return node.getToken().getType() == TokenType.LITERAL_ATOM;
    }

    public static boolean isEqual(TreeNode node1, TreeNode node2)
            throws NullPointerException {

        if(node1 == null || node2 == null)
            throw new NullPointerException("null is not a kind of node");

        if(node1.getLexicalVal().equals(node2.getLexicalVal())){
            if(node1.getToken() == null && node2.getToken() == null)
                return true;
            else return node1.getToken().equals(node2.getToken());
        }
        return false;
    }

    public static String prettyPrint(TreeNode node){
        StringBuilder builder = new StringBuilder();
        prettyPrintHelper(node, builder);
        builder.append("\n");
        return builder.toString();
    }

    private static void prettyPrintHelper(TreeNode node, StringBuilder builder){
        // empty tree
        if (node == null) return;

        // leaf node
        if (node.getLeft() == null && node.getRight() == null) {
            builder.append(node.getLexicalVal());
            return;
        }
        // print inner node
        builder.append("(");
        prettyPrintHelper(node.getLeft(), builder);
        builder.append(".");
        prettyPrintHelper(node.getRight(), builder);
        builder.append(")");
    }


}
