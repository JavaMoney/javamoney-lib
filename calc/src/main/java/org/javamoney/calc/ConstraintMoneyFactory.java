package org.javamoney.calc;

import java.util.Objects;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.MonetaryContext;
import javax.money.MonetaryContext.AmountFlavor;

import org.javamoney.calc.function.MonetaryPredicate;
import org.javamoney.moneta.spi.AbstractAmountFactory;

public class ConstraintMoneyFactory extends
		AbstractAmountFactory<ConstraintMoney> {

	private MonetaryPredicate<MonetaryAmount> constraint;

	private MonetaryAmount baseAmount;

	public ConstraintMoneyFactory(MonetaryAmount baseAmount) {
		Objects.requireNonNull(baseAmount);
		this.baseAmount = baseAmount;
	}	
	
	@Override
	protected ConstraintMoney create(CurrencyUnit currency, Number number,
			MonetaryContext monetaryContext) {
		return new ConstraintMoney(baseAmount.getFactory().setCurrency(currency)
				.setNumber(number).setContext(monetaryContext).create(), constraint);
	}

	@Override
	public Class<ConstraintMoney> getAmountType() {
		return ConstraintMoney.class;
	}

	@Override
	protected MonetaryContext loadDefaultMonetaryContext() {
		return new MonetaryContext.Builder(
				ConstraintMoney.class).setFlavor(AmountFlavor.UNDEFINED)
				.setMaxScale(-1).setFixedScale(false)
				.setPrecision(0)
				.build();
	}

	@Override
	protected MonetaryContext loadMaxMonetaryContext() {
		return new MonetaryContext.Builder(
				ConstraintMoney.class).setFlavor(AmountFlavor.UNDEFINED)
				.setMaxScale(-1).setFixedScale(false)
				.setPrecision(0)
				.build();
	}

}
