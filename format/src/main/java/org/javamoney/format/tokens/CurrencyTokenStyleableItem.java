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
package org.javamoney.format.tokens;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.money.CurrencyUnit;

import org.javamoney.format.*;
import org.javamoney.format.LocalizationContext;

/**
 * {@link org.javamoney.format.StyleableItemFormatToken} that adds a localizable {@link String}, read by key from
 * a {@link ResourceBundle}..
 *
 * @author Anatole Tresch
 */
public class CurrencyTokenStyleableItem extends AbstractStyleableItemFormatToken<CurrencyUnit> {

    public static enum DisplayType {
        CODE, NAME, NUMERIC_CODE, SYMBOL
    }

    private DisplayType displayType = DisplayType.CODE;

    public CurrencyTokenStyleableItem() {
    }

    public CurrencyTokenStyleableItem setDisplayType(DisplayType displayType) {
        if (displayType == null) {
            throw new IllegalArgumentException("Display type null.");
        }
        this.displayType = displayType;
        return this;
    }

    public DisplayType getDisplayType() {
        return this.displayType;
    }

    protected String getToken(CurrencyUnit unit, Locale locale,
                              LocalizationContext style) {
        switch (displayType) {
            case NUMERIC_CODE:
                return String.valueOf(unit.getNumericCode());
            case NAME:
                ItemFormat<CurrencyUnit> cf1 = ItemFormats
                        .getItemFormat(CurrencyUnit.class,
                                new LocalizationContext.Builder(style).set("renderField", "displayName")
                                        .build());
                return cf1.format(unit, locale);
            case SYMBOL:
                ItemFormat<CurrencyUnit> cf2 = ItemFormats.getItemFormat(CurrencyUnit.class,
                        new LocalizationContext.Builder(style)
                                .set("renderField", "symbol")
                                .build());
                return cf2.format(unit, locale);
            default:
            case CODE:
                return unit.getCurrencyCode();
        }
    }

    @Override
    public void parse(ItemParseContext context, Locale locale,
                      LocalizationContext style)
            throws ItemParseException {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
