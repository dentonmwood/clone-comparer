package reader;

import com.csi.czech.clone.Clone;
import com.csi.czech.clone.PyCloneClone;
import com.csi.czech.reader.PyCloneCloneReader;
import com.csi.czech.source.PyCloneSource;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PyCloneCloneReaderTest {
    private static PyCloneCloneReader reader;

    @BeforeAll
    public static void init() {
        reader = new PyCloneCloneReader(new JSONParser());
    }

    @Test
    public void testTwo() throws IOException {
        String input = "[{\"value\": \"ClassDef\", \"match_weight\": 31, "
                + "\"origins\": {\"tests/test_user_notification.py (10, 25)"
                + "\": 1.0, \"tests/test_auth.py (10, 25)\": 1.0}}, "
                + "{\"value\": \"If\", \"match_weight\": 22, \"origins\": "
                + "{\"f8a_notification/github_utils.py (162, 166)\": 1.0, "
                + "\"f8a_notification/github_utils.py (188, 192)\": 1.0}}]\n";
        List<Clone> clones = reader.processJson(input);
        assertEquals(2, clones.size());

        PyCloneClone clone1 = (PyCloneClone) clones.get(0);
        assertEquals("ClassDef", clone1.getValue());
        assertEquals(31, clone1.getMatchWeight());
        PyCloneSource source1 = (PyCloneSource) clone1.getSource1();
        assertEquals("tests/test_auth.py", source1.getFilename());
        assertEquals(10, source1.getStartLine());
        assertEquals(25, source1.getEndLine());
        assertEquals(1.0, source1.getWeight());
        PyCloneSource source2 = (PyCloneSource) clone1.getSource2();
        assertEquals("tests/test_user_notification.py", source2.getFilename());
        assertEquals(10, source2.getStartLine());
        assertEquals(25, source2.getEndLine());
        assertEquals(1.0, source2.getWeight());

        PyCloneClone clone2 = (PyCloneClone) clones.get(1);
        assertEquals("If", clone2.getValue());
        assertEquals(22, clone2.getMatchWeight());
        source2 = (PyCloneSource) clone2.getSource1();
        assertEquals("f8a_notification/github_utils.py", source2.getFilename());
        assertEquals(162, source2.getStartLine());
        assertEquals(166, source2.getEndLine());
        assertEquals(1.0, source2.getWeight());
        source1 = (PyCloneSource) clone2.getSource2();
        assertEquals("f8a_notification/github_utils.py", source1.getFilename());
        assertEquals(188, source1.getStartLine());
        assertEquals(192, source1.getEndLine());
        assertEquals(1.0, source1.getWeight());
    }

    @Test
    public void testBreakingUpClone() throws IOException {
        String input = "[{\"value\": \"If\", \"match_weight\": 22, "
                + "\"origins\": "
                + "{\"a/b/c.py (11, 23)\": 1.0, "
                + "\"1/2/3.py (8, 14)\": 1.0"
                + "\"do/re/mi.py (154, 167)\": 2.0}}]\n";

        List<Clone> clones = reader.processJson(input);
        assertEquals(3, clones.size());

        PyCloneClone clone1 = (PyCloneClone) clones.get(0);
        assertEquals("If", clone1.getValue());
        assertEquals(22, clone1.getMatchWeight());
        PyCloneSource source1 = (PyCloneSource) clone1.getSource1();
        assertEquals("1/2/3.py", source1.getFilename());
        assertEquals(8, source1.getStartLine());
        assertEquals(14, source1.getEndLine());
        assertEquals(1.0, source1.getWeight());
        PyCloneSource source2 = (PyCloneSource) clone1.getSource2();
        assertEquals("a/b/c.py", source2.getFilename());
        assertEquals(11, source2.getStartLine());
        assertEquals(23, source2.getEndLine());
        assertEquals(1.0, source2.getWeight());

        PyCloneClone clone2 = (PyCloneClone) clones.get(1);
        source1 = (PyCloneSource) clone2.getSource1();
        assertEquals("1/2/3.py", source1.getFilename());
        assertEquals(8, source1.getStartLine());
        assertEquals(14, source1.getEndLine());
        assertEquals(1.0, source1.getWeight());
        source2 = (PyCloneSource) clone2.getSource2();
        assertEquals("do/re/mi.py", source2.getFilename());
        assertEquals(154, source2.getStartLine());
        assertEquals(167, source2.getEndLine());
        assertEquals(2.0, source2.getWeight());

        PyCloneClone clone3 = (PyCloneClone) clones.get(2);
        source1 = (PyCloneSource) clone3.getSource1();
        assertEquals("a/b/c.py", source1.getFilename());
        assertEquals(11, source1.getStartLine());
        assertEquals(23, source1.getEndLine());
        assertEquals(1.0, source1.getWeight());
        source2 = (PyCloneSource) clone3.getSource2();
        assertEquals("do/re/mi.py", source2.getFilename());
        assertEquals(154, source2.getStartLine());
        assertEquals(167, source2.getEndLine());
        assertEquals(2.0, source2.getWeight());
    }

    @Test
    public void testMany() throws IOException {
        List<Clone> clones = reader.readClones("testClones/pyclone/testMany.json");
        assertEquals(153, clones.size());
    }
}
