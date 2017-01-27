package org.javamoney.cdi;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Created by atsticks on 26.01.17.
 */
@Ignore
public class CurrencyInjectionTest {

    private static CurrencyInjectedBean bean;

    @BeforeClass
    public static final void startContainer() {
        SEContainer.start();
        bean = SEContainer.getInstance(CurrencyInjectedBean.class);
    }

    @Test
    public void chfCurrency(){
        assertNotNull(bean.chfCurrency);
    }

    @Test
    public void currencies(){
        assertNotNull(bean.currencies);
    }

    @Test
    public void historicCurrency(){
        assertNotNull(bean.historicCurrency);
    }

    @Test
    public void isoCurrencies(){
        assertNotNull(bean.isoCurrencies);
    }

    @Test
    public void numericCurrency(){
        assertNotNull(bean.numericCurrency);
    }

    @AfterClass
    public static final void stopContainer() {
        SEContainer.stop();
    }
}
