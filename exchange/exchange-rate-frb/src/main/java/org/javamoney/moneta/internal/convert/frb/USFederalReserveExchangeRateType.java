package org.javamoney.moneta.internal.convert.frb;

import javax.money.convert.ExchangeRateProviderSupplier;

public enum USFederalReserveExchangeRateType implements ExchangeRateProviderSupplier {

	FRB("FRB", "Exchange rate to the Federal Reserve Bank of the United States, providing the prior week's Monday-Friday data.");

	private final String type;

	private final String description;

	USFederalReserveExchangeRateType(String type, String description) {
		this.type = type;
		this.description = description;
	}

	@Override
	public String get() {
		return type;
	}

	public String getDescription() {
		return description;
	}
}
