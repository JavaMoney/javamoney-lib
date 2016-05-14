/*
 *  Copyright (c) 2012, 2015, Credit Suisse (Anatole Tresch), Werner Keil.
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
 * Models an arbitrary complex calculation.
 * @author Anatole Tresch
 * @author Werner Keil
 * 
 * @param <T> the result type.
 */
public interface ComplexCalculation<I extends ComplexType,T>{
    /**
     * Access the input type descriptor.
     *
     * @return the input type descriptor, never null.
     */
    public I getInputType();

    /**
     * Access the result type.
     *
     * @return the result type, never null.
     */
    public Class<T> getResultType();

    /**
     * Calulates the result.
     *
     * @param input the input value
     * @return the result
     * @throws javax.money.MonetaryException if input validation fails, or an other monetary errors occurring.
     */
    public T calculate(ComplexValue<I> input);
}
