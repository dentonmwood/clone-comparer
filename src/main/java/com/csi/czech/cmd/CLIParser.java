package com.csi.czech.cmd;

import org.apache.commons.cli.*;

import java.io.IOException;

/**
 *
 */
public class CLIParser {
    private final Options options;
    private final CommandLineParser parser;

    // Get help
    private static final String HELP = "h";

    // Modes
    private static final String MODE = "M";
    private static final String SINGLE_MODE = "s";
    private static final String DOUBLE_MODE = "d";
    private static final String HEADER_MODE = "h";

    // Comparison options
    private static final String ONLY_SHOW_COUNTS = "c";

    // File options
    private static final String PYCLONE_OXYGEN = "pO";
    private static final String PYCLONE_CHLORINE = "pC";
    private static final String PYCLONE_IODINE = "pI";
    private static final String NICAD_BLOCKS = "nB";
    private static final String NICAD_FUNCTIONS = "nF";
    private static final String MOSS = "m";

    // Header options
    private static final String INCLUDE_ALGORITHM = "a";

    /**
     * Constructor for the CLI Parser class. Initializes
     * the Commons-CLI parser with the correct command-line
     * options.
     */
    public CLIParser() {
        this.options = new Options();
        this.options.addOption(INCLUDE_ALGORITHM, true,
                "Specify algorithm to include in header (only used in header "
                        + "mode)");
        this.options.addOption(HELP, "get this page");
        this.options.addOption(MODE, true,
                "Set mode - (s)ingle, (d)ouble, (h)eader");
        this.options.addOption(ONLY_SHOW_COUNTS, false,
                "only show tool counts, no percentages or times");
        this.options.addOption(PYCLONE_OXYGEN, true,
                "Specify Cyclone Oxygen file to run (single mode only)");
        this.options.addOption(PYCLONE_CHLORINE, true,
                "Specify Cyclone Chlorine file to run (single or double)");
        this.options.addOption(PYCLONE_IODINE, true,
                "Specify Cyclone Iodine file to run (double only)");
        this.options.addOption(NICAD_BLOCKS, true,
                "Specify NiCad Blocks file to run (single or double)");
        this.options.addOption(NICAD_FUNCTIONS, true,
                "Specify NiCad Functions file to run (single or double)");
        this.options.addOption(MOSS, true,
                "Specify Moss file to run (single only)");
        this.parser = new DefaultParser();
    }

    /**
     * Parses the command-line arguments and returns the results. Results
     * may be one of three classes:
     * <ul>
     *     <li>CloneHelpOptions - print the help dialog</li>
     *     <li>CloneHeaderOptions - print the header</li>
     *     <li>CloneFileOptions - handle the passed files and print the
     *     similarities</li>
     * </ul>
     *
     * @param args the command-line arguments to parse
     * @return an object representing the parsed arguments
     * @throws IOException if the arguments can't be parsed
     */
    public CloneOptions parseArgs(String[] args) throws IOException {
        try {
            CommandLine cmd = parser.parse(this.options, args);

            if (cmd.hasOption(HELP)) {
                return new CloneHelpOptions(this.options, new HelpFormatter());
            }

            if (cmd.hasOption(MODE)) {
                String mode = cmd.getOptionValue(MODE);
                return switch (mode) {
                    case SINGLE_MODE -> receiveFiles(cmd,
                            CloneFileOptions.CloneMode.SINGLE);
                    case DOUBLE_MODE -> receiveFiles(cmd,
                            CloneFileOptions.CloneMode.DOUBLE);
                    case HEADER_MODE -> receiveHeaderOptions(cmd);
                    default -> throw new IllegalArgumentException(
                            "Unexpected value: " + mode);
                };
            } else {
                throw new IOException("Mode is required");
            }
        } catch (ParseException e) {
            throw new IOException(e);
        }
    }

    /**
     * Processes CLI args and returns an object with the parsed files
     * to be compared
     *
     * @param cmd the parsed arguments
     * @param mode the mode of the results (single/double)
     * @return the processed file arguments
     */
    private CloneFileOptions receiveFiles(CommandLine cmd, CloneFileOptions.CloneMode mode) {
        boolean cyclone = false;
        boolean benchmark = false;

        CloneFileOptions cloneOptions = new CloneFileOptions();
        cloneOptions.setMode(mode);

        if (cmd.hasOption(PYCLONE_OXYGEN)) {
            cloneOptions.setCycloneOxygenFile(cmd.getOptionValue(PYCLONE_OXYGEN));
            cyclone = true;
        }
        if (cmd.hasOption(PYCLONE_CHLORINE)) {
            cloneOptions.setCycloneChlorineFile(cmd.getOptionValue(PYCLONE_CHLORINE));
            cyclone = true;
        }
        if (cmd.hasOption(PYCLONE_IODINE)) {
            cloneOptions.setCycloneIodineFile(cmd.getOptionValue(PYCLONE_IODINE));
            cyclone = true;
        }

        if (cmd.hasOption(NICAD_BLOCKS)) {
            cloneOptions.setNicadBlocksFile(cmd.getOptionValue(NICAD_BLOCKS));
            benchmark = true;
        }
        if (cmd.hasOption(NICAD_FUNCTIONS)) {
            cloneOptions.setNicadFunctionsFile(cmd.getOptionValue(NICAD_FUNCTIONS));
            benchmark = true;
        }
        if (cmd.hasOption(MOSS)) {
            cloneOptions.setMossFile(cmd.getOptionValue(MOSS));
            benchmark = true;
        }

        if (!cyclone) {
            throw new IllegalArgumentException("Must specify at least one Cyclone file");
        }
        if (!benchmark && !cmd.hasOption(ONLY_SHOW_COUNTS)) {
            throw new IllegalArgumentException("Must specify at least one benchmark file");
        }
        return cloneOptions;
    }

    /**
     * Processes the header arguments to print
     *
     * @param cmd the parsed CLI arguments
     * @return the header options to print
     */
    private CloneHeaderOptions receiveHeaderOptions(CommandLine cmd) {
        CloneHeaderOptions headerOptions = new CloneHeaderOptions();
        for (String arg: cmd.getArgs()) {
            switch (arg) {
                case PYCLONE_OXYGEN -> headerOptions.setCycloneOxygen(true);
                case PYCLONE_CHLORINE -> headerOptions.setCycloneChlorine(true);
                case PYCLONE_IODINE -> headerOptions.setCycloneIodine(true);
                case NICAD_BLOCKS -> headerOptions.setNicadBlocks(true);
                case NICAD_FUNCTIONS -> headerOptions.setNicadFunctions(true);
                case MOSS -> headerOptions.setMoss(true);
                default -> throw new IllegalArgumentException(
                        "Unexpected header option: " + arg);
            }
        }
        return headerOptions;
    }
}
