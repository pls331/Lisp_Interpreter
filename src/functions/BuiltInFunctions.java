package functions;

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
public interface BuiltInFunctions extends ReservedName{
    HashMap<String, Pair<TreeNode>> dList = new HashMap<>(); // (FunctionName, (formallist, body))

    default TreeNode Eval(TreeNode expr, HashMap<String,TreeNode> aList)
            throws UndefinedBehaviorException, ClassNotFoundException, NoSuchMethodException,
            InvocationTargetException, IllegalAccessException {

        if (expr == null)
            throw new NullPointerException("'null' can not be evaluated.");

        TreeNode ret = null;

        // <editor-fold desc="node ::= Atom">
        if(Atom(expr).equals(nodeT)){
            if(expr.equals(nodeT) || expr.equals(nodeF)){  // evaluate to itself
                ret = expr;
            }
            else if(expr.getTokenType() == TokenType.NUMERIC_ATOM){  // evaluate to itself
                ret = expr;
            }else if(expr.getTokenType() == TokenType.LITERAL_ATOM){  // get from aList
                String varName = expr.getLexicalVal();
                Preconditions.checkUndefinedBehavior(
                        ! aList.containsKey(varName),
                        "Variable has not been Declared/Init. " +
                                " Variable Name:" + varName +
                                " aList: " + aList.toString());
                ret = aList.get(varName);  // list or numerical atom
            }
            else{
                Preconditions.checkUndefinedBehavior(true, "Undefined Behavior for a single atom.");
            }
            return ret;
        }
        //</editor-fold>


        // <editor-fold desc="node ::= apply a Function">
        TreeNode s1;
        TreeNode s2;
        TreeNode s3;
        Class cl = TreeNode.class;

        if(Car(expr).getTokenType() != TokenType.LITERAL_ATOM)
            Preconditions.checkUndefinedBehavior(true,
                    String.format("Function name must be a Literal Atom.", Car(expr).getLexicalVal()));

        String functionName = Car(expr).getLexicalVal();
        //<editor-fold desc="Arithmetic Operator - Binary - Numeric Atom Input">
        if(functionName.equals("PLUS")
                || functionName.equals("MINUS")
                || functionName.equals("TIMES")
                || functionName.equals("GREATER")
                || functionName.equals("LESS")){
            // Length == 3
            try{
                Preconditions.checkUndefinedBehavior(! Cdr(Cdr(Cdr(expr))).equals(nodeF), "Length != 3");
            }catch(UndefinedBehaviorException udbe){
                throw new UndefinedBehaviorException("UndefinedBehavior - Formals list have different length with Actual list");
            }
            s1 = Car(Cdr(expr));
            s2 = Car(Cdr(Cdr(expr)));
            s1 = Eval(s1, aList);  // recursively eval s1
            s2 = Eval(s2, aList); // recursively eval s2
            // Numeric Atoms after eval
            Preconditions.checkUndefinedBehavior(! (isNumeric(s1) && isNumeric(s2)), "NOT Numeric Atom" );
            // use reflection to call by function name
            ret = (TreeNode) getBuiltInMethod(functionName, cl, cl).invoke(this, s1, s2);
        }
            //</editor-fold>

        //<editor-fold desc="EQ - Binary - Atom Input">
        else if(functionName.equals("EQ")){
            // Length == 3
            try{
                Preconditions.checkUndefinedBehavior(! Cdr(Cdr(Cdr(expr))).equals(nodeF), "Length != 3");
            }catch(UndefinedBehaviorException udbe){
                throw new UndefinedBehaviorException("UndefinedBehavior - Formals list have different length with Actual list");
            }
            s1 = Car(Cdr(expr));
            s2 = Car(Cdr(Cdr(expr)));
            s1 = Eval(s1, aList);  // recursively eval s1
            s2 = Eval(s2, aList); // recursively eval s2
            Preconditions.checkUndefinedBehavior(! (Atom(s1).equals(nodeT) && Atom(s2).equals(nodeT)),
                    "Must be an Atom.");

            // use reflection to call by function name
            ret = (TreeNode) getBuiltInMethod(functionName, cl, cl).invoke(this, s1, s2);
        }
            //</editor-fold>

        //<editor-fold desc="ATOM | INT | NULL - Single - Atom Input">
        else if(functionName.equals("ATOM")
                || functionName.equals("INT")
                || functionName.equals("NULL")){
            // Length == 2
            try{
                Preconditions.checkUndefinedBehavior(! Cdr(Cdr(expr)).equals(nodeF), "Length != 2");
            }catch(UndefinedBehaviorException udbe){
                throw new UndefinedBehaviorException("UndefinedBehavior - Formals list have different length with Actual list");
            }
            s1 = Car(Cdr(expr));
            s1 = Eval(s1, aList);  // recursively eval s1

//            Preconditions.checkUndefinedBehavior(! (Atom(s1).equals(nodeT)), "Must be an Atom.");
            // use reflection to call the function by function name
            ret = (TreeNode) getBuiltInMethod(functionName, cl).invoke(this, s1);
        }
            //</editor-fold>

        //<editor-fold desc="CAR | CDR - Single - List Input">
        else if(functionName.equals("CAR")
                || functionName.equals("CDR")){
            // Length == 2
            try{
                Preconditions.checkUndefinedBehavior(! Cdr(Cdr(expr)).equals(nodeF), "Length != 2");
            }catch(UndefinedBehaviorException udbe){
                throw new UndefinedBehaviorException("UndefinedBehavior - Formals list have different length with Actual list");
            }
            s1 = Car(Cdr(expr));
            s1 = Eval(s1, aList);  // recursively eval s1
            Preconditions.checkUndefinedBehavior((Atom(s1).equals(nodeT)), "Must NOT be an Atom.");

            // use reflection to call by function name
            ret = (TreeNode) getBuiltInMethod(functionName, cl).invoke(this, s1);
        }
            //</editor-fold>

        //<editor-fold desc="CONS  - Single - List/Atom Input">
        else if(functionName.equals("CONS")){
            // Length == 3
            try{
                Preconditions.checkUndefinedBehavior(! Cdr(Cdr(Cdr(expr))).equals(nodeF), "Length != 3");
            }catch(UndefinedBehaviorException udbe){
                throw new UndefinedBehaviorException("UndefinedBehavior - Formals list have different length with Actual list");
            }
            s1 = Car(Cdr(expr));
            s2 = Car(Cdr(Cdr(expr)));
            s1 = Eval(s1, aList);  // recursively eval s1
            s2 = Eval(s2, aList); // recursively eval s2
            // use reflection to call by function name
            ret = (TreeNode) getBuiltInMethod(functionName, cl, cl).invoke(this, s1, s2);
        }
            //</editor-fold>

        //<editor-fold desc="QUOTE  - List Input">
        else if(functionName.equals("QUOTE")){
            // Length == 2
            Preconditions.checkUndefinedBehavior(! Cdr(Cdr(expr)).equals(nodeF), "Length != 2");
            s1 = Car(Cdr(expr));
            // use reflection to call by function name
            ret = s1;
        }
        //</editor-fold>

        //<editor-fold desc="COND - Using on demand evaluation">
        else if(functionName.equals("COND")){
            Preconditions.checkUndefinedBehavior( Atom(Cdr(expr)).equals(nodeT), "Length must be > 1");
            TreeNode curNode = Cdr(expr);
            TreeNode s = null, b = null, e = null;
            while(! curNode.equals(nodeF)){
                s = Car(curNode);
                Preconditions.checkUndefinedBehavior(Atom(s).equals(nodeT), "Must be a list");
                // length of s == 2
                Preconditions.checkUndefinedBehavior(! Atom(Cdr(Cdr(s))).equals(nodeT), "Length Must equals 2");
                b = Car(s);
                e = Car(Cdr(s));
                if(! this.Eval(b, aList).equals(nodeF)){
                    ret = this.Eval(e, aList);
                    break;
                }
                curNode = Cdr(curNode);
            }
            if(ret == null) {
                Preconditions.checkUndefinedBehavior(true, "Can not match any S-exp in condition.");
            }

        }
        //</editor-fold>

        //<editor-fold desc="DEFUN - Functon Definition">
        else if(functionName.equals("DEFUN")){
            // Length == 4
            try{
                Preconditions.checkUndefinedBehavior(! Cdr(Cdr(Cdr(Cdr(expr)))).equals(nodeF),
                                                "Function Declaration List Length != 4");
            }catch(UndefinedBehaviorException udbe){
                throw new UndefinedBehaviorException("UndefinedBehavior - Formals list have different length with Actual list");
            }
            s1 = Car(Cdr(expr)); // user-defined function name, must be literal atom
            s2 = Car(Cdr(Cdr(expr))); // formals list
            s3 = Car(Cdr(Cdr(Cdr(expr)))); // body
            // check preconditions
            Preconditions.checkUndefinedBehavior(
                    Atom(s1).equals(nodeF),
                    "Function Name could not be list.");
            Preconditions.checkUndefinedBehavior(
                    ! s1.getTokenType().equals(TokenType.LITERAL_ATOM),
                    "Function Name Atom must be Literal Atom.");
            Preconditions.checkUndefinedBehavior(
                    ReservedName.Function.contain(s1.getLexicalVal()),
                    "Function name must be different from Built-in Function Names.");
            Preconditions.checkUndefinedBehavior(
                    ReservedName.Atom.contain(s1.getLexicalVal()),
                    "Function name must be different from Built-in Atom Names.");
            isUniqueAndValid(s2); // unique & not use reserved name & literal atom

            // add function into dlist
            dList.put(s1.getLexicalVal(), new Pair<>(s2, s3));
            // use reflection to call by function name
            ret = s1;
        }
        //</editor-fold>

        //<editor-fold desc="user-defined Function Call">
        else if(dList.containsKey(functionName)){
            Preconditions.checkUndefinedBehavior(   (Atom(Car(expr)).equals(nodeF)),
                                                   "First element must be atomic element");
            ret = apply(    Car(expr),
                            evlist(Cdr(expr), aList),
                            aList );
        }
        //</editor-fold>
        else{
            Preconditions.checkUndefinedBehavior(true, "undefined function "+ functionName );
        }
        //</editor-fold>

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

    default TreeNode apply(TreeNode funcName, TreeNode actualParam, HashMap<String, TreeNode> aList)
            throws ClassNotFoundException, NoSuchMethodException,
                IllegalAccessException, InvocationTargetException {

        TreeNode body = dList.get(funcName.getLexicalVal()).getSecond();
        TreeNode paramList = dList.get(funcName.getLexicalVal()).getFirst();
        HashMap<String, TreeNode> aList_new = addPairs(paramList, actualParam, aList);
        return Eval(body, aList_new);
    }

    default HashMap<String, TreeNode> addPairs(TreeNode paramList, TreeNode actualParams,
                                                 HashMap<String, TreeNode> aList){
        Preconditions.checkNotNull(paramList);
        Preconditions.checkNotNull(actualParams);
        TreeNode p = paramList;
        TreeNode a = actualParams;
        HashMap<String, TreeNode> newList = new HashMap<>(aList);
        while(  !( p.equals(nodeF) || a.equals(nodeF) ) ){
            newList.put(p.getLeft().getLexicalVal(), a.getLeft());
            p = p.getRight();
            a = a.getRight();
        }
        // param list and actual list must be of same length
        Preconditions.checkUndefinedBehavior( !(p.equals(nodeF) && a.equals(nodeF)),
                                            "Formals list have different length with Actual list");
        return newList;
    }

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
//                ReservedName.Function.contain(name) || ReservedName.Atom.contain(name),
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
