package com.csi.czech.nicad;

import com.csi.czech.common.Clone;
import com.csi.czech.common.Source;

import java.util.Set;

public class NiCadClone extends Clone {
    private Long similarity;
    private Long numLines;

    public NiCadClone(Long numLines, Long similarity) {
        super();
        this.numLines = numLines;
        this.similarity = similarity;
    }

    public Long getSimilarity() {
        return this.similarity;
    }

    public Long getNumLines() {
        return this.numLines;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("NiCad Clone: ").append("numLines: ").append(this.numLines)
                .append(", similarity: ").append(this.similarity)
                .append(", sources: ").append("\n");
        for (Source source: this.sources) {
            stringBuilder.append(source).append("\n");
        }
        return stringBuilder.toString();
    }
}
