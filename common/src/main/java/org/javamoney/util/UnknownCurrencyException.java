package org.javamoney.util;

import javax.money.MonetaryException;

public class UnknownCurrencyException extends MonetaryException {

	private final String code;
	
	public UnknownCurrencyException(String s, String code) {
		super(s);
		this.code = code;
	}

}
