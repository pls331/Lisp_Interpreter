package util;


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

    public static String printDotNotation(TreeNode node){
        StringBuilder builder = new StringBuilder();
        dotNotationHelper(node, builder);
        builder.append("\n");
        return builder.toString();
    }

    private static void dotNotationHelper(TreeNode node, StringBuilder builder){
        // empty tree
        if (node == null) return;
        // leaf node
        if (node.getLeft() == null && node.getRight() == null) {
            builder.append(node.getLexicalVal());
            return;
        }
        // print inner node
        builder.append("(");
        dotNotationHelper(node.getLeft(), builder);
        builder.append(".");
        dotNotationHelper(node.getRight(), builder);
        builder.append(")");
    }


    public static String getListNotation(TreeNode root) {
        if(root == null) return "";

        StringBuilder builder = new StringBuilder();
        listNotationHelper(root, builder);
        return builder.toString();
    }

    private static void listNotationHelper(TreeNode root, StringBuilder builder){
        if(root == null) return;
        if(root.isLeaf()) {
            builder.append(root.getLexicalVal());
            return;
        }

        TreeNode cur = root, left = null;
        builder.append("(");
        while(cur.getRight() != null){
            left = cur.getLeft();
            listNotationHelper(left, builder);
            cur = cur.getRight();
            if(cur.getRight() != null) builder.append(" ");
        }
        if(! cur.equals(nodeNIL)){
            builder.append(" . ");
            builder.append(cur.getLexicalVal());
        }
        builder.append(")");
    }
}
