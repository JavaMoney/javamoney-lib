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