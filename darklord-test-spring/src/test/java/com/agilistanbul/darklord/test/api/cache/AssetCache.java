package com.agilistanbul.darklord.test.api.cache;

import com.agilistanbul.darklord.client.Cache;
import com.agilistanbul.darklord.test.api.asset.Asset;
import com.agilistanbul.darklord.test.api.asset.AssetId;

/**
 * @author trerginl
 * @since 16.01.2014
 */
public interface AssetCache extends Cache {

    public Asset getAsset(AssetId assetId);

    public void setAsset(Asset asset);

}
