package org.javamoney.moneta.internal.convert.yahoo;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "resource"
})
public class YahooCurrencies {

    @XmlElement(required = true)
    protected List<YahooQuoteItem> resource;

    @XmlAttribute(name = "start")
    protected Integer start;

    @XmlAttribute(name = "count")
    protected Integer count;


    public List<YahooQuoteItem> getResource() {
        if (resource == null) {
            resource = new ArrayList<>();
        }
        return this.resource;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer value) {
        this.start = value;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer value) {
        this.count = value;
    }

	@Override
	public String toString() {
		return "YahooCurrencies [resource=" + resource + ", start=" + start
				+ ", count=" + count + "]";
	}

}