package source;

import com.csi.czech.source.MossSource;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

public class MossSourceTest extends SourceTest {
    public Stream<Double> getValidPercentages() {
        return Stream.of(0.0, 0.1, 0.32, 0.99, 1.0);
    }

    public Stream<Double> getInvalidPercentages() {
        return Stream.of(-1.0, -99.0, 1.1, 2.0, Double.MAX_VALUE);
    }

    @Nested
    public class ConstructorTest {
        @TestFactory
        public Stream<DynamicTest> testValid() {
            return getValidFilenames().flatMap(filename
                    -> getValidLineNumbers().flatMap(startLine
                    -> getValidLineNumbers().flatMap(endLine
                    -> getValidPercentages().map(percentMatch
                    -> dynamicTest("filename = " + filename +
                    ", start line = " + startLine + ", end line = " + endLine,
                    () -> {
                        MossSource source = new MossSource(filename,
                                startLine, endLine, percentMatch);
                        assertEquals(filename, source.getFilename());
                        assertEquals(startLine, source.getStartLine());
                        assertEquals(endLine, source.getEndLine());
                        assertEquals(percentMatch, source.getPercentMatch());
                    })))));
        }

        @Test
        public void testNullFilename() {
            assertThrows(NullPointerException.class,
                    () -> new MossSource(null, 0L, 0L, 0.0));
        }

        @TestFactory
        public Stream<DynamicTest> testInvalidStartLine() {
            return getInvalidLineNumbers().map(startLine
                    -> dynamicTest("start line = " + startLine,
                    () -> assertThrows(IllegalArgumentException.class,
                            () -> new MossSource("filename", startLine, 0L,
                                    0.0))));
        }

        @TestFactory
        public Stream<DynamicTest> testInvalidEndLine() {
            return getInvalidLineNumbers().map(endLine
                    -> dynamicTest("end line = " + endLine,
                    () -> assertThrows(IllegalArgumentException.class,
                            () -> new MossSource("filename", 0L, endLine,
                                    0.0))));
        }

        @TestFactory
        public Stream<DynamicTest> testInvalidPercentMatch() {
            return getInvalidPercentages().map(percentMatch -> dynamicTest(
                    "percent match = " + percentMatch,
                    () -> assertThrows(IllegalArgumentException.class,
                            () -> new MossSource("filename", 0L, 0L,
                                    percentMatch))));
        }
    }

    @TestFactory
    public Stream<DynamicTest> testEqualsAndHashcode() {
        return getValidFilenames().flatMap(filename
                -> getValidLineNumbers().flatMap(startLine
                -> getValidLineNumbers().flatMap(endLine
                -> getValidPercentages().map(percentMatch
                -> dynamicTest("filename = " + filename +
                        ", start line = " + startLine + ", end line = " + endLine,
                () -> {
                    MossSource source1 = new MossSource(filename,
                            startLine, endLine, percentMatch);
                    MossSource source2 = new MossSource(filename,
                            startLine, endLine, percentMatch);
                    assertEquals(source1, source2);
                    assertEquals(source1.hashCode(), source2.hashCode());
                })))));
    }
}
