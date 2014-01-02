package org.javamoney.calc;
//
//import javax.money.CurrencyUnit;
//import javax.money.MonetaryContext;
//import javax.money.MonetaryContext.AmountFlavor;
//
//import org.javamoney.moneta.FastMoney;
//import org.javamoney.moneta.spi.AbstractAmountFactory;
//
//public class UltraFastMoneyAmountFactory extends
//		AbstractAmountFactory<UltraFastMoney> {
//
//	@Override
//	protected UltraFastMoney create(CurrencyUnit currency, Number number,
//			MonetaryContext monetaryContext) {
//		return UltraFastMoney.of(currency, number);
//	}
//
//	@Override
//	public Class<UltraFastMoney> getAmountType() {
//		return UltraFastMoney.class;
//	}
//
//	@Override
//	protected MonetaryContext loadDefaultMonetaryContext() {
//		return new MonetaryContext.Builder(
//				FastMoney.class).setFlavor(AmountFlavor.PERFORMANCE)
//				.setMaxScale(32).setFixedScale(false)
//				.setPrecision(String.valueOf(Double.MAX_VALUE).length() - 1)
//				.build();
//	}
//
//	@Override
//	protected MonetaryContext loadMaxMonetaryContext() {
//		return new MonetaryContext.Builder(
//				FastMoney.class)
//				.setMaxScale(32).setFixedScale(false)
//				.setFlavor(AmountFlavor.PERFORMANCE)
//				.setPrecision(String.valueOf(Double.MAX_VALUE).length() - 1)
//				.build();
//	}
//
//}
