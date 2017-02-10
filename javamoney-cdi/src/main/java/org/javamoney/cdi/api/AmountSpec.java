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

import javax.money.MonetaryAmount;
import java.lang.annotation.*;

/**
 * Annotation that allows to optionally refine a {@link javax.money.MonetaryAmountFactory},
 * {@link MonetaryAmount} or {@link javax.money.MonetaryAmountFactoryQuery} to be injected.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@Documented
public @interface AmountSpec {

    /**
     * The implemention type required, normally this does overrule all other settings.
     * @return the implementation type to be created by the factory.
     */
    Class value() default MonetaryAmount.class;

    /**
     * Should the amount created have always the same, fixed scale?
     * @return the fixedScale flag.
     */
    boolean fixedScale() default false;

    /**
     * The maximum scale required.
     * @return the maximum scale.
     */
    int maxScale() default -1;

    /**
     * The required precision.
     * @return the required precision.
     */
    int precision() default -1;

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
