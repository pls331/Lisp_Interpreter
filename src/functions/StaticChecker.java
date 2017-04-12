package functions;

import exception.InvalidTypeException;
import exception.UndefinedBehaviorException;
import util.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;

import static util.TreeUtil.*;

/**
 * Created by lenovo1 on 2017/2/7.
 */
public interface StaticChecker extends ReservedName{

    default TypeSystem evalType(TreeNode expr)
            throws UndefinedBehaviorException, InvalidTypeException{
        if (expr == null)
            throw new NullPointerException("'null' can not be evaluated.");

        TypeSystem ret = null;

        // <editor-fold desc="node ::= Atom">
        if(Atom(expr).equals(nodeT)){
            if(expr.equals(nodeT) || expr.equals(nodeF)){  // evaluate to itself
                ret = TypeSystem.BOOL;
            }else if(expr.equals(nodeNIL)){
                ret = TypeSystem.LIST_NAT;
            }
            else if(expr.getTokenType() == TokenType.NUMERIC_ATOM){  // evaluate to itself
                ret = TypeSystem.NAT;
            }
            else{
                Preconditions.checkUndefinedBehavior(true, "Undefined Behavior for a single atom.");
            }
            return ret;
        }
        //</editor-fold>




    }

    default Method getBuiltInMethod(String funcName, Class<?>... parameterTypes)
            throws NoSuchMethodException {
        StringBuilder builder = new StringBuilder();
        builder.append(funcName.charAt(0));
        builder.append(funcName.substring(1, funcName.length()).toLowerCase());
        funcName = builder.toString();
        Method m = this.getClass().getInterfaces()[0].getMethod(funcName, parameterTypes);
        return m;
    }

    default TreeNode apply(TreeNode funcName, TreeNode actualParam, HashMap<String, TreeNode> aList)
            throws ClassNotFoundException, NoSuchMethodException,
                IllegalAccessException, InvocationTargetException {

        TreeNode body = dList.get(funcName.getLexicalVal()).getSecond();
        TreeNode paramList = dList.get(funcName.getLexicalVal()).getFirst();
        HashMap<String, TreeNode> aList_new = addPairs(paramList, actualParam, aList);
        return Eval(body, aList_new);
    }

//    default HashMap<String, TreeNode> addPairs(TreeNode paramList, TreeNode actualParams,
//                                               HashMap<String, TreeNode> aList){
//        //TODO check length of param & actual
//        Preconditions.checkNotNull(paramList);
//        Preconditions.checkNotNull(actualParams);
//        TreeNode p = paramList;
//        TreeNode a = actualParams;
//        HashMap<String, TreeNode> newList = new HashMap<>(aList);
//        while(  !( p.equals(nodeF) || a.equals(nodeF) ) ){
//            newList.put(p.getLeft().getLexicalVal(), a.getLeft());
//            p = p.getRight();
//            a = a.getRight();
//        }
//        // param list and actual list must be of same length
//        Preconditions.checkUndefinedBehavior( !(p.equals(nodeF) && a.equals(nodeF)),
//                                            "Formals list have different length with Actual list");
//        return newList;
//    }

    /*
    Evaluate the actual list into a list of atoms.
     */

//    default TreeNode evlist(TreeNode x, HashMap<String, TreeNode> aList)
//            throws ClassNotFoundException, NoSuchMethodException,
//            IllegalAccessException, InvocationTargetException {
//        if(x.equals(nodeF))
//            return nodeF;
//        return Cons(Eval(Car(x), aList), evlist(Cdr(x), aList));
//    }

