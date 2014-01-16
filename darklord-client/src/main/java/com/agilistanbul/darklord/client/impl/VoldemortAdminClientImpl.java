package com.agilistanbul.darklord.client.impl;

import com.agilistanbul.darklord.client.VoldemortAdminClient;
import com.agilistanbul.darklord.commons.utils.PropertiesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import voldemort.client.protocol.admin.AdminClient;
import voldemort.client.protocol.admin.AdminClientConfig;
import voldemort.cluster.Node;
import voldemort.store.StoreDefinition;
import voldemort.versioning.Versioned;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the only implementation using AdminClient of Voldemort. Since Voldemort is not build on interfaces,
 * it is not possible to isolate Voldemort API from our code in unit tests. To resolve this issue, this class and
 * the interface is created.
 * <p/>
 * It is important to note that Voldemort's admin client tries to connect with the server while starting up.
 * Therefore be sure to have a server running before creating this class. If the server is not running, the admin
 * client still works.
 * <p/>
 * Sample admin.properties file is available under resource directory.
 *
 * @author Lemi Orhan Ergin
 * @since 06.12.2013
 */
public class VoldemortAdminClientImpl implements VoldemortAdminClient {

    private static final Logger logger = LoggerFactory.getLogger(VoldemortAdminClientImpl.class);

    private AdminClient adminClient;

    public VoldemortAdminClientImpl(String adminUrl, String adminClientConfigPath) {
        try {
            this.adminClient = new AdminClient(adminUrl, detectConfig(adminClientConfigPath));
        } catch (Exception e) {
            logger.error("Unable to create admin client. Operations are in OFFLINE mode now.", e);
        }
    }

    public VoldemortAdminClientImpl(String adminUrl) {
        this(adminUrl, null);
    }

    @Override
    public void clear(int nodeId, String storeName) {
        if (adminClient == null) {
            logger.error("[Voldemort OFFLINE] operation: clear, nodeId: {}, store: {}", nodeId, storeName);
            return;
        }
        try {
            adminClient.storeMntOps.truncate(nodeId, storeName);
            logger.info("[Voldemort CLEARED] operation: clear, store: {}, nodeId: {}", storeName, nodeId);
        } catch (Exception e) {
            logger.error("[Voldemort ERROR] operation: clear, nodeId: {}, store: {}", nodeId, storeName, e);
        }
    }

    @Override
    public List<String> getStoreNames(int nodeId) {
        List<String> storeNames = new ArrayList<>();

        if (adminClient == null) {
            logger.error("[Voldemort OFFLINE] operation: getStoreNames, nodeId: {}, store: {}", nodeId);
            return storeNames;
        }

        Versioned<List<StoreDefinition>> storeDefinitions;

        try {
            storeDefinitions = adminClient.metadataMgmtOps.getRemoteStoreDefList(nodeId);
        } catch (Exception e) {
            logger.error("[Voldemort ERROR] operation: getStoreNames, nodeId: {}", nodeId, e);
            return storeNames;
        }

        for (StoreDefinition def : storeDefinitions.getValue()) {
            storeNames.add(def.getName());
        }

        return storeNames;
    }

    @Override
    public List<Integer> getNodeIds() {
        List<Integer> nodeIds = new ArrayList<>();

        if (adminClient == null) {
            logger.error("[Voldemort OFFLINE] operation: getNodeIds");
            return nodeIds;
        }

        try {
            for (Node node : adminClient.getAdminClientCluster().getNodes()) {
                nodeIds.add(node.getId());
            }
        } catch (Exception e) {
            logger.error("[Voldemort ERROR] operation: getNodeIds", e);
        }
        return nodeIds;
    }

    private AdminClientConfig detectConfig(String path) {
        if (path != null) {
            try {
                return new AdminClientConfig(PropertiesUtils.loadFromClasspathResource(this.getClass(), path));
            } catch (IOException e) {
                logger.error("[VOLDEMORT ERROR] Properties file cannot be loaded due to errors", e);
            }
        }
        return new AdminClientConfig();

    }
}
