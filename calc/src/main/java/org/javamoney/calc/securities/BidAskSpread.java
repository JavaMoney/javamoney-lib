package org.javamoney.calc.securities;

import javax.money.MonetaryAmount;

/**
 * <img src= "http://www.financeformulas.net/formulaimages/Bid%20Ask%20Spread%201.gif" />
 * <p>
 * The bid ask spread formula is the difference between the asking price and bid price of a particular investment.
 * The bid ask spread may be used for various investments and is primarily used in investments that sell on an exchange.
 *
 * @author Manuela Grindei
 * @see http://www.financeformulas.net/Bid-Ask-Spread.html
 */
public final class BidAskSpread {

    /**
     * Private constructor.
     */
    private BidAskSpread() {
    }

    /**
     * Calculates the bid-ask spread.
     *
     * @param askPrice the asking price of the investment
     * @param bidPrice the bid price of the investment
     * @return the bid-ask spread.
     */
    public static MonetaryAmount calculate(MonetaryAmount askPrice, MonetaryAmount bidPrice) {
        return askPrice.subtract(bidPrice);
    }
}
