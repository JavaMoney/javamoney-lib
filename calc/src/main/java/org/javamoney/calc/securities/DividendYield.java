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


import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.math.MathContext;

/**
 * <img src="http://www.financeformulas.net/formulaimages/Dividend%20Yield%201.gif" />
 * <p>
 * <p> The formula for the dividend yield is used to calculate the percentage return on a stock based solely on dividends. The total return on a stock
 * is the combination of dividends and appreciation of a stock.
 *
 * @author Manuela Grindei
 * @link http://www.financeformulas.net/Dividend_Yield.html
 */
public class DividendYield {
    /**
     * Private constructor.
     */
    private DividendYield() {
    }

    /**
     * Calculates the dividend yield.
     *
     * @param dividends    dividends for the period
     * @param initialPrice initial price for the period
     * @return the dividend yield
     */
    public static BigDecimal calculate(MonetaryAmount dividends, MonetaryAmount initialPrice) {
        BigDecimal dividendsValue = BigDecimal.valueOf(dividends.getNumber().doubleValueExact());
        BigDecimal initialPriceValue = BigDecimal.valueOf(initialPrice.getNumber().doubleValueExact());

        return dividendsValue.divide(initialPriceValue, MathContext.DECIMAL64);
    }
}
