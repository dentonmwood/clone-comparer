package com.csi.czech;

import com.csi.czech.common.Clone;
import com.csi.czech.reader.NiCadCloneReader;
import com.csi.czech.reader.PyCloneCloneReader;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class CloneComparer {
    public static void main(String[] args) throws IOException {
        File pyCloneFile = new File(args[0]);
        PyCloneCloneReader pyCloneCloneReader = new PyCloneCloneReader(new JSONParser());
        List<Clone> pyCloneClones = pyCloneCloneReader.readClones(pyCloneFile);
        for (Clone clone: pyCloneClones) {
            System.out.println(clone);
        }

        File niCadFile = new File(args[1]);
        NiCadCloneReader niCadCloneReader = new NiCadCloneReader();
        List<Clone> niCadClones = niCadCloneReader.readClones(niCadFile);
        for (Clone clone: niCadClones) {
            System.out.println(clone);
        }
    }
}
