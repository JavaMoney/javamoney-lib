package org.javamoney.moneta.internal.convert.yahoo;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "meta",
    "resources"
})
@XmlRootElement(name = "list")
public class YahooRoot {

    @XmlElement(required = true)
    private Meta meta;
    @XmlElement(required = true)
    private YahooCurrencies resources;
    @XmlAttribute(name = "version")
    private BigDecimal version;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta value) {
        this.meta = value;
    }

    public YahooCurrencies getResources() {
        return resources;
    }

    public void setResources(YahooCurrencies value) {
        this.resources = value;
    }

    public BigDecimal getVersion() {
        return version;
    }

    public void setVersion(BigDecimal value) {
        this.version = value;
    }

	@Override
	public String toString() {
		return "YahooRoot [meta=" + meta + ", resources=" + resources
				+ ", version=" + version + "]";
	}

}