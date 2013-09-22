/*
 *  Copyright (c) 2012, 2013, Werner Keil, Credit Suisse (Anatole Tresch).
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
 *
 * Contributors:
 *    Anatole Tresch - initial version.
 */
package org.javamoney.extras;

import java.util.Map;


/**
 * Defines a {@link ParameterObjectType} containing several results. Hereby the
 * different results are identified by arbitrary keys. Additionally each
 * {@link ParameterObjectType} has a <i>leading</i> item that identifies the type of
 * result.<br/>
 * A {@link ParameterObjectType} instance is defined to be implemented as immutable
 * object and therefore is very useful for modeling multidimensional results
 * objects or input parameters as they are common in financial applications.
 * 
 * @author Anatole Tresch
 */
public interface ParameterObjectType extends Map<String, Class<?>> {

	/**
	 * A {@link ParameterObjectType}may have a type identifier that helps to identify,
	 * what type of items object is returned.
	 * 
	 * @return the {@link ParameterObjectType}'s type, never null.
	 */
	public String getId();

	/**
	 * This method allows to check if a key within the {@code ParameterObjectType} is a
	 * required value, so a corresponding {@link ParameterObject} is valid.
	 * 
	 * @param key
	 *            the key
	 * @return true, if the corresponding value is required, false otherwise.
	 */
	public boolean isRequired(String key);

	/**
	 * Validates if the given {@link ParameterObject} defines all the attributes
	 * as required by this {@link ParameterObjectType} instance.
	 * 
	 * @param compundValueMap
	 *            the {@link Map} to be validated before a {@link ParameterObject}
	 *            is created.
	 * @see #isValid(ParameterObject)
	 * @throws IllegalArgumentException
	 *             if validation fails.
	 */
	public void validate(Map<String, Object> compundValueMap)
			throws ValidationException;
}
