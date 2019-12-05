import com.csi.czech.cmd.CLIParser;
import com.csi.czech.cmd.CloneOptions;
import org.apache.commons.cli.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class CloneOptionsTest {
    private static CLIParser parser;

    @BeforeAll
    static void init() {
        parser = new CLIParser();
    }

    @Test
    public void testOne() throws IOException, ParseException {
        String oxygenFile = "oxygen.json";
        String nicadBlocksFile = "nicad-blocks.xml";
        String[] args = {"-pO", oxygenFile, "-nB", nicadBlocksFile};
        CloneOptions options = parser.parseArgs(args);
        Assertions.assertEquals(oxygenFile, options.getPycloneOxygenFile());
        Assertions.assertEquals(nicadBlocksFile, options.getNicadBlocksFile());
    }
}
