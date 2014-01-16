package com.agilistanbul.darklord.test.spring;

import com.agilistanbul.darklord.client.Cache;
import com.agilistanbul.darklord.testapi.asset.Asset;
import com.agilistanbul.darklord.testapi.asset.AssetId;
import com.agilistanbul.darklord.testapi.asset.AssetIdImpl;
import com.agilistanbul.darklord.testapi.asset.AssetImpl;
import com.agilistanbul.darklord.testapi.cache.AssetCache;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

/**
 * @author trerginl
 * @since 16.01.2014
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:testApplicationContext.xml"})
public class SpringBasedIntegrationTest {

    @Autowired
    private Cache<AssetId, Asset> newsletterAssetCache;

    @Test
    public void clientCheck() {
        AssetId assetId = new AssetIdImpl("newsletter", 12L);
        newsletterAssetCache.put(assetId, new AssetImpl(assetId, "new years newsletter"));

        assertEquals(new AssetImpl(assetId, "new years newsletter"), newsletterAssetCache.get(new AssetIdImpl("newsletter", 12L)));
    }
}
