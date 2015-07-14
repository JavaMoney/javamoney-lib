package org.javamoney.moneta.internal.convert.yahoo;


import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;

import javax.money.CurrencyUnit;
import javax.money.convert.ConversionContextBuilder;
import javax.money.convert.ExchangeRate;
import javax.money.convert.ProviderContext;
import javax.money.convert.RateType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.javamoney.moneta.CurrencyUnitBuilder;
import org.javamoney.moneta.ExchangeRateBuilder;
import org.javamoney.moneta.spi.DefaultNumberValue;

class YahooRateReadingHandler {

    private final String YAHOO_CURRENCY = "price";
    private final String YAHOO_CURRENCY_PAIR = "name";
    private final String YAHOO_CURRENCY_UPDATE_TIME = "utctime";
    private final String YAHOO_DTO_PACKAGE = "org.javamoney.moneta.internal.convert.yahoo";
    private final String UTC_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    private final String CST_ZONE = "America/Chicago";
    private final String USD_CURRENCY = "USD";


	private final Map<LocalDate, Map<String, ExchangeRate>> excangeRates;

	private final ProviderContext context;

	public YahooRateReadingHandler(final Map<LocalDate, Map<String, ExchangeRate>> excangeRates,
			final ProviderContext context) {
		this.excangeRates = excangeRates;
		this.context = context;
	}

	void parse(final InputStream stream) throws JAXBException, ParseException {
		final Unmarshaller unmarshaller = JAXBContext.newInstance(YAHOO_DTO_PACKAGE).createUnmarshaller();
		final YahooRoot root = (YahooRoot) unmarshaller.unmarshal(stream);
		final YahooCurrencies currencies = root.getResources();

		final DateFormat utcFormatter = new SimpleDateFormat(UTC_DATE_FORMAT);
		utcFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));

		for (YahooQuoteItem quote : currencies.getResource()) {
			int position = quote.getField().indexOf(new YahooField(YAHOO_CURRENCY_PAIR));
			String currencyName = null;
			CurrencyUnit tgtCurrency = null;
			currencyName = quote.getField().get(position).getValue();

			if(currencyName.equals(USD_CURRENCY)){
				continue;
			}

			//NOTE Yahoo responds with <SILVER 1 OZ 999 NY>, <ADIUM 1 OZ> and other similar items which are not currencies
			//NOTE Yahoo responds with <ECS> - ECUADORIAN_SUCRE which does not exist anymore
			//NOTE Yahoo responds with <CNH> - Chinese Yuan when trading offshore

			if (currencyName.length() > 7) {
				tgtCurrency = CurrencyUnitBuilder.of(currencyName, YahooRateProvider.PROVIDER).build();
			} else {
				//one currency with ending SPACE symbol was explored
				tgtCurrency = CurrencyUnitBuilder.of(currencyName.substring(4).trim(), YahooRateProvider.PROVIDER).build();
			}

			position = quote.getField().indexOf(new YahooField(YAHOO_CURRENCY_UPDATE_TIME));
			final LocalDate currencyLastUpdateTime = LocalDateTime.ofInstant(
					utcFormatter.parse(quote.getField().get(position).getValue()).toInstant(),
					ZoneId.of(CST_ZONE)).toLocalDate();//Central American Time

			position = quote.getField().indexOf(new YahooField(YAHOO_CURRENCY));
			final YahooField field = quote.getField().get(position);
			addRate(tgtCurrency, currencyLastUpdateTime,
					BigDecimal.valueOf(Double.parseDouble(field.getValue())));
		}
	}

   private void addRate(final CurrencyUnit term, final LocalDate localDate, final Number rate) {

        final ExchangeRateBuilder builder = new ExchangeRateBuilder(
        		ConversionContextBuilder.create(context, RateType.DEFERRED).build());
        builder.setBase(YahooAbstractRateProvider.BASE_CURRENCY);
        builder.setTerm(term);
        builder.setFactor(DefaultNumberValue.of(rate));
        final ExchangeRate exchangeRate = builder.build();

        Map<String, ExchangeRate> rateMap = this.excangeRates.get(localDate);
        if (Objects.isNull(rateMap)) {
            synchronized (this.excangeRates) {
                rateMap = Optional.ofNullable(this.excangeRates.get(localDate)).orElse(new ConcurrentHashMap<>());
                this.excangeRates.putIfAbsent(localDate, rateMap);
            }
        }
        rateMap.put(term.getCurrencyCode(), exchangeRate);

    }
}