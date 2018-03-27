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
 * <img src="http://www.financeformulas.net/formulaimages/Dividend%20Payout%20Ratio%201.gif" />
 * <p> The dividend payout ratio is the amount of dividends paid to stockholders relative to the amount of total net income of a company. The amount that
 * is not paid out in dividends to stockholders is held by the company for growth. The amount that is kept by the company is called retained earnings.
 *
 * @author Manuela Grindei
 * @link http://www.financeformulas.net/Dividend_Payout_Ratio.html
 */
public class DividendPayoutRatio {

    /**
     * Private constructor.
     */
    private DividendPayoutRatio() {
    }

    /**
     * Calculates the dividend payout ratio.
     *
     * @param dividends the amount of dividends paid to stockholders
     * @param netIncome the amount of total net income of the company
     * @return the dividend payout ratio
     */
    public static BigDecimal calculate(MonetaryAmount dividends, MonetaryAmount netIncome) {
        BigDecimal dividendsValue = BigDecimal.valueOf(dividends.getNumber().doubleValueExact());
        BigDecimal netIncomeValue = BigDecimal.valueOf(netIncome.getNumber().doubleValueExact());

        return dividendsValue.divide(netIncomeValue, MathContext.DECIMAL64);
    }
}
