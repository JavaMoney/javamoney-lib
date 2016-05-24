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

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * Created by atsticks on 15.05.16.
 * TODO value seem not to be reasonable, check!!!
 */
public class PresentValueOfAnnuityFactorTest {

    @Test
    public void calculate_periods0() throws Exception {
        assertEquals(BigDecimal.valueOf(0), PresentValueOfAnnuityPaymentFactor.calculate(Rate.of(0.05), 0));
    }

    @Test
    public void calculate_periods1() throws Exception {
        assertEquals(BigDecimal.valueOf(0), PresentValueOfAnnuityPaymentFactor.calculate(Rate.of(0.05), 1));
    }

    @Test
    public void calculate_periods10() throws Exception {
        assertEquals(BigDecimal.valueOf(0), PresentValueOfAnnuityPaymentFactor.calculate(Rate.of(0.05), 10));
    }
}