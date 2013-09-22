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
package org.javamoney.extras;

import java.util.Map;

/**
 * Defines a {@link CompoundType} containing several results. Hereby the
 * different results are identified by arbitrary keys. Additionally each
 * {@link CompoundType} has a <i>leading</i> item that identifies the type of
 * result.<br/>
 * A {@link CompoundType} instance is defined to be implemented as immutable
 * object and therefore is very useful for modeling multidimensional results
 * objects or input parameters as they are common in financial applications.
 * 
 * @author Anatole Tresch
 */
public interface CompoundType extends Map<String, Class<?>> {

	/**
	 * A {@link CompoundType}may have a type identifier that helps to identify,
	 * what type of items object is returned.
	 * 
	 * @return the {@link CompoundType}'s type, never null.
	 */
	public String getId();

	/**
	 * This method allows to check if a key within the {@code CompoundType} is a
	 * required value, so a corresponding {@link CompoundValue} is valid.
	 * 
	 * @param key
	 *            the key
	 * @return true, if the corresponding value is required, false otherwise.
	 */
	public boolean isRequired(String key);

	/**
	 * Validates if the given {@link CompoundValue} defines all the attributes
	 * as required by this {@link CompoundType} instance.
	 * 
	 * @param compundValueMap
	 *            the {@link Map} to be validated before a {@link CompoundValue}
	 *            is created.
	 * @see #isValid(CompoundValue)
	 * @throws IllegalArgumentException
	 *             if validation fails.
	 */
	public void validate(Map<String, Object> compundValueMap)
			throws ValidationException;
}
