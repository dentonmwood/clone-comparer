package clone;

import com.csi.czech.clone.Clone;
import com.csi.czech.clone.MossClone;
import com.csi.czech.clone.NiCadClone;
import com.csi.czech.clone.PyCloneClone;
import com.csi.czech.source.MossSource;
import com.csi.czech.source.NiCadSource;
import com.csi.czech.source.PyCloneSource;
import org.junit.jupiter.api.*;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

public class MossCloneTest {
    public static Stream<Queue<MossSource>> sources;

    @BeforeAll
    public static void init() {
        List<Queue<MossSource>> sourceList = new ArrayList<>();
        List<Integer> listSizes = Arrays.asList(0, 1, 2);
        for (Integer listSize: listSizes) {
            Queue<MossSource> l = new PriorityQueue<>();
            for (long i = 0; i < listSize; i++) {
                l.add(new MossSource("filename" + i, i + 1, i + 2));
            }
            sourceList.add(l);
        }
        sources = sourceList.stream();
    }

    @Nested
    public class ConstructorTest {
        @TestFactory
        public Stream<DynamicTest> testValid() {
            return sources.map(sourceQueue
                    -> dynamicTest("size = " + sourceQueue.size(), () -> {
                MossClone clone = new MossClone();
                for (MossSource source: sourceQueue) {
                    clone.addSource(source);
                }
                assertNotEquals(sourceQueue, clone.getSources());
            }));
        }
    }

    @Nested
    public class EqualsTest {
        @Test
        public void testExact() {
            Clone clone1 = new MossClone();
            clone1.addSource(new MossSource("file.txt", 15L, 20L));
            clone1.addSource(new MossSource("file2.txt", 33L, 45L));
            Clone clone2 = new MossClone();
            clone2.addSource(new MossSource("file.txt", 15L, 20L));
            clone2.addSource(new MossSource("file2.txt", 33L, 45L));
            assertEquals(clone1, clone2);
        }

        @Test
        public void testNestedSameClass() {
            Clone clone1 = new MossClone();
            clone1.addSource(new MossSource("file.txt", 15L, 17L));
            clone1.addSource(new MossSource("file2.txt", 17L, 18L));

            Clone clone2 = new MossClone();
            clone2.addSource(new MossSource("file.txt", 5L, 25L));
            clone2.addSource(new MossSource("file2.txt", 6L, 21L));

            assertEquals(clone1, clone2);
        }

        @Test
        public void testNestedMultiClassNiCad() {
            Clone clone1 = new MossClone();
            clone1.addSource(new MossSource("file.txt", 15L, 17L));
            clone1.addSource(new MossSource("file2.txt", 17L, 18L));

            Clone clone2 = new NiCadClone(22L, 2L);
            clone2.addSource(new NiCadSource("file.txt", 5L, 25L, 2L));
            clone2.addSource(new NiCadSource("file2.txt", 6L, 21L, 2L));

            assertEquals(clone1, clone2);
        }

        @Test
        public void testNestedMultiClassPyClone() {
            Clone clone1 = new MossClone();
            clone1.addSource(new MossSource("file.txt", 15L, 17L));
            clone1.addSource(new MossSource("file2.txt", 17L, 18L));

            Clone clone2 = new PyCloneClone("module", 2L);
            clone2.addSource(new PyCloneSource("file.txt", 5L, 25L, 2.0));
            clone2.addSource(new PyCloneSource("file2.txt", 6L, 21L, 2.0));

            assertEquals(clone1, clone2);
        }
    }
}
