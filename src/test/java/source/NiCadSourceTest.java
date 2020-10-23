package source;

import com.csi.czech.source.NiCadSource;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

public class NiCadSourceTest extends SourceTest {
    public Stream<Long> getValidPcIds() {
        return Stream.of(0L, 1L, Long.MAX_VALUE);
    }

    public Stream<Long> getInvalidPcIds() {
        return Stream.of(-1L, Long.MIN_VALUE);
    }

    @Nested
    public class ConstructorTest {
        @TestFactory
        public Stream<DynamicTest> testValid() {
            return getValidFilenames().flatMap(filename
                    -> getValidLineNumbers().flatMap(startLine
                    -> getValidLineNumbers().flatMap(endLine
                    -> getValidPcIds().map(pcId -> dynamicTest("filename = "
                    + filename + ", start line = " + startLine + ", end line "
                    + "= " + endLine + ", pcId = " + pcId, () -> {
                NiCadSource source = new NiCadSource(filename, startLine,
                        endLine, pcId);
            })))));
        }

        @Test
        public void testNullFilename() {
            assertThrows(NullPointerException.class,
                    () -> new NiCadSource(null, 0L, 0L, 0L));
        }

        @TestFactory
        public Stream<DynamicTest> testInvalidStartLine() {
            return getInvalidLineNumbers().map(startLine -> dynamicTest(
                    "start line = " + startLine, () -> {
                        assertThrows(IllegalArgumentException.class,
                                () -> new NiCadSource("filename", startLine,
                                        0L, 0L));
                    }));
        }

        @TestFactory
        public Stream<DynamicTest> testInvalidEndLine() {
            return getInvalidLineNumbers().map(endLine -> dynamicTest("end "
                    + "line = " + endLine, () -> {
                assertThrows(IllegalArgumentException.class,
                        () -> new NiCadSource(
                        "filename", 0L, endLine, 0L));
            }));
        }

        @TestFactory
        public Stream<DynamicTest> testInvalidPcIds() {
            return getInvalidPcIds().map(pcId -> dynamicTest("pcId = " + pcId,
                    () -> assertThrows(IllegalArgumentException.class,
                            () -> new NiCadSource("filename", 0L, 0L,
                    pcId))));
        }
    }

    @TestFactory
    public Stream<DynamicTest> testEqualsAndHashCode() {
        return getValidFilenames().flatMap(filename
                -> getValidLineNumbers().flatMap(startLine
                -> getValidLineNumbers().flatMap(endLine
                -> getValidPcIds().map(pcId -> dynamicTest("filename = "
                + filename + ", start line = " + startLine + ", end line "
                + "= " + endLine + ", pcId = " + pcId, () -> {
            NiCadSource source1 = new NiCadSource(filename, startLine,
                    endLine, pcId);
            NiCadSource source2 = new NiCadSource(filename, startLine,
                    endLine, pcId);
            assertEquals(source1, source2);
            assertEquals(source1.hashCode(), source2.hashCode());
        })))));
    }
}
