package com.agilistanbul.darklord.commons.utils;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Lemi Orhan Ergin
 * @since 10.12.2013
 */
public class ResourceUtilsTest {

    private static final Logger logger = LoggerFactory.getLogger(ResourceUtilsTest.class);

    @Test
    public void getClasspathResource() throws Exception {
         assertEquals(new File(this.getClass().getResource("/log4j.properties").getPath()),
                 ResourceUtils.getClasspathResource(this.getClass(), "/log4j.properties"));
    }

    @Test (expected = IOException.class)
    public void getClasspathResourceInvalidPath() throws IOException {
        ResourceUtils.getClasspathResource(this.getClass(), "/x/y/z");
    }

    @Test
    public void getFileFromAbsolutePath() throws IOException {
        String absolutePath = new File(this.getClass().getResource("/log4j.properties").getPath()).getAbsolutePath();
        logger.info("Detected the absolute path: {}", absolutePath);

        assertTrue(ResourceUtils.getFile(this.getClass(), absolutePath).exists());
    }

    @Test
    public void getFileFromRelativePathForMissingInAbsolutePath() throws IOException {
        assertTrue(ResourceUtils.getFile(this.getClass(), "log4j.properties").exists());
    }
}
