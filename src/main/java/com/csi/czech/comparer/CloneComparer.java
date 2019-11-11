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
                if (compareClones(clone1, clone2)) {
                    cloneMap.put(clone1, clone2);
                }
            }
        }
        double numMatchedClones = cloneMap.size();
        double numClones = cloneList2.size();
        return numMatchedClones / numClones;
    }

    private boolean compareClones(Clone clone1, Clone clone2) {
        if (clone1.getSources().size() != 2 || clone2.getSources().size() != 2) {
            throw new IllegalArgumentException("Each clone should only have 2 sources");
        }

        return compareSources(clone1.getSources().get(0), clone2.getSources().get(0))
                && compareSources(clone1.getSources().get(1), clone2.getSources().get(1));
    }

    private boolean compareSources(Source source1, Source source2) {
        if (!source1.getFilename().equals(source2.getFilename())) {
            return false;
        }

        if (Math.abs(source1.getStartLine() - source2.getStartLine()) < 10) {
            return true;
        }
        return false;
    }
}
