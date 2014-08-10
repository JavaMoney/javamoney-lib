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
package org.javamoney.format;

import java.util.Currency;
import java.util.Locale;

/**
 * This interface is implemented by types that are displayable for particular
 * locales. It was defined to provide a simple mechanism for modeling
 * localization as also available on {@link Currency}. Nevertheless it is highly
 * recommended to separate formatting aspects from the types, e.g. by using the
 * JSRs formatting features ({@code ItemFormat}), to format according instances.
 * 
 * @author Anatole Tresch
 * @author Werner Keil
 * @version 0.9
 */
public interface Displayable {

	/**
	 * Gets the name that is suitable for displaying this item for the specified
	 * locale.
	 * 
	 * @param locale
	 *            the locale for which a display name is needed
	 * @return the display name for the specified locale
	 * @exception NullPointerException
	 *                if <code>locale</code> is null
	 */
	public String getDisplayName(Locale locale);
}
