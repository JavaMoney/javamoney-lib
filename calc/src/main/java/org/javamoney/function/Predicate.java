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
package org.javamoney.function;

import java.io.Serializable;

import javax.money.MonetaryAdjuster;

/**
 * This interface models a predicate, which just evaluates to {@code true} or
 * {@code false}, for an arbitrary item. Predicates are used in different areas
 * on the API.
 * <p>
 * Instances of this interface are required to be immutable, thread-safe and
 * {@link Serializable}.
 * 
 * @author Anatole Tresch
 * 
 * @param <T>
 *            The item type targeting
 */
public interface Predicate<T>{
	
	public boolean isPredicateTrue(T item);

}
