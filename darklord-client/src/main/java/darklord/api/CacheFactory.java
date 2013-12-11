package darklord.api;

/**
 * CacheFactory is a simple factory to create Cache objects for the given cache names. For example cache names could be
 * "store" names of Voldemort. We call it cache names in general.
 *
 * @author Lemi Orhan Ergin
 * @since 02.12.2013
 */
public interface CacheFactory<K, V, E extends Cache<K, V>> {

    /**
     * Creates a new instance of object extending Cache. Since there exists different Cache instances for each cache
     * name, the method requires the name of the cache as a parameter.
     *
     * @param cacheName String as name of the cache
     * @return E as the cache instance
     */
    public E get(String cacheName);
}
