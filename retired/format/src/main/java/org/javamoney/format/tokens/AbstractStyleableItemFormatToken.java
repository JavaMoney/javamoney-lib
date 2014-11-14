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

import org.javamoney.format.LocalizationContext;
import org.javamoney.format.StyleableItemFormatToken;

import java.io.IOException;
import java.util.Locale;

/**
 * Base class when implementing a {@link org.javamoney.format.StyleableItemFormatToken}.
 *
 * @param <T> The target type.
 * @author Anatole Tresch
 */
public abstract class AbstractStyleableItemFormatToken<T> implements StyleableItemFormatToken<T> {

    protected abstract String getToken(T item, Locale locale, LocalizationContext style);


    /**
     * Access a configured value as with an additional identifier for this token instance.
     *
     * @param style        the {@link org.javamoney.format.LocalizationContext}
     * @param instanceId   The sub identifier of this token (optional).
     * @param type         key the attribute's key
     * @param defaultValue the attribute's default value, if no such attribute is present.
     * @param type         the attribute's type.
     * @return the parameter value, or the given defaultValue.
     */
    protected <T> T getParamterValue(String key, String instanceId, LocalizationContext style, Class<T> type,
                                     T defaultValue) {
        String accessKey = key;
        if (instanceId != null) {
            accessKey = instanceId + '.' + key;
        }
        T value = style.getAny(accessKey, type);
        if (value == null) {
            accessKey = getClass().getName() + '.' + key;
            value = style.getAny(accessKey, type);
        }
        if (value == null) {
            accessKey = getClass().getSimpleName() + '.' + key;
            value = style.getAny(accessKey, type);
        }
        if (value == null) {
            value = style.getAny(key, type);
        }
        if (value == null) {
            return defaultValue;
        }
        return value;
    }


    /**
     * Access a configured value.
     *
     * @param style the {@link org.javamoney.format.LocalizationContext}
     * @return the parameter value, or the given defaultValue.
     */
    protected <T> T getParamterValue(String key, LocalizationContext style, Class<T> type, T defaultValue) {
        return getParamterValue(key, null, style, type, defaultValue);
    }

    @Override
    public void print(Appendable appendable, T item, Locale locale, LocalizationContext style) throws IOException {
        String token = adjustPreformatted(getToken(adjustValue(item), locale, style));
        if (token == null) {
            throw new IllegalStateException("Token may not be null.");
        }
        appendable.append(token);
    }

    protected T adjustValue(T item) {
        return item;
    }

    protected String adjustPreformatted(String preformattedValue) {
        return preformattedValue;
    }

}
