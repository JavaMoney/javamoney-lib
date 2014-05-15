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

import org.javamoney.format.spi.ItemFormatFactorySpi;

/**
 * Formats instances of {@code T} to a {@link String} or an {@link Appendable}.
 * <p>
 * Note that formatters are not required to be thread-safe. Basically when
 * accessing an {@link ItemFormat} from the {@link ItemFormats} singleton a
 * new instance should be created on each access.<br/>
 * As a consequence classes implementing this interface are not required to be
 * thread.safe, but they must be immutable.
 */
public interface ItemFormat<T> {

	/**
	 * Return the target type this {@link ItemFormat} is expecting and capable
	 * to format.
	 * 
	 * @return the target type, never {@code null}.
	 */
	public Class<T> getTargetClass();

	/**
	 * Access the {@link LocalizationContext} configuring this {@link ItemFormat}.
	 * 
	 * @return Returns the {@link LocalizationContext} attached to this
	 *         {@link ItemFormat}, never {@code null}.
	 */
	public LocalizationContext getStyle();

	/**
	 * Formats a value of {@code T} to a {@code String}. The {@link Locale}
	 * passed defines the overall target {@link Locale}, whereas the
	 * {@link LocalizationContext} attached with the instances configures, how the
	 * {@link ItemFormat} should generally behave. The {@link LocalizationContext}
	 * allows to configure the formatting and parsing in arbitrary details. The
	 * attributes that are supported are determined by the according
	 * {@link ItemFormat} implementation:
	 * <ul>
	 * <li>When the {@link ItemFormat} was created using the
	 * {@link ItemFormatBuilder}, all the {@link StyleableItemFormatToken}, that model the
	 * overall format, and the {@link ParseResultFactory}, that is responsible for
	 * extracting the final parsing result, returned from a parsing call, are
	 * all possible recipients for attributes of the configuring
	 * {@link LocalizationContext}.
	 * <li>When the {@link ItemFormat} was provided by an instance of
	 * {@link ItemFormatFactorySpi} the {@link ItemFormat} returned determines
	 * the capabilities that can be configured.
	 * </ul>
	 * 
	 * So, regardless if an {@link ItemFormat} is created using the fluent style
	 * {@link ItemFormatBuilder} pattern, or provided as preconfigured
	 * implementation, {@link LocalizationContext}s allow to configure them both
	 * effectively.
	 * 
	 * @param item
	 *            the item to print, not {@code null}
	 * @return the string printed using the settings of this formatter
	 * @throws UnsupportedOperationException
	 *             if the formatter is unable to print
	 * @throws ItemFormatException
	 *             if there is a problem while printing
	 */
	public String format(T item, Locale locale);

	/**
	 * Prints a item value to an {@code Appendable}.
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
	 *            the main target {@link Locale} to be used, not {@code null}
	 * @throws UnsupportedOperationException
	 *             if the formatter is unable to print
	 * @throws ItemFormatException
	 *             if there is a problem while printing
	 * @throws IOException
	 *             if an IO error occurs
	 */
	public void print(Appendable appendable, T item, Locale locale)
			throws IOException;

	/**
	 * Fully parses the text into an instance of {@code T}.
	 * <p>
	 * The parse must complete normally and parse the entire text. If the parse
	 * completes without reading the entire length of the text, an exception is
	 * thrown. If any other problem occurs during parsing, an exception is
	 * thrown.
	 * <p>
	 * This method uses a {@link Locale} as an input parameter. Additionally the
	 * {@link ItemFormatException} instance is configured by a
	 * {@link LocalizationContext}. {@link LocalizationContext}s allows to configure
	 * formatting input in detail. This allows to implement complex formatting
	 * requirements using this interface.
	 * 
	 * @param text
	 *            the text to parse, not null
	 * @param locale
	 *            the main target {@link Locale} to be used, not {@code null}
	 * @return the parsed value, never {@code null}
	 * @throws UnsupportedOperationException
	 *             if the formatter is unable to parse
	 * @throws ItemParseException
	 *             if there is a problem while parsing
	 */
	public T parse(CharSequence text, Locale locale) throws ItemParseException;

}
