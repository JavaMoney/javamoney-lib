/*
 * Copyright (c) 2012, 2018, Werner Keil, Anatole Tresch and others.
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
 * Contributors: @atsticks
 */
package org.javamoney.calc;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Objects;

/**
 * Singleton to control the default {@link MathContext} to be used along the calculation library.
 */
public final class CalculationContext {

    private static final CalculationContext instance = new CalculationContext();

    private MathContext mathContext;
    private BigDecimal one;
    private BigDecimal zero;
    private BigDecimal ten;

    private CalculationContext(){
        init(MathContext.DECIMAL64);
    }

    /**
     * Math context math context.
     *
     * @return the math context
     */
    public static MathContext mathContext(){
        return instance.mathContext;
    }

    /**
     * Accesses the number '1' initialized with the current {@link MathContext}.
     *
     * @return the number instance, never null.
     */
    public static BigDecimal one() {
        return instance.one;
    }

    /**
     * Accesses the number '1' initialized with the current {@link MathContext}.
     *
     * @return the number instance, never null.
     */
    public static BigDecimal zero() {
        return instance.zero;
    }

    /**
     * Accesses the number '10' initialized with the current {@link MathContext}.
     *
     * @return the number instance, never null.
     */
    public static BigDecimal ten() {
        return instance.ten;
    }

    /**
     * Creates the given number initialized with the current {@link MathContext}.
     *
     * @param num the number instance.
     * @return the number instance, never null.
     */
    public static BigDecimal bigDecimal(long num) {
        return new BigDecimal(num, mathContext());
    }

    /**
     * Creates the given number initialized with the current {@link MathContext}.
     *
     * @param num the number instance.
     * @return the number instance, never null.
     */
    public static BigDecimal bigDecimal(double num) {
        return new BigDecimal(num, mathContext());
    }

    /**
     * Creates the given number initialized with the current {@link MathContext}.
     *
     * @param num the number instance.
     * @return the number instance, never null.
     */
    public static BigDecimal bigDecimal(BigDecimal num) {
        return new BigDecimal(num.toString(), mathContext());
    }


    /**
     * This method allows o set the {@link MathContext} used for doing calculations.
     * Not te that this affects all calculations at all stages.
     *
     * @param mathContext the new match context, not null.
     */
    public static void setMathContext(MathContext mathContext){
        instance.init(Objects.requireNonNull(mathContext));
    }

    private void init(MathContext mathContext){
        this.mathContext = mathContext;
        one = new BigDecimal(1, mathContext);
        zero = new BigDecimal(0, mathContext);
        ten = new BigDecimal(10, mathContext);
    }

}
