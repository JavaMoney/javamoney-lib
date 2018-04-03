/*
 * Copyright (c) 2012, 2018, Werner Keil, Anatole Tresch and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 * Contributors: @atsticks, @keilw, @otjava
 */
package org.javamoney.moneta.convert.internal.yahoo;

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