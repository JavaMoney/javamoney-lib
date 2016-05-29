/*
 * Copyright (c) 2012, 2013, Credit Suisse (Anatole Tresch), Werner Keil. Licensed under the Apache
 * License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.javamoney.calc.common;

import com.ibm.icu.math.BigDecimal;

import java.util.Objects;

import javax.money.MonetaryAmount;
import javax.money.MonetaryOperator;


/**
 * <p>
 * <img
 * src="http://www.financeformulas.net/Formula%20Images/PV%20of%20Growing%20Perpetuity%201.gif"/> <br/>
 * The present value of a growing perpetuity formula is the cash flow after the first period divided
 * by the difference between the discount rate and the growth rate.
 * <p>
 * A growing perpetuity is a series of periodic payments that grow at a proportionate rate and are
 * received for an infinite amount of time. An example of when the present value of a growing
 * perpetuity formula may be used is commercial real estate. The rental cash flows could be
 * considered indefinite and will grow over time.
 * <p>
 * It is important to note that the discount rate must be higher than the growth rate when using the
 * present value of a growing perpetuity formula. This is due to the present value of a growing
 * perpetuity formula being an infinite geometric series as explained in one of the following
 * sections. In theory, if the growth rate is higher than the discount rate, the growing perpetuity
 * would have an infinite value.
 * 
 * @see http://www.financeformulas.net/Present_Value_of_Growing_Perpetuity.html
 * @author Anatole
 * @author Werner
 * 
 */
public final class PresentValueGrowingAnnuity implements MonetaryOperator {

    private final Rate discountRate;
    private final Rate growthRate;

    /**
     * Constructor.
     *
     * @param discountRate The discount rate, not null.
     * @param growthRate   The growth rate, not null.
     */
    private PresentValueGrowingAnnuity(Rate discountRate, Rate growthRate) {
        this.discountRate = Objects.requireNonNull(discountRate);
        this.growthRate = Objects.requireNonNull(growthRate);
    }

    /**
     * Access a MonetaryOperator for calculation.
     *
     * @param discountRate The discount rate, not null.
     * @param growthRate   The growth rate, not null.
     * @return the operator, never null.
     */
    public static PresentValueGrowingAnnuity of(Rate discountRate, Rate growthRate) {
        return new PresentValueGrowingAnnuity(discountRate, growthRate);
    }

    /**
     * Performs the calculation.
     *
     * @param dividend     the dividend payment
     * @param discountRate The discount rate, not null.
     * @param growthRate   The growth rate, not null.
     * @return the resulting amount, never null.
     */
    public static MonetaryAmount calculate(MonetaryAmount dividend, Rate discountRate, Rate growthRate) {
        Objects.requireNonNull(dividend, "dividend required");
        Objects.requireNonNull(discountRate, "discountRate required");
        Objects.requireNonNull(growthRate, "growthRate required");
        return dividend.divide(discountRate.get().subtract(growthRate.get()));
    }


    @Override
    public MonetaryAmount apply(MonetaryAmount dividend) {
        return calculate(dividend, discountRate, growthRate);
    }

    @Override
    public String toString() {
        return "PresentValueGrowingAnnuity{" +
                "discountRate=" + discountRate +
                ", growthRate=" + growthRate +
                '}';
    }
}
