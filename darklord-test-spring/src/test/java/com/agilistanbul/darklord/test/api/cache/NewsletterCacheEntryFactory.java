package com.agilistanbul.darklord.test.api.cache;

import com.agilistanbul.darklord.client.Cache;
import com.agilistanbul.darklord.test.api.asset.Asset;
import com.agilistanbul.darklord.test.api.asset.AssetId;
import com.agilistanbul.darklord.test.api.asset.AssetImpl;
import net.sf.ehcache.constructs.blocking.CacheEntryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author trerginl
 * @since 17.01.2014
 */
@Component
public class NewsletterCacheEntryFactory implements CacheEntryFactory {

    @Autowired
    private Cache<AssetId, Asset> newsletterAssetCache;

    @Override
    public Object createEntry(Object o) throws Exception {
        Asset asset = newsletterAssetCache.get((AssetId) o);
        return asset != null ? asset : new AssetImpl((AssetId) o, "Newsletter for " + o.toString());
    }
}
