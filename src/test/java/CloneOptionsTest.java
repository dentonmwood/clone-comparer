import com.csi.czech.cmd.CLIParser;
import com.csi.czech.cmd.CloneFileOptions;
import com.csi.czech.cmd.CloneHeaderOptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class CloneOptionsTest {
    private static CLIParser parser;

    @BeforeAll
    static void init() {
        parser = new CLIParser();
    }

    @Nested
    class CloneFileOptionsTest {
        @Test
        public void testOneEach() throws IOException {
            String oxygenFile = "oxygen.json";
            String nicadBlocksFile = "nicad-blocks.xml";
            String[] args = {"-pO", oxygenFile, "-nB", nicadBlocksFile, "-M", "s"};
            CloneFileOptions options = (CloneFileOptions) parser.parseArgs(args);
            assertEquals(oxygenFile, options.getPycloneOxygenFile());
            assertEquals(nicadBlocksFile, options.getNicadBlocksFile());
            assertEquals(CloneFileOptions.CloneMode.SINGLE, options.getMode());
        }

        @Test
        public void testAllFiles() throws IOException {
            String oxygenFile = "oxygen.json";
            String chlorineFile = "test/chlorine.json";
            String iodineFile = "iodine.json";
            String nicadBlocksFile = "nicad_blocks.xml";
            String nicadFunctionsFile = "nicad_functions.xml";
            String mossFile = "moss";
            String[] args = {"-pO", oxygenFile, "-pC", chlorineFile, "-pI", iodineFile,
                    "-nB", nicadBlocksFile, "-nF", nicadFunctionsFile, "-m", mossFile, "-M", "d"};
            CloneFileOptions options = (CloneFileOptions) parser.parseArgs(args);
            assertEquals(oxygenFile, options.getPycloneOxygenFile());
            assertEquals(chlorineFile, options.getPycloneChlorineFile());
            assertEquals(iodineFile, options.getPycloneIodineFile());
            assertEquals(nicadBlocksFile, options.getNicadBlocksFile());
            assertEquals(nicadFunctionsFile, options.getNicadFunctionsFile());
            assertEquals(mossFile, options.getMossFile());
            assertEquals(CloneFileOptions.CloneMode.DOUBLE, options.getMode());
        }

        @Test
        public void testMissingPyclone() {
            String[] args = {"-nB", "nicad_blocks.xml", "-M", "s"};
            assertThrows(IllegalArgumentException.class, () -> {parser.parseArgs(args);});
        }

        @Test
        public void testMissingBenchmark() {
            String[] args = {"-pI", "iodine.json", "-M", "d"};
            assertThrows(IllegalArgumentException.class, () -> {parser.parseArgs(args);});
        }

        @Test
        public void testMissingArgument() {
            String[] args = {"-M", "d", "-pC"};
            assertThrows(IOException.class, () -> {parser.parseArgs(args);});
        }

        @Test
        public void testMissingMode() {
            String[] args = {"-pO", "oxygen.json", "-m", "moss.txt"};
            assertThrows(IOException.class, () -> {parser.parseArgs(args);});
        }
    }

    @Nested
    class CloneHeaderOptionsTest {
        @Test
        public void testOne() throws IOException {
            String[] args = {"-M", "h", "pO"};
            CloneHeaderOptions options = (CloneHeaderOptions) parser.parseArgs(args);
            assertTrue(options.isPycloneOxygen());
            assertFalse(options.isPycloneChlorine());
            assertFalse(options.isPycloneIodine());
            assertFalse(options.isNicadBlocks());
            assertFalse(options.isNicadFunctions());
            assertFalse(options.isMoss());
        }

        @Test
        public void testAll() throws IOException {
            String[] args = {"-M", "h", "pO", "pC", "pI", "nB", "nF", "m"};
            CloneHeaderOptions options = (CloneHeaderOptions) parser.parseArgs(args);
            assertTrue(options.isPycloneOxygen());
            assertTrue(options.isPycloneChlorine());
            assertTrue(options.isPycloneIodine());
            assertTrue(options.isNicadBlocks());
            assertTrue(options.isNicadFunctions());
            assertTrue(options.isMoss());
        }

        @Test
        public void testBad() {
            String[] args = {"-M", "h", "c"};
            assertThrows(IllegalArgumentException.class, () -> parser.parseArgs(args));
        }
    }
}
