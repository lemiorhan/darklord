package com.agilistanbul.darklord.testapi.asset;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

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
        return o instanceof AssetIdImpl && EqualsBuilder.reflectionEquals(this, o);
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
