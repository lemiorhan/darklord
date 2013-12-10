package darklord.client;

/**
 * This is the main interface for a cache object. It has methods for CRUD operations. The implementations of Cache
 * interface are simply clients of cache servers. All keys are assumed as Strings. If the cached items could be
 * in any format (not only POJOs need to be cached unfortunately), use "Object" as V.
 *
 * The methods do not throw any checked exceptions. If the implementations need to throw exceptions due to special
 * requirements, they should throw Runtime Exceptions.
 *
 * @author Lemi Orhan Ergin
 * @since 24.05.2013
 */

public interface Cache<K, V> {

    /**
     * Removes the key-value entry if the key exists
     *
     * @param key K as the key to be deleted
     */
    public void remove(K key);

    /**
     * Gets the value of the key-value entry if the key exists. The returning type is V to make it more generic.
     *
     * @param key K as the key
     * @return V as the value object
     */
	public V get(K key);

    /**
     * Inserts key-value entry if the key does not exist.
     *
     * @param key K as the key
     * @param value V as the value of the object
     */
	public void put(K key, V value);

    /**
     * Updates key-value entry if the key exists.
     *
     * @param key K as the key
     * @param value V as the value of the object
     */
	public void update(K key, V value);

}
