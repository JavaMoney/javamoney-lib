package net.java.javamoney.extras.filter;

import java.util.Set;

import javax.money.MonetaryAmount;
import javax.money.function.InstancesPredicate;

import net.java.javamoney.extras.FlavoredMonetaryAmount;
import net.java.javamoney.extras.MonetaryAmountFlavor;

public class MonetaryAmountFlavorPredicate extends
		InstancesPredicate<MonetaryAmountFlavor> {

	protected boolean isPredicateTrue(MonetaryAmount value,
			Set<MonetaryAmountFlavor> acceptedValues) {
		if (value instanceof FlavoredMonetaryAmount) {
			return acceptedValues.contains(((FlavoredMonetaryAmount) value)
					.getAmountFlavor());
		}
		return false;
	}

}
