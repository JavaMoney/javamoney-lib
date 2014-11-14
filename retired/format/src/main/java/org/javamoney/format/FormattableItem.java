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
 * This interface is implemented by types that require to be represented as
 * {@link String} objects, e.g. for displaying.
 * <p>
 * Classes implementing this interface must be thread-safe and should be
 * immutable.
 * 
 * @author Anatole Tresch
 */
public interface FormattableItem {

	/**
	 * Formats a value to a {@code String}. This method uses a
	 * {@link LocalizationContext} as an input parameter. Styles allows to define
	 * detailed and customized formatting input parameters. This allows to
	 * implement also complex formatting requirements using this interface.
	 * 
	 * @param locale
	 *            The {@link Locale} to be used.
	 * @return the formatted {@link String}
	 * @throws ItemFormatException
	 *             if there is a problem while printing
	 */
	public String format(Locale locale);

	/**
	 * Formats a value to a {@code String}. This method uses a
	 * {@link LocalizationContext} as an input parameter. Styles allows to define
	 * detailed and customized formatting input parameters. This allows to
	 * implement also complex formatting requirements using this interface.
	 * 
	 * @param locale
	 *            The {@link Locale} to be used.
	 * @param style
	 *            the {@link LocalizationContext} to be attached to this
	 *            {@link ItemFormat}, which also contains the target
	 *            {@link Locale} instances to be used, as well as other
	 *            attributes configuring this instance.
	 * @return the formatted {@link String}
	 * @throws ItemFormatException
	 *             if there is a problem while printing
	 */
	public String format(Locale locale, LocalizationContext style);

	/**
	 * Prints a item value to an {@code Appendable}.
	 * <p>
	 * Example implementations of {@code Appendable} are {@code StringBuilder},
	 * {@code StringBuffer} or {@code Writer}. Note that {@code StringBuilder}
	 * and {@code StringBuffer} never throw an {@code IOException}.
	 * 
	 * @param appendable
	 *            the appendable to add to, not null
	 * @@param locale
     *            The {@link Locale} to be used.
	 * @throws UnsupportedOperationException
	 *             if the formatter is unable to print
	 * @throws ItemFormatException
	 *             if there is a problem while printing
	 * @throws IOException
	 *             if an IO error occurs
	 */
	public void print(Appendable appendable, Locale locale) throws IOException;

	/**
	 * Prints a item value to an {@code Appendable}.
	 * <p>
	 * Example implementations of {@code Appendable} are {@code StringBuilder},
	 * {@code StringBuffer} or {@code Writer}. Note that {@code StringBuilder}
	 * and {@code StringBuffer} never throw an {@code IOException}.
	 * 
	 * @param appendable
	 *            the appendable to add to, not null
	 * @param locale
	 *            The {@link Locale} to be used.
	 * @param style
	 *            the {@link LocalizationContext} to be attached to this
	 *            {@link ItemFormat}, which also contains the target
	 *            {@link Locale} instances to be used, as well as other
	 *            attributes configuring this instance.
	 * @throws UnsupportedOperationException
	 *             if the formatter is unable to print
	 * @throws ItemFormatException
	 *             if there is a problem while printing
	 * @throws IOException
	 *             if an IO error occurs
	 */
	public void print(Appendable appendable, Locale locale,
			LocalizationContext style)
			throws IOException;
}
