package com.csi.czech.cmd;

import com.csi.czech.clone.Clone;
import com.csi.czech.comparer.CloneComparer;
import com.csi.czech.reader.MossCloneReader;
import com.csi.czech.reader.NiCadCloneReader;
import com.csi.czech.reader.PyCloneCloneReader;
import com.gargoylesoftware.htmlunit.WebClient;
import org.json.simple.parser.JSONParser;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles arguments given to the command-line. This is an implementation of the
 * Visitor pattern - each type of command-line options class calls its visit method,
 * and the handler handles things accordingly.
 */
public class CLIHandler {

    private final PrintStream sysOut;
    private final Logger logger;

    public CLIHandler(PrintStream sysOut) {
        this.sysOut = sysOut;
        this.logger = Logger.getLogger(this.getClass().toString());
    }

    /**
     * Prints the header line for the results. This should be called in a separate run
     * from file processing and only with the algorithms to run
     *
     * @param options which headers to print
     */
    public void visit(CloneHeaderOptions options) {
        StringBuilder header = new StringBuilder();
        // Leave space for when double algorithm runs
        header.append(",");
        for (CloneHeaderOptions.PyCloneTool tool: options.getPyCloneTools()) {
            header.append("# of ").append(tool.toString()).append(" clones,");
        }
        for (CloneHeaderOptions.BenchmarkTool tool: options.getBenchmarkTools()) {
            header.append("# of ").append(tool.toString()).append(" clones,");
        }
        for (CloneHeaderOptions.PyCloneTool pyCloneTool: options.getPyCloneTools()) {
            for (CloneHeaderOptions.BenchmarkTool benchmarkTool: options.getBenchmarkTools()) {
                header.append("% of ").append(pyCloneTool).append(" in ").append(benchmarkTool).append(",");
                header.append("% of ").append(benchmarkTool).append(" in ").append(pyCloneTool).append(",");
            }
        }
        sysOut.println(header.toString());
    }

    /**
     * Prints the help statement
     * @param options the options to print
     */
    public void visit(CloneHelpOptions options) {
        options.getHelpFormatter().printHelp("clone-comparer", options.getOptions());
    }

    /**
     * Handles the actual processing of the clone files. Takes a series of files,
     * reads the clones in those files, and attempts to process them.
     *
     * @param options the files to process
     * @throws Exception if one or more of the files cannot be read
     */
    public void visit(CloneFileOptions options) {
        // Read the PyClone clones
        List<List<Clone>> pyCloneClones = new ArrayList<>();
        PyCloneCloneReader pyCloneCloneReader = new PyCloneCloneReader(new JSONParser());
        if (options.getPycloneOxygenFile() != null) {
            try {
                pyCloneClones.add(pyCloneCloneReader.readClones(options.getPycloneOxygenFile()));
            } catch (Exception e) {
                pyCloneClones.add(new ArrayList<>());
                logger.log(Level.WARNING,
                        "Could not process Oxygen: " + e.toString());
            }
        }
        if (options.getPycloneChlorineFile() != null) {
            try {
                pyCloneClones.add(pyCloneCloneReader.readClones(options.getPycloneChlorineFile()));
            } catch (Exception e) {
                pyCloneClones.add(new ArrayList<>());
                logger.log(Level.WARNING,
                        "Could not process Chlorine: " + e.toString());
            }
        }
        if (options.getPycloneIodineFile() != null) {
            try {
                pyCloneClones.add(pyCloneCloneReader.readClones(options.getPycloneIodineFile()));
            } catch (Exception e) {
                pyCloneClones.add(new ArrayList<>());
                logger.log(Level.WARNING,
                        "Could not process Iodine: " + e.toString());
            }
        }


        // Read benchmark tool clones
        List<List<Clone>> benchmarkClones = new ArrayList<>();

        // Read the NiCad clones
        NiCadCloneReader niCadCloneReader = new NiCadCloneReader();
        if (options.getNicadBlocksFile() != null) {
            try {
                benchmarkClones.add(niCadCloneReader.readClones(options.getNicadBlocksFile()));
            } catch (Exception e) {
                benchmarkClones.add(new ArrayList<>());
                logger.log(Level.WARNING,
                        "Could not process NiCad Blocks: " + e.toString());
            }
        }
        if (options.getNicadFunctionsFile() != null) {
            try {
                benchmarkClones.add(niCadCloneReader.readClones(options.getNicadFunctionsFile()));
            } catch (Exception e) {
                benchmarkClones.add(new ArrayList<>());
                logger.log(Level.WARNING,
                        "Could not process NiCad Functions: " + e.toString());
            }
        }

        // Read the Moss clones
        if (options.getMossFile() != null) {
            MossCloneReader mossCloneReader = new MossCloneReader(new WebClient());
            try {
                benchmarkClones.add(mossCloneReader.readClones(options.getMossFile()));
            } catch (Exception e) {
                pyCloneClones.add(new ArrayList<>());
                logger.log(Level.WARNING,
                        "Could not process Moss: " + e.toString());
            }
        }

        // Compare the clones
        CloneComparer comparer = new CloneComparer();
        List<Double> percentages = new ArrayList<>();
        for (List<Clone> pyCloneList: pyCloneClones) {
            for (List<Clone> benchmarkList: benchmarkClones) {
                // Compare both ways
                percentages.add(comparer.compareCloneLists(pyCloneList, benchmarkList));
                percentages.add(comparer.compareCloneLists(benchmarkList, pyCloneList));
            }
        }

        StringBuilder results = new StringBuilder();
        if (options.getMode().equals(CloneFileOptions.CloneMode.SINGLE)) {
            // Leave room for when double runs
            results.append(",");
        }

        // Print # of clones found by each tool
        for (List<Clone> pyCloneList: pyCloneClones) {
            results.append(pyCloneList.size()).append(",");
        }
        for (List<Clone> benchmarkList: benchmarkClones) {
            results.append(benchmarkList.size()).append(",");
        }

        // Print % of similarity between tools
        for (Double percentage: percentages) {
            results.append(percentage).append(",");
        }

        // Output the results
        sysOut.println(results.toString());
    }
}
