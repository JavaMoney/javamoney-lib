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
 * Contributors: @manuela-grindei
 */
package org.javamoney.calc.securities;

import javax.money.MonetaryAmount;
import javax.money.MonetaryOperator;

import org.javamoney.calc.common.Rate;

/**
 * <img src="http://www.financeformulas.net/formulaimages/PV%20of%20Stock%20-%20Constant%20Growth%201.gif" />
 * <img src="http://www.financeformulas.net/formulaimages/PV%20of%20Stock%20-%20Zero%20Growth%201.gif" />
 * <p> The formula for the present value of a stock with constant growth is the estimated dividends to be paid divided by the difference between the required rate of return and the growth rate.
 * <p> The formula for the present value of a stock with zero growth is dividends per period divided by the required return per period. The present value of stock formulas are not to be considered an exact or guaranteed approach to valuing a stock but is a more theoretical approach.
 *
 * @author Manuela Grindei
 * @link http://www.financeformulas.net/Present-Value-of-Stock-with-Constant-Growth.html
 * @link http://www.financeformulas.net/Present-Value-of-Stock-with-Zero-Growth.html
 */
public class StockPresentValue implements MonetaryOperator {

	
	private Rate requiredRateOfReturn;
	
	private Rate growthRate;
	
    /**
     * Private constructor.
     */
	private StockPresentValue(Rate requiredRateOfReturn, Rate growthRate) {
		this.requiredRateOfReturn = requiredRateOfReturn;
		this.growthRate = growthRate;
    }

	public Rate getRequiredRateOfReturn() {
		return requiredRateOfReturn;
	}
	
	public Rate getGrowthRate() {
		return growthRate;
	}
	
	/**
	 * Access a MonetaryOperator for calculation.
	 *
	 * @param requiredRateOfReturn the required rate of return
	 * @param growthRate the growth rate
	 * @return the operator
	 */
	public static StockPresentValue of(Rate requiredRateOfReturn, Rate growthRate) {
		return new StockPresentValue(requiredRateOfReturn, growthRate);
	}
	
    /**
     * Calculates the present value of a stock for constant growth.
     *
     * @param estimatedDividends   the estimated dividends for next period
     * @param requiredRateOfReturn the required rate of return
     * @param the                  growth rate
     * @return the present value of the stock
     */
    public static MonetaryAmount calculateForConstantGrowth(MonetaryAmount estimatedDividends, Rate requiredRateOfReturn, Rate growthRate) {
        return estimatedDividends.divide(requiredRateOfReturn.get().subtract(growthRate.get()));
    }

    /**
     * Calculates the present value of a stock for constant growth.
     *
     * @param estimatedDividends   the estimated dividends for next period
     * @param requiredRateOfReturn the required rate of return
     * @return the present value of the stock
     */
    public static MonetaryAmount calculateForZeroGrowth(MonetaryAmount estimatedDividends, Rate requiredRateOfReturn) {
        return calculateForConstantGrowth(estimatedDividends, requiredRateOfReturn, Rate.ZERO);
	}
	
	@Override
	public MonetaryAmount apply(MonetaryAmount estimatedDividends) {
		return calculateForConstantGrowth(estimatedDividends, requiredRateOfReturn, growthRate);
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		
		StockPresentValue that = (StockPresentValue) o;
		
		return requiredRateOfReturn.equals(that.requiredRateOfReturn) && growthRate.equals(that.growthRate);
		
	}
	
	@Override
	public int hashCode() {
		int result = requiredRateOfReturn.hashCode();
		result = 31 * result + growthRate.hashCode();
		return result;
	}
	
	@Override
	public String toString() {
		return "StockPresentValue{" + "requiredRateOfReturn=" + requiredRateOfReturn + ", growthRate=" + growthRate + '}';
    }
}
