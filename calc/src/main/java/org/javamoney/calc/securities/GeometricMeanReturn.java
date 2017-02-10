package org.javamoney.calc.securities;


import org.javamoney.calc.common.Rate;

import java.math.BigDecimal;
import java.util.List;

/**
 * <img src="http://www.financeformulas.net/Formula%20Images/Geometric%20Mean%20Return%201.gif" />
 * <p>
 * The geometric mean return formula is used to calculate the average rate per period on an investment that is compounded over multiple periods.
 *
 * @author Manuela Grindei
 * @see http://www.financeformulas.net/Geometric_Mean_Return.html
 */
public class GeometricMeanReturn {

    /**
     * Private constructor.
     */
    private GeometricMeanReturn() {
    }

    /**
     * Calculates geometric mean return.
     *
     * @param ratesOfReturn   the rates of return
     * @return the geometric mean return
     */
    public static double calculate(List<Rate> ratesOfReturn) {
        BigDecimal product = BigDecimal.ONE;
        for (Rate rateOfReturn : ratesOfReturn) {
            if (rateOfReturn == null) {
                throw new IllegalArgumentException("The list of rates cannot contain null elements");
            }
            product = product.multiply(rateOfReturn.get().add(BigDecimal.ONE));
        }
        return Math.pow(product.doubleValue(), 1 / (double) ratesOfReturn.size()) - 1;
    }
}
