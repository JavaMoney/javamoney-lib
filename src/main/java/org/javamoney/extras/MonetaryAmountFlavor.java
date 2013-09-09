/*
 * Copyright (c) 2012, 2013, Werner Keil, Credit Suisse (Anatole Tresch).
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
 * 
 * Contributors: Anatole Tresch - initial version.
 */
package org.javamoney.extras;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class models the type of a given {@link FlavoredMonetaryAmount} as
 * immutable value type. Basically the types possible are determined by the
 * concrete use cases and implementations. Typical use cases is that amounts
 * represent different results and only should be combined in a well defined way
 * to produce useful results. This class allows to distinguish these types.
 * 
 * @author Anatole Tresch
 */
public final class MonetaryAmountFlavor implements Serializable,
		Comparable<MonetaryAmountFlavor> {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -7505497771490888058L;
	/** The id of this type. */
	private final String id;
	/** The cache of types. */
	private static final Map<String, MonetaryAmountFlavor> CACHED_INSTANCES = new ConcurrentHashMap<String, MonetaryAmountFlavor>();
	/** Unknown amount type. */
	public static final MonetaryAmountFlavor UNKNOWN = MonetaryAmountFlavor
			.of("<unknown>");

	/**
	 * Creates a new instance.
	 * 
	 * @param id
	 *            The rate identifier.
	 * @return The new rate type.
	 */
	public static MonetaryAmountFlavor of(String id) {
		if (id == null) {
			throw new IllegalArgumentException("id required.");
		}
		MonetaryAmountFlavor instance = CACHED_INSTANCES.get(id);
		if (instance == null) {
			instance = new MonetaryAmountFlavor(id);
			CACHED_INSTANCES.put(id, instance);
		}
		return instance;
	}

	/**
	 * Constructs a new instance of an ExchangeRateType..
	 * 
	 * @param id
	 *            The id of this type instance, never null.
	 */
	public MonetaryAmountFlavor(String id) {
		if (id == null) {
			throw new IllegalArgumentException("Id must not be null.");
		}
		this.id = id;
	}

	/**
	 * Get the identifier of this instance.
	 * 
	 * @return The identifier, never null.
	 */
	public String getId() {
		return this.id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MonetaryAmountFlavor other = (MonetaryAmountFlavor) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ExchangeRateType [id=" + id + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(MonetaryAmountFlavor o) {
		if (o == null) {
			return -1;
		}
		int compare = id.compareTo(o.id);
		return compare;
	}

}
