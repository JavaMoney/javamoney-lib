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
package org.javamoney.calc;


/**
 * This interface defines a calculation. It is hereby important to
 * distinguish between <i>internal rounding</i> such as implied by the maximal
 * precision/scale of an amount, and <i>rounding</i> applied to a
 * {@link MonetaryAmount} or a calculation algorithm.
 * <p>
 * Nevertheless extensions provide a MonetaryCalculation, which
 * wraps a {@link MonetaryAmount} and adds implicit rounding.
 * 
 * <p>
 * It is considered to be used as a {@code java.util.function.UnaryOperator} as
 * introduced by Java 8. Modeling it here allows the JSR to forward port
 * functional interfaces, though the JSR itself, is based on Java 7.
 * 
 * @version 0.9.1
 * @author Werner Keil
 * @author Anatole Tresch
 */
// @FunctionalInterface for Java 9
public interface Calculation<T, R> {

	public R calculate(T input);

}
