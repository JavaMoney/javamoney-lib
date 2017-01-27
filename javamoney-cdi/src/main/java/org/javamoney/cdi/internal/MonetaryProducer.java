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
package org.javamoney.cdi.internal;

import org.javamoney.cdi.api.AmountSpec;
import org.javamoney.cdi.api.CurrencySpec;
import org.javamoney.cdi.api.FormatSpec;
import org.javamoney.cdi.api.ConversionSpec;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Singleton;
import javax.money.*;
import javax.money.convert.*;
import javax.money.format.AmountFormatQuery;
import javax.money.format.AmountFormatQueryBuilder;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Producer bean that bridges to the javamoney API.
 */
@Singleton
public final class MonetaryProducer {

    private static final Logger LOG = Logger.getLogger(MonetaryProducer.class.getName());

    @Produces
    public CurrencyUnit currencyUnit(InjectionPoint ip){
        CurrencySpec specAnnot = ip.getAnnotated()!=null?ip.getAnnotated().getAnnotation(CurrencySpec.class):null;
        if(specAnnot==null){
            throw new IllegalArgumentException("@CurrencySpec is required.");
        }
        return Monetary.getCurrency(createCurrencyQuery(specAnnot));
    }


    @Produces
    public Collection<CurrencyUnit> currencyUnits(InjectionPoint ip){
        CurrencySpec specAnnot = ip.getAnnotated()!=null?ip.getAnnotated().getAnnotation(CurrencySpec.class):null;
        if(specAnnot!=null){
            return Monetary.getCurrencies(createCurrencyQuery(specAnnot));
        }
        return Monetary.getCurrencies();
    }


    @Produces
    public ExchangeRateProvider rateProvider(InjectionPoint ip){
        ConversionSpec specAnnot = ip.getAnnotated()!=null?ip.getAnnotated().getAnnotation(ConversionSpec.class):null;
        if(specAnnot!=null){
            return MonetaryConversions.getExchangeRateProvider(createRateQuery(specAnnot));
        }
        return MonetaryConversions.getExchangeRateProvider();
    }

    @Produces
    public Collection<ExchangeRateProvider> rateProviders(InjectionPoint ip){
        List<ExchangeRateProvider> providers = new ArrayList<>();
        ConversionSpec specAnnot = ip.getAnnotated()!=null?ip.getAnnotated().getAnnotation(ConversionSpec.class):null;
        if(specAnnot!=null && specAnnot.providers().length>0){
            for(String providerId:specAnnot.providers()){
                providers.add(MonetaryConversions.getExchangeRateProvider(providerId));
            }
        }else{
            for(String providerId:MonetaryConversions.getConversionProviderNames()){
                providers.add(MonetaryConversions.getExchangeRateProvider(providerId));
            }
        }
        return providers;
    }


    @Produces
    public CurrencyConversion currencyConversion(InjectionPoint ip){
        ConversionSpec specAnnot = ip.getAnnotated()!=null?ip.getAnnotated().getAnnotation(ConversionSpec.class):null;
        if(specAnnot==null){
            throw new IllegalArgumentException("@RateSpec is required.");
        }
        return MonetaryConversions.getConversion(createRateQuery(specAnnot));
    }

    @Produces
    public ExchangeRate exchangeRate(InjectionPoint ip){
        ConversionSpec specAnnot = ip.getAnnotated()!=null?ip.getAnnotated().getAnnotation(ConversionSpec.class):null;
        if(specAnnot==null){
            throw new IllegalArgumentException("@RateSpec is required.");
        }
        ConversionQuery query = createRateQuery(specAnnot);
        return MonetaryConversions.getExchangeRateProvider(query)
                .getExchangeRate(query);
    }

    @Produces
    public Collection<ExchangeRate> exchangeRates(InjectionPoint ip){
        ConversionSpec specAnnot = ip.getAnnotated()!=null?ip.getAnnotated().getAnnotation(ConversionSpec.class):null;
        if(specAnnot==null){
            throw new IllegalArgumentException("@RateSpec is required.");
        }
        ConversionQuery query = createRateQuery(specAnnot);
        Collection<String> providers = query.getProviderNames();
        if(providers.isEmpty()){
            providers = MonetaryConversions.getConversionProviderNames();
        }
        List<ExchangeRate> rates = new ArrayList<>();
        for(String providerId:providers){
            ExchangeRateProvider provider = MonetaryConversions.getExchangeRateProvider(providerId);
            try {
                ExchangeRate rate = provider.getExchangeRate(query);
                if (rate != null) {
                    rates.add(rate);
                }
            }catch(Exception e){
                LOG.log(Level.FINE,
                        "Could not evaluate rate from provider '" + providerId + "': "+  query,
                        e);
            }
        }
        return rates;
    }


    @Produces
    public MonetaryAmountFormat amountFormat(InjectionPoint ip){
        FormatSpec specAnnot = ip.getAnnotated()!=null?ip.getAnnotated().getAnnotation(FormatSpec.class):null;
        if(specAnnot==null){
            throw new IllegalArgumentException("@FormatName is required.");
        }
        return MonetaryFormats.getAmountFormat(createFormatQuery(
                specAnnot,
                ip.getAnnotated().getAnnotation(AmountSpec.class)));
    }


