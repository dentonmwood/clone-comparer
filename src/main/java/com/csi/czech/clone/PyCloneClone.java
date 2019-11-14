package com.csi.czech.clone;

import com.csi.czech.source.Source;

public class PyCloneClone extends Clone {
    private String value;
    private Long matchWeight;

    public PyCloneClone(String value, Long matchWeight) {
        super();
        this.value = value;
        this.matchWeight = matchWeight;
    }

    public String getValue() {
        return this.value;
    }

    public Long getMatchWeight() {
        return this.matchWeight;
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
