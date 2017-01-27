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
public class FormatInjectionTest {

    private static FormatInjectedBean bean;

    @BeforeClass
    public static final void startContainer() {
        SEContainer.start();
        bean = SEContainer.getInstance(FormatInjectedBean.class);
    }

    @Test
    public void defaultFormat(){
        assertNotNull(bean.defaultFormat);
    }

    @Test
    public void defaultFormats(){
        assertNotNull(bean.defaultFormats);
    }

    @Test
    public void defaultWithCustomSeparator(){
        assertNotNull(bean.defaultWithCustomSeparator);
    }

    @Test
    public void germanFormats(){
        assertNotNull(bean.germanFormats);
    }

    @Test
    public void isoProviderFormat(){
        assertNotNull(bean.isoProviderFormat);
    }

    @AfterClass
    public static final void stopContainer() {
        SEContainer.stop();
    }
}
