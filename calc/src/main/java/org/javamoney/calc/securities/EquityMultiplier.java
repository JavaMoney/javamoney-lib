package org.javamoney.calc.securities;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.math.MathContext;

/**
 * <img src="http://www.financeformulas.net/formulaimages/Equity%20Multiplier%201.gif" />
 * <p> The formula for equity multiplier is total assets divided by stockholder's equity. Equity multiplier is a financial leverage ratio that evaluates
 * a company's use of debt to purchase assets.
 *
 * @author Manuela Grindei
 * @see http://www.financeformulas.net/Equity_Multiplier.html
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