    /*
    Check if the parameters in the list of function declaration are unique
    and not same as reserved names.
     */
//    default void isUniqueAndValid(TreeNode param_list){
//        TreeNode tmp = param_list;
//        TreeNode p;
//        String name = null;
//        HashSet<String> nameSet = new HashSet<>();
//        while (! tmp.equals(nodeF)){
//            p = tmp.getLeft();
//            name = p.getLexicalVal();
//
//            Preconditions.checkUndefinedBehavior(
//                    !(p.isLeaf() && p.getTokenType() == TokenType.LITERAL_ATOM),
//                    "In parameter list, only Literal Atom is accepted");
//            Preconditions.checkUndefinedBehavior(
//                Function.contain(name) || Atom.contain(name),
//                "Parameter name must be different with reserved words.");
//            Preconditions.checkUndefinedBehavior(
//                        nameSet.contains(name),
//                    "Parameter name must be unique.");
//
//            nameSet.add(name);
//            tmp = tmp.getRight();
//        }
//    }

    default TreeNode Car(TreeNode node)
            throws NullPointerException, UndefinedBehaviorException{
        Preconditions.checkNotNull(node);
        Preconditions.checkUndefinedBehavior(node.isLeaf(), "Cannot be atom @Car: " + node.toString());
        return node.getLeft();
    }

    default TreeNode Cdr(TreeNode node)
            throws NullPointerException, UndefinedBehaviorException{
        Preconditions.checkNotNull(node);
        Preconditions.checkUndefinedBehavior(node.isLeaf(), "Cannot be atom @Cdr:" + node.toString());
        return node.getRight();
    }

    default TreeNode Cons(TreeNode node1, TreeNode node2)
            throws NullPointerException, UndefinedBehaviorException{
        Preconditions.checkNotNull(node1);
        Preconditions.checkNotNull(node2);

        TreeNode root = new TreeNode(); // inner node
        root.setLeft(node1);
        root.setRight(node2);
        return root;
    }

    default TreeNode Atom(TreeNode node)
            throws NullPointerException, UndefinedBehaviorException{
        Preconditions.checkNotNull(node);
        if(node.isLeaf()){
            return new TreeNode(new Token("T",TokenType.LITERAL_ATOM));
        }else{
            return new TreeNode(new Token("F",TokenType.LITERAL_ATOM));
        }
    }

    default TreeNode Int(TreeNode node)
            throws NullPointerException, UndefinedBehaviorException {
        Preconditions.checkNotNull(node);
        if( node.isLeaf() && node.getToken().getType() == TokenType.NUMERIC_ATOM ){
            return new TreeNode(new Token("T",TokenType.LITERAL_ATOM));
        }else{
            return new TreeNode(new Token("F",TokenType.LITERAL_ATOM));
        }
    }

    default TreeNode Null(TreeNode node)
            throws NullPointerException, UndefinedBehaviorException{
        Preconditions.checkNotNull(node); // undefined
        if( node.isLeaf() && TreeUtil.isNIL(node)){
            return new TreeNode(new Token("T",TokenType.LITERAL_ATOM));
        }else{
            return new TreeNode(new Token("F",TokenType.LITERAL_ATOM));
        }
    }

    default TreeNode Eq(TreeNode node1, TreeNode node2)
            throws NullPointerException, UndefinedBehaviorException{
        Preconditions.checkNotNull(node1);
        Preconditions.checkNotNull(node2);
        // Have exactly 1 treenode
        Preconditions.checkUndefinedBehavior(!node1.isLeaf(), "in Eq() @" + node1.toString());
        Preconditions.checkUndefinedBehavior(!node2.isLeaf(), "in Eq() @" + node2.toString());
        if(isEqual(node1, node2))
            return new TreeNode(new Token("T",TokenType.LITERAL_ATOM));
        return new TreeNode(new Token("F",TokenType.LITERAL_ATOM));
    }

    default TreeNode Plus(TreeNode node1, TreeNode node2)
            throws NullPointerException, UndefinedBehaviorException{
        Preconditions.checkNotNull(node1);
        Preconditions.checkNotNull(node2);
        // Have exactly 1 treenode
        Preconditions.checkUndefinedBehavior(!node1.isLeaf(), "Should be Exactly 1 node");
        Preconditions.checkUndefinedBehavior(!node2.isLeaf(), "Should be Exactly 1 node");
        // Must be Numeric Atom
        Preconditions.checkUndefinedBehavior(! (isNumeric(node1) && isNumeric(node2)), "Must be Numeric Atom");

        int sum = Integer.parseInt(node1.getLexicalVal())
                +Integer.parseInt(node2.getLexicalVal());
        return new TreeNode(new Token(String.valueOf(sum), TokenType.NUMERIC_ATOM));
    }

