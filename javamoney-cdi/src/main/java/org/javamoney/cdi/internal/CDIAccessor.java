/*
 * Copyright (c) 2012, 2013, Werner Keil, Credit Suisse (Anatole Tresch).
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
 * 
 * 
 * Contributors: Anatole Tresch - initial version.
 */
package org.javamoney.cdi.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.*;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Vetoed;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.CDI;
import javax.enterprise.util.TypeLiteral;
import javax.inject.Named;
import javax.naming.InitialContext;

/**
 * Small utility class that glues together service loading and CDI, including support for different CDI version.
 */
final class CDIAccessor {

    private static final Logger LOG = LoggerFactory.getLogger(CDIAccessor.class);

    private static final Instance<?> EMPTY_INSTANCE = new EmptyInstance<>();

    private CDIAccessor() {
    }

    private static <T> Instance<T> emptyInstance() {
        return Instance.class.cast(EMPTY_INSTANCE);
    }

    public static <T> Optional<T> getInstance(Class<T> instanceType,
                                              Annotation... qualifiers) {
        BeanManager man = getBeanManager();
        if (man != null) {
            return Optional.ofNullable(getInstanceByType(man, instanceType, qualifiers));
        }
        return Optional.empty();
    }

    private static BeanManager getBeanManager() {
        // 1 try JNDI
        try {
            InitialContext ctx = new InitialContext();
            BeanManager man = (BeanManager) ctx.lookup("comp:/env/BeanManager");
            if (man != null) {
                return man;
            }
        } catch (Exception e) {
            LoggerFactory.getLogger(CDIAccessor.class).debug("Unable to locate BeanManager from JNDI...", e);
        }
        try {
            Class.forName("javax.enterprise.inject.spi.CDI");
            // OK CDI 1.1 is loaded
            return CDI.current().getBeanManager();
        } catch (ClassNotFoundException e) {
            LoggerFactory.getLogger(CDIAccessor.class).debug("CDI accessor not available (CDI 1.0).");
        } catch (Exception e) {
            LoggerFactory.getLogger(CDIAccessor.class).debug("Unable to locate BeanManager from CDI providers...", e);
        }
        // 3 error, CDI not loaded...
        throw new IllegalStateException("CDI is not available.");
    }

    /**
     * Utility method allowing managed instances of beans to provide entry points
     * for non-managed beans (such as {@link org.jboss.weld.environment.se.WeldContainer}). Should only called
     * once Weld has finished booting.
     *
     * @param manager  the BeanManager to use to access the managed instance
     * @param type     the type of the Bean
     * @param bindings the bean's qualifiers
     * @return a managed instance of the bean
     * @throws IllegalArgumentException                               if the given type represents a type
     *                                                                variable
     * @throws IllegalArgumentException                               if two instances of the same qualifier
     *                                                                type are given
     * @throws IllegalArgumentException                               if an instance of an annotation that is
     *                                                                not a qualifier type is given
     * @throws javax.enterprise.inject.UnsatisfiedResolutionException if no beans can be resolved * @throws
     *                                                                AmbiguousResolutionException if the ambiguous dependency
     *                                                                resolution rules fail
     * @throws IllegalArgumentException                               if the given type is not a bean type of
     *                                                                the given bean
     */
    private static <T> T getInstanceByType(BeanManager manager, Class<T> type, Annotation... bindings) {
        final Bean<?> bean = manager.resolve(manager.getBeans(type));
        if (bean == null) {
            return null;
        }
        CreationalContext<?> cc = manager.createCreationalContext(bean);
        return type.cast(manager.getReference(bean, type, cc));
    }

    public static <T> Instance<T> getInstances(Class<T> instanceType,
                                               Annotation... qualifiers) {
        try {
            return getInstance(Instance.class).get().select(instanceType, qualifiers);
        } catch (Exception e) {
            LOG.info("Failed to load instances from CDI.", e);
            return emptyInstance();
        }
    }

    public static Set<?> getInstances(String name) {
        try {
            return getBeanManager().getBeans(name);
        } catch (Exception e) {
            LOG.info("Failed to load instances from CDI.", e);
            return Collections.emptySet();
        }

    }

    public static void fireEvent(Object evt, Annotation... qualifiers) {
        try {
            getBeanManager().fireEvent(evt, qualifiers);
        } catch (Exception e) {
            LOG.info("Failed to fire event from CDI.", e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T getNamedInstance(Class<T> type, String id) {
        try {
            Set<?> found = getBeanManager().getBeans(id);
            if (found.isEmpty()) {
                return null;
            }
            for (Object object : found) {
                if (type.isAssignableFrom(object.getClass())) {
                    return (T) object;
                }
            }
            return null;
        } catch (Exception e) {
            LOG.info("Failed to load instances from CDI.", e);
            return null;
        }

    }

    public static String getName(Object o) {
        if (o == null) {
            return "<null>";
        }
        Named named = o.getClass().getAnnotation(Named.class);
        if (named != null) {
            return named.value();
        }
        return o.getClass().getSimpleName();
    }

    @Vetoed
    private static final class EmptyInstance<T> implements Instance<T> {
        @Override
        public Instance<T> select(Annotation... annotations) {
            return this;
        }

        @Override
        public <U extends T> Instance<U> select(Class<U> uClass, Annotation... annotations) {
            return new EmptyInstance<>();
        }

        @Override
        public <U extends T> Instance<U> select(TypeLiteral<U> uTypeLiteral, Annotation... annotations) {
            return new EmptyInstance<>();
        }

        @Override
        public boolean isUnsatisfied() {
            return true;
        }

        @Override
        public boolean isAmbiguous() {
            return false;
        }

        @Override
        public void destroy(T t) {
        }

        @Override
        public Iterator<T> iterator() {
            return Collections.emptyIterator();
        }

        @Override
        public T get() {
            throw new NoSuchElementException();
        }
    }
}