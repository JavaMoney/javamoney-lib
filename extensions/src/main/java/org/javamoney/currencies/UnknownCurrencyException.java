package org.javamoney.currencies;

import javax.money.MonetaryException;

public class UnknownCurrencyException extends MonetaryException {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 8619605652664849841L;

	public UnknownCurrencyException(String message, Throwable t) {
		super(message, t);
	}

	public UnknownCurrencyException(String message) {
		super(message);
	}
}
