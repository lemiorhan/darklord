package com.agilistanbul.darklord.test.api.asset;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

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
        return o instanceof AssetImpl && EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