    default TreeNode Minus(TreeNode node1, TreeNode node2)
            throws NullPointerException, UndefinedBehaviorException{
        Preconditions.checkNotNull(node1);
        Preconditions.checkNotNull(node2);
        // Have exactly 1 treenode
        Preconditions.checkUndefinedBehavior(!node1.isLeaf(), "Should be Exactly 1 node");
        Preconditions.checkUndefinedBehavior(!node2.isLeaf(), "Should be Exactly 1 node");
        // Must be Numeric Atom
        Preconditions.checkUndefinedBehavior(! (isNumeric(node1) && isNumeric(node2)), "Must be Numeric Atom");

        int res = Integer.parseInt(node1.getLexicalVal())
                - Integer.parseInt(node2.getLexicalVal());
        return new TreeNode(new Token(String.valueOf(res), TokenType.NUMERIC_ATOM));
    }

    default TreeNode Times(TreeNode node1, TreeNode node2)
            throws NullPointerException, UndefinedBehaviorException{
        Preconditions.checkNotNull(node1);
        Preconditions.checkNotNull(node2);
        // Have exactly 1 treenode
        Preconditions.checkUndefinedBehavior(!node1.isLeaf(), "Should be Exactly 1 node");
        Preconditions.checkUndefinedBehavior(!node2.isLeaf(), "Should be Exactly 1 node");
        // Must be Numeric Atom
        Preconditions.checkUndefinedBehavior(! (isNumeric(node1) && isNumeric(node2)), "Must be Numeric Atom");

        int res = Integer.parseInt(node1.getLexicalVal())
                * Integer.parseInt(node2.getLexicalVal());
        return new TreeNode(new Token(String.valueOf(res), TokenType.NUMERIC_ATOM));
    }


    default TreeNode Less(TreeNode node1, TreeNode node2)
            throws NullPointerException, UndefinedBehaviorException{

        Preconditions.checkNotNull(node1);
        Preconditions.checkNotNull(node2);
        // Have exactly 1 treenode
        Preconditions.checkUndefinedBehavior(!node1.isLeaf(), "Should be Exactly 1 node");
        Preconditions.checkUndefinedBehavior(!node2.isLeaf(), "Should be Exactly 1 node");
        // Must be Numeric Atom
        Preconditions.checkUndefinedBehavior(! (isNumeric(node1) && isNumeric(node2)), "Must be Numeric Atom");

        int val1 = Integer.parseInt(node1.getLexicalVal());
        int val2 = Integer.parseInt(node2.getLexicalVal());
        if (val1 < val2)
            return new TreeNode(new Token("T", TokenType.LITERAL_ATOM));
        return new TreeNode(new Token("NIL", TokenType.LITERAL_ATOM));
    }

    default TreeNode Greater(TreeNode node1, TreeNode node2)
            throws NullPointerException, UndefinedBehaviorException{

        Preconditions.checkNotNull(node1);
        Preconditions.checkNotNull(node2);
        // Have exactly 1 treenode
        Preconditions.checkUndefinedBehavior(!node1.isLeaf(), "Should be Exactly 1 node");
        Preconditions.checkUndefinedBehavior(!node2.isLeaf(), "Should be Exactly 1 node");
        // Must be Numeric Atom
        Preconditions.checkUndefinedBehavior(! (isNumeric(node1) && isNumeric(node2)), "Must be Numeric Atom");

        int val1 = Integer.parseInt(node1.getLexicalVal());
        int val2 = Integer.parseInt(node2.getLexicalVal());
        if (val1 > val2)
            return new TreeNode(new Token("T", TokenType.LITERAL_ATOM));
        return new TreeNode(new Token("NIL", TokenType.LITERAL_ATOM));
    }
}
