package com.agilistanbul.darklord.testapi.cache;

import com.agilistanbul.darklord.client.impl.VoldemortCacheImpl;
import com.agilistanbul.darklord.testapi.asset.Asset;
import com.agilistanbul.darklord.testapi.asset.AssetId;
import voldemort.client.StoreClient;

/**
 * @author trerginl
 * @since 16.01.2014
 */
public class AssetCacheImpl<K, V>  extends VoldemortCacheImpl implements AssetCache {

    public AssetCacheImpl(String storeName, StoreClient storeClient) {
        super(storeName, storeClient);
    }

    @Override
    public Asset getAsset(AssetId assetId) {
        return (Asset) get("" + assetId.getId());
    }

    @Override
    public void setAsset(Asset asset) {
        put("" + asset.getAssetId().getId(), asset);
    }
}
