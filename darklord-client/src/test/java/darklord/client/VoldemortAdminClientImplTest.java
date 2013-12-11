package darklord.client;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

/**
 * @author Lemi Orhan Ergin
 * @since 10.12.2013
 */
public class VoldemortAdminClientImplTest {

    private VoldemortAdminClientImpl adminClient;

    @Before
    public void doBefore() {
        // this will log errors because server is not available
        adminClient = new VoldemortAdminClientImpl("tcp://localhost:6667");
        assertNotNull(adminClient);
    }

    @Test
    public void testClear() throws Exception {
        adminClient.clear(1, "store1");
        // The test is checking no exceptions are thrown.
    }

    @Test
    public void testGetStoreNames() throws Exception {
        assertEquals(new ArrayList(), adminClient.getStoreNames(1));
    }

    @Test
    public void testGetNodeIds() throws Exception {
        assertEquals(new ArrayList(), adminClient.getNodeIds());
    }
}
