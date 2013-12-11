package darklord.server;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

import static org.junit.Assert.assertTrue;

/**
 * In order to run this test, It is not required to have Voldemort installed. Just provide a valid folder as voldemort
 * home and the path of the folder having config files.
 * <p/>
 * This test not only checks if the server starts and stops properly, but also check other features by using client api.
 *
 * @author Lemi Orhan Ergin
 * @since 03.12.2013
 */
public class VoldemortCacheServerIntegrationTest {

    private static final Logger logger = LoggerFactory.getLogger(VoldemortCacheServerIntegrationTest.class);

    private VoldemortCacheServerImpl server;

    @Before
    public void doBefore() throws Exception {
        String serverPropertiesPath = "/darklord/server";
        server = new VoldemortCacheServerImpl(".", this.getClass().getResource(serverPropertiesPath).getPath());
    }

    @After
    public void doAfter() throws IOException {
        server.stop(); // used for stopping the server after failing tests
    }

    @Test
    public void startStopTest() throws InterruptedException {
        server.start();
        checkStatus(true);

        server.stop();
        checkStatus(false);
    }

    @Test
    public void meaninglessStartStopTest() throws InterruptedException {
        // 3 starts
        server.start();
        checkStatus(true);

        server.start();
        checkStatus(true);

        server.start();
        checkStatus(true);

        // 3 stops
        server.stop();
        checkStatus(false);

        server.stop();
        checkStatus(false);

        server.stop();
        checkStatus(false);
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