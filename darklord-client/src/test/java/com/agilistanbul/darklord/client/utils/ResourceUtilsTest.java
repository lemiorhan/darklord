package com.agilistanbul.darklord.client.utils;

import com.agilistanbul.darklord.client.impl.utils.ResourceUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * @author Lemi Orhan Ergin
 * @since 10.12.2013
 */
public class ResourceUtilsTest {

    @Test
    public void getClasspathResource() throws Exception {
         assertEquals(new File(this.getClass().getResource("/log4j.properties").getPath()),
                 ResourceUtils.getClasspathResource(this.getClass(), "/log4j.properties"));
    }

    @Test (expected = IOException.class)
    public void getClasspathResourceInvalidPath() throws IOException {
        ResourceUtils.getClasspathResource(this.getClass(), "/x/y/z");
    }
}
