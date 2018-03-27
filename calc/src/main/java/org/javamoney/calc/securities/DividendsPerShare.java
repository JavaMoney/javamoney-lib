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
 * <img src="http://www.financeformulas.net/formulaimages/Dividends%20Per%20Share%201.gif" />
 * <p>
 * <p> The formula for dividends per share, or DPS, is the annual dividends paid divided by the number of shares outstanding.
 *
 * @author Manuela Grindei
 * @link http://www.financeformulas.net/Dividends_Per_Share.html
 */
public class DividendsPerShare {

    /**
     * Private constructor.
     */
    private DividendsPerShare() {
    }

    /**
     * Calculates the dividends per share.
     *
     * @param dividends      the annual dividends paid
     * @param numberOfShares the number of shares outstanding
     * @return the dividends per share
     */
    public static MonetaryAmount calculate(MonetaryAmount dividends, double numberOfShares) {
        return dividends.divide(numberOfShares);
    }
}
