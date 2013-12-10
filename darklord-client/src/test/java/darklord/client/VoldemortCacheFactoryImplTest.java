package darklord.client;

import darklord.client.impl.VoldemortCacheFactoryImpl;
import darklord.client.impl.VoldemortCacheImpl;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotEquals;

/**
 * It is normal to see {@link voldemort.client.BootstrapFailureException} or {@link java.net.ConnectException}
 * in tests due to missing server running in the background. The aim of this test is to check if factory generates
 * a correct cache object with correct properties.
 *
 * @author Lemi Orhan Ergin
 * @since 05.12.2013
 */
public class VoldemortCacheFactoryImplTest {

    private VoldemortCacheFactoryImpl<String, Object> factory;

    /**
     * Cache factory still works even if the server is unreachable or not running.
     */
    @Test
    public void nonExistingClientFile() throws Exception {
        factory = new VoldemortCacheFactoryImpl<>(Arrays.asList("tcp://localhost:6666"), "xyz.properties");
        assertNotNull(factory);
    }

    /**
     * Cache factory still works even if the server is unreachable or not running.
     */
    @Test
    public void noClientFile() throws Exception {
        factory = new VoldemortCacheFactoryImpl<>(Arrays.asList("tcp://localhost:6666"), null);
        assertNotNull(factory);
    }

    @Test
    public void reusingTheSameCache() throws IOException {
        factory = new VoldemortCacheFactoryImpl<>(Arrays.asList("tcp://localhost:6666"),
                getClass().getResource("/darklord/client/clientForTest.properties").getPath());

        VoldemortCacheImpl cache1 = factory.get("store1");
        assertNotNull(cache1);

        VoldemortCacheImpl cache2 = factory.get("store1");
        assertEquals(cache2, cache1); // cache should be reused

        VoldemortCacheImpl cache3 = factory.get("storeXYZ");
        assertNotEquals(cache3, cache1); // cache should be different
    }
}
