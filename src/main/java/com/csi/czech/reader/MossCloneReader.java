package com.csi.czech.reader;

import com.csi.czech.clone.Clone;
import com.csi.czech.clone.MossClone;
import com.csi.czech.source.MossSource;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
    Taken from:
    https://ksah.in/introduction-to-web-scraping-with-java/
    https://www.geeksforgeeks.org/how-to-remove-duplicates-from-arraylist-in-java/
    https://www.roseindia.net/tutorials/xPath/using-nameXPath.shtml
    https://stackoverflow.com/questions/14526260/how-do-i-get-the-file-name-from-a-string-containing-the-absolute-file-path
 */
public class MossCloneReader implements CloneReader {
    private WebClient client;

    public MossCloneReader(WebClient webClient) {
        this.client = webClient;
        this.client.getOptions().setCssEnabled(false);
        this.client.getOptions().setJavaScriptEnabled(false);
    }

    @Override
    public List<Clone> readClones(String fileUrl) throws IOException {
        HtmlPage mossPage = this.client.getPage(fileUrl);
        List<Clone> clones = new ArrayList<>();

        List<HtmlTableRow> cloneRows = mossPage.getByXPath("//tr");
        List<String> urls = new ArrayList<>();
        for (HtmlTableRow tableRow: cloneRows) {
            List<HtmlTableDataCell> tableData = tableRow.getByXPath("td");
            // Header row will not have any data cells
            if (tableData.size() != 0) {
                HtmlAnchor anchor = tableData.get(0).getFirstByXPath("a");
                urls.add(anchor.getHrefAttribute());
            }
        }

        for (String url: urls) {
            clones.addAll(getClonesFromPage(url));
        }
        return clones;
    }

    public List<Clone> getClonesFromPage(String url) throws IOException {
        HtmlPage clonePage = this.client.getPage(url);

        HtmlFrame cloneFrame = clonePage.getFirstByXPath("//frame");
        HtmlPage cloneTablePage = (HtmlPage) cloneFrame.getEnclosedPage();

        List<String> filenames = new ArrayList<>(2);
        List<Integer> percentMatches = new ArrayList<>(2);
        List<Clone> clones = new ArrayList<>();

        // Get clone info from header row
        List<HtmlTableHeaderCell> headerCells = cloneTablePage.getByXPath("//th");
        for (HtmlTableHeaderCell headerCell: headerCells) {
            String text = headerCell.asText();
            Pattern p = Pattern.compile("([^ ]*) \\(([0-9]+)%\\)");
            Matcher m = p.matcher(text);
            if (m.matches()) {
                String filename = FilenameUtils.getName(m.group(1));
                filenames.add(filename);
                percentMatches.add(Integer.parseInt(m.group(2)));
            }
        }

        List<HtmlTableRow> cloneTableRows = cloneTablePage.getByXPath("//tr");
        for (HtmlTableRow tableRow: cloneTableRows) {
            List<HtmlTableDataCell> tableData = tableRow.getByXPath("td");
            // Header row will not have any data cells
            if (tableData.size() > 0) {
                Clone clone = new MossClone();
                int i = 0;
                // Get the file sources
                for (HtmlTableDataCell dataCell: tableData) {
                    HtmlAnchor urlAnchor = dataCell.getFirstByXPath("a");
                    String lines = urlAnchor.getTextContent();
                    // Don't grab the graphs
                    Pattern p = Pattern.compile("([0-9]+)-([0-9]+)");
                    Matcher m = p.matcher(lines);
                    if (m.matches()) {
                        Long startLine = Long.parseLong(m.group(1));
                        Long endLine = Long.parseLong(m.group(2));
                        clone.addSource(new MossSource(filenames.get(i), startLine, endLine));
                        i++;
                    }
                }
                clones.add(clone);
            }
        }
        return clones;
    }
}
