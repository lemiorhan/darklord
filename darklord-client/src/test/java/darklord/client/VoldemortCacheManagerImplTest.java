package darklord.client;

import darklord.client.impl.VoldemortCacheManagerImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.easymock.EasyMock.*;

/**
 * @author Lemi Orhan Ergin
 * @since 06.12.2013
 */
public class VoldemortCacheManagerImplTest {

    private VoldemortCacheManagerImpl manager;
    private VoldemortAdminClient adminClient;

    @Before
    public void doBefore() {
        adminClient = createMock(VoldemortAdminClient.class);
        manager = new VoldemortCacheManagerImpl(adminClient);
    }

    @Test
    public void clearAll() {
        expect(adminClient.getNodeIds()).andReturn(Arrays.asList(1,2,3));

        expect(adminClient.getStoreNames(1)).andReturn(Arrays.asList("store1", "store2"));
        adminClient.clear(1, "store1");
        expectLastCall();
        adminClient.clear(1, "store2");
        expectLastCall();

        expect(adminClient.getStoreNames(2)).andReturn(Arrays.asList("store1", "store2"));
        adminClient.clear(2, "store1");
        expectLastCall();
        adminClient.clear(2, "store2");
        expectLastCall();

        expect(adminClient.getStoreNames(3)).andReturn(Arrays.asList("store1", "store2"));
        adminClient.clear(3, "store1");
        expectLastCall();
        adminClient.clear(3, "store2");
        expectLastCall();

        replay(adminClient);
        manager.clearAllCachesInAllNodes();
        verify(adminClient);
    }

    @Test
    public void clearAllInEmptyCluster() {
        expect(adminClient.getNodeIds()).andReturn(new ArrayList<Integer>());

        replay(adminClient);
        manager.clearAllCachesInAllNodes();
        verify(adminClient);
    }

    @Test
    public void clearAllStoresInOneNode() {
        expect(adminClient.getStoreNames(3)).andReturn(Arrays.asList("store1", "store2"));
        adminClient.clear(3, "store1");
        expectLastCall();
        adminClient.clear(3, "store2");
        expectLastCall();

        replay(adminClient);
        manager.clearAllCachesInOneNode(3);
        verify(adminClient);
    }

    @Test
    public void clearOneStoreInAllNodes() {
        expect(adminClient.getNodeIds()).andReturn(Arrays.asList(1,2,3));

        adminClient.clear(1, "store2");
        expectLastCall();

        adminClient.clear(2, "store2");
        expectLastCall();

        adminClient.clear(3, "store2");
        expectLastCall();

        replay(adminClient);
        manager.clearCacheInAllNodes("store2");
        verify(adminClient);
    }

    @Test
    public void clearOneStoreInOneNode() {
        adminClient.clear(3, "store2");
        expectLastCall();

        replay(adminClient);
        manager.clearCacheInOneNode("store2", 3);
        verify(adminClient);
    }
}
