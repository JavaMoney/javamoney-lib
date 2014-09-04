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

import org.javamoney.format.spi.ItemFormatFactorySpi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.money.AbstractContext;
import javax.money.AbstractContextBuilder;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class enhances the localization mechanisms as defined by {@link Locale}.
 * It is used to configure formatting/parsing ( {@link ItemFormat} instances )
 * with arbitrary parameters, thus also supporting very complex formatting
 * scenarios. The configuration parameters possible are either determined
 * <ul>
 * <li>by the several {@link StyleableItemFormatToken} added in sequence, and the
 * {@link ParseResultFactory}, when using an {@link ItemFormat} instance created with
 * the {@link ItemFormatBuilder}.
 * <li>by the preoconfigured and provided {@link ItemFormat} instance, provided
 * by an implementation of the {@link ItemFormatFactorySpi}.
 * <p>
 * Further more when parsing amounts, it is often desirable to control the
 * checks for the required decimals of the given target currency (aka lenient
 * fraction parsing). In even more advanced use cases, also additional
 * configuration attributes may be necessary to be passed to a formatter/parser
 * instance.
 * <p>
 * Finally instances of {@link LocalizationContext} can be registered to the
 * internal style cache, which allows to share the according styles, by
 * accessing them using {@link #of(Class)} of {@link #of(Class, String)}.
 * <p>
 * This class is thread safe, immutable and {@link Serializable}. The containing
 * {@link Builder} class however is NOT thread-safe.
 *
 * @author Anatole Tresch
 */
public final class LocalizationContext extends AbstractContext implements Serializable{

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 8612440355369457473L;

    private static final Logger LOG = LoggerFactory.getLogger(LocalizationContext.class);

    /**
     * the default id.
     */
    public static final String DEFAULT_ID = "default";

    /**
     * The style's name, by default ({@link #DEFAULT_ID}.
     */
    private String id = DEFAULT_ID;

    /**
     * The style's target type.
     */
    private Class<?> targetType;


    /**
     * The shared map of LocalizationStyle instances.
     */
    private static final Map<String,LocalizationContext> STYLE_MAP =
            new ConcurrentHashMap<String,LocalizationContext>();

    /**
     * Access a cached <i>default</i> style for a type. This equals to
     * {@link #of(Class, String)}, hereby passing
     * {@link LocalizationContext#DEFAULT_ID} as {@code styleId}.
     *
     * @param targetType The target type, not {@code null}.
     * @param styleId    The style's id, not {@code null}.
     * @return the according style, if a corresponding style is cached, or
     * {@code null].
     */
    public static final LocalizationContext of(Class<?> targetType, String styleId){
        return STYLE_MAP.get(getKey(targetType, styleId));
    }

    /**
     * Access a cached <i>default</i> style for a type. This equals to
     * {@link #of(Class, String)}, hereby passing
     * {@link LocalizationContext#DEFAULT_ID} as {@code styleId}.
     *
     * @param targetType The target type, not {@code null}.
     * @return the according style, if a corresponding style is cached, or
     * {@code null].
     */
    public static final LocalizationContext of(Class<?> targetType){
        return of(targetType, LocalizationContext.DEFAULT_ID);
    }

    /**
     * Collects all styles currently registered within the style cache for the
     * given type.
     *
     * @param targetType the target type, not {@code null}.
     * @return a set of style identifiers for the given type, never null.
     */
    public static Collection<String> getSupportedStyleIds(Class<?> targetType){
        Set<String> result = new HashSet<String>();
        String className = targetType.getName();
        for(String key : STYLE_MAP.keySet()){
            int index = key.indexOf('_');
            if(className.equals(key.substring(0, index))){
                result.add(key.substring(index + 1));
            }
        }
        return result;
    }

    /**
     * Access a cached style for a type.
     *
     * @param targetType The target type, not {@code null}.
     * @param styleId    The style's id, not {@code null}.
     * @return the according style, if a corresponding style is cached, or
     * {@code null].
     */
    private static String getKey(Class<?> targetType, String styleId){
        return targetType.getName() + "_" + (styleId != null ? styleId : "default");
    }

    /**
     * Creates a new instance of a style.
     *
     * @param builder The style's builder (not null).
     */
    private LocalizationContext(Builder builder){
        super(builder);
        this.id = builder.id;
        this.targetType = builder.targetType;
    }

    /**
     * Get the style's identifier, not null.
     *
     * @return the style's id.
     */
    public String getId(){
        return id;
    }

    /**
     * Get the style's target type used.
     *
     * @return the translation (default) locale
     */
    public final Class<?> getTargetType(){
        return this.targetType;
    }

    /**
     * Access the ItemFormat class that should be instantiated by default for formatting this style.
     *
     * @return the default item format class, or null.
     */
    public final Class<? extends ItemFormat<?>> getDefaultItemFormatClass(){
        String defaultItemFormatClassName = getText("defaultItemFormatClassName");
        if(defaultItemFormatClassName != null){
            try{
                return (Class<? extends ItemFormat<?>>) Class.forName(defaultItemFormatClassName);
            }
            catch(Exception e){
                LOG.error("Failed to load ItemFormat class: " + defaultItemFormatClassName, e);
            }
        }
        return getAny("defaultItemFormatClass", Class.class);
    }


    /**
     * Method allows to check, if a given style is a default style, which is
     * equivalent to a style id equal to {@link #DEFAULT_ID}.
     *
     * @return true, if the instance is a default style.
     */
    public boolean isDefaultStyle(){
        return DEFAULT_ID.equals(getId());
    }

    /**
     * Builder to of new instances of {@link LocalizationContext}.
     * <p>
     * This class is not thread-safe and should not be used in multiple threads.
     * However {@link LocalizationContext} instances created can securely shared
     * among threads.
     *
     * @author Anatole Tresch
     */
    public static final class Builder extends AbstractContextBuilder<Builder,LocalizationContext>{
        /**
         * The style's id.
         */
        private String id = DEFAULT_ID;

        /**
         * The formated type.
         */
        private Class<?> targetType;

        /**
         * The default class of ItemFormat to be used.
         */
        private Class<? extends ItemFormat<?>> defaultItemFormatClass;

        /**
         * Constructor.
         *
         * @param targetType the target type, not null.
         */
        public Builder(Class<?> targetType){
            this.targetType = targetType;
            setId(DEFAULT_ID);
        }

        /**
         * Constructor.
         *
         * @param styleId    The style's id.
         * @param targetType The target TYPE
         * @return the {@link LocalizationContext} created.
         */
        public Builder(Class<?> targetType, String styleId){
            setId(styleId);
            this.targetType = targetType;
        }

        /**
         * Creates a new instance of a style. This method will copy all
         * attributes and properties from the given style. The style created
         * will not be read-only, even when the base style is read-only.
         *
         * @param baseContext The style to be used as a base style.
         */
        public Builder(LocalizationContext baseContext){
            importContext(baseContext);
            this.id = baseContext.getId();
            this.targetType = baseContext.getTargetType();
        }

        /**
         * Creates a new instance of {@link LocalizationContext}.
         *
         * @return a new instance of {@link LocalizationContext}, never
         * {@code null}
         * @throws IllegalStateException if this builder can not of a new instance.
         */
        public LocalizationContext build(){
            return build(false);
        }

        /**
         * Creates a new instance of {@link LocalizationContext}.
         *
         * @param register flag for registering the style into the global cache.
         * @return a new instance of {@link LocalizationContext}, never
         * {@code null}
         * @throws IllegalStateException if this builder can not of a new instance.
         */
        public LocalizationContext build(boolean register){
            LocalizationContext style = new LocalizationContext(this);
            if(register){
                STYLE_MAP.put(getKey(this.targetType, this.id), style);
            }
            return style;
        }

        /**
         * Constructor for a <i>default</i> style.
         */
        public Builder(){
        }

        /**
         * Method allows to check, if a given style is a default style, which is
         * equivalent to a style {@code id} equal to {@link #DEFAULT_ID}.
         *
         * @return {@code true}, if the instance is a <i>default</i> style.
         */
        public boolean isDefaultStyle(){
            return DEFAULT_ID.equals(getId());
        }

        /**
         * Sets the style's id.
         *
         * @param id the style's id, not {@code null}.
         * @return this instance, for chaining.
         */
        public Builder setId(String id){
            Objects.requireNonNull(id, "style id required.");
            this.id = id;
            return this;
        }


        /**
         * Sets the given targetType.
         *
         * @param targetType The instance's targetType, not {@code null}.
         * @return The Builder instance for chaining.
         */
        public <T> Builder setTargetType(Class<?> targetType){
            Objects.requireNonNull(targetType, "targetType required.");
            this.targetType = targetType;
            return this;
        }

        /**
         * Sets the default formatter to be used by this style.
         *
         * @param itemFormatClass the default formatter class, not null.
         * @param <T>             the target type
         * @return The Builder instance for chaining.
         */
        public <T> Builder setDefaultItemFormat(Class<? extends ItemFormat<?>> itemFormatClass){
            Objects.requireNonNull(itemFormatClass);
            this.defaultItemFormatClass = itemFormatClass;
            return this;
        }

        /**
         * Get the style's identifier, not {@code null}.
         *
         * @return the style's id.
         */
        public String getId(){
            return id;
        }

    }

}
