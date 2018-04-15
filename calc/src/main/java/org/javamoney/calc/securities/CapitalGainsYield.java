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
 * The formula for the capital gains yield is used to calculate the return on a stock based solely on the appreciation of the stock. The formula for capital
 * gains yield does not include dividends paid on the stock, which can be found using the dividend yield. The capital gains yield and dividend yield is
 * combined to calculate the total stock return.
 *
 * The capital gains yield formula uses the rate of change formula. Calculating the capital gains yield is effectively calculating the rate of change of the
 * stock price. The rate of change can be found by subtracting an ending amount from the original amount then divided by the original amount.
 *
 * @author Manuela Grindei
 * @see <a href="http://www.financeformulas.net/Capital-Gains-Yield.html">http://www.financeformulas.net/Capital-Gains-Yield.html</a>
 */
public class CapitalGainsYield {

    /**
     * Private constructor.
     */
    private CapitalGainsYield() {
    }

    /**
     * Calculates the return on a stock based solely on the appreciation of the stock.
     *
     * @param initialStockPrice          initial stock price
     * @param stockPriceAfterFirstPeriod stock price after first period
     * @return the capital gains yield
     */
    public static BigDecimal calculate(MonetaryAmount initialStockPrice, MonetaryAmount stockPriceAfterFirstPeriod) {
        BigDecimal initialStockPriceValue = BigDecimal.valueOf(initialStockPrice.getNumber().doubleValueExact());
        BigDecimal stockValueAfterFirstPeriod = BigDecimal.valueOf(stockPriceAfterFirstPeriod.getNumber().doubleValueExact());

        return stockValueAfterFirstPeriod.subtract(initialStockPriceValue).divide(initialStockPriceValue, MathContext.DECIMAL64);
    }
}
