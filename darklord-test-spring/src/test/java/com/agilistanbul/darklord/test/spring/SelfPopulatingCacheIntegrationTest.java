package com.agilistanbul.darklord.test.spring;

import com.agilistanbul.darklord.client.Cache;
import com.agilistanbul.darklord.test.api.asset.Asset;
import com.agilistanbul.darklord.test.api.asset.AssetId;
import com.agilistanbul.darklord.test.api.asset.AssetIdImpl;
import com.agilistanbul.darklord.test.api.asset.AssetImpl;
import com.agilistanbul.darklord.test.api.cache.NewsletterCacheProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author trerginl
 * @since 16.01.2014
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:testApplicationContext-voldemort.xml", "classpath:testApplicationContext-ehcache.xml"})
public class SelfPopulatingCacheIntegrationTest {

    @Autowired
    private NewsletterCacheProvider provider;

    @Autowired
    private Cache<AssetId, Asset> newsletterAssetCache;

    @Test
    public void ehcacheCheck() {
        // from data provider

        AssetId assetId12 = new AssetIdImpl("newsletter", 12L);
        Asset asset12 = new AssetImpl(assetId12, "Newsletter for AssetIdImpl[id=12,type=newsletter]");

        Asset assetFetched = provider.get(assetId12);
        assertEquals(asset12, assetFetched);

        // from voldemort

        AssetId assetId14 = new AssetIdImpl("newsletter", 14L);
        Asset asset14 = new AssetImpl(assetId14, "new years newsletter");

        newsletterAssetCache.put(assetId14, asset14);
        assertEquals(asset14, provider.get(assetId14));
    }

}