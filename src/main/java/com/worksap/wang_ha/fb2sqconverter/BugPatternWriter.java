package com.worksap.wang_ha.fb2sqconverter;

import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.Writer;
import java.util.List;

public class BugPatternWriter {
    private final Writer writer;

    public BugPatternWriter(Writer writer) {
        this.writer = writer;
    }

    public void write(List<BugPattern> bugPatterns) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
        Document document = builder.newDocument();
        document.setXmlVersion("1.0");
        document.setXmlStandalone(true);
        Element root = document.createElement("rules");
        document.appendChild(root);

        for (BugPattern bugPattern: bugPatterns) {
            Element rule = document.createElement("rule");
            rule.setAttribute("key", bugPattern.getKey());
            rule.setAttribute("priority", "MAJOR");
            root.appendChild(rule);

            Element configKey = document.createElement("configKey");
            configKey.setTextContent(bugPattern.getKey());
            rule.appendChild(configKey);

            Element name = document.createElement("name");
            name.setTextContent(bugPattern.getName());
            rule.appendChild(name);

            Element description = document.createElement("description");
            CDATASection cdataDescription = document.createCDATASection(bugPattern.getDescription());
            description.appendChild(cdataDescription);
            rule.appendChild(description);

            Element category = document.createElement("tag");
            category.setTextContent(bugPattern.getCategory());
            rule.appendChild(category);

            if (bugPattern.getCategory().trim().equalsIgnoreCase("correctness")) {
                Element type = document.createElement("tag");
                type.setTextContent("bug");
                rule.appendChild(type);
            }

            for (String tag: bugPattern.getTags()) {
                Element tagElement = document.createElement("tag");
                tagElement.setTextContent(tag.trim().toLowerCase());
                rule.appendChild(tagElement);
            }
        }

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        DOMSource source = new DOMSource(document);
        StreamResult streamResult = new StreamResult(writer);
        transformer.transform(source, streamResult);
    }
}
