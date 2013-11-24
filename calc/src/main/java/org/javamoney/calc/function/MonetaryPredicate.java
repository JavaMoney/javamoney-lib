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
package org.javamoney.calc.function;

/**
 * This interface models a predicate, which just evaluates to {@code true} or
 * {@code false}, for an arbitrary item. Predicates are used in different areas
 * of the API.
 * <p>
 * This interface is modeled after <code>java.util.function.Predicate</code> from Java 8 on. 
 * It is meant to extend it and could be fully replaced by this functional interface in a later version of JavaMoney.
 * <p>
 * Instances of this interface are required to be immutable, thread-safe and
 * {@link Serializable}.
 * 
 * @author Anatole Tresch
 * @author Werner Keil
 * 
 * @param <T>
 *            The item type targeting
 */
public interface MonetaryPredicate<T> { // extends java.util.function.Predicate<T> for Java 8/9
	
	public boolean test(T item);

}
