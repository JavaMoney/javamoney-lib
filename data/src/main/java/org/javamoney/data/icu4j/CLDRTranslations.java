/*
 *  Copyright (c) 2012, 2013, Credit Suisse (Anatole Tresch), Werner Keil.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.javamoney.data.icu4j;


import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.money.spi.Bootstrap;

import org.javamoney.moneta.spi.LoaderService;
import org.javamoney.moneta.spi.LoaderService.UpdatePolicy;
import org.javamoney.data.AbstractXmlResourceLoaderListener;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public final class CLDRTranslations {

    private static final CLDRTranslations INSTANCE = new CLDRTranslations();

    private Map<String, LanguageTranslations> translations = new ConcurrentHashMap<>();

    private CLDRTranslations() {
    }

    // @Produces
    public static CLDRTranslations getInstance() {
        return INSTANCE;
    }

    public static LanguageTranslations getInstance(String language) {
        LanguageTranslations transl = INSTANCE.translations.get(language);
        if (transl == null) {
            try {
                transl = new LanguageTranslations(language);
            } catch (Exception e) {
                throw new IllegalArgumentException("Unsupported language.", e);
            }
            INSTANCE.translations.put(language, transl);
        }
        return transl;
    }

    public static LanguageTranslations getInstance(Locale locale) {
        return getInstance(locale.getLanguage());
    }

    public static final class LanguageTranslations extends
            AbstractXmlResourceLoaderListener {

        private Map<String, String> languageTranslations = new HashMap<>();
        private Map<String, String> territoryTranslations = new HashMap<>();
        private Map<String, CurrencyTranslations> currencyTranslations = new HashMap<>();

        public String getLanguageName(String langCode) {
            return this.languageTranslations.get(langCode);
        }

        public String getRegionName(String regionCode) {
            return this.territoryTranslations.get(regionCode);
        }

        public CurrencyTranslations getCurrencyTranslations(String isoCode) {
            return this.currencyTranslations.get(isoCode);
        }

        public LanguageTranslations(String language)
                throws MalformedURLException, URISyntaxException {
            LoaderService loader = Bootstrap.getService(LoaderService.class);

            URL backupUrl = CLDRTranslations.class.getResource(
                    "java-money/defaults/cldr/main/" + language
                            + ".xml");
            URI backupURI = backupUrl == null ? null : backupUrl.toURI();
            loader.registerAndLoadData(
                    "CLDR-Translations_" + language,
                    UpdatePolicy.ONSTARTUP,
                    new HashMap<String, String>(),
                    this,
                    backupURI,
                    new URI("http://unicode.org/repos/cldr/trunk/common/main/"
                            + language + ".xml"));
        }

        @Override
        protected void loadDocument(Document document) {
            Map<String, String> languageTranslations = new HashMap<>();
            Map<String, String> territoryTranslations = new HashMap<>();
            Map<String, CurrencyTranslations> currencyTranslations = new HashMap<>();
            // ldml(root)/localeDisplayNames/languages/language*: <language
            // type="aa">Afar</language>
            // ldml/localeDisplayNames/territories/territory*: <territory
            // type="001">Welt</territory>
            // ldml/decimalFormats/...
            // ldml/scientificFormats/...
            // ldml/percentFormats/currencyFormatLength/currencyFormat
            // ldml/numbers/currencies/currency*
            // <currency type="AED">
            // <displayName>VAE-Dirham</displayName>
            // <displayName count="one">VAE-Dirham</displayName>
            // <displayName count="other">VAE-Dirham</displayName>
            // <symbol draft="contributed">AED</symbol>
            // </currency>
            NodeList territoryTranslationsNodes = getDocument()
                    .getElementsByTagName(
                            "territory"); // ldml/localeDisplayNames/territories
            NodeList languageTranslationsNodes = getDocument()
                    .getElementsByTagName(
                            "language"); // ldml/localeDisplayNames/languages
            NodeList currencyTranslationsNodes = getDocument()
                    .getElementsByTagName("currency"); // ldml/numbers/currencies

            for (int i = 0; i < languageTranslationsNodes.getLength(); i++) {
                Node node = languageTranslationsNodes.item(i);
                NamedNodeMap languageAttrs = node.getAttributes();
                String langCode = languageAttrs.getNamedItem("type") != null ?
                        languageAttrs.getNamedItem("type").getNodeValue() : null;
                if (langCode != null) {
                    languageTranslations.put(langCode, node.getTextContent());
                }
            }
            for (int i = 0; i < territoryTranslationsNodes.getLength(); i++) {
                Node node = territoryTranslationsNodes.item(i);
                NamedNodeMap territoryTrans = node.getAttributes();
                String regionCode = territoryTrans.getNamedItem("type")
                        .getNodeValue();
                String regionName = node.getTextContent();
                territoryTranslations.put(regionCode, regionName);
            }
            for (int i = 0; i < currencyTranslationsNodes.getLength(); i++) {
                Node node = currencyTranslationsNodes.item(i);
                NamedNodeMap currencyAttrs = node.getAttributes();
                String code = currencyAttrs.getNamedItem("type").getNodeValue();
                CurrencyTranslations cl = new CurrencyTranslations(code);
                for (int j = 0; j < node.getChildNodes().getLength(); j++) {
                    Node chNode = node.getChildNodes().item(j);
                    if ("displayName".equals(chNode.getNodeName())) {
                        currencyAttrs = chNode.getAttributes();
                        if (currencyAttrs.getLength() == 0) {
                            cl.setDisplayName(null, chNode.getTextContent());
                        } else {
                            Node countNode = currencyAttrs.getNamedItem("count");
                            if (countNode != null) {
                                cl.setDisplayName(
                                        currencyAttrs.getNamedItem("count")
                                                .getNodeValue(), chNode
                                                .getTextContent());
                            } else {
                                cl.setDisplayName(
                                        null, chNode
                                                .getTextContent());
                            }
                        }
                    } else if ("symbol".equals(chNode.getNodeName())) {
                        cl.setSymbol(chNode.getTextContent());
                    }
                }
                currencyTranslations.put(code, cl);
            }

            this.territoryTranslations = territoryTranslations;
            this.languageTranslations = languageTranslations;
            this.currencyTranslations = currencyTranslations;
        }
    }

    public static final class CurrencyTranslations {
        private String isoCode;
        private String displayName;
        private String displayNameOne;
        private String displayNameOther;
        private String symbol;

        public CurrencyTranslations(String isoCode) {
            this.isoCode = isoCode;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public void setDisplayName(String type, String displayName) {
            if (type == null) {
                this.displayName = displayName;
            } else if ("one".equals(type)) {
                this.displayNameOne = displayName;
            } else if ("other".equals(type)) {
                this.displayNameOther = displayName;
            }
        }

        /**
         * @return the displayName
         */
        public final String getDisplayName() {
            return displayName;
        }

        /**
         * @param displayName the displayName to set
         */
        public final void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        /**
         * @return the displayNameOne
         */
        public final String getDisplayNameOne() {
            return displayNameOne;
        }

        /**
         * @param displayNameOne the displayNameOne to set
         */
        public final void setDisplayNameOne(String displayNameOne) {
            this.displayNameOne = displayNameOne;
        }

        /**
         * @return the displayNameOther
         */
        public final String getDisplayNameMultiple() {
            return displayNameOther;
        }

        /**
         * @param displayNameOther the displayNameOther to set
         */
        public final void setDisplayNameOther(String displayNameOther) {
            this.displayNameOther = displayNameOther;
        }

        /**
         * @return the isoCode
         */
        public final String getIsoCode() {
            return isoCode;
        }

        /**
         * @return the symbol
         */
        public final String getSymbol() {
            return symbol;
        }

    }

}

