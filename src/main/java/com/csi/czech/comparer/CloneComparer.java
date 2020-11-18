package com.csi.czech.comparer;

import com.csi.czech.clone.Clone;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Compares clones which have been parsed from the input files.
 * Contains only the method to run the comparison.
 */
public class CloneComparer {
    /**
     * Takes two lists of parsed clones and compares them,
     * returning the percentage p of clones in the first
     * list which map to clones in the second (0 <= p <= 1)
     *
     * @param cloneList1 the first clone list to compare
     * @param cloneList2 the second clone list to compare
     * @return the percentage of similarity
     */
    public double compareCloneLists(List<Clone> cloneList1,
                                    List<Clone> cloneList2) {
        if (cloneList1.isEmpty() || cloneList2.isEmpty()) {
            if (cloneList1.isEmpty() && cloneList2.isEmpty()) {
                // They caught the same clones, technically
                return 1.0;
            }
            // One of these is bad
            return 0.0;
        }

        Map<Clone, Clone> cloneMap = new HashMap<>();
        for (int i = 0; i < cloneList1.size(); i++) {
            for (int j = i + 1; j < cloneList2.size(); j++) {
                Clone clone1 = cloneList1.get(i);
                Clone clone2 = cloneList2.get(j);
                if (clone1.equals(clone2)) {
                    // Overwrite any existing clone so we only count each once
                    cloneMap.put(clone2, clone1);
                }
            }
        }

        double numMatchedClones = cloneMap.size();
        double numClones = cloneList2.size();
        if (numClones == 0) {
            return 0.0;
        }
        return numMatchedClones / numClones;
    }
}
