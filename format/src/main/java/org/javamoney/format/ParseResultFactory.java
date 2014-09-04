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

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;

/**
 * This class models the component that interprets/assembles the result
 * collected by several {@link StyleableItemFormatToken} to of the final item {@code T} to
 * be returned as the parse result, and as defined by
 * {@link ItemFormat#getTargetClass()}.<br/>
 * As an example parsing a monetary amount includes parsing of a {@link Number}
 * as well as a {@link CurrencyUnit}. An instance of {@link ParseResultFactory} finally
 * than creates an instance of {@link MonetaryAmount}, e.g.
 * {@code javax.money.Money} to be returned by the
 * {@code ItemFormat<MonetaryAmount>}, assembled from the {@link Number} and the
 * {@link CurrencyUnit} parsed earlier.
 *
 * @param <T> the target type
 * @author Anatole Tresch
 */
public interface ParseResultFactory<T>{
    /**
     * Returns {@code true}, if the required final target item is available from
     * the parsed results, this method is used by the {@link ItemFormat} to
     * evaluate if further parsing of an input stream can be stopped.
     *
     * @param context The current {@link ItemParseContext}.
     * @return {@code true}, if the required item can be found in the
     * {@link ItemParseContext}'s results.
     */
    boolean isComplete(ItemParseContext<T> context);

    /**
     * Creates the item parsed using the parse results contained in the
     * {@link ItemParseContext}.
     *
     * @param context
     * @return
     */
    public T createItemParsed(ItemParseContext<T> context);

}