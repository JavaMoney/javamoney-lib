/*
 * Copyright (c) 2012, 2018, Werner Keil, Anatole Tresch and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 * Contributors: @atsticks - initial version.
 */
package org.javamoney.calc.common;

import javax.money.MonetaryOperator;
import java.util.Objects;

/**
 * Base class for a {@link MonetaryOperator} taking a calculatoric rate and period number.
 *
 * @author Anatole Tresch
 */
public abstract class AbstractRateAndPeriodBasedOperator implements MonetaryOperator {

    /**
     * The target rate and periods, not null.
     */
    protected final RateAndPeriods rateAndPeriods;

    /**
     * Constructor.
     *
     * @param rateAndPeriods    the target rate and periods, not null.
     */
    protected AbstractRateAndPeriodBasedOperator(RateAndPeriods rateAndPeriods) {
        this.rateAndPeriods = Objects.requireNonNull(rateAndPeriods);
    }

    /**
     * Get the rate and periods.
     * @return the rate and periods, never null.
     */
    public RateAndPeriods getRateAndPeriods() {
        return rateAndPeriods;
    }

    /**
     * Get the current rate.
     * @return the current rate, never null.
     */
    public Rate getRate(){
        return rateAndPeriods.getRate();
    }

    /**
     * Get the current periods, never null.
     * @return the current periods, >= 0.
     */
    public int getPeriods(){
        return rateAndPeriods.getPeriods();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(getClass().equals(o.getClass()))) return false;

        AbstractRateAndPeriodBasedOperator that = (AbstractRateAndPeriodBasedOperator) o;

        return rateAndPeriods.equals(that.rateAndPeriods);
    }

    @Override
    public int hashCode() {
        return rateAndPeriods.hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "\n " + rateAndPeriods +
                '}';
    }

}
