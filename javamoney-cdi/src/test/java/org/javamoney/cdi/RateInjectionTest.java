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
public class RateInjectionTest {

    private static RateInjectedBean bean;

    @BeforeClass
    public static final void startContainer() {
        SEContainer.start();
        bean = SEContainer.getInstance(RateInjectedBean.class);
    }

    @Test
    public void allChfEurRates(){
        assertNotNull(bean.allChfEurRates);
    }

    @Test
    public void allHistoricProviders(){
        assertNotNull(bean.allHistoricProviders);
    }

    @Test
    public void allProviders(){
        assertNotNull(bean.allProviders);
    }

    @Test
    public void deferredProvider(){
        assertNotNull(bean.deferredProvider);
    }

    @Test
    public void ecbProvider(){
        assertNotNull(bean.ecbProvider);
    }

    @Test
    public void ecbProviderAsList(){
        assertNotNull(bean.ecbProviderAsList);
    }

    @Test
    public void euroConverter(){
        assertNotNull(bean.euroConverter);
    }

    @AfterClass
    public static final void stopContainer() {
        SEContainer.stop();
    }
}
