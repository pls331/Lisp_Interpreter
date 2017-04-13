import java.nio.charset.Charset

/**
 * Created by lenovo1 on 2017/4/12.
 */
class TypeCheckerTest extends GroovyTestCase {

    void testValid(){
        // evaluate valid input
        InputStream is = null;
        is = new FileInputStream(new File("ValidTestCase.txt"));
        System.setIn(is);

        TypeChecker typeChecker = new TypeChecker(System.in);
        typeChecker.parse( false ); // Project 2 output
        typeChecker.eval(false, true);
        ArrayList<String> resList = typeChecker.getResult();
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
