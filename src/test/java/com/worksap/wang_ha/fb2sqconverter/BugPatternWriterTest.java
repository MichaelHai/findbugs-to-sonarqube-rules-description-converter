package com.worksap.wang_ha.fb2sqconverter;

import org.junit.Test;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class BugPatternWriterTest {
    @Test
    public void testCreateAndWritePatternsToAFile() throws TransformerException, ParserConfigurationException {
        StringWriter stringWriter = new StringWriter();
        BugPatternWriter writer = new BugPatternWriter(stringWriter);

        BugPattern bugPattern1 = new BugPattern("HB_CONTROLLER_SHOULD_NOT_DEPEND_ON_DAO", "correctness");
        bugPattern1.setName("Controller never depends on DAO.");
        bugPattern1.setDescription("<p>Controller never depend on DAO.</p>");
        BugPattern bugPattern2 = new BugPattern("HB_SHARED_SERVICE_SHOULD_NOT_DEPEND_ON_SERVICE", "correctness");
        bugPattern2.setName("SharedService should not depend on Service.");
        bugPattern2.setDescription("SharedService should not depend on Service.");
        List<BugPattern> bugPatterns = Arrays.asList(bugPattern1, bugPattern2);
        writer.write(bugPatterns);

        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><rules>\n" +
                "  <rule key=\"HB_CONTROLLER_SHOULD_NOT_DEPEND_ON_DAO\" priority=\"MAJOR\">\n" +
                "    <configKey>HB_CONTROLLER_SHOULD_NOT_DEPEND_ON_DAO</configKey>\n" +
                "    <name>Controller never depends on DAO.</name>\n" +
                "    <description><![CDATA[<p>Controller never depend on DAO.</p>]]></description>\n" +
                "    <tag>correctness</tag>\n" +
                "  </rule>\n" +
                "  <rule key=\"HB_SHARED_SERVICE_SHOULD_NOT_DEPEND_ON_SERVICE\" priority=\"MAJOR\">\n" +
                "    <configKey>HB_SHARED_SERVICE_SHOULD_NOT_DEPEND_ON_SERVICE</configKey>\n" +
                "    <name>SharedService should not depend on Service.</name>\n" +
                "    <description><![CDATA[SharedService should not depend on Service.]]></description>\n" +
                "    <tag>correctness</tag>\n" +
                "  </rule>\n" +
                "</rules>\n", stringWriter.toString());
    }
}
