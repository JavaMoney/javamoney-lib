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
 * The formula for price to sales ratio, sometimes referenced as the P/S Ratio, is the perceived value of a stock by the market compared to the revenues of the company. The price to sales ratio is calculated by dividing the stock price by sales per share. Sales per share uses the weighted average of shares for the time period evaluated, which is generally one year.
 *
 * @author Manuela Grindei
 * @see <a href="http://www.financeformulas.net/Price-to-Sales-Ratio.html">http://www.financeformulas.net/Price-to-Sales-Ratio.html</a>
 */
public class PriceToSalesRatio {

    /**
     * Private constructor.
     */
    private PriceToSalesRatio() {
    }

    /**
     * Calculates the price to sales ratio.
     *
     * @param sharePrice    the share price
     * @param salesPerShare the sales per share
     * @return the price to sales ratio
     */
    public static BigDecimal calculate(MonetaryAmount sharePrice, MonetaryAmount salesPerShare) {
        BigDecimal sharePriceValue = BigDecimal.valueOf(sharePrice.getNumber().doubleValueExact());
        BigDecimal salesPerShareValue = BigDecimal.valueOf(salesPerShare.getNumber().doubleValueExact());

        return sharePriceValue.divide(salesPerShareValue, MathContext.DECIMAL64);
    }
}
