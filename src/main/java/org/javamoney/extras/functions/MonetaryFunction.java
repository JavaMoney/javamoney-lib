package org.javamoney.extras.functions;

public interface MonetaryFunction<T, R> {

	public R calculate(T input);

}
