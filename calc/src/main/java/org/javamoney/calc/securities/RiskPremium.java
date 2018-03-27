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

import org.javamoney.calc.common.Rate;

import java.math.BigDecimal;

/**
 * <img src="http://www.financeformulas.net/formulaimages/Risk%20Premium%201.gif" />
 * <img src="http://www.financeformulas.net/formulaimages/CAPM%202.gif" />
 * <p>
 * <p> The formula for risk premium, sometimes referred to as default risk premium, is the return on an investment minus the return that would be earned on a risk free investment. The risk premium is the amount that an investor would like to earn for the risk involved with a particular investment.
 *
 * @author Manuela Grindei
 * @link http://www.financeformulas.net/Risk-Premium.html
 */
public class RiskPremium {
    /**
     * Private constructor.
     */
    private RiskPremium() {
    }

    /**
     * Calculates the risk premium.
     *
     * @param assetReturn    the asset or investment return
     * @param riskFreeReturn the risk-free return
     * @return the risk premium
     */
    public static BigDecimal calculate(Rate assetReturn, Rate riskFreeReturn) {
        return assetReturn.get().subtract(riskFreeReturn.get());
    }

    /**
     * Calculates the risk premium.
     *
     * @param beta           a scalar
     * @param marketReturn   the return on the market
     * @param riskFreeReturn the risk-free return
     * @return the risk premium
     */
    public static BigDecimal calculateWithCAPM(BigDecimal beta, Rate marketReturn, Rate riskFreeReturn) {
        return beta.multiply(marketReturn.get().subtract(riskFreeReturn.get()));
    }
}
