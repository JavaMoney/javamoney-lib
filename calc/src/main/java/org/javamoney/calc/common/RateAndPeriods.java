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
 * Contributors: @atsticks
 */
package org.javamoney.calc.common;

import java.util.Objects;

/**
 * Data container class that summarizes a calculatoric rate and the number of periods the rate should be applied.
 */
public class RateAndPeriods {

    /**
     * the target rate, not null.
     */
    private final Rate rate;
    /**
     * the periods, >= 0.
     */
    private final int periods;

    /**
     * Private constructor.
     * @param rate the rate, not null.
     * @param periods the periods, >= 0.
     */
    private RateAndPeriods(Rate rate, int periods) {
        if(periods<0){
            throw new IllegalArgumentException("Periods must be >= 0");
        }
        this.periods = periods;
        this.rate = Objects.requireNonNull(rate);
    }

    /**
     * Get a new instance.
     * @param rate the target rate, not null.
     * @param periods the periods, >= 0.
     * @return a new instance.
     */
    public static RateAndPeriods of(Rate rate, int periods) {
        return new RateAndPeriods(rate, periods);
    }

    /**
     * Get a new instance.
     * @param rate the target rate, not null.
     * @param periods the periods, >= 0.
     * @return a new instance.
     */
    public static RateAndPeriods of(double rate, int periods) {
        return new RateAndPeriods(Rate.of(rate), periods);
    }

    /**
     * Get the current target rate.
     * @return the target rate, never null.
     */
    public Rate getRate() {
        return rate;
    }

    /**
     * The target periods, >= 0.
     * @return the target periods.
     */
    public int getPeriods() {
        return periods;
    }

    @Override
    public String toString() {
        return "RateAndPeriods{\n" +
                "  rate=" + rate +
                "\n  periods=" + periods +
                "}";
    }

}
