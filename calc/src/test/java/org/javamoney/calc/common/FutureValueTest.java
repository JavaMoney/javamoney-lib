/*
 * Copyright (c) 2012, 2014, Credit Suisse (Anatole Tresch), Werner Keil.
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
package org.javamoney.calc.common;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import javax.money.MonetaryOperator;
import javax.money.MonetaryRoundings;
import javax.money.RoundingContext;

import org.javamoney.moneta.Money;
import org.junit.Ignore;
import org.junit.Test;

public class FutureValueTest {

	@Test
	@Ignore("rounding apply doesn't work as expected")
	public void test() {
		FutureValue f = FutureValue.of();
		Money money = Money.of(100, "CHF");
		MonetaryOperator rounding = MonetaryRoundings.getRounding(
				new RoundingContext.Builder().setScale(2)
						//.setRoundingMode(RoundingMode.HALF_EVEN)
						.build());
		assertEquals(Money.of(BigDecimal.valueOf(95.24), "CHF"), f.calculate(money,new Rate(0.05), 1)
				.with(rounding));
		assertEquals(Money.of(BigDecimal.valueOf(90.7), "CHF"),
				f.calculate(money,new Rate(0.05), 2).with(rounding));
		assertEquals(Money.of(BigDecimal.valueOf(86.38), "CHF"),
				f.calculate(money,new Rate(0.05), 3).with(rounding));
	}
}
