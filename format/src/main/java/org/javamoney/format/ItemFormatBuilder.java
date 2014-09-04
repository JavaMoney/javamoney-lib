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

import org.javamoney.format.tokens.LiteralTokenStyleableItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * This class implements a builder that allows creating of {@link ItemFormat}
 * instances programmatically using a fluent API. The formatting hereby is
 * modeled by a concatenation of {@link StyleableItemFormatToken} instances. The same
 * {@link StyleableItemFormatToken} instances also are responsible for implementing the
 * opposite, parsing, of an item from an input character sequence. Each
 * {@link StyleableItemFormatToken} gets access to the current parsing location, and the
 * original and current character input sequence, modeled by the
 * {@link ItemParseContext}. Finall if parsing of a part failed, a
 * {@link StyleableItemFormatToken} throws an {@link ItemParseException} describing the
 * problem.
 * <p>
 * This class is not thread-safe and therefore should not be shared among
 * different threads.
 *
 * @param <T> the target type.
 * @author Anatole Tresch
 */
public class ItemFormatBuilder<T>{
    /**
     * The tokens to be used for formatting/parsing.
     */
    private List<StyleableItemFormatToken<T>> tokens = new ArrayList<StyleableItemFormatToken<T>>();
    /**
     * The localization configuration.
     */
    private LocalizationContext localizationContext;
    /**
     * The target type being parsed/formatted.
     */
    private Class<T> targetType;
    /**
     * The item factory to be used.
     */
    private ParseResultFactory<T> parseResultFactory;

    /**
     * Creates a new Builder.
     */
    public ItemFormatBuilder(){

    }

    /**
     * Creates a new Builder.
     *
     * @param targetType the target class.
     */
    public ItemFormatBuilder(Class<T> targetType){
        if(targetType == null){
            throw new IllegalArgumentException("targetType must not be null.");
        }
        this.targetType = targetType;
    }

    /**
     * Access the target class, which this formatter can handle with.
     *
     * @return the target class, never null.
     */
    public Class<T> getTargetType(){
        return this.targetType;
    }

    /**
     * Configure the format with the given {@link LocalizationContext}.
     *
     * @param style the localizationStyle to be applied.
     * @return the builder instance, for chaining.
     */
    public ItemFormatBuilder<T> withStyle(LocalizationContext style){
        if(style == null){
            throw new IllegalArgumentException("localizationStyle required.");
        }
        this.localizationContext = style;
        return this;
    }

    /**
     * Configure the format with the given target type.
     *
     * @param targetType the target type to be applied.
     * @return the builder instance, for chaining.
     */
    public ItemFormatBuilder<T> withTargetType(Class<T> targetType){
        if(targetType == null){
            throw new IllegalArgumentException("targetType required.");
        }
        this.targetType = targetType;
        return this;
    }

    /**
     * Add a {@link StyleableItemFormatToken} to the token list.
     *
     * @param token the token to add.
     * @return the builder, for chaining.
     */
    public ItemFormatBuilder<T> append(StyleableItemFormatToken<T> token){
        this.tokens.add(token);
        return this;
    }

    /**
     * Add a {@link StyleableItemFormatToken} to the token list.
     *
     * @param token the token to add.
     * @return the builder, for chaining.
     */
    public ItemFormatBuilder<T> append(String token){
        this.tokens.add(new LiteralTokenStyleableItem<T>(token));
        return this;
    }

    /**
     * The toal number of tokens.
     *
     * @return the number of tokens.
     */
    public int getTokenCount(){
        return this.tokens.size();
    }

    /**
     * Set the item factory used, to of the item parsed from the results in
     * the {@link ItemParseContext}.
     *
     * @param parseResultFactory the {@link ParseResultFactory}.
     * @return the builder, for chaining.
     */
    public ItemFormatBuilder<T> withItemFactory(ParseResultFactory<T> parseResultFactory){
        this.parseResultFactory = parseResultFactory;
        return this;
    }

    /**
     * Get the configured item factory.
     *
     * @return the {@link ParseResultFactory}.
     */
    public ParseResultFactory<T> getParseResultFactory(){
        return parseResultFactory;
    }

    /**
     * Clears the builder (tokens, item factory).
     */
    public void clear(){
        this.tokens.clear();
        this.parseResultFactory = null;
    }

    /**
     * Checks if the builder is ready for creating a {@link ItemFormatBuilder}.
     *
     * @return true, if a format instance can be of.
     * @see #build()
     */
    public boolean isBuildable(){
        return this.localizationContext != null && this.targetType != null && !this.tokens.isEmpty();
    }

    /**
     * Access all the token used for building up this format.
     *
     * @return the token used by this formatter, never {@code null}.
     */
    public List<StyleableItemFormatToken<T>> getTokens(){
        return Collections.unmodifiableList(this.tokens);
    }

    /**
     * Get the configuring {@link LocalizationContext}.
     *
     * @return the localizationStyle instance applied, never null.
     */
    public LocalizationContext getLocalizationContext(){
        return this.localizationContext;
    }

