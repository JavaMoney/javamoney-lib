package org.javamoney.calc.securities;

import org.javamoney.calc.common.Rate;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * <img src="http://www.financeformulas.net/formulaimages/Tax%20Equivalent%20Yield%201.gif" />
 * <p> The tax equivalent yield formula is used to compare the yield between a tax-free investment and an investment that is taxed.
 *
 * @author Manuela Grindei
 * @see http://www.financeformulas.net/Tax_Equivalent_Yield.html
 */
public class TaxEquivalentYield {

    /**
     * Private constructor.
     */
    private TaxEquivalentYield() {
    }

    /**
     * Calculates the tax equivalent yield.
     *
     * @param taxFreeYield the yield of the tax-free investment
     * @param taxRate      the tax rate
     * @return the tax equivalent yield
     */
    public static BigDecimal calculate(Rate taxFreeYield, Rate taxRate) {
        return taxFreeYield.get().divide(BigDecimal.ONE.subtract(taxRate.get()), MathContext.DECIMAL64);
    }
}
