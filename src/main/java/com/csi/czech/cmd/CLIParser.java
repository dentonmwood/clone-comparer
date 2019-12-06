package com.csi.czech.cmd;

import org.apache.commons.cli.*;

import java.io.IOException;

public class CLIParser {
    private Options options;
    private CommandLineParser parser;

    // Get help
    private static final String HELP = "h";

    // Modes
    private static final String MODE = "M";
    private static final String SINGLE_MODE = "s";
    private static final String DOUBLE_MODE = "d";
    private static final String HEADER_MODE = "h";

    // File options
    private static final String PYCLONE_OXYGEN = "pO";
    private static final String PYCLONE_CHLORINE = "pC";
    private static final String PYCLONE_IODINE = "pI";
    private static final String NICAD_BLOCKS = "nB";
    private static final String NICAD_FUNCTIONS = "nF";
    private static final String MOSS = "m";

    // Header options
    private static final String INCLUDE_ALGORITHM = "a";

    public CLIParser() {
        this.options = new Options();
        this.options.addOption(INCLUDE_ALGORITHM, true, "Specify algorithm to include in header (only used in header mode)");
        this.options.addOption(HELP, "get this page");
        this.options.addOption(MODE, true, "Set mode - (s)ingle, (d)ouble, (h)eader");
        this.options.addOption(PYCLONE_OXYGEN, true, "Specify PyClone Oxygen file to run (single mode only)");
        this.options.addOption(PYCLONE_CHLORINE, true, "Specify PyClone Chlorine file to run (single or double)");
        this.options.addOption(PYCLONE_IODINE, true, "Specify PyClone Iodine file to run (double only)");
        this.options.addOption(NICAD_BLOCKS, true, "Specify NiCad Blocks file to run (single or double)");
        this.options.addOption(NICAD_FUNCTIONS, true, "Specify NiCad Functions file to run (single or double)");
        this.options.addOption(MOSS, true, "Specify Moss file to run (single only)");
        this.parser = new DefaultParser();
    }

    public CloneOptions parseArgs(String[] args) throws IOException {
        try {
            CommandLine cmd = parser.parse(this.options, args);

            if (cmd.hasOption(HELP)) {
                return new CloneHelpOptions(this.options, new HelpFormatter());
            }

            if (cmd.hasOption(MODE)) {
                String mode = cmd.getOptionValue(MODE);
                switch (mode) {
                    case SINGLE_MODE:
                        return receiveFiles(cmd, CloneFileOptions.CloneMode.SINGLE);
                    case DOUBLE_MODE:
                        return receiveFiles(cmd, CloneFileOptions.CloneMode.DOUBLE);
                    case HEADER_MODE:
                        return receiveHeaderOptions(cmd);
                    default:
                        throw new IllegalArgumentException("Unexpected value: " + mode);
                }
            } else {
                throw new IOException("Mode is required");
            }
        } catch (ParseException e) {
            throw new IOException(e);
        }
    }

    private CloneFileOptions receiveFiles(CommandLine cmd, CloneFileOptions.CloneMode mode) {
        boolean pyclone = false;
        boolean benchmark = false;

        CloneFileOptions cloneOptions = new CloneFileOptions();
        cloneOptions.setMode(mode);

        if (cmd.hasOption(PYCLONE_OXYGEN)) {
            cloneOptions.setPycloneOxygenFile(cmd.getOptionValue(PYCLONE_OXYGEN));
            pyclone = true;
        }
        if (cmd.hasOption(PYCLONE_CHLORINE)) {
            cloneOptions.setPycloneChlorineFile(cmd.getOptionValue(PYCLONE_CHLORINE));
            pyclone = true;
        }
        if (cmd.hasOption(PYCLONE_IODINE)) {
            cloneOptions.setPycloneIodineFile(cmd.getOptionValue(PYCLONE_IODINE));
            pyclone = true;
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

        if (!pyclone) {
            throw new IllegalArgumentException("Must specify at least one PyClone file");
        }
        if (!benchmark) {
            throw new IllegalArgumentException("Must specify at least one benchmark file");
        }
        return cloneOptions;
    }

    private CloneHeaderOptions receiveHeaderOptions(CommandLine cmd) {
        CloneHeaderOptions headerOptions = new CloneHeaderOptions();
        for (String arg: cmd.getArgs()) {
            switch (arg) {
                case PYCLONE_OXYGEN:
                    headerOptions.setPycloneOxygen(true);
                    break;
                case PYCLONE_CHLORINE:
                    headerOptions.setPycloneChlorine(true);
                    break;
                case PYCLONE_IODINE:
                    headerOptions.setPycloneIodine(true);
                    break;
                case NICAD_BLOCKS:
                    headerOptions.setNicadBlocks(true);
                    break;
                case NICAD_FUNCTIONS:
                    headerOptions.setNicadFunctions(true);
                    break;
                case MOSS:
                    headerOptions.setMoss(true);
                    break;
                default:
                    throw new IllegalArgumentException("Unexpected header option: " + arg);
            }
        }
        return headerOptions;
    }
}
