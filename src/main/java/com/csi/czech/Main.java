package com.csi.czech;

import com.csi.czech.common.Clone;
import com.csi.czech.comparer.CloneComparer;
import com.csi.czech.reader.MossCloneReader;
import com.csi.czech.reader.NiCadCloneReader;
import com.csi.czech.reader.PyCloneCloneReader;
import com.gargoylesoftware.htmlunit.WebClient;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length != 5) {
            throw new IllegalArgumentException("Arguments: <pyclone oxygen file> <pyclone chlorine file> " +
                    "<nicad blocks file> <nicad functions file> <moss file link>");
        }

        String pyCloneOxygenFile = args[0];
        String pyCloneChlorineFile = args[1];
        String niCadBlocksFile = args[2];
        String niCadFunctionsFile = args[3];
        String mossUrl = args[4];

        PyCloneCloneReader pyCloneCloneReader = new PyCloneCloneReader(new JSONParser());
        List<Clone> pyCloneOxygenClones = pyCloneCloneReader.readClones(pyCloneOxygenFile);
        List<Clone> pyCloneChlorineClones = pyCloneCloneReader.readClones(pyCloneChlorineFile);
        for (Clone clone: pyCloneChlorineClones) {
            System.out.println(clone);
        }

        NiCadCloneReader niCadCloneReader = new NiCadCloneReader();
        List<Clone> nicadBlockClones = niCadCloneReader.readClones(niCadBlocksFile);
        List<Clone> nicadFunctionClones = niCadCloneReader.readClones(niCadFunctionsFile);

        MossCloneReader mossCloneReader = new MossCloneReader(new WebClient());
        List<Clone> mossClones = mossCloneReader.readClones(mossUrl);

        CloneComparer comparer = new CloneComparer();
        double percentOxygenBlock = comparer.compareCloneLists(pyCloneOxygenClones, nicadBlockClones);
        double percentBlockOxygen = comparer.compareCloneLists(nicadBlockClones, pyCloneOxygenClones);
        double percentOxygenFunction = comparer.compareCloneLists(pyCloneOxygenClones, nicadFunctionClones);
        double percentFunctionOxygen = comparer.compareCloneLists(nicadBlockClones, pyCloneOxygenClones);
        double percentOxygenMoss = comparer.compareCloneLists(pyCloneOxygenClones, mossClones);
        double percentMossOxygen = comparer.compareCloneLists(mossClones, pyCloneOxygenClones);

        double percentChlorineBlock = comparer.compareCloneLists(pyCloneChlorineClones, nicadBlockClones);
        double percentBlockChlorine = comparer.compareCloneLists(nicadBlockClones, pyCloneChlorineClones);
        double percentChlorineFunction = comparer.compareCloneLists(pyCloneChlorineClones, nicadFunctionClones);
        double percentFunctionChlorine = comparer.compareCloneLists(nicadFunctionClones, pyCloneChlorineClones);
        double percentChlorineMoss = comparer.compareCloneLists(pyCloneChlorineClones, mossClones);
        double percentMossChlorine = comparer.compareCloneLists(mossClones, pyCloneChlorineClones);

        StringBuilder results = new StringBuilder();
        results.append(percentOxygenBlock).append(",")
                .append(percentBlockOxygen).append(",")
                .append(percentOxygenFunction).append(",")
                .append(percentFunctionOxygen).append(",")
                .append(percentOxygenMoss).append(",")
                .append(percentMossOxygen).append(",")
                .append(percentChlorineBlock).append(",")
                .append(percentBlockChlorine).append(",")
                .append(percentChlorineFunction).append(",")
                .append(percentFunctionChlorine).append(",")
                .append(percentChlorineMoss).append(",")
                .append(percentMossChlorine);

        System.out.println(results.toString());
    }
}
