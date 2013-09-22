package org.javamoney.extras.functions;


public interface CompoundFunction<T> {

	public CompoundType getInputTape();

	public Class<T> getResultType();

	public T calculate(CompoundValue input);

}
