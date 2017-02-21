package com.worksap.wang_ha.fb2sqconverter;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class BugPatternReaderTest {
    private BugPatternReader reader;

    @Before
    public void setup() {
        String patternFile = getClass().getResource("/findbugs.xml").getFile();
        String messageFile = getClass().getResource("/messages.xml").getFile();
        this.reader = new BugPatternReader(patternFile, messageFile);
    }


    @Test
    public void testBugPatternKeyCanBeReadFromDefaultFile() throws Exception {
        List<BugPattern> patterns = reader.readPatterns();

        assertEquals(20, patterns.size());
        assertEquals("HB_CONTROLLER_SHOULD_NOT_DEPEND_ON_DAO", patterns.get(0).getKey());
        assertEquals("correctness", patterns.get(0).getCategory());
    }

    @Test
    public void testBugDetailInformationWithCDATACanBeRetrievedFromDefaultFile() throws ParserConfigurationException, SAXException, IOException {
        BugPattern bugPattern = new BugPattern("HB_CONTROLLER_SHOULD_NOT_DEPEND_ON_DAO", "correctness");

        reader.readDetailInformationTo(bugPattern);

        assertEquals("Controller never depends on DAO.", bugPattern.getName());
        assertEquals("<p>Controller never depend on DAO.</p>", bugPattern.getDescription());
    }

    @Test
    public void testBugDetailInformationWithNormalDetailsCanBeRetrievedFromDefaultFile() throws ParserConfigurationException, SAXException, IOException {
        BugPattern bugPattern = new BugPattern("HB_SHARED_SERVICE_SHOULD_NOT_DEPEND_ON_SERVICE", "correctness");

        reader.readDetailInformationTo(bugPattern);

        assertEquals("SharedService should not depend on Service.", bugPattern.getName());
        assertEquals("SharedService should not depend on Service.", bugPattern.getDescription());
    }

    @Test
    public void testBugDetailInformationCanBeReadIntoLists() throws IOException, SAXException, ParserConfigurationException {
        BugPattern bugPattern1 = new BugPattern("HB_CONTROLLER_SHOULD_NOT_DEPEND_ON_DAO", "correctness");
        BugPattern bugPattern2 = new BugPattern("HB_SHARED_SERVICE_SHOULD_NOT_DEPEND_ON_SERVICE", "correctness");
        List<BugPattern> patterns = Arrays.asList(bugPattern1, bugPattern2);

        reader.readDetailInformationTo(patterns);

        assertEquals("Controller never depends on DAO.", patterns.get(0).getName());
        assertEquals("SharedService should not depend on Service.", patterns.get(1).getName());
    }

}
