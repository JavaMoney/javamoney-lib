/*
 *  Copyright (c) 2012, 2013, Trivadis (Anatole Tresch), Werner Keil.
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
package org.javamoney.calc.common;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * Created by atsticks on 29.05.16.
 */
public class WeightedAverageTest {

    @Test
    public void ofWeightedValue() throws Exception {
        WeightedAverage.WeightedValue val = WeightedAverage.ofWeightedValue(
                new BigDecimal(100.45), new BigDecimal(243)
        );
        assertEquals(val.getValue(), new BigDecimal(100.45));
        assertEquals(val.getWeight(), new BigDecimal(243));
    }

    @Test
    public void getValues() throws Exception {

    }

    @Test
    public void calculateWeightedAverage_None() throws Exception {
        WeightedAverage.Builder builder = WeightedAverage.newBuilder();
        assertEquals(builder.calculate(), new BigDecimal(0));
        assertEquals(builder.build().calculateWeightedAverage(),
                new BigDecimal(0));
    }

    @Test
    public void calculateWeightedAverage_1() throws Exception {
        WeightedAverage.Builder builder = WeightedAverage.newBuilder();
        builder.add(100,50);
        assertEquals(builder.calculate(), new BigDecimal(100));
        assertEquals(builder.build().calculateWeightedAverage(),
                new BigDecimal(100));
    }

    @Test
    public void calculateWeightedAverage_2_Distributed() throws Exception {
        WeightedAverage.Builder builder = WeightedAverage.newBuilder();
        builder.add(50,50);
        builder.add(100,50);
        assertEquals(builder.calculate().doubleValue(), new BigDecimal(75).doubleValue(), 0.0d);
        assertEquals(builder.build().calculateWeightedAverage().doubleValue(),
                75.0, 0.0d);
    }

    @Test
    public void calculateWeightedAverage_3_Distributed() throws Exception {
        WeightedAverage.Builder builder = WeightedAverage.newBuilder();
        builder.add(50,0.5);
        builder.add(100,0.5);
        assertEquals(builder.calculate().doubleValue(), new BigDecimal(75).doubleValue(), 0.0d);
        assertEquals(builder.build().calculateWeightedAverage().doubleValue(),
                75.0, 0.0d);
    }

    @Test
    public void calculateWeightedAverage_Multiple_NonDistributed() throws Exception {
        WeightedAverage.Builder builder = WeightedAverage.newBuilder();
        builder.add(2334, 0.1);
        builder.add(12321,0.2);
        builder.add(22,0.4);
        builder.add(12,0.3);
        assertEquals(builder.calculate().doubleValue(),
                new BigDecimal(2710).doubleValue(), 0.0d);
        assertEquals(builder.build().calculateWeightedAverage().doubleValue(),
                new BigDecimal(2710).doubleValue(), 0.0d);
    }
}