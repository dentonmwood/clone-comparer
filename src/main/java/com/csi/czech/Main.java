package com.csi.czech;

import com.csi.czech.common.Clone;
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
        System.out.println("Getting PyClone Oxygen clones");
        List<Clone> pyCloneOxygenClones = pyCloneCloneReader.readClones(pyCloneOxygenFile);
        System.out.println("Getting PyClone Chlorine clones");
        List<Clone> pyCloneChlorineClones = pyCloneCloneReader.readClones(pyCloneChlorineFile);

        NiCadCloneReader niCadCloneReader = new NiCadCloneReader();
        System.out.println("Getting NiCad block clones");
        List<Clone> nicadBlockClones = niCadCloneReader.readClones(niCadBlocksFile);
        System.out.println("Getting NiCad function clones");
        List<Clone> nicadFunctionClones = niCadCloneReader.readClones(niCadFunctionsFile);

        MossCloneReader mossCloneReader = new MossCloneReader(new WebClient());
        System.out.println("Getting Moss clones");
        List<Clone> mossClones = mossCloneReader.readClones(mossUrl);
        System.out.println("Done");

        System.out.println("PyClone - Oxygen");
        for (Clone clone: pyCloneOxygenClones) {
            System.out.println(clone);
        }

        System.out.println("PyClone - Chlorine");
        for (Clone clone: pyCloneChlorineClones) {
            System.out.println(clone);
        }

        System.out.println("NiCad - Function");
        for (Clone clone: nicadFunctionClones) {
            System.out.println(clone);
        }

        System.out.println("NiCad - Block");
        for (Clone clone: nicadBlockClones) {
            System.out.println(clone);
        }

        System.out.println("Moss");
        for (Clone clone: mossClones) {
            System.out.println(clone);
        }
    }
}
