package com.csi.czech;

import com.csi.czech.pyclone.Origin;

import java.util.List;

public class Clone {
    private String value;
    private Long matchWeight;
    private List<Origin> origins;

    public Clone(String value, Long matchWeight, List<Origin> origins) {
        this.value = value;
        this.matchWeight = matchWeight;
        this.origins = origins;
    }

    public String getValue() {
        return this.value;
    }

    public Long getMatchWeight() {
        return this.matchWeight;
    }

    public List<Origin> getOrigins() {
        return this.origins;
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        ret.append("Clone: ").append(this.value)
                .append(", ").append(this.matchWeight).append(", origins: ").append("\n");
        for (Origin origin: this.origins) {
            ret.append(origin.toString());
        }
        return ret.toString();
    }
}
