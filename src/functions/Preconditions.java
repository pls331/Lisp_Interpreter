package functions;

import exception.EmptyListException;
import exception.InvalidTypeException;
import exception.UndefinedBehaviorException;
import util.TypeSystem;

/**
 * Created by lenovo1 on 2017/2/7.
 */
public class Preconditions {

    public static void checkNotNull(Object node){
        if (node == null){
            throw new NullPointerException("Precondition check");
        }
    }

    public static void checkUndefinedBehavior(boolean expr, String msg)
        throws UndefinedBehaviorException{
        if(expr){
                throw new UndefinedBehaviorException("UndefinedBehavior - " + msg);
            }
    }

    public static void checkType(TypeSystem expectedType, TypeSystem inferredType, String msg)
            throws InvalidTypeException {
        if (!inferredType.isOfType(expectedType)) {
            throw new InvalidTypeException("TYPE ERROR: " + msg);
        }
    }

    public static void checkEmptyList(int minLen,int inferedLen, String msg)
            throws EmptyListException {
        if(!(inferedLen >= minLen)){
            throw new EmptyListException(msg);
        }
    }
}
