/*
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
 * This interface defines a {@link MonetaryFunction}. It is considered to be
 * adapted/compatible with {@code java.util.function.Function} as introduced in
 * Java 8.
 * <p>
 * This interface is modeled after {@link java.util.function.Function} from Java 8 on. 
 * It is meant to extend it and could be fully replaced by this functional interface in a later version of JavaMoney.

 * 
 * @author Anatole Tresch
 * @author Werner Keil
 * 
 * @param <T>
 *            The input type of the function.
 * @param <R>
 *            The result type of the function.
 */
public interface MonetaryFunction<T, R> {  // extends java.util.function.Function<T, R> for Java 8/9

	/**
	 * Apply a function to the input argument T, yielding an appropriate result R.
	 * @param value the input value
	 * @return the result of the function
	 */
	public R apply(T value);

}