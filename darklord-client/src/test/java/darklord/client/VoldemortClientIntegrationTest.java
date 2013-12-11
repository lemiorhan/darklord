package darklord.client;

import darklord.client.utils.ResourceUtils;
import darklord.server.VoldemortCacheServerImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.*;


/**
 * The main aim to test client the functionality of client API
 *
 * @author trerginl
 * @since 11.12.2013
 */
public class VoldemortClientIntegrationTest {

    private static final Logger logger = LoggerFactory.getLogger(VoldemortClientIntegrationTest.class);

    private VoldemortCacheServerImpl server;

    @Before
    public void doBefore() throws Exception {
        String serverPropertiesPath = "/darklord/server";
        server = new VoldemortCacheServerImpl(".", ResourceUtils.getClasspathResource(this.getClass(), serverPropertiesPath).getAbsolutePath());
    }

    @After
    public void doAfter() throws IOException {
        server.stop(); // used for stopping the server after failing tests
    }

    @Test
    public void dataSendReceiveTest() throws IOException {
        server.start();
        checkStatus(true);

        VoldemortCacheFactoryImpl<String, Object> cacheFactory = new VoldemortCacheFactoryImpl<>(Arrays.asList("tcp://localhost:6666"),
                getClass().getResource("/darklord/client/client.properties").getPath());

        VoldemortCacheImpl<String, Object> assetCache = cacheFactory.get("asset");

        // put
        assetCache.put("key", "value");
        assertEquals("value", assetCache.get("key"));

        // update existing
        assetCache.update("key", "value2");
        assertEquals("value2", assetCache.get("key"));

        // update non-existing
        assetCache.update("keyX", "value");
        assertEquals("value", assetCache.get("keyX"));

        // remove existing
        assetCache.remove("key");
        assertNull(assetCache.get("key"));

        // remove non-existing
        assetCache.remove("keyY");
        assertNull(assetCache.get("keyY"));

        server.stop();
        checkStatus(false);
    }

    @Test
    public void managingData() throws IOException {
        server.start();
        checkStatus(true);

        VoldemortCacheFactoryImpl<String, Object> cacheFactory = new VoldemortCacheFactoryImpl<>(Arrays.asList("tcp://localhost:6666"),
                getClass().getResource("/darklord/client/client.properties").getPath());

        VoldemortAdminClientImpl adminClient = new VoldemortAdminClientImpl("tcp://localhost:6667",
                getClass().getResource("/darklord/client/admin.properties").getPath());

        VoldemortCacheManagerImpl manager = new VoldemortCacheManagerImpl(adminClient);


        VoldemortCacheImpl<String, Object> assetCache = cacheFactory.get("asset");

        // clear all in all nodes
        assetCache.put("key", "value");
        assertEquals("value", assetCache.get("key"));
        manager.clearAllCachesInAllNodes();

        // clear all in one node
        assetCache.put("key", "value");
        assertEquals("value", assetCache.get("key"));

        manager.clearAllCachesInOneNode(0);
        assertNull(assetCache.get("key"));

        // clear a store in all
        assetCache.put("key", "value");
        assertEquals("value", assetCache.get("key"));

        manager.clearCacheInAllNodes("asset");
        assertNull(assetCache.get("key"));

        // clear a store in one node
        assetCache.put("key", "value");
        assertEquals("value", assetCache.get("key"));

        manager.clearCacheInOneNode("asset", 0);
        assertNull(assetCache.get("key"));
    }

    @Test
    public void tryingToManageDataAfterServerStops() throws IOException {
        server.start();
        checkStatus(true);

        VoldemortCacheFactoryImpl<String, Object> cacheFactory = new VoldemortCacheFactoryImpl<>(Arrays.asList("tcp://localhost:6666"),
                ResourceUtils.getClasspathResource(this.getClass(), "/darklord/client/client.properties").getAbsolutePath());

        VoldemortAdminClientImpl adminClient = new VoldemortAdminClientImpl("tcp://localhost:6667",
                ResourceUtils.getClasspathResource(this.getClass(), "/darklord/client/admin.properties").getAbsolutePath());

        VoldemortCacheManagerImpl manager = new VoldemortCacheManagerImpl(adminClient);
        VoldemortCacheImpl<String, Object> assetCache = cacheFactory.get("asset");

        server.stop(); // server stops now!

        // now we call all client methods to see how they behave

        assetCache.put("key", "value");
        manager.clearAllCachesInAllNodes();
        assetCache.update("key", "value");
        manager.clearAllCachesInOneNode(0);
        assetCache.remove("key");
        manager.clearCacheInAllNodes("asset");
        manager.clearCacheInOneNode("asset", 0);
        assertNull(assetCache.get("key"));

        // we should see the test is passing. It means the api logs the errors and does not throw exceptions to user.
    }

    /**
     * Since voldemort client is LazyStoreClient, it does not halt if the connection cannot be established in
     * contrast to admin client.
     */
    @Test
    public void cacheToConnectToStoppedServer() throws IOException {
        VoldemortCacheFactoryImpl cacheFactory = new VoldemortCacheFactoryImpl(Arrays.asList("tcp://localhost:6666"),
                getClass().getResource("/darklord/client/client.properties").getPath());

        VoldemortCacheImpl assetCache = cacheFactory.get("asset");
        assertNotNull(assetCache);
    }

    /**
     * Admin client throws exception if the connection is lost. There is no such a lazy admin client unfortunately.
     */
    @Test
    public void adminClientToConnectToStoppedServer() throws IOException {
        VoldemortAdminClientImpl adminClient = new VoldemortAdminClientImpl("tcp://localhost:6667",
                getClass().getResource("/darklord/client/admin.properties").getPath());
        assertNotNull(adminClient);
    }

    private void checkStatus(boolean statusToCheck) {
        int limit = 15; //seconds
        int t = 0;
        int interval = 250; //milliseconds
        boolean statusOk = !statusToCheck;

        while (t < limit * 1000) {
            if (server.isRunning() == statusToCheck) {
                statusOk = true;
                break;
            }
            t += interval;
            logger.info("Status check done at {} ms", t);
        }
        assertTrue("Server status should be " + (statusToCheck ? "started" : "stopped"), statusOk);
    }

}
