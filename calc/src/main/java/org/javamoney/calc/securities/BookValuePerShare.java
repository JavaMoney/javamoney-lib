package org.javamoney.calc.securities;


import javax.money.MonetaryAmount;

/**
 * <img src=http://www.financeformulas.net/formulaimages/Book%20Value%20per%20Share%201.gif />
 * <p>
 * The book value per share formula is used to calculate the per share value of a company based on its equity available to common shareholders. The term
 * "book value" is a company's assets minus its liabilities and is sometimes referred to as stockholder's equity, owner's equity, shareholder's equity, or
 * simply equity. Common stockholder's equity, or owner's equity, can be found on the balance sheet for the company. In the absence of preferred shares, the
 * total stockholder's equity is used.
 *
 * @author Manuela Grindei
 * @see http://www.financeformulas.net/Book-Value-per-Share.html
 */
public class BookValuePerShare {

    /**
     * Private constructor.
     */
    private BookValuePerShare() {
    }

    /**
     * Calculates the per share value of a company based on its equity.
     *
     * @param equity               the total common stockholder's equity
     * @param numberOfCommonShares the number of common shares
     * @return the book value per share
     */
    public static MonetaryAmount calculate(MonetaryAmount equity, int numberOfCommonShares) {
        return equity.divide(numberOfCommonShares);
    }
}
