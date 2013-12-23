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

import java.math.BigDecimal;

import javax.money.MonetaryAmount;
import javax.money.MonetaryOperator;

/**
 * A rate is simply a multiplicand that is sued as a constant, e.g. a calculalatory interest rate.
 * When combined with a {@link MonetaryAmount} the according abosulte rate related to that amount is
 * returned, e.g. a 10 % interest rate is modeled as {@code 0.1}, whereas the absolute value given
 * an amount of {@code USD 100} will be {@code USD 10}.
 * 
 * @author Anatole Tresch
 * 
 */
public final class Rate implements MonetaryOperator { // ,Supplier<BigDecimal> for Java 8/9
	/** The rate factor. */
	private BigDecimal rate;

	/**
	 * Creates a new rate instance.
	 * 
	 * @param rate
	 *            the rate, not {@code null}.
	 */
	public Rate(BigDecimal rate) {
		if (rate == null) {
			throw new IllegalArgumentException();
		}
		this.rate = rate;
	}

	/**
	 * Creates a new rate instance.
	 * 
	 * @param rate
	 *            the rate, not {@code null}.
	 */
	public Rate(Number rate) {
		if (rate == null) {
			throw new IllegalArgumentException();
		}
		this.rate = BigDecimal.valueOf(rate.doubleValue());
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((rate == null) ? 0 : rate.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rate other = (Rate) obj;
		if (rate == null) {
			if (other.rate != null)
				return false;
		} else if (!rate.equals(other.rate))
			return false;
		return true;
	}

	/**
	 * Access the rate, never {@code null}.
	 * 
	 * @return
	 */
	public BigDecimal get() {
		return this.rate;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Rate[" + rate + "]";
	}

	/*
	 * (non-Javadoc)
	 * @see javax.money.MonetaryOperator#apply(javax.money.MonetaryAmount)
	 */
	@Override
	public <T extends MonetaryAmount> T apply(T amount) {
		return (T) amount.multiply(rate);
	}

}
