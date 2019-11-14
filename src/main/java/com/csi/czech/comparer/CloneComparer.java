package com.csi.czech.comparer;

import com.csi.czech.common.Clone;
import com.csi.czech.common.Source;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CloneComparer {
    public double compareCloneLists(List<Clone> cloneList1, List<Clone> cloneList2) {
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