    /**
     * This method creates an {@link ItemFormat} based on this instance, hereby
     * using the given a {@link ParseResultFactory} to extract the item to be returned
     * from the {@link ItemParseContext}'s results.
     *
     * @return the {@link ItemFormat} instance, never null.
     */
    public ItemFormat<T> build(){
        if(this.parseResultFactory == null){
            return new TokenizedItemFormat<T>(targetType, localizationContext,
                                              new DefaultParseResultFactory<T>(targetType), tokens);
        }
        return new TokenizedItemFormat<T>(targetType, localizationContext, parseResultFactory, tokens);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString(){
        return "BuildableItemFormat [targetType=" + targetType + ", localizationStyle=" + localizationContext +
                ", tokens=" + tokens + "]";
    }

    /**
     * Adapter implementation that implements the {@link ItemFormat} interface
     * based on a {@link ItemFormatBuilder} and a {@link ParseResultFactory}.
     *
     * @param <T> the target type
     * @author Anatole Tresch
     */
    private final static class TokenizedItemFormat<T> implements ItemFormat<T>{
        /**
         * The tokens to be used for formatting/parsing.
         */
        private List<StyleableItemFormatToken<T>> tokens = new ArrayList<StyleableItemFormatToken<T>>();
        /**
         * The localization configuration.
         */
        private LocalizationContext style;
        /**
         * The target type being parsed/formatted.
         */
        private Class<T> targetType;
        /**
         * The item factory to be used.
         */
        private ParseResultFactory<T> parseResultFactory;

        /**
         * Creates a new instance.
         *
         * @param buildItemFormat    the base buildItemFormat, not null.
         * @param parseResultFactory the parseResultFactory to be used, not null.
         */
        public TokenizedItemFormat(Class<T> targetType, LocalizationContext style,
                                   ParseResultFactory<T> parseResultFactory, StyleableItemFormatToken<T>... tokens){
            this(targetType, style, parseResultFactory, Arrays.asList(tokens));
        }

        /**
         * Creates a new instance.
         *
         * @param buildItemFormat    the base buildItemFormat, not null.
         * @param parseResultFactory the parseResultFactory to be used, not null.
         */
        public TokenizedItemFormat(Class<T> targetType, LocalizationContext style,
                                   ParseResultFactory<T> parseResultFactory, List<StyleableItemFormatToken<T>> tokens){
            if(targetType == null){
                throw new IllegalArgumentException("Target Class must not be null.");
            }
            if(style == null){
                throw new IllegalArgumentException("LocalizationStyle must not be null.");
            }
            if(parseResultFactory == null){
                throw new IllegalArgumentException("ParseResultFactory must not be null.");
            }
            if(tokens == null || tokens.isEmpty()){
                throw new IllegalArgumentException("tokens must not be null or empty.");
            }
            this.targetType = targetType;
            this.style = style;
            this.parseResultFactory = parseResultFactory;
            this.tokens.addAll(tokens);
        }

        /*
         * (non-Javadoc)
         *
         * @see javax.money.format.ItemFormat#getTargetClass()
         */
        @Override
        public Class<T> getTargetClass(){
            return this.targetType;
        }

        /*
         * (non-Javadoc)
         *
         * @see javax.money.format.ItemFormat#getLocalizationStyle()
         */
        @Override
        public LocalizationContext getStyle(){
            return this.style;
        }

        /**
         * Print the item into an {@link Appendable}.
         *
         * @param appendable the appendable, not null
         * @param item       the item being formatted, not null
         * @throws IOException forwarded exception thrown by the {@link Appendable}.
         */
        public void print(Appendable appendable, T item, Locale locale) throws IOException{
            for(StyleableItemFormatToken<T> token : tokens){
                token.print(appendable, item, locale, style);
            }
        }

        /**
         * Formats the item as {@link String}.
         *
         * @param item the item being formatted, not null
         * @return The formatted String, not null.
         * @throws ItemFormatException If formatting fails.
         */
        public String format(T item, Locale locale){
            StringBuilder builder = new StringBuilder();
            try{
                print(builder, item, locale);
            }
            catch(IOException e){
                throw new ItemFormatException("Error foratting of " + item, e);
            }
            return builder.toString();
        }

        /**
         * Parses the input text into an item of type T.
         *
         * @param text The input text
         * @return the item to be parsed.
         * @throws ItemParseException If parsing failed.
         */
        public T parse(CharSequence text, Locale locale) throws ItemParseException{
            ItemParseContext<T> ctx = new ItemParseContext<T>(text, parseResultFactory);
            for(StyleableItemFormatToken<T> token : tokens){
                token.parse(ctx, locale, style);
                if(ctx.isComplete()){
                    return ctx.getItem();
                }
            }
            if(ctx.isComplete()){
                return ctx.getItem();
            }
            throw new ItemParseException("Parsing of item of type " + getTargetClass() + " failed from " + ctx);
        }

        /*
         * (non-Javadoc)
         *
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString(){
            return "BuildableItemFormat [targetType=" + targetType + ", localizationStyle=" + style +
                    ", parseResultFactory=" + parseResultFactory + ", tokens=" + tokens + "]";
        }

    }

}