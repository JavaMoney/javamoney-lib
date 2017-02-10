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
 * Annotation that allows to define a {@link javax.money.format.MonetaryAmountFormat} or
 * {@link javax.money.format.AmountFormatQuery} to be injected.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@Documented
public @interface FormatSpec {

    /**
     * The format's name.
     * @return the format's name, not null.
     */
    String name();

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
     * The target locale  in the format as created by {@link java.util.Locale#toString()}.
     * @return the target local code.
     */
    String locale() default "";
}


