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
package org.javamoney.moneta.convert.yahoo;

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