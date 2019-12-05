package com.csi.czech.cmd;

import org.apache.commons.cli.*;

import java.io.IOException;

public class CLIParser {
    private Options options;
    private CommandLineParser parser;

    private static String DUAL_MODE = "d";
    private static String PYCLONE_OXYGEN = "pO";
    private static String PYCLONE_CHLORINE = "pC";
    private static String PYCLONE_IODINE = "pI";
    private static String NICAD_BLOCKS = "nB";
    private static String NICAD_FUNCTIONS = "nF";
    private static String MOSS = "m";

    public CLIParser() {
        this.options = new Options();
        this.options.addOption(DUAL_MODE, false, "Run in dual-repo mode");
        this.options.addOption(PYCLONE_OXYGEN, true, "Specify PyClone Oxygen file to run");
        this.options.addOption(PYCLONE_CHLORINE, true, "Specify PyClone Chlorine file to run");
        this.options.addOption(PYCLONE_IODINE, true, "Specify PyClone Iodine file to run");
        this.options.addOption(NICAD_BLOCKS, true, "Specify NiCad Blocks file to run");
        this.options.addOption(NICAD_FUNCTIONS, true, "Specify NiCad Functions file to run");
        this.options.addOption(MOSS, false, "Specify Moss file to run");
        this.parser = new DefaultParser();
    }

    public CloneOptions parseArgs(String[] args) throws IOException {
        try {
            CommandLine cmd = parser.parse(this.options, args);
            boolean pyclone = false;
            boolean benchmark = false;

            CloneOptions cloneOptions = new CloneOptions();

            if (cmd.hasOption(DUAL_MODE)) {
                cloneOptions.setDualMode(true);
            } else {
                cloneOptions.setDualMode(false);
            }

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

            if (!pyclone || !benchmark) {
                throw new IOException("Must specify at least one PyClone file and one benchmark file");
            }

            return cloneOptions;
        } catch (ParseException e) {
            throw new IOException(e);
        }
    }
}
