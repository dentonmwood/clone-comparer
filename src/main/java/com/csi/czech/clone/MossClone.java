package com.csi.czech.clone;

import com.csi.czech.source.Source;

/**
 * Represents a clone detected by the Moss tool. Mostly useful for debugging
 * since it doesn't differ from the generic class.
 */
public class MossClone extends Clone {
    /**
     * Constructor for the Moss class.
     */
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
