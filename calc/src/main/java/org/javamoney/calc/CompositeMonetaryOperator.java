/*
 *  Copyright (c) 2012, 2014, Credit Suisse (Anatole Tresch), Werner Keil.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.javamoney.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.money.MonetaryAmount;
import javax.money.MonetaryOperator;

/**
 * A composition is an operator that contains multiple other operators that are applied as
 * a chain of functions to a MonetaryAmount. This allows to easily encapsulate a chain of operations
 * to a higher valued operation programmatically.
 *
 * @author Anatole
 * @author Werner
 */
public class CompositeMonetaryOperator implements MonetaryOperator {

    private List<MonetaryOperator> functions = new ArrayList<>();

    @SafeVarargs
    public CompositeMonetaryOperator(Iterable<MonetaryOperator>... operations) {
        if (operations != null) {
            for (Iterable<MonetaryOperator> iterable : operations) {
                for (MonetaryOperator monetaryOperator : iterable) {
                    functions.add(monetaryOperator);
                }
            }
        }
    }

    public CompositeMonetaryOperator(String name, MonetaryOperator... operations) {
        Objects.requireNonNull(name);
        for (MonetaryOperator monetaryOperator : operations) {
            functions.add(monetaryOperator);
        }
    }

    @Override
    public MonetaryAmount apply(MonetaryAmount value) {
        MonetaryAmount amount = value;
        for (MonetaryOperator op : functions) {
            amount = op.apply(amount);
        }
        return amount;
    }

    @Override
    public String toString() {
        return "Composition{chain=" + functions +
                '}';
    }
}
