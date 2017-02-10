package org.javamoney.calc.securities;


import javax.money.MonetaryAmount;
import java.math.BigDecimal;

/**
 * <img src="http://www.financeformulas.net/formulaimages/Diluted%20EPS%201.gif" />
 * <p>
 * Diluted earnings per share or Diluted EPS, is a firm's net income divided by the sum of its average shares and other convertible instruments.
 * <p>
 * A company's net income can be found on its income statement.
 * <p>
 * A company's average shares refers to the weighted average of common shares throughout the year. The weights of each factor would be the length of time
 * each quantity of common shares is outstanding.
 * <p>
 * The term "convertible instruments" refers to any financial instrument that could possibly be converted into a common shares. For reference, a few examples
 * of convertible instruments that may be considered in the diluted earnings per share formula are stock options and convertible preferred stocks, but there
 * are many others and anything than has the availability to be converted to a common share could be included.
 *
 * @author Manuela Grindei
 * @see http://www.financeformulas.net/Diluted-EPS.html
 */
public class DilutedEarningsPerShare {

    /**
     * Private constructor.
     */
    private DilutedEarningsPerShare() {
    }

    /**
     * Calculates the diluted earnings per share.
     *
     * @param netIncome                   the firm's net income
     * @param averageShares               the firm's average shares
     * @param otherConvertibleInstruments other convertible instruments
     * @return the diluted earnings per share
     */
    public static MonetaryAmount calculate(MonetaryAmount netIncome, BigDecimal averageShares, BigDecimal otherConvertibleInstruments) {
        return netIncome.divide(averageShares.add(otherConvertibleInstruments));
    }
}
