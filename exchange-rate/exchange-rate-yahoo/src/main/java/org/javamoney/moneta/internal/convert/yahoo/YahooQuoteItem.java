package org.javamoney.moneta.internal.convert.yahoo;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "field" })
public class YahooQuoteItem {

	@XmlElement(required = true)
	protected List<YahooField> field;
	@XmlAttribute(name = "classname")
	protected String classname;

	/**
	 * Gets the value of the field property.
	 *
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the field property.
	 *
	 * <p>
	 * For example, to add a new item, do as follows:
	 *
	 * <pre>
	 * getField().add(newItem);
	 * </pre>
	 *
	 *
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link YahooRoot.YahooCurrencies.YahooQuoteItem.YahooField }
	 *
	 *
	 */
	public List<YahooField> getField() {
		if (field == null) {
			field = new ArrayList<YahooField>();
		}
		return this.field;
	}

	/**
	 * Gets the value of the classname property.
	 *
	 * @return possible object is {@link String }
	 *
	 */
	public String getClassname() {
		return classname;
	}

	/**
	 * Sets the value of the classname property.
	 *
	 * @param value
	 *            allowed object is {@link String }
	 *
	 */
	public void setClassname(String value) {
		this.classname = value;
	}

}