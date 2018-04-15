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
 * The book value per share formula is used to calculate the per share value of a company based on its equity available to common shareholders. The term
 * "book value" is a company's assets minus its liabilities and is sometimes referred to as stockholder's equity, owner's equity, shareholder's equity, or
 * simply equity. Common stockholder's equity, or owner's equity, can be found on the balance sheet for the company. In the absence of preferred shares, the
 * total stockholder's equity is used.
 *
 * @author Manuela Grindei
 * @see <a href="http://www.financeformulas.net/Book-Value-per-Share.html">http://www.financeformulas.net/Book-Value-per-Share.html</a>
 */
public class BookValuePerShare {

    /**
     * Private constructor.
     */
    private BookValuePerShare() {
    }

    /**
     * Calculates the per share value of a company based on its equity.
     *
     * @param equity               the total common stockholder's equity
     * @param numberOfCommonShares the number of common shares
     * @return the book value per share
     */
    public static MonetaryAmount calculate(MonetaryAmount equity, int numberOfCommonShares) {
        return equity.divide(numberOfCommonShares);
    }
}
