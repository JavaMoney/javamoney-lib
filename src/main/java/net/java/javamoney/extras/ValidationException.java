/*
 *  Copyright (c) 2012, 2013, Werner Keil, Credit Suisse (Anatole Tresch).
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 * Contributors:
 *    Anatole Tresch - initial version.
 */
package net.java.javamoney.extras;

import javax.money.MonetaryException;

/**
 * Exception that is thrown when a {@link ParameterObject} or a value in a
 * {@link ParameterObject} could not be validated successfully.
 * 
 * @author Anatole Tresch
 */
public class ValidationException extends MonetaryException {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -5308404907335737203L;

	/**
	 * Constructor.
	 * 
	 * @param message
	 *            the exception message
	 */
	public ValidationException(String message) {
		super(message);
	}

	/**
	 * Constructor.
	 * 
	 * @param message
	 *            the exception message
	 * @param cause
	 *            the cause, or {@code null}
	 */
	public ValidationException(String message, Throwable cause) {
		super(message, cause);
	}

}
