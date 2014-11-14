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

///**
// * When accessing {@link ItemFormat} instances from the {@link org.javamoney.format.ItemFormats} singleton, it is also
// * possible to access an ItemFormat configured with a LocalizationStyle directly,
// * e.g. ba calling {@link org.javamoney.format.ItemFormats#getItemFormat(Class, LocalizationStyle)}. Hereby it is
// * possible that
// * the item format to be used is defined as a default by the LocalizationStyle. When the format will be instantiated
// * finally
// * it can obtain references to the concrete target type and localization style by implementing this interface.
// * Created by Anatole Tresch on 17.03.14.
// */
//public interface ConfigurableItemFormat<T> extends ItemFormat<T>{
//
//    /**
//     * Configures the given instance with the values.
//     *
//     * @param targetType the target type, not null.
//     * @param style      the localization style, not null
//     * @param <T>        the target type's class.
//     */
//    <T> void configure(Class<T> targetType, LocalizationStyle style);
//
//}
