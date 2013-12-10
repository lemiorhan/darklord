package darklord.server.impl;

import darklord.server.CacheServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import voldemort.common.service.VoldemortService;
import voldemort.server.VoldemortConfig;
import voldemort.server.VoldemortServer;

/**
 * This is a local Voldemort server that can be started and stopped. It is not mandatory to have Voldemort installed on
 * your local machine. All dependencies that Voldemort server needs are given in the pom file. The config files can be
 * stored in the classpath to keep the versions. Normally local server is only used in local machines for testing
 * purposes.
 * <p/>
 * All configuration of server is done on config files.
 * <p/>
 * The server normally creates 1 node having several partitions. No need to have more.
 *
 * @author Lemi Orhan Ergin
 * @since 02.12.2013
 */
public class VoldemortCacheServerImpl implements CacheServer {

    private static final Logger logger = LoggerFactory.getLogger(VoldemortCacheServerImpl.class);

    private VoldemortService server;

    private VoldemortConfig config;

    public VoldemortCacheServerImpl(String voldemortHomePath, String voldemortConfigPath) {
        config = VoldemortConfig.loadFromVoldemortHome(voldemortHomePath, voldemortConfigPath);
    }

    public VoldemortCacheServerImpl(VoldemortService server, VoldemortConfig config) {
        this.server = server;
        this.config = config;
    }

    @Override
    public void start() {
        if (isRunning()) {
            logger.info("Voldemort cache server is already running");
            return;
        }

        if (server == null) {
            server = new VoldemortServer(config);
        }

        server.start();
        logger.info("Local Voldemort cache server is started");
    }

    @Override
    public void stop() {
        if (isRunning()) {
            server.stop();
            logger.info("Local Voldemort cache server is stopped");
        }
    }

    @Override
    public boolean isRunning() {
        return server != null && server.isStarted();
    }

}
