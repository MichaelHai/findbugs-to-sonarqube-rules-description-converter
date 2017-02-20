package com.worksap.wang_ha.fb2sqconverter;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class BugPatternReaderTest {
    @Test
    public void testBugPatternKeyCanBeReadFromDefaultFile() throws Exception {
        BugPatternReader reader = new BugPatternReader();
        List<String> patterns = reader.readPatterns();

        assertEquals(20, patterns.size());
        assertEquals("HB_CONTROLLER_SHOULD_NOT_DEPEND_ON_DAO", patterns.get(0));
    }
}
