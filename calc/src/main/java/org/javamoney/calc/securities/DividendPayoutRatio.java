package org.javamoney.calc.securities;


import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.math.MathContext;

/**
 * <img src="http://www.financeformulas.net/formulaimages/Dividend%20Payout%20Ratio%201.gif" />
 * <p> The dividend payout ratio is the amount of dividends paid to stockholders relative to the amount of total net income of a company. The amount that
 * is not paid out in dividends to stockholders is held by the company for growth. The amount that is kept by the company is called retained earnings.
 *
 * @author Manuela Grindei
 * @see http://www.financeformulas.net/Dividend_Payout_Ratio.html
 */
public class DividendPayoutRatio {

    /**
     * Private constructor.
     */
    private DividendPayoutRatio() {
    }

    /**
     * Calculates the dividend payout ratio.
     *
     * @param dividends the amount of dividends paid to stockholders
     * @param netIncome the amount of total net income of the company
     * @return the dividend payout ratio
     */
    public static BigDecimal calculate(MonetaryAmount dividends, MonetaryAmount netIncome) {
        BigDecimal dividendsValue = BigDecimal.valueOf(dividends.getNumber().doubleValueExact());
        BigDecimal netIncomeValue = BigDecimal.valueOf(netIncome.getNumber().doubleValueExact());

        return dividendsValue.divide(netIncomeValue, MathContext.DECIMAL64);
    }
}
