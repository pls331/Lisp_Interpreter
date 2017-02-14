package functions;

import exception.UndefinedBehaviorException;

/**
 * Created by lenovo1 on 2017/2/7.
 */
public class Preconditions {

    public static void checkNotNull(Object node){
        if (node == null){
            throw new NullPointerException();
        }
    }

    public static void checkUndefinedBehavior(boolean expr, String msg){
        if(expr){
            throw new UndefinedBehaviorException(msg);
        }
    }

}
