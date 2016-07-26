package org.javamoney.calc.securities;

import org.javamoney.calc.common.Rate;

import javax.money.MonetaryAmount;

/**
 * <img src="http://www.financeformulas.net/formulaimages/PV%20of%20Stock%20-%20Constant%20Growth%201.gif" />
 * <img src="http://www.financeformulas.net/formulaimages/PV%20of%20Stock%20-%20Zero%20Growth%201.gif" />
 * <p> The formula for the present value of a stock with constant growth is the estimated dividends to be paid divided by the difference between the required rate of return and the growth rate.
 * <p> The formula for the present value of a stock with zero growth is dividends per period divided by the required return per period. The present value of stock formulas are not to be considered an exact or guaranteed approach to valuing a stock but is a more theoretical approach.
 *
 * @author Manuela Grindei
 * @see http://www.financeformulas.net/Present-Value-of-Stock-with-Constant-Growth.html
 * @see http://www.financeformulas.net/Present-Value-of-Stock-with-Zero-Growth.html
 */
public class StockPresentValue {

    /**
     * Private constructor.
     */
    private StockPresentValue() {
    }

    /**
     * Calculates the present value of a stock for constant growth.
     *
     * @param estimatedDividends   the estimated dividends for next period
     * @param requiredRateOfReturn the required rate of return
     * @param the                  growth rate
     * @return the present value of the stock
     */
    public static MonetaryAmount calculateForConstantGrowth(MonetaryAmount estimatedDividends, Rate requiredRateOfReturn, Rate growthRate) {
        return estimatedDividends.divide(requiredRateOfReturn.get().subtract(growthRate.get()));
    }

    /**
     * Calculates the present value of a stock for constant growth.
     *
     * @param estimatedDividends   the estimated dividends for next period
     * @param requiredRateOfReturn the required rate of return
     * @return the present value of the stock
     */
    public static MonetaryAmount calculateForZeroGrowth(MonetaryAmount estimatedDividends, Rate requiredRateOfReturn) {
        return calculateForConstantGrowth(estimatedDividends, requiredRateOfReturn, Rate.ZERO);
    }
}
