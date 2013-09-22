package net.java.javamoney.extras;

import java.math.BigDecimal;

import javax.money.MonetaryAmount;
import javax.money.MonetaryOperator;

public final class Rate implements MonetaryOperator {

	private BigDecimal rate;

	public Rate(BigDecimal rate) {
		if (rate == null) {
			throw new IllegalArgumentException();
		}
		this.rate = rate;
	}

	public Rate(Number rate) {
		if (rate == null) {
			throw new IllegalArgumentException();
		}
		this.rate = BigDecimal.valueOf(rate.doubleValue());
	}

	/*
	 * (non-Javadoc)
	 * 
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
	 * 
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

	public BigDecimal getRate() {
		return this.rate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Rate [rate=" + rate + "]";
	}

	@Override
	public MonetaryAmount apply(MonetaryAmount value) {
		return value.multiply(rate);
	}

}
