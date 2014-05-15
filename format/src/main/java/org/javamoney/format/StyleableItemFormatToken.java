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
package org.javamoney.format;

import java.io.IOException;
import java.util.Locale;

/**
 * Formats instances of {@code T} to a {@link String} or an {@link Appendable}.
 * Instances of {@link StyleableItemFormatToken} can be added to a {@link ItemFormatBuilder}
 * to assemble a complex input/output {@link ItemFormat} using a programmatic
 * fluent API. Hereby each {@link StyleableItemFormatToken} instance represent a part of the
 * overall formatted String. Similarly when parsing an input by calling
 * {@link ItemFormat#parse(CharSequence, Locale)} each {@link StyleableItemFormatToken} can
 * read and forward the current {@link ItemParseContext}, or through an error, if
 * the input does not provide a parseable input for the given
 * {@link StyleableItemFormatToken}.
 * <p>
 * Classes implementing this interface are required to be thread-safe and
 * immutable.
 * 
 * @author Anatole Tresch
 */
public interface StyleableItemFormatToken<T>{


	/**
	 * Prints an item value to an {@code Appendable}.
	 * <p>
	 * Example implementations of {@code Appendable} are {@code StringBuilder},
	 * {@code StringBuffer} or {@code Writer}. Note that {@code StringBuilder}
	 * and {@code StringBuffer} never throw an {@code IOException}.
	 * 
	 * @param appendable
	 *            the appendable to add to, not null
	 * @param item
	 *            the item to print, not null
	 * @param locale
	 *            the {@link Locale} to be used, not null.
	 * @param style
	 *            the {@link LocalizationContext} to be used.
	 * @throws UnsupportedOperationException
	 *             if the formatter is unable to print
	 * @throws ItemFormatException
	 *             if there is a problem while printing
	 * @throws IOException
	 *             if an IO error occurs
	 */
	public void print(Appendable appendable, T item, Locale locale,
			LocalizationContext style)
			throws IOException;

	/**
	 * Parses an item from the given {@link ItemParseContext}. Any parsed item can
	 * be added to the {@link ItemParseContext} using
	 * {@link ItemParseContext#addParseResult(Object, Object)} as results. At the
	 * end of the parsing process an instance of {@link ParseResultFactory} is
	 * transferring the results parsed into the target item to be parsed.
	 * 
	 * @param context
	 *            the parse context
	 * @param locale
	 *            the {@link Locale} to be used, not null.
	 * @param style
	 *            the {@link LocalizationContext} to be used.
	 * @throws ItemParseException
	 *             thrown, if parsing fails.
	 */
	public void parse(ItemParseContext<T> context, Locale locale,
			LocalizationContext style)
			throws ItemParseException;

}
