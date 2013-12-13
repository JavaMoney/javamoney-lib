package org.javamoney.util;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import javax.money.CurrencyUnit;
import javax.money.spi.CurrencyProviderSpi;

public class ConfigurableCurrencyUnitProvider implements CurrencyProviderSpi {

	private static Map<String, CurrencyUnit> currencyUnits = new ConcurrentHashMap<>();
	private static Map<Locale, CurrencyUnit> currencyUnitsByLocale = new ConcurrentHashMap<>();

	@Override
	public CurrencyUnit getCurrencyUnit(String currencyCode) {
		return currencyUnits.get(currencyCode);
	}

	@Override
	public CurrencyUnit getCurrencyUnit(Locale locale) {
		return currencyUnitsByLocale.get(locale);
	}

	public static CurrencyUnit registerCurrencyUnit(CurrencyUnit currencyUnit) {
		Objects.requireNonNull(currencyUnit);
		return ConfigurableCurrencyUnitProvider.currencyUnits.put(
				currencyUnit.getCurrencyCode(),
				currencyUnit);
	}

	public static CurrencyUnit registerCurrencyUnit(CurrencyUnit currencyUnit,
			Locale locale) {
		Objects.requireNonNull(locale);
		Objects.requireNonNull(currencyUnit);
		return ConfigurableCurrencyUnitProvider.currencyUnitsByLocale.put(
				locale, currencyUnit);
	}

	public static CurrencyUnit removeCurrencyUnit(String currencyCode) {
		Objects.requireNonNull(currencyCode);
		return ConfigurableCurrencyUnitProvider.currencyUnits
				.remove(currencyCode);
	}

	public static CurrencyUnit removeCurrencyUnit(Locale locale) {
		Objects.requireNonNull(locale);
		return ConfigurableCurrencyUnitProvider.currencyUnitsByLocale
				.remove(locale);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ConfigurableCurrencyUnitProvider [currencyUnits="
				+ currencyUnits + ", currencyUnitsByLocale="
				+ currencyUnitsByLocale + "]";
	}

}
