package com.csi.czech.reader;

import com.csi.czech.clone.Clone;

import java.io.IOException;
import java.util.List;

public interface CloneReader {
    List<Clone> readClones(String filename) throws IOException;
}
