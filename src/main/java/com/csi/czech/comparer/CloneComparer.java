package com.csi.czech.comparer;

import com.csi.czech.clone.Clone;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CloneComparer {
    public double compareCloneLists(List<Clone> cloneList1, List<Clone> cloneList2) {
        if (cloneList1.size() == 0 || cloneList2.size() == 0) {
            if (cloneList1.size() == 0 && cloneList2.size() == 0) {
                // They caught the same clones, technically
                return 1.0;
            }
            // One of these is bad
            return 0.0;
        }

        Map<Clone, Clone> cloneMap = new HashMap<>();
        for (Clone clone1: cloneList1) {
            for (Clone clone2: cloneList2) {
                if (clone1.equals(clone2) && !cloneMap.containsValue(clone2)) {
                    cloneMap.put(clone1, clone2);
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
