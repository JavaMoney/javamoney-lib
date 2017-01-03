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
 * @see http://www.financeformulas.net/Risk-Premium.html
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
