package org.javamoney.currencies;

import static org.junit.Assert.*;

import java.util.Set;

import javax.money.CurrencyUnit;
import javax.money.MonetaryCurrencies;

import org.junit.Ignore;
import org.junit.Test;

public class CurrencyMappingsTest {

	@Test
	public void testGetNamespaces() {
		Set<String> namespaces = CurrencyMappings.getNamespaces();
		assertNotNull(namespaces);
		assertTrue(namespaces.contains("ISO-4217"));
	}
	
	@Test
	public void testIsNamespaceAvailable() {
		assertTrue(CurrencyMappings.isNamespaceAvailable("ISO-4217"));
		assertFalse(CurrencyMappings.isNamespaceAvailable("foo"));
	}
	
	@Test
	@Ignore("An 'UGS' currency somewhere causes this to fail")
	public void testGetCurrencies() {
		Set<CurrencyUnit> units = CurrencyMappings.getCurrencies("ISO-4217");
		assertNotNull(units);
		assertFalse(units.isEmpty());
		assertTrue(units.contains(MonetaryCurrencies.getCurrency("CHF")));
		assertFalse(units.contains(MonetaryCurrencies.getCurrency("SDR")));
	}
	
	@Test
	public void testNamespacesString(){
		Set<String> ns = CurrencyMappings.getNamespaces("CHF");
		assertNotNull(ns);
		assertFalse(ns.isEmpty());
		assertTrue(ns.contains("ISO-4217"));
	}
	
	@Test
	public void testNamespacesCurrencyUnit(){
		Set<String> ns = CurrencyMappings.getNamespaces(MonetaryCurrencies.getCurrency("GBP"));
		assertNotNull(ns);
		assertFalse(ns.isEmpty());
		assertTrue(ns.contains("ISO-4217"));
	}

}
