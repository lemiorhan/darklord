package com.agilistanbul.darklord.testapi.asset;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * @author trerginl
 * @since 16.01.2014
 */
public class AssetIdImpl implements AssetId, Serializable {

    private long id;

    private String type;

    public AssetIdImpl(String type, long id) {
        this.id = id;
        this.type = type;
    }

    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AssetIdImpl assetId = (AssetIdImpl) o;

        if (id != assetId.id) return false;
        if (type != null ? !type.equals(assetId.type) : assetId.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("type", type)
                .toString();
    }
}
