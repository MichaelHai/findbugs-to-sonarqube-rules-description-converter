package com.worksap.wang_ha.fb2sqconverter;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BugPatternReader {
    private final String fileName;

    public BugPatternReader() {
        this.fileName = getClass().getResource("/findbugs.xml").getFile();
    }

    public List<String> readPatterns() throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(true);
        factory.setIgnoringElementContentWhitespace(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        File file = new File(fileName);
        Document doc = builder.parse(file);
        // Do something with the document here.

        NodeList nodes = doc.getElementsByTagName("BugPattern");

        List<String> patterns = new ArrayList<>();
        for (int i = 0; i < nodes.getLength(); i++) {
            patterns.add(nodes.item(i).getAttributes().getNamedItem("type").getNodeValue());
        }

        return patterns;

    }
}
