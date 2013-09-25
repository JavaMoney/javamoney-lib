package org.javamoney.extras.functions;

import java.util.ArrayList;
import java.util.List;

import javax.money.MonetaryAmount;
import javax.money.MonetaryAdjuster;

public class Composition implements MonetaryAdjuster {

	private List<MonetaryAdjuster> functions = new ArrayList<>();

	@SafeVarargs
	public Composition(Iterable<MonetaryAdjuster>... operations) {
		if (operations != null) {
			for (Iterable<MonetaryAdjuster> iterable : operations) {
				for (MonetaryAdjuster monetaryOperator : iterable) {
					functions.add(monetaryOperator);
				}
			}
		}
	}

	public Composition(MonetaryAdjuster... operations) {
		for (MonetaryAdjuster monetaryOperator : operations) {
			functions.add(monetaryOperator);
		}
	}

	@Override
	public MonetaryAmount adjustInto(MonetaryAmount value) {
		MonetaryAmount amount = value;
		for (MonetaryAdjuster op : functions) {
			amount = op.adjustInto(amount);
		}
		return amount;
	}

}
