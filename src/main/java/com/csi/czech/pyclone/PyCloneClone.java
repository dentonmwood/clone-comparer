package com.csi.czech.pyclone;

import com.csi.czech.common.Clone;
import com.csi.czech.common.Source;

import java.util.Set;

public class PyCloneClone extends Clone {
    private String value;
    private Long matchWeight;

    public PyCloneClone(Set<Source> origins, String value, Long matchWeight) {
        super(origins);
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
