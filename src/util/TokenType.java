package util;

/**
 * Created by lenovo1 on 2017/2/14.
 */
public enum TokenType {
    LITERAL_ATOM("LITERAL_ATOM"),
    NUMERIC_ATOM("NUMERIC_ATOM"),
    NIL("NIL"),
    OPEN_PARENTHESIS("OPEN_PARENTHESIS"),
    CLOSING_PARENTHESIS("CLOSING_PARENTHESIS");

    private String type;

    TokenType(String type) {this.type = type;}

    public String getType(){ return this.type; }
}
