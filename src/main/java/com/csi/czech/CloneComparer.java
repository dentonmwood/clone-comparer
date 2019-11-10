package com.csi.czech;

import com.csi.czech.common.Clone;
import com.csi.czech.reader.MossCloneReader;
import com.csi.czech.reader.NiCadCloneReader;
import com.csi.czech.reader.PyCloneCloneReader;
import com.gargoylesoftware.htmlunit.WebClient;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class CloneComparer {
    public static void main(String[] args) throws IOException {
        if (args.length != 3) {
            throw new IllegalArgumentException("Arguments: <pyclone file> <nicad file> <moss file link>");
        }

        PyCloneCloneReader pyCloneCloneReader = new PyCloneCloneReader(new JSONParser());
        List<Clone> pyCloneClones = pyCloneCloneReader.readClones(args[0]);
        for (Clone clone: pyCloneClones) {
            System.out.println(clone);
        }

        NiCadCloneReader niCadCloneReader = new NiCadCloneReader();
        List<Clone> niCadClones = niCadCloneReader.readClones(args[1]);
        for (Clone clone: niCadClones) {
            System.out.println(clone);
        }

        MossCloneReader mossCloneReader = new MossCloneReader(new WebClient());
        List<Clone> mossClones = mossCloneReader.readClones(args[2]);
        for (Clone clone: mossClones) {
            System.out.println(clone);
        }
    }
}
