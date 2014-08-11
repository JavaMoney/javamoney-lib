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
package org.javamoney.data;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.javamoney.moneta.spi.LoaderService.LoaderListener;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 * Abstract base class for loading XML document using the {@link org.javamoney.moneta.spi.LoaderService}.
 */
public abstract class AbstractXmlResourceLoaderListener implements
        LoaderListener {

    private DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
            .newInstance();

    private Document document;

    /**
     * Constructor.
     */
    public AbstractXmlResourceLoaderListener() {
        docBuilderFactory.setIgnoringComments(true);
        docBuilderFactory.setIgnoringElementContentWhitespace(true);
        docBuilderFactory.setValidating(false);
    }

    @Override
    public void newDataLoaded(String dataId, InputStream is) {
        try {
            InputSource inputSource = new InputSource(is);
            docBuilderFactory.setValidating(false);
            DocumentBuilder builder = docBuilderFactory.newDocumentBuilder();
            builder.setEntityResolver((publicId, systemId) -> new InputSource(new ByteArrayInputStream(
                    "<?xml version='1.0' encoding='UTF-8'?>"
                            .getBytes())));
            document = builder.parse(inputSource);
            document.normalize();
            loadDocument(document);
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE,
                    "Failed to parse resource " + dataId, e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE,
                            "Error closing input stream from " + dataId, e);
                }
            }
        }
    }

    /**
     * Access the underlying XML document, last read.
     *
     * @return the underlying document.
     */
    public Document getDocument() {
        return this.document;
    }

    /**
     * Method to be implemented to load the document data.
     *
     * @param document the document, not null.
     */
    protected abstract void loadDocument(Document document);

}
