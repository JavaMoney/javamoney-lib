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
package org.javamoney.moneta.internal.convert.yahoo;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
class ObjectFactory {


    public ObjectFactory() {
    }

    public YahooRoot createList() {
        return new YahooRoot();
    }

    public YahooCurrencies createListResources() {
        return new YahooCurrencies();
    }

    public YahooQuoteItem createListResourcesResource() {
        return new YahooQuoteItem();
    }


    public Meta createListMeta() {
        return new Meta();
    }

    public YahooField createListResourcesResourceField() {
        return new YahooField();
    }

}