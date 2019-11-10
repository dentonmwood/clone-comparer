package com.csi.czech.common;

import java.util.List;

public abstract class Clone {
    protected List<Source> sources;

    public Clone(List<Source> sources) {
        this.sources = sources;
    }

    public List<Source> getSources() {
        return this.sources;
    }
}
