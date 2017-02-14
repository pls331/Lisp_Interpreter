package functions;

import util.TokenUtil;
import util.TreeNode;

/**
 * Created by lenovo1 on 2017/2/7.
 */
public interface BuiltInFunctions {
    default TreeNode Car(TreeNode node){
        Preconditions.checkNotNull(node);
        Preconditions.checkUndefinedBehavior(node.isLeaf(), "in Car @ " + node.toString());
        return node.getLeft();
    }

    default TreeNode Cdr(TreeNode node) {
        Preconditions.checkNotNull(node);
        Preconditions.checkUndefinedBehavior(node.isLeaf(), "Cdr:" + node.toString());
        return node.getRight();
    }

    default TreeNode Cons(TreeNode node1, TreeNode node2) {
        Preconditions.checkNotNull(node1);
        Preconditions.checkNotNull(node2);

        TreeNode root = new TreeNode();
        root.setLeft(node1);
        root.setRight(node2);
        return root;
    }

    default TreeNode Atom(TreeNode node) {
        Preconditions.checkNotNull(node);
        return new TreeNode(node.isLeaf() ? "T" : "NIL" );
    }

    default TreeNode Int(TreeNode node) {
        Preconditions.checkNotNull(node);
        return new TreeNode(TokenUtil.isNumericAtom(node.getLexicalVal()) ? "T" : "NIL");
    }

    default TreeNode Null(TreeNode node){
        Preconditions.checkNotNull(node); // undefined
        Preconditions.checkUndefinedBehavior(node.isLeaf(), "in Null() @" + node.toString());
        return new TreeNode(TokenUtil.isNIL(node.getLexicalVal()) ? "T" : "NIL");
    }

    default TreeNode Eq(TreeNode node1, TreeNode node2){
        Preconditions.checkNotNull(node1);
        Preconditions.checkNotNull(node2);
        Preconditions.checkUndefinedBehavior(node1.isLeaf(), "in Eq() @" + node1.toString());
        Preconditions.checkUndefinedBehavior(node2.isLeaf(), "in Eq() @" + node2.toString());


        return null;
    }
}
