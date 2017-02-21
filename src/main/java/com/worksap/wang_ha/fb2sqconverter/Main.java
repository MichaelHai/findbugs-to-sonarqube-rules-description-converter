package com.worksap.wang_ha.fb2sqconverter;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException, TransformerException {
        if (args.length < 3) {
            System.out.println("usage: java -jar findbugs-to-sonarqube-rules-description-converter.jar findbugs-rules.xml message.xml sonarqube-rules.xml");
            return;
        }

        BugPatternReader reader = new BugPatternReader(args[0], args[1]);
        List<BugPattern> patterns = reader.readPatterns();
        reader.readDetailInformationTo(patterns);

        for (int i = 3; i < args.length; i++) {
            String tag = args[i];
            for (BugPattern pattern: patterns) {
                pattern.addTag(tag);
            }
        }

        BugPatternWriter writer = new BugPatternWriter(new FileWriter(args[2]));
        writer.write(patterns);
    }
}
