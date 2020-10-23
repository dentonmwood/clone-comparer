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
    @Nested
    public class ConstructorTest {
        @TestFactory
        public Stream<DynamicTest> testValid() {
            return getValidFilenames().flatMap(filename
                    -> getValidLineNumbers().flatMap(startLine
                    -> getValidLineNumbers().map(endLine
                    -> dynamicTest("filename = " + filename +
                    ", start line = " + startLine + ", end line = " + endLine,
                    () -> {
                        MossSource source = new MossSource(filename,
                                startLine, endLine);
                        assertEquals(filename, source.getFilename());
                        assertEquals(startLine, source.getStartLine());
                        assertEquals(endLine, source.getEndLine());
                    }))));
        }

        @Test
        public void testNullFilename() {
            assertThrows(NullPointerException.class,
                    () -> new MossSource(null, 0L, 0L));
        }

        @TestFactory
        public Stream<DynamicTest> testInvalidStartLine() {
            return getInvalidLineNumbers().map(startLine
                    -> dynamicTest("start line = " + startLine,
                    () -> assertThrows(IllegalArgumentException.class,
                            () -> new MossSource("filename", startLine, 0L))));
        }

        @TestFactory
        public Stream<DynamicTest> testInvalidEndLine() {
            return getInvalidLineNumbers().map(endLine
                    -> dynamicTest("end line = " + endLine,
                    () -> assertThrows(IllegalArgumentException.class,
                            () -> new MossSource("filename", 0L, endLine))));
        }
    }

    @TestFactory
    public Stream<DynamicTest> testEqualsAndHashcode() {
        return getValidFilenames().flatMap(filename
                -> getValidLineNumbers().flatMap(startLine
                -> getValidLineNumbers().map(endLine
                -> dynamicTest("filename = " + filename +
                        ", start line = " + startLine + ", end line = " + endLine,
                () -> {
                    MossSource source1 = new MossSource(filename,
                            startLine, endLine);
                    MossSource source2 = new MossSource(filename,
                            startLine, endLine);
                    assertEquals(source1, source2);
                    assertEquals(source1.hashCode(), source2.hashCode());
                }))));
    }
}
