package util;

/**
 * Created by lenovo1 on 2017/2/7.
 */
public class        TokenUtil {
    public static boolean isNumericAtom(String token){
        // check is Numeric Atom
        if (token.length() == 0) return false;

        for(char ch : token.toCharArray())
            if (!Character.isDigit(ch))
                return false;
        return true;
    }

    public static boolean isNIL(String token) {
        if (token.length() == 0) return false;
        return token.equals("NIL");
    }

}
