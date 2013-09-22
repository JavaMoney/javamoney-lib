package net.java.javamoney.extras.filter;

import javax.money.MonetaryAmount;
import javax.money.Predicate;

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
