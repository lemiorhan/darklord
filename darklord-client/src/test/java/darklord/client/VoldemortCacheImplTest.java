package darklord.client;

import darklord.api.Cache;
import org.apache.commons.lang.SerializationUtils;
import org.junit.Before;
import org.junit.Test;
import voldemort.client.DefaultStoreClient;
import voldemort.client.StoreClient;
import voldemort.serialization.SerializationException;
import voldemort.versioning.Occurred;
import voldemort.versioning.Version;

import java.io.Serializable;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

/**
 * @author Lemi Orhan Ergin
 * @since 05.12.2013
 */
public class VoldemortCacheImplTest {

    private StoreClient<String, Object> storeClient;
    private VoldemortCacheImpl<String, Object> cache;

    @Before
    public void doBefore() {
        storeClient = createMock(StoreClient.class);
        cache = new VoldemortCacheImpl<>("store", storeClient);
    }

    @Test
    public void deleteObjects() throws Exception {
        expect(storeClient.delete("key")).andReturn(true);

        replay(storeClient);

        cache.remove("key");

        verify(storeClient);
    }

    @Test
    public void deleteNonExitingObjects() throws Exception {
        expect(storeClient.delete("key")).andReturn(false);

        replay(storeClient);

        cache.remove("key");

        verify(storeClient);
    }

    @Test
    public void deleteWithException() throws Exception {
        expect(storeClient.delete("key")).andThrow(new RuntimeException("Error in deletion"));

        replay(storeClient);

        cache.remove("key");

        verify(storeClient);
    }

    @Test
    public void getValidObject() throws Exception {
        expect(storeClient.getValue("key")).andReturn(SerializationUtils.serialize("value"));

        replay(storeClient);

        Object value = cache.get("key");
        assertEquals("value", value);

        verify(storeClient);
    }

    @Test
    public void getNoObject() {
        expect(storeClient.getValue("key")).andReturn(null);

        replay(storeClient);

        assertNull(cache.get("key"));

        verify(storeClient);
    }

    @Test
    public void getIfStoreNotFound() {
        expect(storeClient.getValue("key")).andThrow(new RuntimeException("Store not found"));

        replay(storeClient);

        assertNull(cache.get("key"));

        verify(storeClient);
    }

    @Test
    public void getWithDeserializationError() {
        expect(storeClient.getValue("key")).andReturn("anyString".getBytes());

        replay(storeClient);

        assertNull(cache.get("key"));

        verify(storeClient);
    }

    @Test
    public void putObjects() throws Exception {
        expect(storeClient.put(isA(String.class), isA(Serializable.class))).andReturn(new Version() {

            @Override
            public Occurred compare(Version v) {
                return Occurred.AFTER;
            }
        });

        replay(storeClient);

        cache.put("key", "value");

        verify(storeClient);
    }

    @Test
    public void putNullValue() throws Exception {
        replay(storeClient);

        cache.put("key", null);

        verify(storeClient);
    }

    @Test
    public void putWithSerializationError() throws Exception {
        expect(storeClient.put(isA(String.class), isA(Serializable.class))).andThrow(new SerializationException("problem"));

        replay(storeClient);

        cache.put("key", "value");

        verify(storeClient);
    }

    @Test
    public void updateObjects() throws Exception {
        expect(storeClient.put(isA(String.class), isA(Serializable.class))).andReturn(new Version() {

            @Override
            public Occurred compare(Version v) {
                return Occurred.AFTER;
            }
        });

        replay(storeClient);

        cache.update("key", "value");

        verify(storeClient);
    }

    @Test
    public void compare() {
        Cache cache1 = new VoldemortCacheImpl<>("store", storeClient);
        Cache cache2 = new VoldemortCacheImpl<>("store", storeClient);
        Cache cache3 = new VoldemortCacheImpl<>("store", new DefaultStoreClient<String, Object>());

        assertEquals(cache1, cache2);
        assertNotEquals(cache1, cache3);
    }

    @Test
    public void toStringTest() {
        Cache cache = new VoldemortCacheImpl<>("store", new DefaultStoreClient<String, Object>());
        assertTrue("ToString method should return "+cache.toString(),
                cache.toString().matches("\\QVoldemortCacheImpl[storeName=store,storeClient=voldemort.client.DefaultStoreClient@\\E.*]"));
    }
}
