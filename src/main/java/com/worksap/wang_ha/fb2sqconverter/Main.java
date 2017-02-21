package com.worksap.wang_ha.fb2sqconverter;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException, TransformerException {
        if (args.length < 1) {
            System.out.println("Please input a filename");
            return;
        }

        BugPatternReader reader = new BugPatternReader();
        List<BugPattern> patterns = reader.readPatterns();
        reader.readDetailInformationTo(patterns);

        for (int i = 1; i < args.length; i++) {
            String tag = args[i];
            for (BugPattern pattern: patterns) {
                pattern.addTag(tag);
            }
        }

        BugPatternWriter writer = new BugPatternWriter(new FileWriter(args[0]));
        writer.write(patterns);
    }
}
