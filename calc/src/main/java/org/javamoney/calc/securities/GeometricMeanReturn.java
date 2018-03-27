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
 * Contributors: @manuela-grindei
 */
package org.javamoney.calc.securities;


import org.javamoney.calc.common.Rate;

import java.math.BigDecimal;
import java.util.List;

/**
 * <img src="http://www.financeformulas.net/Formula%20Images/Geometric%20Mean%20Return%201.gif" />
 * <p>
 * The geometric mean return formula is used to calculate the average rate per period on an investment that is compounded over multiple periods.
 *
 * @author Manuela Grindei
 * @link http://www.financeformulas.net/Geometric_Mean_Return.html
 */
public class GeometricMeanReturn {

    /**
     * Private constructor.
     */
    private GeometricMeanReturn() {
    }

    /**
     * Calculates geometric mean return.
     *
     * @param ratesOfReturn   the rates of return
     * @return the geometric mean return
     */
    public static double calculate(List<Rate> ratesOfReturn) {
        BigDecimal product = BigDecimal.ONE;
        for (Rate rateOfReturn : ratesOfReturn) {
            if (rateOfReturn == null) {
                throw new IllegalArgumentException("The list of rates cannot contain null elements");
            }
            product = product.multiply(rateOfReturn.get().add(BigDecimal.ONE));
        }
        return Math.pow(product.doubleValue(), 1 / (double) ratesOfReturn.size()) - 1;
    }
}
