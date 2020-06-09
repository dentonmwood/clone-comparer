package clone;

import com.csi.czech.clone.MossClone;
import com.csi.czech.source.MossSource;
import com.csi.czech.source.PyCloneSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.TestFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

public class MossCloneTest {
    public static Stream<Queue<MossSource>> sources;

    @BeforeAll
    public static void init() {
        List<Queue<MossSource>> sourceList = new ArrayList<>();
        List<Integer> listSizes = Arrays.asList(0, 1, 5, 17);
        for (Integer listSize: listSizes) {
            Queue<MossSource> l = new ArrayList<>();
            for (long i = 0; i < listSize; i++) {
                l.add(new MossSource("filename" + i, i, i + 1);
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
}
