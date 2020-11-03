package reader;

import com.csi.czech.clone.Clone;
import com.csi.czech.clone.NiCadClone;
import com.csi.czech.reader.NiCadCloneReader;
import com.csi.czech.source.NiCadSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NiCadCloneReaderTest {
    private static NiCadCloneReader reader;

    @BeforeAll
    public static void init() {
        reader = new NiCadCloneReader();
    }

    @Test
    public void testOne() throws IOException {
        List<Clone> clones = reader.readClones("testClones/nicad/testOne.xml");
        assertEquals(1, clones.size());

        NiCadClone niCadClone = (NiCadClone) clones.get(0);
        assertEquals(10, niCadClone.getNumLines());
        assertEquals(100, niCadClone.getSimilarity());

        NiCadSource source1 = (NiCadSource) niCadClone.getSource1();
        assertEquals("src/models/similarity_with_frequent_patterns/scoring/similarity/relativesimilarity.py", source1.getFilename());
        assertEquals(58, source1.getStartLine());
        assertEquals(64 , source1.getEndLine());
        assertEquals(28, source1.getPcId());

        NiCadSource source2 = (NiCadSource) niCadClone.getSource2();
        assertEquals("src/models/similarity_with_frequent_patterns/scoring"
                + "/similarity/relativesimilarity.py", source2.getFilename());
        assertEquals(66, source2.getStartLine());
        assertEquals(72, source2.getEndLine());
        assertEquals(30, source2.getPcId());
    }

    @Test
    public void testMany() throws IOException {
        List<Clone> clones = reader.readClones("testClones/nicad/testMany.xml");
        assertEquals(4, clones.size());

        NiCadClone niCadClone = (NiCadClone) clones.get(0);
        assertEquals(18, niCadClone.getNumLines());
        assertEquals(73, niCadClone.getSimilarity());

        NiCadSource source1 = (NiCadSource) niCadClone.getSource1();
        assertEquals("tests/test_signals.py", source1.getFilename());
        assertEquals(15, source1.getStartLine());
        assertEquals(35, source1.getEndLine());
        assertEquals(174, source1.getPcId());

        NiCadSource source2 = (NiCadSource) niCadClone.getSource2();
        assertEquals("tests/test_signals.py", source2.getFilename());
        assertEquals(36, source2.getStartLine());
        assertEquals(60, source2.getEndLine());
        assertEquals(177, source2.getPcId());

        niCadClone = (NiCadClone) clones.get(1);
        assertEquals(16, niCadClone.getNumLines());
        assertEquals(88, niCadClone.getSimilarity());

        source1 = (NiCadSource) niCadClone.getSource1();
        assertEquals("tests/test_templating.py", source1.getFilename());
        assertEquals(61, source1.getStartLine());
        assertEquals(80, source1.getEndLine());
        assertEquals(31, source1.getPcId());

        source2 = (NiCadSource) niCadClone.getSource2();
        assertEquals("tests/test_templating.py", source2.getFilename());
        assertEquals(81, source2.getStartLine());
        assertEquals(102, source2.getEndLine());
        assertEquals(33, source2.getPcId());

        niCadClone = (NiCadClone) clones.get(2);
        assertEquals(15, niCadClone.getNumLines());
        assertEquals(73, niCadClone.getSimilarity());

        source1 = (NiCadSource) niCadClone.getSource1();
        assertEquals("tests/test_appctx.py", source1.getFilename());
        assertEquals(89, source1.getStartLine());
        assertEquals(110, source1.getEndLine());
        assertEquals(120, source1.getPcId());

        source2 = (NiCadSource) niCadClone.getSource2();
        assertEquals("tests/test_appctx.py", source2.getFilename());
        assertEquals(111, source2.getStartLine());
        assertEquals(131, source2.getEndLine());
        assertEquals(124, source2.getPcId());

        niCadClone = (NiCadClone) clones.get(3);
        assertEquals(12, niCadClone.getNumLines());
        assertEquals(83, niCadClone.getSimilarity());

        source1 = (NiCadSource) niCadClone.getSource1();
        assertEquals("tests/test_appctx.py", source1.getFilename());
        assertEquals(55, source1.getStartLine());
        assertEquals(72, source1.getEndLine());
        assertEquals(116, source1.getPcId());

        source2 = (NiCadSource) niCadClone.getSource2();
        assertEquals("tests/test_appctx.py", source2.getFilename());
        assertEquals(73, source2.getStartLine());
        assertEquals(88, source2.getEndLine());
        assertEquals(118, source2.getPcId());
    }
}
