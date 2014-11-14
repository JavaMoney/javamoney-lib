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

import org.javamoney.format.ItemParseContext;
import org.javamoney.format.LocalizationContext;
import org.javamoney.format.StyleableItemFormatToken;
import org.javamoney.format.ItemParseException;

import java.io.IOException;
import java.io.Serializable;
import java.util.Locale;

/**
 * {@link org.javamoney.format.StyleableItemFormatToken} which adds an arbitrary literal constant value to the
 * output.
 * <p>
 * This class is thread safe, immutable and serializable.
 * 
 * @author Anatole Tresch
 * 
 * @param <R>
 *            The item type.
 */
public final class LiteralTokenStyleableItem<R> implements StyleableItemFormatToken<R>, Serializable {

	/**
	 * The literal part.
	 */
	private String token;

	/**
	 * Constructor.
	 * 
	 * @param token
	 *            The literal token part.
	 */
	public LiteralTokenStyleableItem(String token) {
		if (token == null) {
			throw new IllegalArgumentException("Token is required.");
		}
		this.token = token;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.money.format.StyleableItemFormatToken#parse(javax.money.format.ItemParseContext,
	 * java.util.Locale, javax.money.format.LocalizationStyle)
	 */
	@Override
	public void parse(ItemParseContext<R> context, Locale locale,
			LocalizationContext style)
			throws ItemParseException{
		if (!context.consume(token)) {
			throw new ItemParseException("Expected '" + token + "' in "
					+ context.getInput().toString());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.format.StyleableItemFormatToken#print(java.lang.Appendable,
	 * java.lang.Object, java.util.Locale, javax.money.format.LocalizationStyle)
	 */
	@Override
	public void print(Appendable appendable, R item, Locale locale,
			LocalizationContext style)
			throws IOException {
		appendable.append(this.token);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LiteralTokenStyleableItem [token=" + token + "]";
	}

}
