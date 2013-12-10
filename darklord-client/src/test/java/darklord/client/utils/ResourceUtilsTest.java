package darklord.client.utils;

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
         assertEquals(new File(this.getClass().getResource("/darklord/client/utils/ResourceUtils.class").getPath()),
                 ResourceUtils.getClasspathResource(this.getClass(), "/darklord/client/utils/ResourceUtils.class"));
    }

    @Test (expected = IOException.class)
    public void getClasspathResourceInvalidPath() throws IOException {
        ResourceUtils.getClasspathResource(this.getClass(), "/x/y/z");
    }
}
