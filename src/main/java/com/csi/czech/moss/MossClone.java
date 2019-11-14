package com.csi.czech.moss;

import com.csi.czech.common.Clone;
import com.csi.czech.common.Source;

public class MossClone extends Clone {
    public MossClone() {
        super();
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
