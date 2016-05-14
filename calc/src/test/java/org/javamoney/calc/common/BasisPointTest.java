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
package org.javamoney.calc.common;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import org.javamoney.calc.common.BasisPoint;
import org.javamoney.moneta.Money;
import org.junit.Test;

/**
 * @author Werner Keil
 */
public class BasisPointTest {

    @Test
    public void testOf() {
        BasisPoint perc = BasisPoint.of(BigDecimal.ONE);
        assertNotNull(perc);
    }

    @Test
    public void testApply() {
        Money m = Money.of(BigDecimal.valueOf(2.35d), "CHF");
        assertEquals(Money.of(BigDecimal.valueOf(0.00235d), "CHF"),
                BasisPoint.of(BigDecimal.TEN).apply(m));
    }

    @Test
    public void testApply10() {
        Money m = Money.of(3, "CHF");
        assertEquals(Money.of(BigDecimal.valueOf(0.003d), "CHF"),
                BasisPoint.of(BigDecimal.TEN).apply(m));
    }

    @Test
    public void testApply20() {
        Money m = Money.of(12, "CHF");
        assertEquals(Money.of(BigDecimal.valueOf(0.024d), "CHF"),
                BasisPoint.of(BigDecimal.valueOf(20)).apply(m));
    }

    @Test
    public void testApply30() {
        Money m = Money.of(12, "CHF");
        assertEquals(Money.of(BigDecimal.valueOf(0.036d), "CHF"),
                BasisPoint.of(BigDecimal.valueOf(30)).apply(m));
    }

    @Test
    public void testToString() {
        assertEquals("15\u2031", BasisPoint.of(BigDecimal.valueOf(15))
                .toString());
    }
}
