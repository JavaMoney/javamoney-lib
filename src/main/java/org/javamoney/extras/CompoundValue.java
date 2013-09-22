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
 * Defines a {@link CompoundValue} containing several results. Hereby the
 * different results are identified by arbitrary keys. Additionally each
 * {@link CompoundValue} has a <i>leading</i> item that identifies the type of
 * result.<br/>
 * A {@link CompoundValue} instance is defined to be implemented as immutable
 * object and therefore is very useful for modeling multidimensional results
 * objects or input parameters as they are common in financial applications.
 * 
 * @author Anatole Tresch
 */
public interface CompoundValue extends Map<String,Object>{

	/**
	 * A {@link CompoundValue} may have an identifier that helps to identify,
	 * what type of items object is returned.
	 * 
	 * @return the {@link CompoundValue}'s type, never null.
	 */
	public String getId();

	/**
	 * Get the compound type of this instance.
	 * 
	 * @return the compound type, never {@code null}.
	 */
	public CompoundType getCompoundType();
	
}
