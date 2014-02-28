package com.agilistanbul.darklord.test.api.cache;

import com.agilistanbul.darklord.test.api.asset.Asset;
import com.agilistanbul.darklord.test.api.asset.AssetId;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author trerginl
 * @since 17.01.2014
 */
@Component
public class NewsletterCacheProvider {

    @Autowired
    private Ehcache selfPopulatingNewsletterCache;

    public Asset get(AssetId assetId) {
        Element element = selfPopulatingNewsletterCache.get(assetId);
        return (Asset) element.getObjectValue();
    }
}
