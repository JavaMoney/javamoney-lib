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
 * The formula for equity multiplier is total assets divided by stockholder's equity. Equity multiplier is a financial leverage ratio that evaluates
 * a company's use of debt to purchase assets.
 *
 * @author Manuela Grindei
 * @see <a href="http://www.financeformulas.net/Equity_Multiplier.html">http://www.financeformulas.net/Equity_Multiplier.html</a>
 */
public class EquityMultiplier {

    /**
     * Private constructor.
     */
    private EquityMultiplier() {
    }

    /**
     * Calculates the equity multiplier.
     *
     * @param totalAssets the total assets
     * @param equity      the stockholder's equity
     * @return the equity multiplier
     */
    public static BigDecimal calculate(MonetaryAmount totalAssets, MonetaryAmount equity) {
        BigDecimal totalAssetValue = BigDecimal.valueOf(totalAssets.getNumber().doubleValueExact());
        BigDecimal equityValue = BigDecimal.valueOf(equity.getNumber().doubleValueExact());

        return totalAssetValue.divide(equityValue, MathContext.DECIMAL64);
    }
}
