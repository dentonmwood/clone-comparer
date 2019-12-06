package com.csi.czech.reader;

import com.csi.czech.clone.NiCadClone;
import com.csi.czech.source.NiCadSource;
import org.apache.commons.io.FilenameUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/*
    Taken from https://www.journaldev.com/1198/java-sax-parser-example
    https://alvinalexander.com/java/java-strip-characters-string-letters-numbers-replace
 */

/**
 * Handler required to use Java SAX parser. Iterates through the XML
 * file and returns the results as a list of clones.
 */
public class NiCadCloneHandler extends DefaultHandler {
    /** The clones being read */
    private List<NiCadClone> clones;
    /** The individual clone being read */
    private NiCadClone clone;
    /** The data being read */
    private StringBuilder data = null;

    /**
     * Getter for the read clones
     * @return the clones which have been read
     */
    public List<NiCadClone> getClones() {
        return this.clones;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase("clones")) {
            this.clones = new ArrayList<>();
        } else if (qName.equalsIgnoreCase("clone")) {
            Long numLines = Long.parseLong(attributes.getValue("nlines"));
            Long similarity = Long.parseLong(attributes.getValue("similarity"));
            this.clone = new NiCadClone(numLines, similarity);
        } else if (qName.equalsIgnoreCase("source")) {
            String filePath = attributes.getValue("file");
            String filename = FilenameUtils.getName(filePath).replaceAll(".pyindent", "");
            Long startLine = Long.parseLong(attributes.getValue("startline"));
            Long endLine = Long.parseLong(attributes.getValue("endline"));
            Long pcId = Long.parseLong(attributes.getValue("pcid"));
            this.clone.addSource(new NiCadSource(filename, startLine, endLine, pcId));
        }
        this.data = new StringBuilder();
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if (qName.equalsIgnoreCase("clone")) {
            this.clones.add(this.clone);
        }
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        data.append(new String(ch, start, length));
    }
}
