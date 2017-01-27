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
public class AmountInjectionTest {

    private static AmountInjectedBean bean;

    @BeforeClass
    public static final void startContainer() {
        SEContainer.start();
        bean = SEContainer.getInstance(AmountInjectedBean.class);
    }

    @Test
    public void testFixedScaleFactories(){
        assertNotNull(bean.fixedScaleFactories);
    }

    @Test
    public void testMoneyFactory(){
        assertNotNull(bean.moneyFactory);
    }

    @Test
    public void testPrecisionFactory(){
        assertNotNull(bean.precisionFactory);
    }

    @Test
    public void testProviderFactories(){
        assertNotNull(bean.providerFactories);
    }

    @Test
    public void testScaledFactory(){
        assertNotNull(bean.scaledFactory);
    }

    @AfterClass
    public static final void stopContainer() {
        SEContainer.stop();
    }
}
