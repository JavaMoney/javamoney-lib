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

    /**
     * Gets the value of the resource property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the resource property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getResource().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link YahooRoot.YahooCurrencies.YahooQuoteItem }
     *
     *
     */
    public List<YahooQuoteItem> getResource() {
        if (resource == null) {
            resource = new ArrayList<YahooQuoteItem>();
        }
        return this.resource;
    }

    /**
     * Gets the value of the start property.
     *
     * @return
     *     possible object is
     *     {@link Integer }
     *
     */
    public Integer getStart() {
        return start;
    }

    /**
     * Sets the value of the start property.
     *
     * @param value
     *     allowed object is
     *     {@link Integer }
     *
     */
    public void setStart(Integer value) {
        this.start = value;
    }

    /**
     * Gets the value of the count property.
     *
     * @return
     *     possible object is
     *     {@link Integer }
     *
     */
    public Integer getCount() {
        return count;
    }

    /**
     * Sets the value of the count property.
     *
     * @param value
     *     allowed object is
     *     {@link Integer }
     *
     */
    public void setCount(Integer value) {
        this.count = value;
    }


}