package com.csi.czech.cmd;

import com.csi.czech.clone.Clone;
import com.csi.czech.comparer.CloneComparer;
import com.csi.czech.reader.MossCloneReader;
import com.csi.czech.reader.NiCadCloneReader;
import com.csi.czech.reader.PyCloneCloneReader;
import com.gargoylesoftware.htmlunit.WebClient;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles arguments given to the command-line. This is an implementation of the
 * Visitor pattern - each type of command-line options class calls its visit method,
 * and the handler handles things accordingly.
 */
public class CLIHandler {

    private final PrintStream sysOut;

    public CLIHandler(PrintStream sysOut) {
        this.sysOut = sysOut;
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
     * @throws IOException if one or more of the files cannot be read
     */
    public void visit(CloneFileOptions options) throws IOException {
        // Read the PyClone clones
        List<List<Clone>> pyCloneClones = new ArrayList<>();
        PyCloneCloneReader pyCloneCloneReader = new PyCloneCloneReader(new JSONParser());
        if (options.getPycloneOxygenFile() != null) {
            pyCloneClones.add(pyCloneCloneReader.readClones(options.getPycloneOxygenFile()));
        }
        if (options.getPycloneChlorineFile() != null) {
            pyCloneClones.add(pyCloneCloneReader.readClones(options.getPycloneChlorineFile()));
        }
        if (options.getPycloneIodineFile() != null) {
            pyCloneClones.add(pyCloneCloneReader.readClones(options.getPycloneIodineFile()));
        }


        // Read benchmark tool clones
        List<List<Clone>> benchmarkClones = new ArrayList<>();

        // Read the NiCad clones
        NiCadCloneReader niCadCloneReader = new NiCadCloneReader();
        if (options.getNicadBlocksFile() != null) {
            benchmarkClones.add(niCadCloneReader.readClones(options.getNicadBlocksFile()));
        }
        if (options.getNicadFunctionsFile() != null) {
            benchmarkClones.add(niCadCloneReader.readClones(options.getNicadFunctionsFile()));
        }

        // Read the Moss clones
        if (options.getMossFile() != null) {
            MossCloneReader mossCloneReader = new MossCloneReader(new WebClient());
            benchmarkClones.add(mossCloneReader.readClones(options.getMossFile()));
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
