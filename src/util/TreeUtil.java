package util;

/**
 * Created by lenovo1 on 2017/2/16.
 */
public class TreeUtil {
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
