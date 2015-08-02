package org.javamoney.moneta.internal.convert.yahoo;

import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class YahooField {

	@XmlAttribute(name = "name")
	private String name;

	@XmlValue
	private String value;

	public YahooField(){}

	public YahooField(final String field){
		name = field;
	}

	public String getName() {
		return name;
	}

	public void setName(String value) {
		this.name = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(name);
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}

		if(YahooField.class.isInstance(obj)) {
			YahooField other = YahooField.class.cast(obj);
			return Objects.equals(name, other.name);
		}
		return false;
	}

	@Override
	public String toString() {
		return "YahooField [name=" + name + ", value=" + value + "]";
	}
}