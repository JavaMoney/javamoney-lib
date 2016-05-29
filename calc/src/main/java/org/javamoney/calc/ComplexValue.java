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
package org.javamoney.calc;

import java.util.HashMap;
import java.util.Map;

/**
 * Defines a {@link ComplexValue} containing several results. A value's type is
 * defined by a {@link ComplexType}. Hereby the
 * different results are identified by arbitrary keys. Additionally each
 * {@link ComplexValue} has a <i>leading</i> item that identifies the type of
 * result.<br/>
 * A {@link ComplexValue} instance is defined to be implemented as immutable
 * object and therefore is very useful for modeling multidimensional results
 * objects or input parameters as they are common in financial applications.
 * 
 * @author Anatole Tresch
 */
public final class ComplexValue<T extends ComplexType> {

	private T type;
	private Map<String, Object> args = new HashMap<String, Object>();

	private ComplexValue(T type, Map<String, Object> args) {
		if (type == null) {
			throw new IllegalArgumentException("CompoundType required.");
		}
		if (args == null) {
			throw new IllegalArgumentException("args required.");
		}
		type.validate(args);
		this.type = type;
		this.args = args;
	}

	/**
	 * Get the compound type of this instance.
	 * 
	 * @return the compound type, never {@code null}.
	 */
	public T getComplexType() {
		return this.type;
	}

	public <T> T get(String key, Class<T> type) {
		return get(key, type, null);
	}

	public <T> T get(String key, Class<T> type, T defaultValue) {
		@SuppressWarnings("unchecked")
		T t = (T) this.args.get(key);
		if (t == null) {
			return defaultValue;
		}
		return t;
	}

	/**
	 * Allows to check if a given key is present in the value map.
	 * @param key the target key, not null.
	 * @return true if the given key is present.
     */
	public boolean isPresent(String key) {
		return this.args.containsKey(key);
	}

	public static final class Builder {
		private ComplexType type;
		private Map<String, Object> args = new HashMap<String, Object>();

		public Builder() {
		}

		public Builder(ComplexType type) {
			withType(type);
		}

		public Builder withType(ComplexType type) {
			if (type == null) {
				throw new IllegalArgumentException("type required.");
			}
			this.type = type;
			return this;
		}

		public Builder with(String key, Object value) {
			this.args.put(key, value);
			return this;
		}

		public ComplexValue build() {
			return new ComplexValue(type, this.args);
		}
	}

}
