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

import static org.junit.Assert.*;

/**
 * Created by atsticks on 27.05.16.
 */
public class RateTest {

    @Test
    public void of_BD() throws Exception {
        Rate r = Rate.of(BigDecimal.valueOf(0.0567));
        assertNotNull(r);
    }

    @Test
    public void of_Num() throws Exception {
        Rate r = Rate.of(0.0567f);
        assertNotNull(r);
    }

    @Test(expected=NullPointerException.class)
    public void of_Null() throws Exception {
        Rate.of((Number)null);
    }

    @Test
    public void testHashCode() throws Exception {
        Rate r1 = Rate.of(0.0567f);
        Rate r2 = Rate.of(0.0567d);
        assertTrue(r1.hashCode()==r2.hashCode());
        r2 = Rate.of(0.0568d);
        assertFalse(r1.hashCode()==r2.hashCode());
    }

    @Test
    public void testEquals() throws Exception {
        Rate r1 = Rate.of(0.0567f);
        Rate r2 = Rate.of(0.0567d);
        assertEquals(r1, r2);
        r2 = Rate.of(0.0568d);
        assertNotSame(r1, r2);
    }

    @Test
    public void get() throws Exception {
        Rate r1 = Rate.of(0.0567f);
        assertEquals(BigDecimal.valueOf(0.0567d), r1.get());
    }

    @Test
    public void testToString() throws Exception {
        Rate r1 = Rate.of(0.0567f);
        assertEquals("Rate[0.0567]", r1.toString());
    }

    @Test
    public void apply() throws Exception {
        Rate r1 = Rate.of(0.05);
        assertEquals(Money.of(5, "CHF"), r1.apply(Money.of(100, "CHF")));
        assertEquals(Money.of(5, "CHF"), Money.of(100, "CHF").with(r1));
    }
}