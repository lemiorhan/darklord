package darklord.server;

import darklord.server.impl.VoldemortCacheServerImpl;
import org.junit.Before;
import org.junit.Test;
import voldemort.common.service.VoldemortService;
import voldemort.server.VoldemortConfig;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Lemi Orhan Ergin
 * @since 07.12.2013
 */
public class VoldemortCacheServerImplTest {

    private VoldemortCacheServerImpl server;
    private VoldemortService service;
    private VoldemortConfig config;

    @Before
    public void doBefore() {
        service = createMock(VoldemortService.class);
        config = createMock(VoldemortConfig.class);
        server = new VoldemortCacheServerImpl(service, config);
    }

    @Test
    public void start() throws Exception {
        expect(service.isStarted()).andReturn(false);
        service.start();
        expectLastCall();

        replay(service, config);
        server.start();
        verify(service, config);
    }

    @Test
    public void startButAlreadyStarted() throws Exception {
        expect(service.isStarted()).andReturn(true);

        replay(service, config);
        server.start();
        verify(service, config);
    }

    @Test
    public void stop() throws Exception {
        expect(service.isStarted()).andReturn(true);
        service.stop();
        expectLastCall();

        replay(service, config);
        server.stop();
        verify(service, config);
    }

    @Test
    public void stopButAlreadyStopped() throws Exception {
        expect(service.isStarted()).andReturn(false);

        replay(service, config);
        server.stop();
        verify(service, config);
    }

    @Test
    public void isRunningForNullService() throws Exception {
        VoldemortCacheServerImpl server2 = new VoldemortCacheServerImpl(null, config);
        assertFalse(server2.isRunning());
    }

    @Test
    public void isRunningButStopped() throws Exception {
        expect(service.isStarted()).andReturn(false);

        replay(service, config);
        assertFalse(server.isRunning());
        verify(service, config);
    }

    @Test
    public void isRunningForStarted() throws Exception {
        expect(service.isStarted()).andReturn(true);

        replay(service, config);
        assertTrue(server.isRunning());
        verify(service, config);
    }
}
