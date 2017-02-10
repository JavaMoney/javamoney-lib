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
package org.javamoney.cdi.api;

import java.lang.annotation.*;

/**
 * Annotation that allows to define a {@link  javax.money.CurrencyQuery}
 * or an {@link javax.money.CurrencyUnit} to be injected.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@Documented
public @interface CurrencySpec {

    /**
     * The currency's code.
     * @return the format's name, not null.
     */
    String[] codes() default {};

    /**
     * The currency's numeric code.
     * @return the format's name, not null.
     */
    int[] numericCodes() default {};

    /**
     * The provider names.
     * @return the provider names.
     */
    String[] providers() default {};

    /**
     * Any additional attributes in the form {@code key=value}.
     * @return any addtiional attributes.
     */
    String[] attributes() default {};

    /**
     * Return the country codes for the currency/currencies requested.
     * @return the country codes.
     */
    String[] countries() default{};
}


