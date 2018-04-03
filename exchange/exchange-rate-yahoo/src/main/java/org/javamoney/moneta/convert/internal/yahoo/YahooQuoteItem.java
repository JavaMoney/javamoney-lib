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
			field = new ArrayList<>();
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