import java.nio.charset.Charset
/**
 * Created by lenovo1 on 2017/3/14.
 */
class InterpreterTest extends GroovyTestCase {

    void testInvalid() {
        // evaluate invalid input
        FileInputStream fis = new FileInputStream(new File("InvalidTestCase"));
        System.setIn(fis);
        Interpreter interpreter2 = new Interpreter(System.in);
        interpreter2.parse( false ); // Project 2 output
        interpreter2.eval(false, true);
        println "####### Invalid Test Case #######"
        ArrayList<String> msgList = interpreter2.getExceptionMsgList()
        ArrayList<String> ansList = getAnswers("InvalidTestAnswer.txt")
        assertSame(msgList.size(), ansList.size())
        for(int i = 0; i < msgList.size(); i++){
            println String.format("%-4d | %s", i+1, msgList.get(i))
            println String.format("%-4d | %s", i+1, ansList.get(i))
            assertTrue(msgList.get(i) == ansList.get(i))
        }
    }

    void testValid() {
        // evaluate valid input
        InputStream is = null;
        is = new FileInputStream(new File("ValidTestCase.txt"));
        System.setIn(is);

        Interpreter interpreter = new Interpreter(System.in);
        interpreter.parse( false ); // Project 2 output
        interpreter.eval(false, true);
        ArrayList<String> resList = interpreter.getResult();
        ArrayList<String> ansList = getAnswers("ValidAnswer.txt");
        assert(resList.size() == ansList.size())
        println "####### Valid Test Case #######\n\n"
        println String.format("%-4s | %-25s | %-25s", "Line", "Result", "Expected")
        for(int i = 0; i < resList.size(); i++){
            println String.format("%-4d | %-25s | %-25s", i+1, resList.get(i), ansList.get(i))
            assertTrue(resList.get(i).equals(ansList.get(i)))
        }

    }

    public ArrayList<String> getAnswers(String fileName){
        InputStream fis = new FileInputStream(new File(fileName) );
        InputStreamReader isr = new InputStreamReader(fis, Charset.defaultCharset())
        BufferedReader br = new BufferedReader(isr)
        String line;
        ArrayList<String> answers = new ArrayList<>()
        while((line = br.readLine()) != null){
            answers.add(line);
        }
        return answers;
    }
}
