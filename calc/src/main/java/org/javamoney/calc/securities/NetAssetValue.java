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
 * The net asset value formula is used to calculate a mutual fund's value per share. A mutual fund is a pool of investments that are divided
 * into shares to be purchased by investors. Each share contains a weighted portion of each investment in the collective pool. The premise of
 * grouping in this manner is to minimize risk by diversifying.
 *
 * @author Manuela Grindei
 * @see <a href="http://www.financeformulas.net/Net_Asset_Value.html">http://www.financeformulas.net/Net_Asset_Value.html</a>
 */
public class NetAssetValue {

    /**
     * Private constructor.
     */
    private NetAssetValue() {
    }

    /**
     * Calculates the net asset value.
     *
     * @param fundAssets        the assets of the fund
     * @param fundLiabilities   the liabilities of the fund
     * @param outstandingShares the outstanding shares
     * @return the net asset value
     */
    public static MonetaryAmount calculate(MonetaryAmount fundAssets, MonetaryAmount fundLiabilities, double outstandingShares) {
        return fundAssets.subtract(fundLiabilities).divide(outstandingShares);
    }
}
