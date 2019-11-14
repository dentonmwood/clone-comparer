package com.csi.czech.clone;

import com.csi.czech.source.Source;

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
