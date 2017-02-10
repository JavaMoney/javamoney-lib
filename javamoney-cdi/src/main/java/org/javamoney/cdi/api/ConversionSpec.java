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

import javax.money.convert.RateType;
import java.lang.annotation.*;

/**
 * Annotation that allows to optionally refine a {@link javax.money.convert.CurrencyConversion},
 * {@link javax.money.convert.ExchangeRateProvider} or {@link javax.money.convert.ConversionQuery}
 * to be injected.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@Documented
public @interface ConversionSpec {

    /**
     * The type of rates to be created/accessed.
     * @return the rate type, not null.
     */
    RateType[] rateTypes() default {};

    /**
     * The base currency.
     * @return the base currency.
     */
    String baseCurrency() default "";

    /**
     * The target/terminating currency.
     * @return the target/terminating currency.
     */
    String termCurrency() default "";

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
}


