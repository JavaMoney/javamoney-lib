/*
 * Copyright (c) 2012, 2015, Credit Suisse (Anatole Tresch), Werner Keil.
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
package org.javamoney.calc.common;

import org.junit.Test;

import javax.money.MonetaryException;

import static org.junit.Assert.*;

/**
 * Created by atsticks on 15.05.16.
 */
public class FutureValueFactorTest {

    /**
     * Values compared with {@code http://www.financeformulas.net/Future-Value-Factor.html#calcHeader}.
     * @throws Exception
     */
    @Test
    public void calculate_PositiveRates() throws Exception {
        assertEquals(1.0, FutureValueFactor.calculate(Rate.of(0.05),0).doubleValue(), 0.0d);
        assertEquals(1.0500, FutureValueFactor.calculate(Rate.of(0.05),1).doubleValue(), 0.0d);
        assertEquals(1.628894626777441, FutureValueFactor.calculate(Rate.of(0.05),10).doubleValue(), 0.0d);
    }

    /**
     * Values compared with {@code http://www.financeformulas.net/Future-Value-Factor.html#calcHeader}.
     * @throws Exception
     */
    @Test
    public void calculate_NegativeRates() throws Exception {
        assertEquals(1.0, FutureValueFactor.calculate(Rate.of(-0.05),0).doubleValue(), 0.0d);
        assertEquals(0.9500, FutureValueFactor.calculate(Rate.of(-0.05),1).doubleValue(), 0.0d);
        assertEquals(0.5987369392383789, FutureValueFactor.calculate(Rate.of(-0.05),10).doubleValue(), 0.0d);
    }

    @Test
    public void calculate_Invalid(){
        assertEquals(1.0, FutureValueFactor.calculate(Rate.of(0),0).doubleValue(), 0.0d);
    }

}