package com.csi.czech.clone;

import com.csi.czech.source.Source;

/**
 * Represents a clone detected by the PyClone tool
 */
public class PyCloneClone extends Clone {
    /** Represents the Python AST classification of the code snippet */
    private String value;
    /** Number representing the similarity between the two code snippets */
    private Long matchWeight;

    /**
     * Constructor for the PyClone clone class
     *
     * @param value the Python AST class of the snippet
     * @param matchWeight the similarity between the snippets
     */
    public PyCloneClone(String value, Long matchWeight) {
        super();
        this.value = value;
        this.matchWeight = matchWeight;
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        ret.append("PyClone Clone: value: ").append(this.value)
                .append(", match weight: ").append(this.matchWeight).append(", origins: ").append("\n");
        for (Source origin: this.sources) {
            ret.append(origin.toString());
        }
        return ret.toString();
    }
}
