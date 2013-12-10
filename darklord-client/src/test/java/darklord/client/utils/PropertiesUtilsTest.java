package darklord.client.utils;

import darklord.client.utils.PropertiesUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Lemi Orhan Ergin
 * @since 10.12.2013
 */
public class PropertiesUtilsTest {

    @Test
    public void loadFromFilesystemResource() throws Exception {
        String absolutePath = getClass().getResource("/darklord/client/sample.properties").getPath();
        Properties props = PropertiesUtils.loadFromFilesystemResource(absolutePath);
        assertProperties(props);
    }

    @Test (expected = IOException.class)
    public void loadFromFilesystemResourceNoFileFound() throws Exception {
        String absolutePath = "/tmp/nonExisting.properties";
        PropertiesUtils.loadFromFilesystemResource(absolutePath);
    }

    @Test
    public void testLoadFromClasspathResource() throws Exception {
        Properties props = PropertiesUtils.loadFromClasspathResource(this.getClass(),
                "darklord/client/sample.properties");
        assertProperties(props);
    }

    @Test
    public void testLoadFromClasspathResourceWithSlash() throws Exception {
        Properties props = PropertiesUtils.loadFromClasspathResource(this.getClass(),
                "/darklord/client/sample.properties");
        assertProperties(props);
    }

    @Test (expected = IOException.class)
    public void testLoadFromClasspathResourceNoFileFound() throws Exception {
        PropertiesUtils.loadFromClasspathResource(this.getClass(),
                "/darklord/client/nonExisting.properties");
    }

    private void assertProperties(Properties props) {
        assertNotNull(props);
        assertEquals(2, props.size());
        assertEquals("value1", props.getProperty("key1"));
        assertEquals("value2", props.getProperty("key2"));
    }
}