    @Produces
    public Collection<MonetaryAmountFormat> amountFormats(InjectionPoint ip){
        FormatSpec specAnnot = ip.getAnnotated()!=null?ip.getAnnotated().getAnnotation(FormatSpec.class):null;
        if(specAnnot==null){
            throw new IllegalArgumentException("@FormatName is required.");
        }
        return MonetaryFormats.getAmountFormats(createFormatQuery(
                specAnnot,
                ip.getAnnotated().getAnnotation(AmountSpec.class)));
    }


    @Produces
    public MonetaryAmountFactory amountFactory(InjectionPoint ip){
        AmountSpec specAnnot = ip.getAnnotated()!=null?ip.getAnnotated().getAnnotation(AmountSpec.class):null;
        if(specAnnot!=null){
            return Monetary.getAmountFactory(createAmountQuery(specAnnot));
        }
        return Monetary.getDefaultAmountFactory();
    }


    @Produces
    public Collection<MonetaryAmountFactory> amountFactories(InjectionPoint ip){
        AmountSpec specAnnot = ip.getAnnotated()!=null?ip.getAnnotated().getAnnotation(AmountSpec.class):null;
        if(specAnnot!=null){
            return Collection.class.cast(Monetary.getAmountFactories(createAmountQuery(specAnnot)));
        }
        return Collection.class.cast(Monetary.getAmountFactories());
    }


    private CurrencyQuery createCurrencyQuery(CurrencySpec specAnnot) {
        CurrencyQueryBuilder b = CurrencyQueryBuilder.of();
        b.setCountries(convertToCountries(specAnnot.countries()));
        b.setCurrencyCodes(specAnnot.codes());
        b.setNumericCodes(specAnnot.numericCodes());
        if(specAnnot.attributes().length>0){
            applyAttributes(specAnnot.attributes(), b);
        }
        if(specAnnot.providers().length>0){
            b.setProviderNames(specAnnot.providers());
        }
        return b.build();
    }

    private Locale[] convertToCountries(String[] codes) {
        Locale[] locales = new Locale[codes.length];
        for(int i=0;i<codes.length;i++){
            locales[i] = new Locale("", codes[i]);
        }
        return locales;
    }

    private ConversionQuery createRateQuery(ConversionSpec specAnnot) {
        ConversionQueryBuilder b = ConversionQueryBuilder.of();
        if(!specAnnot.baseCurrency().isEmpty()){
            b.setBaseCurrency(specAnnot.baseCurrency());
        }
        if(!specAnnot.termCurrency().isEmpty()){
            b.setTermCurrency(specAnnot.termCurrency());
        }
        if(specAnnot.attributes().length>0){
            applyAttributes(specAnnot.attributes(), b);
        }
        if(specAnnot.providers().length>0){
            b.setProviderNames(specAnnot.providers());
        }
        if(specAnnot.rateTypes().length==0){
            b.setRateTypes(specAnnot.rateTypes());
        }
        return b.build();
    }

    private AmountFormatQuery createFormatQuery(FormatSpec specAnnot, AmountSpec amountSpec) {
        AmountFormatQueryBuilder b = AmountFormatQueryBuilder.of(specAnnot.name());
        if(specAnnot.providers().length>0){
            b.setProviderNames(specAnnot.providers());
        }
        if(specAnnot.attributes().length>0){
            applyAttributes(specAnnot.attributes(), b);
        }
        if(specAnnot.locale().isEmpty()){
            b.setLocale(createLocale(specAnnot.locale()));
        }
        if(amountSpec!=null){
            b.setMonetaryAmountFactory(Monetary.getAmountFactory(createAmountQuery(amountSpec)));
        }
        return b.build();
    }

    private Locale createLocale(String locale) {
        String[] parts = locale.split("\\_", 3);
        switch(parts.length){
            case 1:
                return new Locale(parts[0]);
            case 2:
                return new Locale(parts[0], parts[1]);
            default:
                return new Locale(parts[0], parts[1], parts[2]);
        }
    }

    private MonetaryAmountFactoryQuery createAmountQuery(AmountSpec specAnnot) {
        MonetaryAmountFactoryQueryBuilder b = MonetaryAmountFactoryQueryBuilder.of();
        if(specAnnot.value()!=MonetaryAmount.class){
            b.setTargetType(specAnnot.value());
        }
        if(specAnnot.providers().length>0){
            b.setProviderNames(specAnnot.providers());
        }
        if(specAnnot.attributes().length>0){
            applyAttributes(specAnnot.attributes(), b);
        }
        if(specAnnot.fixedScale()){
            b.setFixedScale(true);
        }
        if(specAnnot.maxScale()>0){
            b.setMaxScale(specAnnot.maxScale());
        }
        if(specAnnot.precision()>0){
            b.setPrecision(specAnnot.precision());
        }
        return b.build();
    }

    private void applyAttributes(String[] attributes, AbstractQueryBuilder b) {
        for(String attr:attributes){
            String[] parts = attr.split("=", 2);
            b.set(parts[0], parts[1]);
        }
    }

}
