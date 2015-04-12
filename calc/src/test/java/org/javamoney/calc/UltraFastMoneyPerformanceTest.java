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
 * 
 * Contributors: Anatole Tresch - initial implementation Wernner Keil -
 * extensions and adaptions.
 */
package org.javamoney.calc;

//import java.math.BigDecimal;
//
//import javax.money.CurrencyUnit;
//import javax.money.MonetaryAmount;
//import javax.money.MonetaryCurrencies;
//import javax.money.Monetary;
//
//import org.javamoney.calc.UltraFastMoney;
//import org.junit.Test;
//
///**
// * @author Anatole
// * 
// */
//public class UltraFastMoneyPerformanceTest {
//
//	private static final BigDecimal TEN = new BigDecimal(10.0d);
//	protected static final CurrencyUnit EURO = MonetaryCurrencies
//			.getCurrency("EUR");
//	protected static final CurrencyUnit DOLLAR = MonetaryCurrencies
//			.getCurrency("USD");
//
//	@Test
//	public void testPerformanceNoRounding() {
//		UltraFastMoney moneyuf = UltraFastMoney.of(EURO, BigDecimal.ONE);
//		long start = System.currentTimeMillis();
//		int NUM = 100000;
//		for (int i = 0; i < NUM; i++) {
//			moneyuf = moneyuf.add(UltraFastMoney.of(EURO, 1234567.3444));
//			moneyuf = moneyuf.subtract(UltraFastMoney.of(EURO, 232323));
//			moneyuf = moneyuf.multiply(3.4);
//			moneyuf = moneyuf.divide(5.456);
//		}
//		long end = System.currentTimeMillis();
//		long duration = end - start;
//		System.out.println("Duration for " + NUM
//				+ " operations (UltraFastMoney/double): "
//				+ duration + " ms (" + ((duration * 1000) / NUM)
//				+ " ns per loop) -> "
//				+ moneyuf);
//	}
//	
//	
//	@Test
//	public void testPerformance() {
//		int NUM = 100000;
//		StringBuilder b = new StringBuilder();
//		b.append("PerformanceTest - Looping code UltraFastMoney,double:\n");
//		b.append("=====================================================\n");
//		b.append("FastMoney money1 = money1.add(UltraFastMoney.of(EURO, 1234567.3444));\n");
//		b.append("money1 = money1.subtract(UltraFastMoney.of(EURO, 232323));\n");
//		b.append("money1 = money1.multiply(3.4);\n");
//		b.append("money1 = money1.divide(5.456);\n");
//		b.append("money1 = money1.with(Monetary.getRounding());\n");
//		System.out.println(b);
//		b.setLength(0);
//		MonetaryAmount adding = UltraFastMoney.of(EURO, 1234567.3444);
//		MonetaryAmount subtracting = UltraFastMoney.of(EURO, 232323);
//		UltraFastMoney moneyuf = UltraFastMoney.of(EURO, BigDecimal.ONE);
//		long start = System.currentTimeMillis();
//		for (int i = 0; i < NUM; i++) {
//			moneyuf = moneyuf.add(adding);
//			moneyuf = moneyuf.subtract(subtracting);
//			moneyuf = moneyuf.multiply(3.4);
//			moneyuf = moneyuf.divide(5.456);
//			moneyuf = moneyuf.with(Monetary.getRounding());
//		}
//		long end = System.currentTimeMillis();
//		long duration = end - start;
//		System.out.println("Duration for " + NUM
//				+ " operations (UltraFastMoney,double): "
//				+ duration + " ms (" + ((duration * 1000) / NUM)
//				+ " ns per loop) -> "
//				+ moneyuf);
//		System.out.println();
//	}
//
//}
