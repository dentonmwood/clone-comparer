package comparer;

import com.csi.czech.clone.Clone;
import com.csi.czech.clone.CycloneClone;
import com.csi.czech.clone.MossClone;
import com.csi.czech.clone.NiCadClone;
import com.csi.czech.comparer.CloneComparer;
import com.csi.czech.source.CycloneSource;
import com.csi.czech.source.MossSource;
import com.csi.czech.source.NiCadSource;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CloneComparerTest {
    @Test
    public void singleTest() {
        Clone cycloneClone1 = new CycloneClone("module", 2L);
        cycloneClone1.addSource(new CycloneSource("filea.txt", 15L,
                20L, 2.0));
        cycloneClone1.addSource(new CycloneSource("file2.txt", 35L, 45L, 1.0));

        Clone cycloneClone2 = new CycloneClone("module", 2L);
        cycloneClone2.addSource(new CycloneSource("filea.txt", 15L,
                20L, 2.0));
        cycloneClone2.addSource(new CycloneSource("file2.txt", 35L, 45L, 1.0));

        List<Clone> cloneList1 = Collections.singletonList(cycloneClone1);
        List<Clone> cloneList2 = Collections.singletonList(cycloneClone2);

        assertEquals(1.0, CloneComparer.compareCloneLists(cloneList1,
                cloneList2));
        assertEquals(1.0, CloneComparer.compareCloneLists(cloneList2,
                cloneList1));
    }

    @Test
    public void acrossTypes() {
        List<Clone> cloneList1 = new ArrayList<>();
        Clone cycloneClone = new CycloneClone("module", 2L);
        cycloneClone.addSource(new CycloneSource("filea.txt", 15L,
                20L, 2.0));
        cycloneClone.addSource(new CycloneSource("file2.txt", 35L, 45L, 1.0));
        cloneList1.add(cycloneClone);

        List<Clone> cloneList2 = new ArrayList<>();
        Clone nicadClone = new NiCadClone(23L, 2L);
        nicadClone.addSource(new NiCadSource("filea.txt", 10L,
                25L, 22L));
        nicadClone.addSource(new NiCadSource("file2.txt", 25L, 55L, 1L));
        cloneList2.add(nicadClone);

        List<Clone> cloneList3 = new ArrayList<>();
        Clone mossClone = new MossClone();
        mossClone.addSource(new MossSource("filea.txt", 10L, 25L, 0.9));
        mossClone.addSource(new MossSource("file2.txt", 25L, 55L, 0.8));
        cloneList3.add(mossClone);


        assertEquals(1.0, CloneComparer.compareCloneLists(cloneList1,
                cloneList2));
        assertEquals(1.0, CloneComparer.compareCloneLists(cloneList1, cloneList3));
        assertEquals(1.0, CloneComparer.compareCloneLists(cloneList2, cloneList3));
    }

    @Test
    public void noMatch() {
        Clone cycloneClone1 = new CycloneClone("module", 2L);
        cycloneClone1.addSource(new CycloneSource("filea.txt", 15L,
                20L, 2.0));
        cycloneClone1.addSource(new CycloneSource("file2.txt", 35L, 45L, 1.0));

        Clone cycloneClone2 = new CycloneClone("module", 2L);
        cycloneClone2.addSource(new CycloneSource("filea.txt", 25L,
                30L, 2.0));
        cycloneClone2.addSource(new CycloneSource("file2.txt", 45L, 50L, 1.0));

        List<Clone> cloneList1 = Collections.singletonList(cycloneClone1);
        List<Clone> cloneList2 = Collections.singletonList(cycloneClone2);

        assertEquals(0.0, CloneComparer.compareCloneLists(cloneList1, cloneList2));
        assertEquals(0.0, CloneComparer.compareCloneLists(cloneList2, cloneList1));
    }

    @Nested
    public class PartialMatch {
        @Test
        public void halfOfListOneInListTwo() {
            List<Clone> cloneList1 = new ArrayList<>();

            Clone cycloneClone = new CycloneClone("module", 2L);
            cycloneClone.addSource(new CycloneSource("filea.txt", 15L, 20L, 2.0));
            cycloneClone.addSource(new CycloneSource("file2.txt", 35L, 45L, 1.0));
            cloneList1.add(cycloneClone);

            cycloneClone = new CycloneClone("function", 2L);
            cycloneClone.addSource(new CycloneSource("fileb.txt", 15L, 20L,
                    2.0));
            cycloneClone.addSource(new CycloneSource("filec.txt", 35L, 45L,
                    1.0));
            cloneList1.add(cycloneClone);

            List<Clone> cloneList2 = new ArrayList<>();

            cycloneClone = new CycloneClone("module", 2L);
            cycloneClone.addSource(new CycloneSource("filea.txt", 15L, 20L,
                    2.0));
            cycloneClone.addSource(new CycloneSource("file2.txt", 35L, 45L,
                    1.0));
            cloneList2.add(cycloneClone);

            assertEquals(0.5,
                    CloneComparer.compareCloneLists(cloneList1, cloneList2));
            assertEquals(1.0, CloneComparer.compareCloneLists(cloneList2,
                    cloneList1));
        }

        @Test
        public void testSmallerPercentage() {
            List<Clone> cloneList1 = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                Clone cycloneClone = new CycloneClone("module", 2L);
                cycloneClone.addSource(new CycloneSource("filea.txt",
                        5L * (i + 1), 5L * (i + 2), 2.0));
                cycloneClone.addSource(new CycloneSource("file2.txt",
                        6L * (i + 1), 6L * (i + 2), 1.0));
                cloneList1.add(cycloneClone);
            }

            List<Clone> cloneList2 = new ArrayList<>();
            Clone cycloneClone = new CycloneClone("function", 2L);
            cycloneClone.addSource(new CycloneSource("filea.txt", 5L, 10L,
                    2.0));
            cycloneClone.addSource(new CycloneSource("file2.txt", 6L, 12L,
                    1.0));
            cloneList2.add(cycloneClone);

            assertEquals(0.1, CloneComparer.compareCloneLists(cloneList1,
                    cloneList2));
            assertEquals(1.0, CloneComparer.compareCloneLists(cloneList2,
                    cloneList1));
        }

        @Test
        public void testPartialMatchBothWays() {
            List<Clone> cloneList1 = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                Clone cycloneClone = new CycloneClone("module", 2L);
                long startLine = 5L * (i + 1);
                cycloneClone.addSource(new CycloneSource("filea.txt",
                        startLine, startLine + 5, 2.0));
                startLine = 6L * (i + 1);
                cycloneClone.addSource(new CycloneSource("file2.txt",
                        startLine, startLine + 6, 1.0));
                cloneList1.add(cycloneClone);
            }

            List<Clone> cloneList2 = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                Clone nicadClone = new NiCadClone(23L, 2L);
                long startLine = 10L * (j + 1);
                nicadClone.addSource(new NiCadSource("filea.txt",
                        startLine, startLine + 5, 4L));
                startLine = 12L * (j + 1);
                nicadClone.addSource(new NiCadSource("file2.txt", startLine,
                        startLine + 6, 5L));
                cloneList2.add(nicadClone);
            }

            assertEquals(0.5, CloneComparer.compareCloneLists(cloneList1,
                    cloneList2));
            assertEquals(0.5, CloneComparer.compareCloneLists(cloneList2,
                    cloneList1));
        }
    }

    @Test
    public void testCloseMatch() {
        Clone cycloneClone1 = new CycloneClone("module", 2L);
        cycloneClone1.addSource(new CycloneSource("filea.txt", 15L,
                20L, 2.0));
        cycloneClone1.addSource(new CycloneSource("file2.txt", 35L, 45L, 1.0));

        Clone cycloneClone2 = new CycloneClone("module", 2L);
        cycloneClone2.addSource(new CycloneSource("filea.txt", 18L,
                23L, 2.0));
        cycloneClone2.addSource(new CycloneSource("file2.txt", 32L, 42L, 1.0));

        List<Clone> cloneList1 = Collections.singletonList(cycloneClone1);
        List<Clone> cloneList2 = Collections.singletonList(cycloneClone2);

        assertEquals(1.0, CloneComparer.compareCloneLists(cloneList1,
                cloneList2));
        assertEquals(1.0, CloneComparer.compareCloneLists(cloneList2,
                cloneList1));
    }

    @Test
    public void oneEmptyList() {
        List<Clone> cloneList1 = new ArrayList<>();

        Clone cycloneClone = new CycloneClone("module", 2L);
        cycloneClone.addSource(new CycloneSource("filea.txt", 15L,
                20L, 2.0));
        cycloneClone.addSource(new CycloneSource("file2.txt", 35L, 45L, 1.0));
        cloneList1.add(cycloneClone);

        List<Clone> cloneList2 = new ArrayList<>();

        assertEquals(0.0, CloneComparer.compareCloneLists(cloneList1, cloneList2));
        assertEquals(0.0, CloneComparer.compareCloneLists(cloneList2, cloneList1));
    }

    @Test
    public void twoEmptyLists() {
        List<Clone> cloneList1 = new ArrayList<>();
        List<Clone> cloneList2 = new ArrayList<>();

        assertEquals(1.0, CloneComparer.compareCloneLists(cloneList1,
                cloneList2));
        assertEquals(1.0, CloneComparer.compareCloneLists(cloneList2,
                cloneList1));
    }
}
