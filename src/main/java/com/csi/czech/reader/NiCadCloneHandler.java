package com.csi.czech.reader;

import com.csi.czech.nicad.NiCadClone;
import com.csi.czech.nicad.NiCadSource;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/*
    Taken from https://www.journaldev.com/1198/java-sax-parser-example
 */
public class NiCadCloneHandler extends DefaultHandler {
    private List<NiCadClone> clones;
    private NiCadClone clone;
    private NiCadSource source;
    private StringBuilder data = null;

    public List<NiCadClone> getClones() {
        return this.clones;
    }

    boolean bSource = false;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase("clones")) {
            this.clones = new ArrayList<>();
        } else if (qName.equalsIgnoreCase("clone")) {
            Long numLines = Long.parseLong(attributes.getValue("nlines"));
            Long similarity = Long.parseLong(attributes.getValue("similarity"));
            this.clone = new NiCadClone(numLines, similarity);
        } else if (qName.equalsIgnoreCase("source")) {
            String file = attributes.getValue("file");
            Long startLine = Long.parseLong(attributes.getValue("startline"));
            Long endLine = Long.parseLong(attributes.getValue("endline"));
            Long pcId = Long.parseLong(attributes.getValue("pcid"));
            this.clone.addSource(new NiCadSource(file, startLine, endLine, pcId));
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
