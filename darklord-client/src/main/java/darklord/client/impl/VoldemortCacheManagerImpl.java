package darklord.client.impl;

import darklord.client.CacheManager;
import darklord.client.VoldemortAdminClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Default implementation of CacheManager. It requires to have VoldemortAdminClient to make administrative tasks.
 *
 * @author Lemi Orhan Ergin
 * @since 02.12.2013
 */
public class VoldemortCacheManagerImpl implements CacheManager {

    private static final Logger logger = LoggerFactory.getLogger(VoldemortCacheManagerImpl.class);

    private VoldemortAdminClient adminClient;

    public VoldemortCacheManagerImpl(VoldemortAdminClient adminClient) {
        this.adminClient = adminClient;
    }

    @Override
    public void clearCacheInAllNodes(String cacheName) {
        List<Integer> nodeIds = adminClient.getNodeIds();
        for (Integer nodeId : nodeIds) {
            clearCacheInOneNode(cacheName, nodeId);
        }
    }

    @Override
    public void clearAllCachesInAllNodes() {
        List<Integer> nodeIds = adminClient.getNodeIds();
        for (Integer nodeId : nodeIds) {
            clearAllCachesInOneNode(nodeId);
        }
    }

    @Override
    public void clearCacheInOneNode(String cacheName, Integer nodeId) {
        adminClient.clear(nodeId, cacheName);
    }

    @Override
    public void clearAllCachesInOneNode(Integer nodeId) {
        List<String> storeNames = adminClient.getStoreNames(nodeId);
        for (String storeName : storeNames) {
            clearCacheInOneNode(storeName, nodeId);
        }
    }


}
