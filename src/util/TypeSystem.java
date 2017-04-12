package util;

/**
 * Created by lenovo1 on 2017/4/10.
 */
public enum TypeSystem {
    AnyT("Any Type"),
    BOOL("Bool"),
    NAT("Nat"),
    LIST_NAT("List(Nat)");

    private String description;
    TypeSystem(String t) {
        description = t;
    }

    public static boolean contains(TypeSystem type){
        boolean res = false;
        for(TypeSystem t: TypeSystem.values()){
            if(t.equals(type))
                res = true;
        }
        return res;
    }

    /*
    Same type or subtype.
     */
    public boolean isOfType(TypeSystem anotherType){
        if(this.equals(anotherType) || anotherType.equals(TypeSystem.AnyT)){
           return true;
        }else {
            return false;
        }
    }

    public String toString(){
        return description;
    }
}
