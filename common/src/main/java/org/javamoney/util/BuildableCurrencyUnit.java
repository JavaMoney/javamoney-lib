package org.javamoney.util;

import java.util.Locale;
import java.util.Objects;

import javax.money.CurrencyUnit;

public final class BuildableCurrencyUnit implements CurrencyUnit, Comparable<CurrencyUnit> {

	private String currencyCode;
	private int numericCode;
	private int defaultFractionDigits;

	public BuildableCurrencyUnit(String currencyCode, int numericCode,
			int defaultFractionDigits) {
		Objects.requireNonNull(currencyCode, "currencyCode required");
		if (numericCode < -1) {
			throw new IllegalArgumentException("numericCode must be >= -1");
		}
		if (defaultFractionDigits < 0) {
			throw new IllegalArgumentException(
					"defaultFractionDigits must be >= 0");
		}
		this.defaultFractionDigits = defaultFractionDigits;
		this.numericCode = numericCode;
		this.currencyCode = currencyCode;
	}

	@Override
	public String getCurrencyCode() {
		return currencyCode;
	}

	@Override
	public int getNumericCode() {
		return numericCode;
	}

	@Override
	public int getDefaultFractionDigits() {
		return defaultFractionDigits;
	}
	
	@Override
	public int compareTo(CurrencyUnit o) {
		return this.currencyCode.compareTo(o.getCurrencyCode());
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((currencyCode == null) ? 0 : currencyCode.hashCode());
		return result;
	}

	/* (non-Javadoc)
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
		BuildableCurrencyUnit other = (BuildableCurrencyUnit) obj;
		if (currencyCode == null) {
			if (other.currencyCode != null)
				return false;
		} else if (!currencyCode.equals(other.currencyCode))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BuildableCurrencyUnit [currencyCode=" + currencyCode
				+ ", numericCode=" + numericCode + ", defaultFractionDigits="
				+ defaultFractionDigits + "]";
	}



	public static final class Builder {
		private String currencyCode;
		private int numericCode = -1;
		private int defaultFractionDigits = 2;

		public Builder() {
		}

		public Builder(String currencyCode) {
			Objects.requireNonNull(currencyCode, "currencyCode required");
			this.currencyCode = currencyCode;
		}

		public Builder setCurrencyCode(String currencyCode) {
			Objects.requireNonNull(currencyCode, "currencyCode required");
			this.currencyCode = currencyCode;
			return this;
		}

		public Builder setNumericCode(int numericCode) {
			if (numericCode < -1) {
				throw new IllegalArgumentException("numericCode must be >= -1");
			}
			this.numericCode = numericCode;
			return this;
		}

		public Builder setDefaultFractionDigits(int defaultFractionDigits) {
			if (defaultFractionDigits < 0) {
				throw new IllegalArgumentException(
						"defaultFractionDigits must be >= 0");
			}
			this.defaultFractionDigits = defaultFractionDigits;
			return this;
		}

		public BuildableCurrencyUnit build() {
			return build(false);
		}
		
		public BuildableCurrencyUnit build(boolean register) {
			BuildableCurrencyUnit cu = new BuildableCurrencyUnit(currencyCode, numericCode,
					defaultFractionDigits);
			if(register){
				ConfigurableCurrencyUnitProvider.registerCurrencyUnit(cu);
			}
			return cu;
		}
		
		public BuildableCurrencyUnit build(boolean register, Locale locale) {
			BuildableCurrencyUnit cu = new BuildableCurrencyUnit(currencyCode, numericCode,
					defaultFractionDigits);
			if(register){
				ConfigurableCurrencyUnitProvider.registerCurrencyUnit(cu);
				ConfigurableCurrencyUnitProvider.registerCurrencyUnit(cu, locale);
			}
			return cu;
		}
	}

}
