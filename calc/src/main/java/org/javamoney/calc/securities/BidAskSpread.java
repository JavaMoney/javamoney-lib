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
 * The bid ask spread formula is the difference between the asking price and bid price of a particular investment.
 *
 * The bid ask spread may be used for various investments and is primarily used in investments that sell on an exchange.
 *
 * @author Manuela Grindei
 * @see <a href="http://www.financeformulas.net/Bid-Ask-Spread.html">http://www.financeformulas.net/Bid-Ask-Spread.html</a>
 */
public final class BidAskSpread {

    /**
     * Private constructor.
     */
    private BidAskSpread() {
    }

    /**
     * Calculates the bid-ask spread.
     *
     * @param askPrice the asking price of the investment
     * @param bidPrice the bid price of the investment
     * @return the bid-ask spread.
     */
    public static MonetaryAmount calculate(MonetaryAmount askPrice, MonetaryAmount bidPrice) {
        return askPrice.subtract(bidPrice);
    }
}
