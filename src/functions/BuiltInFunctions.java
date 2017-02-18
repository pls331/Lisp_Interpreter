package functions;

import exception.UndefinedBehaviorException;
import util.Token;
import util.TokenType;
import util.TreeNode;
import util.TreeUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static util.TreeUtil.*;

/**
 * Created by lenovo1 on 2017/2/7.
 */
public interface BuiltInFunctions {

    default TreeNode Eval(TreeNode node)
            throws UndefinedBehaviorException, ClassNotFoundException, NoSuchMethodException,
            InvocationTargetException, IllegalAccessException {

        if (node == null)
            throw new NullPointerException("null can not be evaluated.");

        TreeNode ret = null;

        // node ::= Atom
        if(Atom(node).equals(nodeT)){
            if(node.equals(nodeT)
                    || node.equals(nodeNIL)){
                ret = node;
            }
            else if(node.getTokenType() == TokenType.NUMERIC_ATOM){
                ret = node;
            }
            else{
                // TODO change in Project4 -> function cal
                Preconditions.checkUndefinedBehavior(true, "in Atom");
            }
            return ret;
        }

        // node ::= List
        TreeNode s1;
        TreeNode s2;
        Class cl = TreeNode.class;

        if(Car(node).getTokenType() != TokenType.LITERAL_ATOM)
            Preconditions.checkUndefinedBehavior(true, "Illegal list at front of the list, " +
                    "not a Literal Atom");

        String functName = Car(node).getLexicalVal();
        //<editor-fold desc="Arithmetic Operator - Binary - Numeric Atom Input">
        if(functName.equals("PLUS")
                || functName.equals("MINUS")
                || functName.equals("TIMES")
                || functName.equals("GREATER")
                || functName.equals("LESS")){
            // Length == 3
            Preconditions.checkUndefinedBehavior(! Cdr(Cdr(Cdr(node))).equals(nodeNIL), "Length != 3");
            s1 = Car(Cdr(node));
            s2 = Car(Cdr(Cdr(node)));
            if(! Atom(s1).equals(nodeT)){
                s1 = Eval(s1);  // recursively eval s1
            }
            if(! Atom(s2).equals(nodeT)){
                s2 = Eval(s2); // recursively eval s2
            }
            // Numeric Atoms after eval
            Preconditions.checkUndefinedBehavior(! (isNumeric(s1) && isNumeric(s2)), "NOT Numeric Atom" );
            // use reflection to call by function name
            ret = (TreeNode) getBuiltInMethod(functName, cl, cl).invoke(this, s1, s2);
        }
            //</editor-fold>

        //<editor-fold desc="EQ - Binary - Atom Input">
        else if(functName.equals("EQ")){
            // Length == 3
            Preconditions.checkUndefinedBehavior(! Cdr(Cdr(Cdr(node))).equals(nodeNIL), "Length != 3");
            s1 = Car(Cdr(node));
            s2 = Car(Cdr(Cdr(node)));
            if(! Atom(s1).equals(nodeT)){
                s1 = Eval(s1);  // recursively eval s1
            }
            if(! Atom(s2).equals(nodeT)){
                s2 = Eval(s2); // recursively eval s2
            }
            Preconditions.checkUndefinedBehavior(! (Atom(s1).equals(nodeT) && Atom(s2).equals(nodeT)),
                    "Must be an Atom.");

            // use reflection to call by function name
            ret = (TreeNode) getBuiltInMethod(functName, cl, cl).invoke(this, s1, s2);
        }
            //</editor-fold>

        //<editor-fold desc="ATOM | INT | NULL - Single - Atom Input">
        else if(functName.equals("ATOM")
                || functName.equals("INT")
                || functName.equals("NULL")){
            // Length == 2
            Preconditions.checkUndefinedBehavior(! Cdr(Cdr(node)).equals(nodeNIL), "Length != 2");
            s1 = Car(Cdr(node));
            if(! Atom(s1).equals(nodeT)){
                s1 = Eval(s1);  // recursively eval s1
            }
            Preconditions.checkUndefinedBehavior(! (Atom(s1).equals(nodeT)),
                    "Must be an Atom.");

            // use reflection to call by function name
            ret = (TreeNode) getBuiltInMethod(functName, cl).invoke(this, s1);
        }
            //</editor-fold>

        //<editor-fold desc="CAR | CDR - Single - List Input">
        else if(functName.equals("CAR")
                || functName.equals("CDR")){
            // Length == 2
            Preconditions.checkUndefinedBehavior(! Cdr(Cdr(node)).equals(nodeNIL), "Length != 2");
            s1 = Car(Cdr(node));
            if(! Atom(s1).equals(nodeT)){
                s1 = Eval(s1);  // recursively eval s1
            }
            Preconditions.checkUndefinedBehavior(! (Atom(s1).equals(nodeNIL)),
                    "Must NOT be an Atom.");

            // use reflection to call by function name
            ret = (TreeNode) getBuiltInMethod(functName, cl).invoke(this, s1);
        }
            //</editor-fold>

        //<editor-fold desc="CONS  - Single - List/Atom Input">
        else if(functName.equals("CONS")){
            // Length == 3
            Preconditions.checkUndefinedBehavior(! Cdr(Cdr(Cdr(node))).equals(nodeNIL), "Length != 3");
            s1 = Car(Cdr(node));
            s2 = Car(Cdr(Cdr(node)));
            if(! Atom(s1).equals(nodeT)){
                s1 = Eval(s1);  // recursively eval s1
            }
            if(! Atom(s2).equals(nodeT)){
                s2 = Eval(s2); // recursively eval s2
            }
            // use reflection to call by function name
            ret = (TreeNode) getBuiltInMethod(functName, cl, cl).invoke(this, s1, s2);
        }
            //</editor-fold>

        //<editor-fold desc="QUOTE  - List Input">
        else if(functName.equals("QUOTE")){
            // Length == 2
            Preconditions.checkUndefinedBehavior(! Cdr(Cdr(node)).equals(nodeNIL), "Length != 2");
            s1 = Car(Cdr(node));
            // use reflection to call by function name
            ret = s1;
        }
            //</editor-fold>

        //<editor-fold desc="COND - Using on demand evaluation">
        else if(functName.equals("COND")){
            Preconditions.checkUndefinedBehavior( Atom(Cdr(node)).equals(nodeT), "Length must be > 1");
            TreeNode curNode = Cdr(node);
            TreeNode s = null, b = null, e = null;
            while(! curNode.equals(nodeNIL)){
                s = Car(curNode);
                Preconditions.checkUndefinedBehavior(Atom(s).equals(nodeT), "Must be a list");
                // length of s == 2
                Preconditions.checkUndefinedBehavior(! Atom(Cdr(Cdr(s))).equals(nodeT), "Length Must equals 2");
                b = Car(s);
                e = Car(Cdr(s));
                if(! this.Eval(b).equals(nodeNIL)){
                    ret = this.Eval(e);
                    break;
                }
                curNode = Cdr(curNode);
            }
            if(ret == null) {
                Preconditions.checkUndefinedBehavior(true, "Can not match any S-exp in condition.");
            }

        }
            //</editor-fold>

        else{
            Preconditions.checkUndefinedBehavior(true, "Function does not match any of known ones.");
        }

        return ret;
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

    default TreeNode Car(TreeNode node)
            throws NullPointerException, UndefinedBehaviorException{
        Preconditions.checkNotNull(node);
        Preconditions.checkUndefinedBehavior(node.isLeaf(), "in Car @ " + node.toString());
        return node.getLeft();
    }

    default TreeNode Cdr(TreeNode node)
            throws NullPointerException, UndefinedBehaviorException{
        Preconditions.checkNotNull(node);
        Preconditions.checkUndefinedBehavior(node.isLeaf(), "Cdr:" + node.toString());
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
            return new TreeNode(new Token("NIL",TokenType.LITERAL_ATOM));
        }
    }

    default TreeNode Int(TreeNode node)
            throws NullPointerException, UndefinedBehaviorException {
        Preconditions.checkNotNull(node);
        if( node.isLeaf() && node.getToken().getType() == TokenType.NUMERIC_ATOM ){
            return new TreeNode(new Token("T",TokenType.LITERAL_ATOM));
        }else{
            return new TreeNode(new Token("NIL",TokenType.LITERAL_ATOM));
        }
    }

    default TreeNode Null(TreeNode node)
            throws NullPointerException, UndefinedBehaviorException{
        Preconditions.checkNotNull(node); // undefined
        if( node.isLeaf() && TreeUtil.isNIL(node)){
            return new TreeNode(new Token("T",TokenType.LITERAL_ATOM));
        }else{
            return new TreeNode(new Token("NIL",TokenType.LITERAL_ATOM));
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
        return new TreeNode(new Token("NIL",TokenType.LITERAL_ATOM));
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
