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

/**
 * The formula for earnings per share, or EPS, is a company's net income expressed on a per share basis. Net income for a particular company can be found
 * on its income statement. It is important to note that the earnings per share formula only references common stock and any preferred stock dividends is
 * subtracted from the net income, if applicable.
 *
 * @author Manuela Grindei
 * @see <a href="http://www.financeformulas.net/Earnings_Per_Share.html">http://www.financeformulas.net/Earnings_Per_Share.html</a>
 */
public class EarningsPerShare {
    /**
     * Private constructor.
     */
    private EarningsPerShare() {
    }

    /**
     * Calculates the earnings per share.
     *
     * @param netIncome                          the company's net income
     * @param weightedAverageOfOutstandingShares the weighted average of outstanding shares of common stock
     * @return the earnings per share
     */
    public static MonetaryAmount calculate(MonetaryAmount netIncome, double weightedAverageOfOutstandingShares) {
        return netIncome.divide(weightedAverageOfOutstandingShares);
    }
}
