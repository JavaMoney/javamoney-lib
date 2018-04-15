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
 * The capital asset pricing model provides a formula that calculates the expected return on a security based on its level of risk. The formula for the
 * capital asset pricing model is the risk free rate plus beta times the difference of the return on the market and the risk free rate.
 *
 * @author Manuela Grindei
 * @see <a href="http://www.financeformulas.net/Capital-Asset-Pricing-Model.html">http://www.financeformulas.net/Capital-Asset-Pricing-Model.html</a>
 */
public class CapitalAssetPricingModelFormula {

    /**
     * Private constructor.
     */
    private CapitalAssetPricingModelFormula() {
    }

    /**
     * Calculates the expected return using the CAPM model.
     *
     * @param riskFreeRate the risk-free rate
     * @param beta         a scalar
     * @param marketReturn the return on the market
     * @return the expected return
     */
    public static Rate calculate(Rate riskFreeRate, BigDecimal beta, Rate marketReturn) {
        return calculate(riskFreeRate, beta, marketReturn, BigDecimal.ZERO);
    }

    /**
     * Calculates the expected return using the CAPM model with regression analysis.
     *
     * @param riskFreeRate the risk-free rate
     * @param beta         a scalar
     * @param marketReturn the return on the market
     * @param epsilon      error in regression
     * @return the expected return
     */
    public static Rate calculate(Rate riskFreeRate, BigDecimal beta, Rate marketReturn, BigDecimal epsilon) {
        return Rate.of(epsilon.add(riskFreeRate.get().add(beta.multiply(marketReturn.get().subtract(riskFreeRate.get())))));
    }
}
