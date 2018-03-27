/*
 * Copyright (c) 2012, 2018, Werner Keil, Anatole Tresch and others.
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
 *
 * Contributors: @manuela-grindei
 */
package org.javamoney.calc.securities;

import org.javamoney.calc.common.Rate;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * <img src="http://www.financeformulas.net/formulaimages/Tax%20Equivalent%20Yield%201.gif" />
 * <p> The tax equivalent yield formula is used to compare the yield between a tax-free investment and an investment that is taxed.
 *
 * @author Manuela Grindei
 * @link http://www.financeformulas.net/Tax_Equivalent_Yield.html
 */
public class TaxEquivalentYield {

    /**
     * Private constructor.
     */
    private TaxEquivalentYield() {
    }

    /**
     * Calculates the tax equivalent yield.
     *
     * @param taxFreeYield the yield of the tax-free investment
     * @param taxRate      the tax rate
     * @return the tax equivalent yield
     */
    public static BigDecimal calculate(Rate taxFreeYield, Rate taxRate) {
        return taxFreeYield.get().divide(BigDecimal.ONE.subtract(taxRate.get()), MathContext.DECIMAL64);
    }
}
