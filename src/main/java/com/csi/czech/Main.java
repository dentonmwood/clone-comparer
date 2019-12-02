package com.csi.czech;

import com.csi.czech.clone.Clone;
import com.csi.czech.comparer.CloneComparer;
import com.csi.czech.reader.MossCloneReader;
import com.csi.czech.reader.NiCadCloneReader;
import com.csi.czech.reader.PyCloneCloneReader;
import com.gargoylesoftware.htmlunit.WebClient;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Main class - runs each of the comparer tools and prints a CSV line with
 * the results. Can also be used to print a header line for the results.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length != 6) {
            if (args.length == 1 && "header".equals(args[0])) {
                printHeader();
                return;
            } else {
                throw new IllegalArgumentException("Arguments: <mode> <pyclone oxygen file> <pyclone chlorine file> " +
                        "<nicad blocks file> <nicad functions file> <moss file link>");
            }
        }

        // Grab command-line arguments
        String mode = args[0];
        String pyCloneOxygenFile = args[1];
        String pyCloneChlorineFile = args[2];
        String niCadBlocksFile = args[3];
        String niCadFunctionsFile = args[4];
        String mossFile = args[5];

        // Read the PyClone clones
        List<List<Clone>> pyCloneClones = new ArrayList<>();
        PyCloneCloneReader pyCloneCloneReader = new PyCloneCloneReader(new JSONParser());
        if (!mode.equals("double")) {
            pyCloneClones.add(pyCloneCloneReader.readClones(pyCloneOxygenFile));
        } else {
            // Double mode does not currently support oxygen
            pyCloneClones.add(new ArrayList<>());
        }
        pyCloneClones.add(pyCloneCloneReader.readClones(pyCloneChlorineFile));

        // Read benchmark tool clones
        List<List<Clone>> benchmarkClones = new ArrayList<>();

        // Read the NiCad clones
        NiCadCloneReader niCadCloneReader = new NiCadCloneReader();
        benchmarkClones.add(niCadCloneReader.readClones(niCadBlocksFile));
        benchmarkClones.add(niCadCloneReader.readClones(niCadFunctionsFile));

        // Read the Moss clones
        if (!mode.equals("double")) {
            MossCloneReader mossCloneReader = new MossCloneReader(new WebClient());
            benchmarkClones.add(mossCloneReader.readClones(mossFile));
        } else {
            // Double mode does not currently support moss
            benchmarkClones.add(new ArrayList<>());
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

        System.out.println(results.toString());
    }

    /**
     * Prints the header line for the results. Useful for telling what's what.
     */
    public static void printHeader() {
        StringBuilder header = new StringBuilder();
        header
                .append("# of Oxygen clones,")
                .append("# of Chlorine clones,")
                .append("# of NiCad Block clones,")
                .append("# of NiCad Function Clones,")
                .append("# of Moss Clones,")
                .append("% of Oxygen in NiCad Block,")
                .append("% of NiCad Block in Oxygen,")
                .append("% of Oxygen in NiCad Function,")
                .append("% of NiCad Function in Oxygen,")
                .append("% of Oxygen in Moss,")
                .append("% of Moss in Oxygen,")
                .append("% of Chlorine in NiCad Block,")
                .append("% of NiCad Block in Chlorine,")
                .append("% of Chlorine in NiCad Function,")
                .append("% of NiCad Function in Chlorine,")
                .append("% of Chlorine in Moss,")
                .append("% of Moss in Chlorine,");
        System.out.println(header.toString());
    }
}
