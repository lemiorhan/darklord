package com.agilistanbul.darklord.testapi.asset;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * @author trerginl
 * @since 16.01.2014
 */
public class AssetImpl implements Asset, Serializable {

    private AssetId assetId;

    private String content;

    public AssetImpl(AssetId assetId, String content) {
        this.assetId = assetId;
        this.content = content;
    }

    @Override
    public AssetId getAssetId() {
        return this.assetId;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AssetImpl asset = (AssetImpl) o;

        if (assetId != null ? !assetId.equals(asset.assetId) : asset.assetId != null) return false;
        if (content != null ? !content.equals(asset.content) : asset.content != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = assetId != null ? assetId.hashCode() : 0;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("assetId", assetId)
                .append("content", content)
                .toString();
    }
}
