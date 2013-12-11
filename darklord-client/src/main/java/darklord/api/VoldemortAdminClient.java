package darklord.api;

import java.util.List;

/**
 * 3rd party Voldemort API is built in such a bad way that it is really hard to isolate it from your unit tests.
 *
 * The API does 3 bad practices unfortunately:
 * 1) Very limited usage of interfaces on core client api
 * 2) Does business logic in constructors
 * 3) Uses inner classes for external access
 *
 * That's why this interface is designed for isolating Voldemort's Admin Client from our application code.
 *
 * @author Lemi Orhan Ergin
 * @since 06.12.2013
 */
public interface VoldemortAdminClient {

    /**
     * Truncates all data of a store in a specific node
     */
    public void clear(int nodeId, String storeName);

    /**
     * Gets the list of available stores in a specific node
     */
    public List<String> getStoreNames(int nodeId);

    /**
     * Gets the list of node ids in Voldemort cluster
     */
    public List<Integer> getNodeIds();
}
