package org.javamoney.moneta.internal.convert.yahoo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "type" })
public class Meta {

	@XmlElement(required = true)
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String value) {
		this.type = value;
	}

	@Override
	public String toString() {
		return "Meta [type=" + type + "]";
	}

}