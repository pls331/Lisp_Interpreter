/**
 * Created by lenovo1 on 2017/3/14.
 */
class InterpreterTest extends GroovyTestCase {
    void testMain() {
        //region create STDIN from file
        InputStream is = null;
        is = new FileInputStream(new File("EvaluateTest.txt"));
        System.setIn(is);

        Interpreter interpreter = new Interpreter(System.in);
        interpreter.parse( false ); // Project 2 output
        interpreter.eval(true);
        ArrayList<String> resList = interpreter.getResult();

        System.exit(0);
    }

    public ArrayList<String> getAnswers(){

    }
}
