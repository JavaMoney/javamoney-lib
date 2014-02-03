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
package org.javamoney.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.javamoney.moneta.spi.LoaderService.LoaderListener;
import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public abstract class AbstractXmlResourceLoaderListener implements
		LoaderListener {

	private DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
			.newInstance();

	private Document document;

	public AbstractXmlResourceLoaderListener() {
		docBuilderFactory.setIgnoringComments(true);
		docBuilderFactory.setIgnoringElementContentWhitespace(true);
		docBuilderFactory.setValidating(false);
	}

	public void newDataLoaded(String dataId, InputStream is) {
		try {
			InputSource inputSource = new InputSource(is);
			DocumentBuilder builder = docBuilderFactory.newDocumentBuilder();
			builder.setEntityResolver(new EntityResolver() {
				public InputSource resolveEntity(java.lang.String publicId,
						java.lang.String systemId) throws SAXException,
						java.io.IOException {
					if (systemId.contains("ldmlSupplemental.dtd"))
						// this deactivates the open office DTD
						return new InputSource(new ByteArrayInputStream(
								"<?xml version='1.0' encoding='UTF-8'?>"
										.getBytes()));
					else
						return null;
				}
			});
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

	public Document getDocument() {
		return this.document;
	}

	protected abstract void loadDocument(Document document);

}
