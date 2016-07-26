package org.javamoney.calc.securities;


import org.javamoney.calc.common.Rate;

import java.math.BigDecimal;
import java.util.List;

/**
 * <img src="http://www.financeformulas.net/Formula%20Images/Holding%20Period%20Return%201.gif" />
 * <p> The formula for the holding period return is used for calculating the return on an investment over multiple periods.
 *
 * @author Manuela Grindei
 * @see http://www.financeformulas.net/Holding_Period_Return.html
 */
public class HoldingPeriodReturn {

    /**
     * Private constructor.
     */
    private HoldingPeriodReturn() {
    }

    /**
     * Calculates the holding period return.
     *
     * @param returns the list of returns per periods
     * @return the holding period return
     */
    public static BigDecimal calculate(List<Rate> returns) {
        BigDecimal product = BigDecimal.ONE;
        for (Rate rateOfReturn : returns) {
            if (rateOfReturn == null) {
                throw new IllegalArgumentException("The list of returns cannot contain null elements");
            }
            product = product.multiply(rateOfReturn.get().add(BigDecimal.ONE));
        }
        return product.subtract(BigDecimal.ONE);
    }

    /**
     * Calculates the holding period return.
     *
     * @param periodicRate    the return per period
     * @param numberOfPeriods the number of periods
     * @return the holding period return
     */
    public static BigDecimal calculateForSameReturn(Rate periodicRate, int numberOfPeriods) {
        if (periodicRate == null) {
            throw new IllegalArgumentException("The list of returns cannot contain null elements");
        }
        if (numberOfPeriods <= 0) {
            throw new IllegalArgumentException("The number of periods should be positive");
        }
        BigDecimal product = BigDecimal.ONE;
        final BigDecimal multiplicand = periodicRate.get().add(BigDecimal.ONE);
        for (int i = 0; i < numberOfPeriods; i++) {
            product = product.multiply(multiplicand);
        }

        return product.subtract(BigDecimal.ONE);
    }
}
