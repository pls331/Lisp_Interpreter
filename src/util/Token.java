package util;

/**
 * Created by lenovo1 on 2017/2/14.
 */
public class Token {
    private final String type;
    private final String lexval;

    public Token(String lexval, String type){
        this.lexval = lexval;
        this.type = type;
    }
}
