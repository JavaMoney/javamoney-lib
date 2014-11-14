/*
 * Copyright (c) 2012, 2013, Credit Suisse (Anatole Tresch), Werner Keil.
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
 */
package org.javamoney.format;

import javax.money.MonetaryAmount;
import javax.money.format.*;
import java.io.IOException;
import java.util.Locale;
import java.util.Objects;

public class DefaultAmountFormat implements ItemFormat<MonetaryAmount>{

    private LocalizationContext style;

    @Override
    public Class getTargetClass(){
        return MonetaryAmount.class;
    }

    public DefaultAmountFormat(LocalizationContext style){
        Objects.requireNonNull(style);
        this.style = style;
    }

    @Override
    public LocalizationContext getStyle(){
        return style;
    }


    @Override
    public String format(MonetaryAmount item, Locale locale) throws ItemFormatException{
        MonetaryAmountFormat amountFormat = style.get(MonetaryAmountFormat.class);
        if(amountFormat == null){
            AmountFormatQuery formatContext = style.get(AmountFormatQuery.class);
            if(formatContext != null){
                amountFormat = MonetaryFormats.getAmountFormat(formatContext);
            }else{
                amountFormat = MonetaryFormats.getAmountFormat(
                        AmountFormatQueryBuilder.of(style.get(Locale.class)).importContext(style).build());
            }
        }
        return amountFormat.format(item);
    }

    @Override
    public void print(Appendable appendable, MonetaryAmount item, Locale locale) throws IOException{
        appendable.append(format(item, locale));
    }

    @Override
    public MonetaryAmount parse(CharSequence text, Locale locale) throws ItemParseException{
        MonetaryAmountFormat amountFormat = style.get(MonetaryAmountFormat.class);
        if(amountFormat == null){
            amountFormat = MonetaryFormats.getAmountFormat(locale);
        }
        return amountFormat.parse(text);
    }

    @Override
    public String toString(){
        return "DefaultAmountFormat{" +
                "style=" + style +
                '}';
    }
}
