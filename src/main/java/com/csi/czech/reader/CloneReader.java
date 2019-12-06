package com.csi.czech.reader;

import com.csi.czech.clone.Clone;

import java.io.IOException;
import java.util.List;

/**
 * Generic interface for reading clones from a file.
 */
public interface CloneReader {
    /**
     * Read the clones from the given file.
     * @param filename the file to read clones from
     * @return the read clones
     * @throws IOException if the clones cannot be read
     */
    List<Clone> readClones(String filename) throws IOException;
}
