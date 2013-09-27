/*
 * Copyright (c) 2012, 2013, Credit Suisse (Anatole Tresch), Werner Keil.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.javamoney.convert;

import javax.money.CurrencyUnit;
import javax.money.MonetaryException;

/**
 * Exception thrown when a monetary conversion operation fails.
 * 
 * @author Werner Keil
 * @author Stephen Colebourne
 * @author Anatole Tresch
 */
public class CurrencyConversionException extends MonetaryException {

	/** Serialization lock. */
	private static final long serialVersionUID = -7743240650686883450L;

	/** Base currency. */
	private CurrencyUnit base;
	/** Terminating currency. */
	private CurrencyUnit term;
	/** The acquired target timestamp, may be null. */
	private Long timestamp;

	/**
	 * Constructs an <code>CurrencyConversionException</code> with the specified
	 * detail message, timestamp, source and target currency.
	 * 
	 * @param source
	 *            the source currency, may be null.
	 * 
	 * @param target
	 *            the target currency, may be null.
	 * 
	 * @param message
	 *            the detail message.
	 */
	public CurrencyConversionException(CurrencyUnit base,
			CurrencyUnit term, Long timestamp, String message) {
		super("Cannot convert " + String.valueOf(base) + " into "
				+ String.valueOf(term) +": " + message);
		this.base = base;
		this.term = term;
		this.timestamp = timestamp;
	}

	/**
	 * Constructs an <code>CurrencyConversionException</code> with the specified
	 * source and target currency.
	 * 
	 * @param source
	 *            the source currency, may be null.
	 * 
	 * @param target
	 *            the target currency, may be null.
	 */
	public CurrencyConversionException(CurrencyUnit base,
			CurrencyUnit term, Long timestamp) {
		super("Cannot convert " + String.valueOf(base) + " into "
				+ String.valueOf(term));
		this.base = base;
		this.term = term;
		this.timestamp = timestamp;
	}

	/**
	 * Constructs a new exception with the specified source and target currency,
	 * detail message and cause.
	 * 
	 * <p>
	 * Note that the detail message associated with <code>cause</code> is
	 * <i>not</i> automatically incorporated in this exception's detail message.
	 * 
	 * @param source
	 *            the source currency, may be null.
	 * @param target
	 *            the target currency, may be null.
	 * @param message
	 *            the detail message (which is saved for later retrieval by the
	 *            {@link Throwable#getMessage()} method).
	 * @param cause
	 *            the cause (which is saved for later retrieval by the
	 *            {@link Throwable#getCause()} method). (A <tt>null</tt> value
	 *            is permitted, and indicates that the cause is nonexistent or
	 *            unknown.)
	 */
	public CurrencyConversionException(CurrencyUnit base,
			CurrencyUnit term, Long timestamp, String message, Throwable cause) {
		super(message, cause);
		this.base = base;
		this.term = term;
		this.timestamp = timestamp;
	}

	/**
	 * Gets the first currency at fault.
	 * 
	 * @return the currency at fault, may be null
	 */
	public CurrencyUnit getBase() {
		return base;
	}

	/**
	 * Gets the second currency at fault.
	 * 
	 * @return the currency at fault, may be null
	 */
	public CurrencyUnit getTerm() {
		return term;
	}

	/**
	 * Gets the queried timestamp at fault.
	 * 
	 * @return the queried timestamp, or {@code null}.
	 */
	public Long getTimestamp() {
		return this.timestamp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CurrencyConversionException [base=" + base + ", term="
				+ term + ", timestamp=" + timestamp + "]: " + getMessage();
	}

}
