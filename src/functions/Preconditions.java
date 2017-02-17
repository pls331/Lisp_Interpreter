package functions;

import exception.UndefinedBehaviorException;

/**
 * Created by lenovo1 on 2017/2/7.
 */
public class Preconditions {

    public static void checkNotNull(Object node){
        if (node == null){
            throw new NullPointerException("Precondition check");
        }
    }

    public static void checkUndefinedBehavior(boolean expr, String msg){
        try{
            if(expr){
                throw new UndefinedBehaviorException(msg);
            }
        }catch (Exception e){
            throw new UndefinedBehaviorException(msg);
        }
    }

}
