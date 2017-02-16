package util;

/**
 * Created by lenovo1 on 2017/2/14.
 */
public class Token {
    private final TokenType type;
    private final String lexval;

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

    public String getLexval() {
        return lexval;
    }

    public TokenType getType(){
        return this.type;
    }

    @Override
    public String toString() {
        return String.format("<%s :: %s>", lexval, type.toString());
    }
}
