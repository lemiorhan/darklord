package com.agilistanbul.darklord.testapi.cache;

import com.agilistanbul.darklord.client.Cache;
import com.agilistanbul.darklord.testapi.asset.Asset;
import com.agilistanbul.darklord.testapi.asset.AssetId;

/**
 * @author trerginl
 * @since 16.01.2014
 */
public interface AssetCache extends Cache {

    public Asset getAsset(AssetId assetId);

    public void setAsset(Asset asset);

}
