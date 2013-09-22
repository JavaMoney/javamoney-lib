package org.javamoney.extras.functions;

import java.util.ArrayList;
import java.util.List;

import javax.money.MonetaryAmount;
import javax.money.MonetaryOperator;

public class Composition implements MonetaryOperator {

	private List<MonetaryOperator> functions = new ArrayList<>();

	@SafeVarargs
	public Composition(Iterable<MonetaryOperator>... operations) {
		if (operations != null) {
			for (Iterable<MonetaryOperator> iterable : operations) {
				for (MonetaryOperator monetaryOperator : iterable) {
					functions.add(monetaryOperator);
				}
			}
		}
	}

	public Composition(MonetaryOperator... operations) {
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

}
