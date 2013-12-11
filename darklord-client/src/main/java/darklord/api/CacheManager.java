package darklord.api;

/**
 * It represents the administration functionality of the caches. The main reason of having a separate class for
 * administration tasks is not to mess up the core api with non-core functionalities.
 *
 * @author Lemi Orhan Ergin
 * @since 02.12.2013
 */
public interface CacheManager {

    /**
     * Removes the content of the given cache in all nodes of the cluster. It is useful to clean a cache throughout
     * the cluster.
     *
     * @param cacheName name of the cache to delete
     */
    public void clearCacheInAllNodes(String cacheName);

    /**
     * Removes the content of all caches in all nodes in a cluster. Be careful! It deletes everything in the cluster.
     */
    public void clearAllCachesInAllNodes();

    /**
     * Clears a specific cache in a specific node in the cluster.
     *
     * @param cacheName name of the cache to clear
     * @param nodeId the id of the target node
     */
    public void clearCacheInOneNode(String cacheName, Integer nodeId);

    /**
     * Clears all caches in a specific cluster.
     *
     * @param nodeId the id of the target node
     */
    public void clearAllCachesInOneNode(Integer nodeId);
}
