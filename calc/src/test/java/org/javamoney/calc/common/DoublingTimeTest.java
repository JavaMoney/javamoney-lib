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
public class DoublingTimeTest {

    /**
     * Values are taken from: http://www.financeformulas.net/Doubling_Time.html#calcHeader
     * @throws Exception
     */
    @Test
    public void calculate() throws Exception {
        assertEquals(8.467838642560691, DoublingTime.calculate(Rate.of(0.0853)).doubleValue(), 0.0d);
        assertEquals(1.709511291351455, DoublingTime.calculate(Rate.of(0.5)).doubleValue(), 0.0d);
        assertEquals(1.0, DoublingTime.calculate(Rate.of(1)).doubleValue(), 0.0d);
        assertEquals(15.74730183648559, DoublingTime.calculate(Rate.of(0.045)).doubleValue(), 0.0d);
    }

    @Test(expected = MonetaryException.class)
    public void calculate_Invalid(){
        DoublingTime.calculate(Rate.of(0));
    }
}