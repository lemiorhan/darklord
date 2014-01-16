package com.agilistanbul.darklord.client.impl;

import com.agilistanbul.darklord.client.Cache;
import org.apache.commons.lang.SerializationUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import voldemort.client.StoreClient;
import voldemort.versioning.Version;

import java.io.Serializable;

/**
 * This is the default implementation of Voldemort Cache. It uses Voldemort's StoreClient api to access Voldemort.
 *
 * Logs are defined as [title] first, comma separated "key:value"s later in order to follow logging best practices.
 *
 * @author Lemi Orhan Ergin
 * @since 24.05.2013
 */

public class VoldemortCacheImpl<K, V> implements Cache<K, V> {

    private static final Logger logger = LoggerFactory.getLogger(VoldemortCacheImpl.class);

    private String storeName;
    private StoreClient<K, V> storeClient;

    public VoldemortCacheImpl(String storeName, StoreClient<K, V> storeClient) {
        this.storeName = storeName;
        this.storeClient = storeClient;
    }

    @Override
    public void remove(K key) {
        boolean deleteResult;
        try {
            deleteResult = storeClient.delete(key);
        } catch (Exception e) {
            logger.error("[Voldemort ERROR] operation: delete, store: {}, key: {}", storeName, key, e);
            return;
        }

        if (!deleteResult) {
            // if there exists nothing to delete, delete method returns false. It might be good to see
            // the attempts that calls for deleting a non-existing item for debugging purposes
            logger.debug("[Voldemort NOTHING_TO_DELETE] operation: delete, store: {}, key: {}", storeName, key);
        }
    }

    @Override
    public V get(K key) {
        byte[] obj;
        try {
            // we are interested in only the values, not the versioned list of values because we store objects, not json
            // that is the reason why we return just the last value.
            obj = (byte[]) storeClient.getValue(key);
        } catch (Exception exception) {
            logger.error("[Voldemort ERROR] operation: get, store: {}, key: {}", storeName, key, exception);
            return null;
        }

        if (obj != null) {
            logger.info("[Voldemort SUCCESS] operation: get, status: HIT, store: {}, key: {}", storeName, key);
            try {
                return (V) SerializationUtils.deserialize(obj);
            } catch (Exception e) {
                logger.error("[Voldemort ERROR] operation: get, store: {}, key: {}", storeName, key, e);
                return null;
            }
        }
        logger.info("[Voldemort SUCCESS] operation: get, status: MISS, store: {}, key: {}", storeName, key);
        return (V) obj;
    }

    @Override
    public void put(K key, V value) {
        if (value == null) {
            logger.info("[Voldemort REJECTED] operation: put, store: {}, key: {}, value: NULL", storeName, key);
            return;
        }
        try {
            Version version = storeClient.put(key,
                    (V) SerializationUtils.serialize((Serializable) value));
            logger.info("[Voldemort SUCCESS] operation: put, store: {}, key: {}, version: {}", storeName, key, version);
        } catch (Exception exception) {
            logger.error("[Voldemort ERROR] operation: put, store: {}, key: {}", storeName, key, exception);
        }
    }

    @Override
    public void update(K key, V value) {
        put(key, value);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof VoldemortCacheImpl && EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
