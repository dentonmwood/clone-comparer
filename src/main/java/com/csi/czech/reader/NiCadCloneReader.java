package com.csi.czech.reader;

import com.csi.czech.clone.Clone;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
    Taken from https://www.journaldev.com/1198/java-sax-parser-example
    https://ksah.in/introduction-to-web-scraping-with-java/
 */

/**
 * Reads NiCad clones. The clones are returned as an XML, so we
 * use the Java SAX parser to process them.
 */
public class NiCadCloneReader implements CloneReader {
    @Override
    public List<Clone> readClones(String inputFilename) throws IOException {
        File file = new File(inputFilename);
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            NiCadCloneHandler niCadCloneHandler = new NiCadCloneHandler();
            saxParser.parse(file, niCadCloneHandler);
            return new ArrayList<>(niCadCloneHandler.getClones());
        } catch (ParserConfigurationException | SAXException e) {
            throw new IOException("XML parsing error: " + e);
        }
    }
}
