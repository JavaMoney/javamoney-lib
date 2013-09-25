package org.javamoney.extras.filter;

import javax.money.MonetaryAmount;

import org.javamoney.ext.Predicate;


public final class MonetaryFunctionsExtras {

	private MonetaryFunctionsExtras() {
	}

	public static Predicate<MonetaryAmount> withFlavors(String... flavors) {
		return new MonetaryAmountFlavorPredicate<>(flavors);
	}

	public static Predicate<MonetaryAmount> withFlavors(
			Iterable<String>... flavors) {
		return new MonetaryAmountFlavorPredicate<>(flavors);
	}

}
