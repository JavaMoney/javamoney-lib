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
package org.javamoney.extras.functions;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


import org.javamoney.ext.Predicate;
import org.javamoney.extras.ValidationException;

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
public final class CompoundType implements Serializable {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 4831291549617485148L;

	private final Predicate<Map<String, Object>> validationPredicate;
	@SuppressWarnings("rawtypes")
	private final Map<String, Class> typeDef;
	private final Set<String> typeRequired;
	private final String id;

	private CompoundType(String id,
			@SuppressWarnings("rawtypes") Map<String, Class> typeDef,
			Set<String> typeRequired,
			Predicate<Map<String, Object>> validationPredicate) {
		if (id == null) {
			throw new IllegalArgumentException("id required.");
		}
		this.id = id;
		this.typeDef = typeDef;
		this.typeRequired = typeRequired;
		this.validationPredicate = validationPredicate;
	}

	/**
	 * A {@link CompoundType}may have a type identifier that helps to identify,
	 * what type of items object is returned.
	 * 
	 * @return the {@link CompoundType}'s type, never null.
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * This method allows to check if a key within the {@code CompoundType} is a
	 * required value, so a corresponding {@link CompoundValue} is valid.
	 * 
	 * @param key
	 *            the key
	 * @return true, if the corresponding value is required, false otherwise.
	 */
	public boolean isRequired(String key) {
		return typeRequired.contains(key);
	}

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
	@SuppressWarnings("unchecked")
	public void validate(Map<String, Object> compundValueMap)
			throws ValidationException {
		// Check for required fields to be present
		for (String key : this.typeRequired) {
			Object value = compundValueMap.get(key);
			if (value == null) {
				throw new ValidationException("Required value '" + key
						+ "' of type " + typeDef.get(key) + " is missing.");
			}
		}
		// Check the fields type for all possible fields
		for (@SuppressWarnings("rawtypes")
		Map.Entry<String, Class> entry : this.typeDef.entrySet()) {
			Object value = compundValueMap.get(entry.getKey());
			if (value != null
					&& !entry.getValue().isAssignableFrom(value.getClass())) {
				throw new ValidationException("Value  for '"
						+ entry.getKey()
						+ "' has invalid type type "
						+ value.getClass().getName() + ", required: "
						+ entry.getValue() + ".");
			}
		}
		if (validationPredicate != null
				&& !validationPredicate.isPredicateTrue(compundValueMap)) {
			throw new ValidationException("Validation predicate failed '"
					+ validationPredicate + ".");
		}
	}

	public static final class Builder {
		@SuppressWarnings("rawtypes")
		private Map<String, Class> typeDef = new HashMap<String, Class>();
		private Set<String> typeRequired = new HashSet<String>();
		private String id;
		private Predicate<Map<String, Object>> validationPredicate;

		public Builder() {
		}

		public Builder withIdForInput(Class<?> type) {
			if (type == null) {
				throw new IllegalArgumentException("type required.");
			}
			this.id = type.getName() + "_in";
			return this;
		}

		public Builder withIdForOutput(Class<?> type) {
			if (type == null) {
				throw new IllegalArgumentException("type required.");
			}
			this.id = type.getName() + "_out";
			return this;
		}

		public Builder withId(String id) {
			if (id == null || id.trim().isEmpty()) {
				throw new IllegalArgumentException("id required.");
			}
			this.id = id;
			return this;
		}

		public Builder withValidationPredicate(
				Predicate<Map<String, Object>> predicate) {
			this.validationPredicate = predicate;
			return this;
		}

		public Builder withArg(String key, Class<?> type) {
			this.typeDef.put(key, type);
			return this;
		}

		public Builder withRequiredArg(String key, Class<?> type) {
			this.typeDef.put(key, type);
			this.typeRequired.add(key);
			return this;
		}

		public CompoundType build() {
			return new CompoundType(id, typeDef, typeRequired,
					validationPredicate);
		}
	}

	public void checkInput(CompoundValue input) {
		if (input == null) {
			throw new IllegalArgumentException("Input missing, required: "
					+ this);
		}
		if (!this.equals(input.getCompoundType())) {
			throw new IllegalArgumentException("Invalid input, was " + input
					+ ", required: " + this);
		}
	}
}
