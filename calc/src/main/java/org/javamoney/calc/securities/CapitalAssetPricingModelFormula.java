package org.javamoney.calc.securities;


import org.javamoney.calc.common.Rate;

import java.math.BigDecimal;

/**
 * <img src="http://www.financeformulas.net/formulaimages/CAPM%201.gif" />
 * <p>
 * The capital asset pricing model provides a formula that calculates the expected return on a security based on its level of risk. The formula for the
 * capital asset pricing model is the risk free rate plus beta times the difference of the return on the market and the risk free rate.
 *
 * @author Manuela Grindei
 * @see http://www.financeformulas.net/Capital-Asset-Pricing-Model.html
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
