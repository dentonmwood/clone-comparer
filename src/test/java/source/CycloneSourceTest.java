package source;

import com.csi.czech.source.CycloneSource;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

public class CycloneSourceTest extends SourceTest {
    public Stream<Double> getValidWeights() {
        return Stream.of(0.0, 1.0, 34.2354, Double.MAX_VALUE);
    }

    public Stream<Double> getInvalidWeights() {
        return Stream.of(-0.1, -1.0, -324.324, Double.MIN_NORMAL);
    }

    @Nested
    public class ConstructorTest {
        @TestFactory
        public Stream<DynamicTest> testValid() {
            return getValidFilenames().flatMap(filename
                    -> getValidLineNumbers().flatMap(startLine
                    -> getValidLineNumbers().flatMap(endLine
                    -> getValidWeights().map(weight
                    -> dynamicTest("filename = " + filename
                    + ", startline = " + startLine + ", endline = " + endLine, () -> {
                        CycloneSource source = new CycloneSource(filename, startLine, endLine, weight);
                        assertEquals(filename, source.getFilename());
                        assertEquals(startLine, source.getStartLine());
                        assertEquals(endLine, source.getEndLine());
            })))));
        }

        @Test
        public void testNullFilename() {
            assertThrows(NullPointerException.class,
                    () -> new CycloneSource(null, 0L, 0L, 0.0));
        }

        @TestFactory
        public Stream<DynamicTest> testInvalidStartLine() {
            return getInvalidLineNumbers().map(startLine
                    -> dynamicTest("startLine = " + startLine, () -> {
                assertThrows(IllegalArgumentException.class,
                        () -> new CycloneSource("filename", startLine, 0L, 0.0));
            }));
        }

        @TestFactory
        public Stream<DynamicTest> testInvalidEndLine() {
            return getInvalidLineNumbers().map(endLine -> dynamicTest("endLine = " + endLine, () -> {
                assertThrows(IllegalArgumentException.class, () -> new CycloneSource("filename", 0L, endLine, 0.0));
            }));
        }

        @TestFactory
        public Stream<DynamicTest> testInvalidWeight() {
            return getInvalidWeights().map(weight -> dynamicTest("weight = " + weight, () -> {
                assertThrows(IllegalArgumentException.class, () -> new CycloneSource("filename", 0L, 0L, weight));
            }));
        }
    }

    @TestFactory
    public Stream<DynamicTest> testEqualsAndHashcode() {
        return getValidFilenames().flatMap(filename
                -> getValidLineNumbers().flatMap(startLine
                -> getValidLineNumbers().flatMap(endLine
                -> getValidWeights().map(weight
                -> dynamicTest("filename = " + filename
                + ", startline = " + startLine + ", endline = " + endLine, () -> {
            CycloneSource source1 = new CycloneSource(filename, startLine, endLine, weight);
            CycloneSource source2 = new CycloneSource(filename, startLine, endLine, weight);
            assertEquals(source1, source2);
            assertEquals(source1.hashCode(), source2.hashCode());
        })))));
    }

    @TestFactory
    public Stream<DynamicTest> testToString() {
        return getValidFilenames().flatMap(filename
                -> getValidLineNumbers().flatMap(startLine
                -> getValidLineNumbers().flatMap(endLine
                -> getValidWeights().map(weight
                -> dynamicTest("filename = " + filename
                + ", startline = " + startLine + ", endline = " + endLine, () -> {
            CycloneSource source = new CycloneSource(filename, startLine, endLine, weight);
            assertEquals("CycloneSource{weight=" + weight + ", filename='" + filename + '\'' +
                    ", startLine=" + startLine + ", endLine=" + endLine + '}', source.toString());
        })))));
    }
}
