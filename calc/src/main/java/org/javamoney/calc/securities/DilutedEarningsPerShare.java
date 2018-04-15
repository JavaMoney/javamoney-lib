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

/**
 * Diluted earnings per share or Diluted EPS, is a firm's net income divided by the sum of its average shares and other convertible instruments.
 *
 * A company's net income can be found on its income statement.
 *
 * A company's average shares refers to the weighted average of common shares throughout the year. The weights of each factor would be the length of time
 * each quantity of common shares is outstanding.
 *
 * The term "convertible instruments" refers to any financial instrument that could possibly be converted into a common shares. For reference, a few examples
 * of convertible instruments that may be considered in the diluted earnings per share formula are stock options and convertible preferred stocks, but there
 * are many others and anything than has the availability to be converted to a common share could be included.
 *
 * @author Manuela Grindei
 * @see <a href="http://www.financeformulas.net/Diluted-EPS.html">http://www.financeformulas.net/Diluted-EPS.html</a>
 */
public class DilutedEarningsPerShare {

    /**
     * Private constructor.
     */
    private DilutedEarningsPerShare() {
    }

    /**
     * Calculates the diluted earnings per share.
     *
     * @param netIncome                   the firm's net income
     * @param averageShares               the firm's average shares
     * @param otherConvertibleInstruments other convertible instruments
     * @return the diluted earnings per share
     */
    public static MonetaryAmount calculate(MonetaryAmount netIncome, BigDecimal averageShares, BigDecimal otherConvertibleInstruments) {
        return netIncome.divide(averageShares.add(otherConvertibleInstruments));
    }
}
