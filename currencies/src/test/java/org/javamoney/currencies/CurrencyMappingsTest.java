package org.javamoney.currencies;

import org.javamoney.moneta.BuildableCurrencyUnit;
import org.junit.Test;

import javax.money.CurrencyContextBuilder;
import javax.money.CurrencyUnit;
import javax.money.MonetaryCurrencies;
import java.util.Collection;
import java.util.Set;

import static org.junit.Assert.*;

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
    public void testGetCurrencies() {
        Collection<CurrencyUnit> units =
                MonetaryCurrencies.getCurrencies(CurrencyMappings.getNamespaceQuery("ISO-4217"));
        assertNotNull(units);
        assertFalse(units.isEmpty());
        assertTrue(units.contains(MonetaryCurrencies.getCurrency("CHF")));
        BuildableCurrencyUnit bu =
                new BuildableCurrencyUnit.Builder("SDR", CurrencyContextBuilder.create("test").build())
                        .setNumericCode(-1).setDefaultFractionDigits(3).build();
        assertFalse(units.contains(bu));
    }

    @Test
    public void testNamespacesString() {
        Set<String> ns = CurrencyMappings.getNamespaces("CHF");
        assertNotNull(ns);
        assertFalse(ns.isEmpty());
        assertTrue(ns.contains("ISO-4217"));
    }

    @Test
    public void testNamespacesCurrencyUnit() {
        Set<String> ns = CurrencyMappings.getNamespaces(MonetaryCurrencies.getCurrency("GBP"));
        assertNotNull(ns);
        assertFalse(ns.isEmpty());
        assertTrue(ns.contains("ISO-4217"));
    }

}
