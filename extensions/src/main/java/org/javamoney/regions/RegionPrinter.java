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
package org.javamoney.regions;

import java.io.IOException;

import org.javamoney.regions.RegionTreeNode;

/**
 * Small utility class for printing out a {@link RegionTreeNode}.
 * 
 * @author Anatole Tresch
 */
public final class RegionPrinter {
	/**
	 * Singleton constructor.
	 */
	private RegionPrinter() {
	}

	/**
	 * Format a {@link RegionTreeNode} to text.
	 * 
	 * @param tree
	 *            the {@link RegionTreeNode} to be formatted
	 * @return the {@link RegionTreeNode} textual representation
	 */
	public static String getAsText(RegionTreeNode tree) {
		return getAsText(tree, "");
	}

	/**
	 * Format a {@link RegionTreeNode} to text.
	 * 
	 * @param tree
	 *            the {@link RegionTreeNode} to be formatted
	 * @param intend
	 *            the initial intend to be used
	 * @return the {@link RegionTreeNode} textual representation
	 */
	public static String getAsText(RegionTreeNode tree, String intend) {
		StringBuilder b = new StringBuilder();
		try {
			printTree(tree, b, intend);
		} catch (IOException e) {
			e.printStackTrace(); // TODO
			b.append("Error: " + e);
		}
		return b.toString();
	}

	/**
	 * Print the whole tree into the given {@link Appendable}.
	 * 
	 * @param tree
	 *            the region tree
	 * @param appendable
	 *            the appendable
	 * @param intend
	 *            the initial intend
	 * @throws IOException
	 *             any exception fomr the appendable
	 */
	public static void printTree(RegionTreeNode tree, Appendable appendable,
			String intend)
			throws IOException {
		appendable.append(intend + tree.toString()).append("\n");
		intend = intend + "  ";
		for (RegionTreeNode region : tree.getChildren()) {
			printTree(region, appendable, intend);
		}
	}

}
