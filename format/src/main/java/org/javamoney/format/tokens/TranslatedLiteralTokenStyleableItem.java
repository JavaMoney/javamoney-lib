/*
 * Copyright (c) 2012, 2013, Credit Suisse (Anatole Tresch), Werner Keil.
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
 */
package org.javamoney.format.tokens;

import java.util.Locale;
import java.util.ResourceBundle;


import org.javamoney.format.ItemParseContext;
import org.javamoney.format.ItemParseException;
import org.javamoney.format.LocalizationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link org.javamoney.format.StyleableItemFormatToken} that adds a localizable {@link String}, read by key from
 * a {@link ResourceBundle}..
 * 
 * @author Anatole Tresch
 * 
 * @param <T>
 *            The concrete type.
 */
public class TranslatedLiteralTokenStyleableItem<T> extends AbstractStyleableItemFormatToken<T>{

	private String bundle;
	private String key;
	private Logger LOG = LoggerFactory.getLogger(TranslatedLiteralTokenStyleableItem.class);

	public TranslatedLiteralTokenStyleableItem(String key) {
		if (key == null) {
			throw new IllegalArgumentException("Token is required.");
		}
		this.key = key;
	}

	public TranslatedLiteralTokenStyleableItem(String key, String bundle) {
		setKey(key);
		setBundle(bundle);
	}

	public TranslatedLiteralTokenStyleableItem<T> setKey(String key) {
		if (key == null) {
			throw new IllegalArgumentException("Token is required.");
		}
		this.key = key;
		return this;
	}

	public TranslatedLiteralTokenStyleableItem<T> setBundle(String bundle) {
		if (bundle == null) {
			throw new IllegalArgumentException("Bundle is required.");
		}
		this.bundle = bundle;
		return this;
	}

	public String getBundle() {
		return this.bundle;
	}

	public String getKey() {
		return this.key;
	}

	protected String getToken(T item, Locale locale, LocalizationContext style) {
		return getTokenInternal(locale, style);
	};

	private String getTokenInternal(Locale locale, LocalizationContext style) {
		if (bundle == null) {
			return String.valueOf(key);
		}
		try {
			ResourceBundle rb = ResourceBundle.getBundle(bundle,
					locale);
			return rb.getString(key);
		} catch (Exception e) {
			return String.valueOf(key);
		}
	}

	@Override
	public void parse(ItemParseContext context, Locale locale, LocalizationContext style)
			throws ItemParseException {
		String token = getTokenInternal(locale, style);
		if (!context.consume(token)) {
			LOG.debug("Optional: " + token + " not found at "
						+ context.getInput().toString());
		}
	}
}
