/*
 * Copyright (c) 2012, 2013, Werner Keil, Credit Suisse (Anatole Tresch).
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
 * 
 * Contributors: Anatole Tresch - initial version.
 */
package org.javamoney.cdi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.RoundingMode;
import java.text.ParseException;
import java.util.Locale;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.MonetaryAmounts;
import javax.money.MonetaryContext;
import javax.money.MonetaryCurrencies;
import javax.money.MonetaryOperator;
import javax.money.MonetaryRoundings;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;

import org.javamoney.convert.ConversionProvider;
import org.javamoney.convert.ExchangeRate;
import org.javamoney.convert.ExchangeRateType;
import org.javamoney.convert.MonetaryConversions;
import org.javamoney.moneta.Money;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SmokeTest {
	private static final Logger logger = LoggerFactory
			.getLogger(SmokeTest.class);

	private static final ExchangeRateType RATE_TYPE = ExchangeRateType
			.of("EZB");

	@Test
	public void testCreateAmounts() {
		// Creating one
		CurrencyUnit currency = MonetaryCurrencies.getCurrency("CHF");
		Money amount1 = Money.of(currency, 1.0d);
		Money amount2 = Money.of(currency, 1.0d);
		Money amount3 = amount1.add(amount2);
		logger.debug(amount1 + " + " + amount2 + " = " + amount3);
		assertEquals(1.0d, amount1.getNumber().doubleValue(), 0);
		assertEquals(1.0d, amount2.getNumber().doubleValue(), 0);
		assertEquals(2.0d, amount3.getNumber().doubleValue(), 0);
	}

	@Test
	public void testCreateMoney() {
		// Creating one
		Money amount1 = Money.of("CHF", 1.0d);
		Money amount2 = Money.of("CHF", 1.0d);
		Money amount3 = amount1.add(amount2);
		logger.debug(amount1 + " + " + amount2 + " = " + amount3);
		assertEquals(1.0d, amount1.getNumber().doubleValue(), 0);
		assertEquals(1.0d, amount2.getNumber().doubleValue(), 0);
		assertEquals(2.0d, amount3.getNumber().doubleValue(), 0);
	}

	@Test
	public void testExchange() {
		ConversionProvider prov = MonetaryConversions
				.getConversionProvider(RATE_TYPE);
		assertNotNull(prov);
		ExchangeRate rate1 = prov.getExchangeRate(
				MonetaryCurrencies.getCurrency("CHF"),
				MonetaryCurrencies.getCurrency("EUR"));
		ExchangeRate rate2 = prov.getExchangeRate(
				MonetaryCurrencies.getCurrency("EUR"),
				MonetaryCurrencies.getCurrency("CHF"));
		ExchangeRate rate3 = prov.getExchangeRate(
				MonetaryCurrencies.getCurrency("CHF"),
				MonetaryCurrencies.getCurrency("USD"));
		ExchangeRate rate4 = prov.getExchangeRate(
				MonetaryCurrencies.getCurrency("USD"),
				MonetaryCurrencies.getCurrency("CHF"));
		System.out.println(rate1);
		System.out.println(rate2);
		System.out.println(rate3);
		System.out.println(rate4);
	}

	@Test
	public void testCurrencyConverter() {
		MonetaryOperator rounding = MonetaryRoundings
				.getRounding(new MonetaryContext.Builder().setMaxScale(2)
						.setAttribute(RoundingMode.HALF_UP).build());

		MonetaryAmount srcCHF = Money.of(MonetaryCurrencies.getCurrency("CHF"),
				100.15);
		MonetaryAmount srcUSD = Money.of(MonetaryCurrencies.getCurrency("USD"),
				100.15);
		MonetaryAmount srcEUR = Money.of(MonetaryCurrencies.getCurrency("EUR"),
				100.15);

		MonetaryAmount tgt = MonetaryConversions
				.getConversionProvider(RATE_TYPE).getConverter()
				.convert(srcCHF, MonetaryCurrencies.getCurrency("EUR"));
		MonetaryAmount tgt3 = MonetaryConversions
				.getConversionProvider(RATE_TYPE).getConverter()
				.convert(tgt, MonetaryCurrencies.getCurrency("CHF"));
		assertEquals(srcCHF.with(rounding), tgt3.with(rounding));
		tgt = MonetaryConversions.getConversionProvider(RATE_TYPE)
				.getConverter()
				.convert(srcEUR, MonetaryCurrencies.getCurrency("CHF"));
		tgt3 = MonetaryConversions.getConversionProvider(RATE_TYPE)
				.getConverter()
				.convert(tgt, MonetaryCurrencies.getCurrency("EUR"));
		assertEquals(srcEUR.with(rounding), rounding.apply(tgt3));
		tgt = MonetaryConversions.getConversionProvider(RATE_TYPE)
				.getConverter()
				.convert(srcCHF, MonetaryCurrencies.getCurrency("USD"));
		tgt3 = MonetaryConversions.getConversionProvider(RATE_TYPE)
				.getConverter()
				.convert(tgt, MonetaryCurrencies.getCurrency("CHF"));
		assertEquals(srcCHF, tgt3);
		assertEquals(srcCHF.with(rounding), rounding.apply(tgt3));
	}

	@Test
	public void testAmountFormatRoundTrip() throws ParseException {
		// Using parsers
		MonetaryAmountFormat format = MonetaryFormats
				.getAmountFormat(Locale.GERMANY);
		assertNotNull(format);
		MonetaryAmount amount = MonetaryAmounts.getAmountFactory()
				.setCurrency("CHF").setNumber(10.50).create();
		String formatted = format.format(amount);
		assertNotNull(formatted);
		MonetaryAmount parsed = format.parse(formatted);
		assertNotNull(parsed);
		assertEquals(amount, parsed);
	}

	@Test
	public void testCurrencyAccess() {
		// Creating one
		CurrencyUnit currency = MonetaryCurrencies.getCurrency("INR");
		assertNotNull(currency);
	}
}
