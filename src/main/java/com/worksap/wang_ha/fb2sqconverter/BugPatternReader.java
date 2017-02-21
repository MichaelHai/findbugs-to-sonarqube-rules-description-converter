package com.worksap.wang_ha.fb2sqconverter;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BugPatternReader {
    private final String patternFile;
    private final String messageFile;

    public BugPatternReader() {
        this.patternFile = getClass().getResource("/findbugs.xml").getFile();
        this.messageFile = getClass().getResource("/messages.xml").getFile();
    }

    public List<BugPattern> readPatterns() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        File file = new File(patternFile);
        Document doc = builder.parse(file);
        // Do something with the document here.

        NodeList nodes = doc.getElementsByTagName("BugPattern");

        List<BugPattern> patterns = new ArrayList<>();
        for (int i = 0; i < nodes.getLength(); i++) {
            String key = nodes.item(i).getAttributes().getNamedItem("type").getNodeValue();
            String category = nodes.item(i).getAttributes().getNamedItem("category").getNodeValue().toLowerCase();
            patterns.add(new BugPattern(key, category));
        }

        return patterns;

    }

    public void readDetailInformationTo(BugPattern bugPattern) throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        File file = new File(messageFile);
        Document doc = builder.parse(file);

        NodeList nodes = doc.getElementsByTagName("BugPattern");
        for (int i = 0; i < nodes.getLength(); i++) {
            Node item = nodes.item(i);
            String type = item.getAttributes().getNamedItem("type").getNodeValue();
            if (type.equals(bugPattern.getKey())) {
                String name = ((Element) item).getElementsByTagName("ShortDescription").item(0).getTextContent();
                bugPattern.setName(name);
                String description = ((Element) item).getElementsByTagName("Details").item(0).getTextContent().trim();
                bugPattern.setDescription(description);
            }

        }
    }

    public void readDetailInformationTo(List<BugPattern> patterns) throws ParserConfigurationException, SAXException, IOException {
        for (BugPattern pattern: patterns) {
            readDetailInformationTo(pattern);
        }
    }
}
