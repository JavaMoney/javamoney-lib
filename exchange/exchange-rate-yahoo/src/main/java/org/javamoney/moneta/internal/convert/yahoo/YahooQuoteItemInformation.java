package org.javamoney.moneta.internal.convert.yahoo;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.TimeZone;

import javax.money.CurrencyUnit;
import javax.money.Monetary;

class YahooQuoteItemInformation {

	private static final String PRICE_KEY = "price";
	private static final String UPDATE_TIME_KEY = "utctime";
	private static final String NAME_KEY = "name";
	private static final String CST_ZONE = "America/Chicago";
	private static final String UTC_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

	private static final YahooField YAHOO_FIELD_PRICE = new YahooField(PRICE_KEY);
	private static final YahooField YAHOO_FIELD_NAME = new YahooField(NAME_KEY);
	private static final YahooField YAHOO_FIELD_UPDATE_TIME = new YahooField(UPDATE_TIME_KEY);


	private final CurrencyUnit currency;

	private final LocalDate localDate;

	private final BigDecimal value;

	private YahooQuoteItemInformation(CurrencyUnit currency, LocalDate localDate,
			BigDecimal value) {
		this.currency = currency;
		this.localDate = localDate;
		this.value = value;
	}

	public CurrencyUnit getCurrency() {
		return currency;
	}

	public boolean isCurrencyValid() {
		return currency != null;
	}

	public LocalDate getLocalDate() {
		return localDate;
	}

	public BigDecimal getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "YahooQuoteItemInformation [currency=" + currency
				+ ", localDate=" + localDate + ", value=" + value + "]";
	}

	public static YahooQuoteItemInformation of(List<YahooField> fields) throws ParseException{
		CurrencyUnit currency;
		LocalDate localDate;
		BigDecimal value;

		int positionName = fields.indexOf(YAHOO_FIELD_NAME);
		int positionPrice = fields.indexOf(YAHOO_FIELD_PRICE);
		int positionUctime = fields.indexOf(YAHOO_FIELD_UPDATE_TIME);
		String currencyName = fields.get(positionName).getValue().trim();
		String price = fields.get(positionPrice).getValue();
		String time = fields.get(positionUctime).getValue();

		if(currencyName.length() == 7) {
			currency = Monetary.getCurrency(currencyName.substring(4).trim());
		} else {
			currency = null;
		}
		final DateFormat utcFormatter = new SimpleDateFormat(UTC_DATE_FORMAT);
		utcFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		localDate =  LocalDateTime.ofInstant(
				utcFormatter.parse(time).toInstant(),
				ZoneId.of(CST_ZONE)).toLocalDate();

		value = BigDecimal.valueOf(Double.parseDouble(price));
		return new YahooQuoteItemInformation(currency, localDate, value);
	}

}
