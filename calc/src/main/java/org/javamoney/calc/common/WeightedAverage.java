/*
 *  Copyright (c) 2012, 2013, Trivadis (Anatole Tresch), Werner Keil.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.javamoney.calc.common;

import org.javamoney.calc.CalculationContext;
import org.javamoney.calc.ComplexCalculation;
import org.javamoney.calc.ComplexType;
import org.javamoney.calc.ComplexValue;
import org.javamoney.moneta.Money;

import javax.money.MonetaryAmount;
import javax.money.MonetaryOperator;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.NumberFormat;
import java.util.*;


/**
 * This class allows to extract the Weighted Average of a collection of {@link MonetaryAmount} or
 * number instances.
 *
 *
 * The concept of weighted average is used in various financial formulas. Weighted average cost of
 * capital (WACC) and weighted average beta are two examples that use this formula.
 *
 * Another example of using the weighted average formula is when a company has a wide fluctuation
 * in sales, perhaps due to producing a seasonal product. If the company would like to calculate the
 * average of one of their variable expenses, the company could use the weighted average formula with
 * sales as the weight to gain a better understanding of their expenses compared to how much they
 * produce or sell.
 *
 * This class does - other as the example referenced above - not require that all weights sum up to {@code 1.0}.
 * Instead of it calculates the overall sum of weights and uses this as the overall reference value
 * to be used for determining the effective weights given.
 *
 * @author Anatole Tresch
 *
 * @see <a href="http://www.financeformulas.net/Weighted_Average.html#Calc-Header">
 *     http://www.financeformulas.net/Weighted_Average.html#Calc-Header</a>
 */
public final class WeightedAverage {

	/**
	 * A weighted value is a value also with a weight attached.
	 */
	public static final class WeightedValue{
		private BigDecimal value;
		private BigDecimal weight;
		private WeightedValue(BigDecimal value, BigDecimal weight){
			this.value = Objects.requireNonNull(value);
			this.weight = Objects.requireNonNull(weight);
		}

		/**
		 * Access the value.
		 * @return the value, not null.
		 */
		public BigDecimal getValue() {
			return value;
		}

		/**
		 * Access the weight.
		 * @return the weight, not null.
         */
		public BigDecimal getWeight() {
			return weight;
		}

		@Override
		public String toString() {
			return "WeightedValue{" +
					"value=" + value +
					", weight=" + weight +
					'}';
		}
	}

	private WeightedAverage(Collection<WeightedValue> values){
		this.values.addAll(values);
	}

	/**
	 * Allows to create a weighted value.
	 * @param value the value, not null.
	 * @param weight the weight, not null
     * @return the new instance, never null.
     */
	public static WeightedValue ofWeightedValue(BigDecimal value, BigDecimal weight){
		return new WeightedValue(value, weight);
	}

	/**
	 * List of values to be used to calculate the overal weighted average.
	 */
	private final List<WeightedValue> values = new ArrayList<>();

	/**
	 * Creates a new builder instance.
	 * @return a new builder, never null.
     */
	public static Builder newBuilder() {
		return new Builder();
	}

	/**
	 * Access the weighted values of thgis calculation.
	 * @return the weighted values
     */
	public Collection<WeightedValue> getValues() {
		return Collections.unmodifiableCollection(values);
	}

	/**
	 * Get the weighted average for the current weigted values.
	 * @return the weighted average, not null.
     */
	public BigDecimal calculateWeightedAverage(){
		return calculateWeightedAverage(this.values);
	}

	/**
	 * Get the weighted average for the current weigted values.
	 * @return the weighted average, not null.
	 */
	public static BigDecimal calculateWeightedAverage(Collection<WeightedValue> values){
		BigDecimal totalWeight = new BigDecimal(0, MathContext.DECIMAL64);
		for(WeightedValue val:values){
			totalWeight = totalWeight.add(val.getWeight(), CalculationContext.mathContext());
		}
		BigDecimal total = CalculationContext.zero();
		for(WeightedValue val:values){
			BigDecimal weight = val.getWeight().divide(totalWeight, CalculationContext.mathContext());
			total = total.add(val.getValue().multiply(weight, CalculationContext.mathContext()));
		}
		return total;
	}

	public static final class Builder{
		/**
		 * List of values to be used to calculate the overal weighted average.
		 */
		private final List<WeightedValue> values = new ArrayList<>();

		private Builder(){}

		public Builder add(WeightedValue val){
			this.values.add(val);
			return this;
		}
		public Builder add(BigDecimal value, BigDecimal weight){
			this.values.add(ofWeightedValue(value, weight));
			return this;
		}
		public Builder add(Number value, Number weight){
			this.values.add(ofWeightedValue(
					new BigDecimal(value.toString()), new BigDecimal(weight.toString())));
			return this;
		}

		public BigDecimal calculate(){
			return WeightedAverage.calculateWeightedAverage(this.values);
		}

		public WeightedAverage build(){
			return new WeightedAverage(this.values);
		}

		@Override
		public String toString() {
			return "WeightedAverage.Builder{" +
					"values=" + values +
					'}';
		}
	}
}
