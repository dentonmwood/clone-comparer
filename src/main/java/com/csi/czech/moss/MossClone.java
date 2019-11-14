package com.csi.czech.moss;

import com.csi.czech.common.Clone;
import com.csi.czech.common.Source;

import java.util.Set;

public class MossClone extends Clone {
    public MossClone(Set<Source> sources) {
        super(sources);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Moss clone: ")
                .append("sources: ").append("\n");
        for (Source source: this.sources) {
            stringBuilder.append(source).append("\n");
        }
        return stringBuilder.toString();
    }
}
