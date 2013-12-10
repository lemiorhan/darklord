package darklord.server;

/**
 * It represents a cache server running on local machines. Normally no instance of CacheServer is required in
 * environments like dev or testQA. This interface can also be used for creating fake implementations too.
 *
 * @author Lemi Orhan Ergin
 * @since 02.12.2013
 */
public interface CacheServer {

    /**
     * Starts the server
     */
    public void start();

    /**
     * Stops the server
     */
    public void stop();

    /**
     * Represents the status of the server. It returns true if the server is running.
     *
     * @return boolean as the status of the server
     */
    public boolean isRunning();
}
