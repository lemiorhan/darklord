package com.agilistanbul.darklord.client.impl;

import com.agilistanbul.darklord.client.Cache;
import com.agilistanbul.darklord.client.CacheFactory;
import com.agilistanbul.darklord.commons.utils.ResourceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import voldemort.client.*;

import java.util.List;

/**
 * It represents the factory that generates VoldemortCache. Note that Voldemort cache needs to have a config file
 * containing all customizations and parameters. All configuration parameters should be entered to that file instead
 * of adding via the api.
 *
 * {@link ClientConfig} has the full list of parameters used in client.properties file. A sample properties file is
 * also available under resources directory.
 *
 * It is important to note that Voldemort's client factory tries to connect with the server while starting up.
 * Therefore be sure to have a server running before creating this class. If the server is not running, cache factory
 * still works.
 *
 * @author Lemi Orhan Ergin
 * @since 02.12.2013
 */
public class VoldemortCacheFactoryImpl<K, V> implements CacheFactory<K, V, Cache<K, V>> {

    private static final Logger logger = LoggerFactory.getLogger(VoldemortCacheFactoryImpl.class);

    private StoreClientFactory clientFactory;

    public VoldemortCacheFactoryImpl(List<String> nodeUrls, String configFilePath) {
        try {
            clientFactory = new CachingStoreClientFactory(
                    new SocketStoreClientFactory(
                            new ClientConfig(ResourceUtils.getFile(this.getClass(), configFilePath)).setBootstrapUrls(nodeUrls)
                    ));
        } catch (Exception e) {
            logger.error("Unable to create cache factory", e);
        }
    }

    @Override
    public Cache<K, V> get(String cacheName) {
        return new VoldemortCacheImpl<>(cacheName, (StoreClient<K, V>) clientFactory.getStoreClient(cacheName));
    }
}
