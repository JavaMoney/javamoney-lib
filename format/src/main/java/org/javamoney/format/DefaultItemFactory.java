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
package org.javamoney.format;

/**
 * Default implementation of {@link ItemFactory} that looks up resulting item
 * under the {@link Class} or {@link Class#getName()} key. This is used as
 * described in {@link ItemFactory} as a last step after all {@link FormatToken}
 * instances in {@link ItemFormat}, created by the {@link ItemFormatBuilder},
 * has parsed the input. This instance of {@link ItemFactory} is trying to
 * lookup an instance of type {@link ItemFormat#getTargetClass()} from the
 * current {@link ParseContext}.
 * <p>
 * This class is thread-safe and immutable.
 * 
 * @author Anatole Tresch
 * 
 * @param <T>
 *            the item type.
 */
public final class DefaultItemFactory<T> implements ItemFactory<T> {
	/** The item class. */
	private Class<T> itemClass;

	/**
	 * Constructor.
	 * 
	 * @param itemClass
	 *            The item class, not {@code null}.
	 */
	public DefaultItemFactory(Class<T> itemClass) {
		this.itemClass = itemClass;
	}

	/**
	 * Accesses the final item from the {@link ParseContext} as defined by
	 * {@link ItemFormat#getTargetClass()}.
	 * 
	 * @param context
	 *            the {@link ParseContext}.
	 * @return the item parsed.
	 * @throws IllegalStateException
	 *             , if the item could not be found.
	 * @see #isComplete(ParseContext)
	 * @throws IllegalStateException
	 *             if no such result could be evaluated.
	 */
	@Override
	public T createItemParsed(ParseContext<T> context) {
		T item = context.getResult(itemClass, itemClass);
		if (item == null) {
			item = context.getResult(itemClass.getName(), itemClass);
		}
		if (item == null) {
			throw new IllegalStateException("Parsing is not complete.");
		}
		return item;
	}

	/**
	 * CHecks if the required item is available within the {@link ParseContext},
	 * using the class or fully qualified class name as a key.
	 * 
	 * @param context
	 *            the {@link ParseContext}.
	 * @return {@code true}, if the item parsed was found or can be created.
	 * @see #apply(ParseContext)
	 */
	@Override
	public boolean isComplete(ParseContext<T> context) {
		return context.getResult(itemClass, itemClass) != null
				|| context.getResult(itemClass.getName(), itemClass) != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DefaultItemFactory [itemClass=" + itemClass + "]";
	}

}