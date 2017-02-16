package util;

/**
 * Created by lenovo1 on 2017/2/14.
 */
public class Token {
    public final TokenType type;
    public final String lexval;

    public Token(String lexval, TokenType tokenType){
        this.lexval = lexval;
        this.type = tokenType;
    }

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
