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

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * Created by atsticks on 15.05.16.
 * @see http://www.mrzeno.com/Present-Value-Annuity-Factor-PVAF.php
 * Note: {@code http://www.financeformulas.net/Present-Value-Annuity-Factor.html#calcHeader}
 * shows incorrect values in this case.
 */
public class PresentValueOfAnnuityPaymentFactorTest {

    @Test
    public void calculate_periods0() throws Exception {
        assertEquals(BigDecimal.valueOf(0), PresentValueOfAnnuityPaymentFactor.calculate(Rate.of(0.05), 0));
        assertEquals(BigDecimal.valueOf(0), PresentValueOfAnnuityPaymentFactor.calculate(Rate.of(-0.05), 0));
    }

    @Test
    public void calculate_periods1() throws Exception {
        assertEquals(BigDecimal.valueOf(0.952380952380952), PresentValueOfAnnuityPaymentFactor.calculate(Rate.of(0.05), 1));
        assertEquals(BigDecimal.valueOf(1.05263157894736), PresentValueOfAnnuityPaymentFactor.calculate(Rate.of(-0.05), 1));
    }

    @Test
    public void calculate_periods10() throws Exception {
        // Values are correct due to
        // http://www.mrzeno.com/Present-Value-Annuity-Factor-PVAF.php
        assertEquals(BigDecimal.valueOf(7.721734929184812), PresentValueOfAnnuityPaymentFactor.calculate(Rate.of(0.05), 10));
        assertEquals(BigDecimal.valueOf(13.40365140230186), PresentValueOfAnnuityPaymentFactor.calculate(Rate.of(-0.05), 10));
    }
}