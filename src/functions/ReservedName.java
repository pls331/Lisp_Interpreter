package functions;

public interface ReservedName {
    public enum Function {
        CAR("CAR"), CDR("CDR"), CONS("CONS"),
        ATOM("ATOM"), EQ("EQ"), NULL("NULL"), INT("INT"),
        PLUS("PLUS"), MINUS("MINUS"), TIMES("TIMES"),
        LESS("LESS"), GREATER("GREATER"),
        COND("COND"), QUOTE("QUOTE"), DEFUN("DEFUN");

        private String name;
        Function(String name){
            this.name = name;
        }

        public static boolean contain(String name){
            String s = null;
            for(Function f : Function.values()){
                if(f.getName().equals(name))
                    return true;
            }
            return false;
        }


        public String getName() {
            return name;
        }
    }

    enum Atom {
        NIL("NIL"), T("T");

        private String name;
        Atom(String name){
            this.name = name;
        }

        public static boolean contain(String name){
            String s = null;
            for(Atom atom : Atom.values()){
                if(atom.getName().equals(name))
                    return true;
            }
            return false;
        }


        public String getName() {
            return name;
        }
    }

}
