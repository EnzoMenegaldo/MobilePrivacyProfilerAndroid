package fr.inria.diverse.mobileprivacyprofiler;

import org.junit.Test;


import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void parseTest() throws Exception{
        String parseDummy = "[{Object A{,}}, {[{,}],ObjectB et b}, { hébhéObj@ctC}, {ObjectD}, {ObjectE}]";
        fr.inria.diverse.mobileprivacyprofiler.test.Test test = new fr.inria.diverse.mobileprivacyprofiler.test.Test();
        List<String> parseOutput = test.parser(parseDummy);
        assertEquals(parseDummy,parseOutput.toString());
    }
}