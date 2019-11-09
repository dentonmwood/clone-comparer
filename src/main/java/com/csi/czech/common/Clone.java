package com.csi.czech.common;

import java.util.List;

public abstract class Clone {
    protected List<Source> sources;

    public Clone(List<Source> origins) {
        this.sources = origins;
    }

    public List<Source> getSources() {
        return this.sources;
    }
}
