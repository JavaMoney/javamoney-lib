package org.javamoney.calc.common;

import java.util.Objects;

public class RateAndPeriods {

    /**
     * the target rate, not null.
     */
    private final Rate rate;
    /**
     * the periods, >= 0.
     */
    private final int periods;

    private RateAndPeriods(Rate rate, int periods) {
        if(periods<0){
            throw new IllegalArgumentException("Periods must be >= 0");
        }
        this.periods = periods;
        this.rate = Objects.requireNonNull(rate);
    }

    public static RateAndPeriods of(Rate rate, int periods) {
        return new RateAndPeriods(rate, periods);
    }

    public static RateAndPeriods of(double rate, int periods) {
        return new RateAndPeriods(Rate.of(rate), periods);
    }

    public Rate getRate() {
        return rate;
    }

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

    public RatePeriodsBuilder toBuilder() {
        return builder().withRate(rate).withPeriods(periods);
    }

    public static RatePeriodsBuilder builder() {
        return new RatePeriodsBuilder();
    }

    public static class RatePeriodsBuilder {

        private Rate rate;
        private int periods;

        private RatePeriodsBuilder() {
        }

        public RatePeriodsBuilder withRate(Rate rate) {
            this.rate = Objects.requireNonNull(rate);
            return this;
        }

        public RatePeriodsBuilder withPeriods(int periods) {
            if(periods<0){
                throw new IllegalArgumentException("Periods must be >= 0");
            }
            this.periods = periods;
            return this;
        }

        public RateAndPeriods build() {
            return new RateAndPeriods(this.rate, this.periods);
        }
    }
}
