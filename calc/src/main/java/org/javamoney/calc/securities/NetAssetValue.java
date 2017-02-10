package org.javamoney.calc.securities;


import javax.money.MonetaryAmount;

/**
 * <img src="http://www.financeformulas.net/formulaimages/Net%20Asset%20Value%201.gif" />
 * <p> The net asset value formula is used to calculate a mutual fund's value per share. A mutual fund is a pool of investments that are divided
 * into shares to be purchased by investors. Each share contains a weighted portion of each investment in the collective pool. The premise of
 * grouping in this manner is to minimize risk by diversifying.
 *
 * @author Manuela Grindei
 * @see http://www.financeformulas.net/Net_Asset_Value.html
 */
public class NetAssetValue {

    /**
     * Private constructor.
     */
    private NetAssetValue() {
    }

    /**
     * Calculates the net asset value.
     *
     * @param fundAssets        the assets of the fund
     * @param fundLiabilities   the liabilities of the fund
     * @param outstandingShares the outstanding shares
     * @return the net asset value
     */
    public static MonetaryAmount calculate(MonetaryAmount fundAssets, MonetaryAmount fundLiabilities, double outstandingShares) {
        return fundAssets.subtract(fundLiabilities).divide(outstandingShares);
    }
}
