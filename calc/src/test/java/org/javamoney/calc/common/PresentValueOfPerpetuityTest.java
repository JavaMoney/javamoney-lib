/*
 *  Copyright (c) 2012, 2013, Trivadis (Anatole Tresch), Werner Keil.
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
package org.javamoney.calc.common;

import org.javamoney.moneta.Money;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

/**
 * Created by atsticks on 15.05.16.
 * @see http://www.financeformulas.net/Perpetuity.html#calcHeader
 * shows incorrect values in this case.
 */
public class PresentValueOfPerpetuityTest {

    @Test
    public void testRate(){
        PresentValueOfPerpetuity op = PresentValueOfPerpetuity.of(Rate.of(0.05));
        assertEquals(Rate.of(0.05), op.getRate());
    }

    @Test
    public void testApply(){
        PresentValueOfPerpetuity op = PresentValueOfPerpetuity.of(Rate.of(0.05));
        assertEquals(Money.of(2000, "CHF"), Money.of(100, "CHF").with(op));
        assertEquals(Money.of(2000, "CHF"), op.apply(Money.of(100, "CHF")));
        assertEquals(Money.of(-2000, "CHF"), Money.of(-100, "CHF").with(op));
        op = PresentValueOfPerpetuity.of(Rate.of(-0.05));
        assertEquals(Money.of(-2000, "CHF"), op.apply(Money.of(100, "CHF")));
    }

    @Test
    public void calculate_positive() throws Exception {
        assertEquals(Money.of(2000, "CHF"), PresentValueOfPerpetuity.calculate(Money.of(100, "CHF"), Rate.of(0.05)));
    }

    @Test
    public void calculate_negative() throws Exception {
        assertEquals(Money.of(-2000, "CHF"), PresentValueOfPerpetuity.calculate(Money.of(100, "CHF"), Rate.of(-0.05)));
    }

    @Test
    public void calculate_positive_value_neg() throws Exception {
        assertEquals(Money.of(-2000, "CHF"), PresentValueOfPerpetuity.calculate(Money.of(-100, "CHF"), Rate.of(0.05)));
    }

    @Test
    public void calculate_negative_value_neg() throws Exception {
        assertEquals(Money.of(2000, "CHF"), PresentValueOfPerpetuity.calculate(Money.of(-100, "CHF"), Rate.of(-0.05)));
    }

    @Test
    public void testToString(){
        PresentValueOfPerpetuity op = PresentValueOfPerpetuity.of(Rate.of(0.056778));
        assertEquals("PresentValueOfPerpetuity{rate=Rate[0.056778]}", op.toString());
    }
}