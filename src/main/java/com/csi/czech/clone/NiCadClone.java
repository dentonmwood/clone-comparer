package com.csi.czech.clone;

import com.csi.czech.source.NiCadSource;
import com.csi.czech.source.Source;

/**
 * Represents a clone detected by the NiCad tool.
 */
public class NiCadClone extends Clone {
    /** Percentage of similarity between the sources */
    private Long similarity;
    /** Number of lines which are similar */
    private Long numLines;

    /**
     * Constructor for the NiCad clone class
     *
     * @param numLines the number of similar lines
     * @param similarity the similarity percentage
     */
    public NiCadClone(Long numLines, Long similarity) {
        super();
        this.numLines = numLines;
        this.similarity = similarity;
    }

    @Override
    public void addSource(Source source) {
        if (!(source instanceof NiCadSource)) {
            throw new IllegalArgumentException("Only NiCad sources can be "
                    + "attached to a NiCad clone");
        }
        super.addSource(source);
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
