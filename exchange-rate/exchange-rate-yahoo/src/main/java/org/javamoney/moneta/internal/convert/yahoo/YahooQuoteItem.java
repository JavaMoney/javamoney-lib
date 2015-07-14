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
	private List<YahooField> field;

	@XmlAttribute(name = "classname")
	private String classname;

	public List<YahooField> getField() {
		if (field == null) {
			field = new ArrayList<YahooField>();
		}
		return this.field;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String value) {
		this.classname = value;
	}

	@Override
	public String toString() {
		return "YahooQuoteItem [field=" + field + ", classname=" + classname
				+ "]";
	}

}