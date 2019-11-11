package com.csi.czech.comparer;

import com.csi.czech.common.Clone;
import com.csi.czech.common.Source;

import java.util.List;

public class CloneComparer {
    public double compareCloneLists(List<Clone> cloneList1, List<Clone> cloneList2) {
        int numClonesEqual = 0;
        for (Clone clone1: cloneList1) {
            for (Clone clone2: cloneList2) {
                if (compareClones(clone1, clone2)) {
                    numClonesEqual++;
                }
            }
        }
        return 0.0;
    }

    public boolean compareClones(Clone clone1, Clone clone2) {
        List<Source> sourceList1 = clone1.getSources();
        List<Source> sourceList2 = clone2.getSources();
        if (sourceList1.size() > 2 || sourceList2.size() > 2) {
            throw new IllegalArgumentException("Each clone should only have 2 sources");
        }

        for (Source source1: sourceList1) {
            for (Source source2: sourceList2) {

            }
        }
        return false;
    }
}
