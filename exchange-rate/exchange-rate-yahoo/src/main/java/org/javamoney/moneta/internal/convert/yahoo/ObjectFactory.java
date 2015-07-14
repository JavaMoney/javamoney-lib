package org.javamoney.moneta.internal.convert.yahoo;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: generated
     *
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link YahooRoot }
     *
     */
    public YahooRoot createList() {
        return new YahooRoot();
    }

    /**
     * Create an instance of {@link YahooRoot.YahooCurrencies }
     *
     */
    public YahooCurrencies createListResources() {
        return new YahooCurrencies();
    }

    /**
     * Create an instance of {@link YahooRoot.YahooCurrencies.YahooQuoteItem }
     *
     */
    public YahooQuoteItem createListResourcesResource() {
        return new YahooQuoteItem();
    }

    /**
     * Create an instance of {@link YahooRoot.Meta }
     *
     */
    public Meta createListMeta() {
        return new Meta();
    }

    /**
     * Create an instance of {@link YahooRoot.YahooCurrencies.YahooQuoteItem.YahooField }
     *
     */
    public YahooField createListResourcesResourceField() {
        return new YahooField();
    }

}