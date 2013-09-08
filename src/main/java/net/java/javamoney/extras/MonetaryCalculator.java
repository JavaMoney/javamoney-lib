package net.java.javamoney.extras;

import java.util.Collection;

import javax.money.MonetaryAmount;
import javax.money.MonetaryFunction;

public class MonetaryCalculator {

	public String addFilter(MonetaryFunction<MonetaryAmount, Boolean> filter) {
		return filter.getClass().getSimpleName();
	}

	public String addFilter(String filterId,
			MonetaryFunction<MonetaryAmount, Boolean> filter) {
		return filterId;
	}

	public <T extends MonetaryFunction<MonetaryAmount, Boolean>> T getFilter(
			String filterId) {
		return null;
	}

	public <T extends MonetaryFunction<MonetaryAmount, Boolean>> T removeFilter(
			String filterId) {
		return null;
	}

	public <T extends MonetaryFunction<MonetaryAmount, Boolean>> T getFilter(
			Class<T> visitorType) {
		return null;
	}

	public <T extends MonetaryFunction<MonetaryAmount, Boolean>> T getFilter(
			String visitorId, Class<T> visitorType) {
		return null;
	}

	public String addAmountVisitor(
			MonetaryFunction<MonetaryAmount, Boolean> visitor) {
		return visitor.getClass().getSimpleName();
	}

	public String addAmountVisitor(String visitorId,
			MonetaryFunction<MonetaryAmount, Boolean> visitor) {
		return visitorId;
	}

	public <T extends MonetaryFunction<MonetaryAmount, Boolean>> T getVisitor(
			String visitorId) {
		return null;
	}

	public <T extends MonetaryFunction<MonetaryAmount, Boolean>> T getVisitor(
			Class<T> visitorType) {
		return null;
	}

	public <T extends MonetaryFunction<MonetaryAmount, Boolean>> T getVisitor(
			String visitorId, Class<T> visitorType) {
		return null;
	}

	public <T extends MonetaryFunction<MonetaryAmount, Boolean>> T removeVisitor(
			String visitorId) {
		return null;
	}

	public Collection<String> getVisitorIds() {
		return null;
	}

	public Collection<String> getFilterIds() {
		return null;
	}

	public Collection<MonetaryAmount> getFiltered(String filterId) {
		return null;
	}

	public int getVisited(String visitorId) {
		return 0;
	}

	public void init(MonetaryAmount... amounts) {

	}

	public void init(Collection<MonetaryAmount> amounts) {

	}

	public void reset() {

	}

	public void calculate() {

	}

}
