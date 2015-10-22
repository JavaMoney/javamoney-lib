package org.javamoney.moneta.internal.convert.yahoo;

import javax.money.convert.ExchangeRateProviderSupplier;

public enum YahooExchangeRateType implements ExchangeRateProviderSupplier {

	YAHOO("YAHOO", "Exchange rate to the Yahoo finance.");

	private final String type;

	private final String description;

	YahooExchangeRateType(String type, String description) {
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
