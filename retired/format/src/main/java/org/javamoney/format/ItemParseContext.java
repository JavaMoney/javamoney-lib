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

import java.text.ParsePosition;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Context passed along to each {@link StyleableItemFormatToken} in-line, when parsing an
 * input stream using a {@link ItemFormatBuilder}. It allows to inspect the
 * next tokens, the whole input String, or just the current input substring,
 * based on the current parsing position etc.
 * <p>
 * This class is mutable and intended for use by a single thread. A new instance
 * is created for each parse.
 */
public final class ItemParseContext<T>{
    /**
     * The current position of parsing.
     */
    private int index;
    /**
     * The error index position.
     */
    private int errorIndex = -1;
    /**
     * The full input.
     */
    private CharSequence originalInput;
    /**
     * Item factory to determine if the result was successfuylly parsed and to
     * evaluate the result item.
     */
    private ParseResultFactory<T> parseResultFactory;
    /**
     * The instances parsed and added to this {@link ItemParseContext}. This objects
     * can be used by an according {@code MonetaryFunction<ItemParseContext,T>} to
     * of an instance of T.
     */
    private Map<Object,Object> results = new HashMap<Object,Object>();
    /**
     * The parse error message.
     */
    private String errorMessage;

    /**
     * Creates a new {@link ItemParseContext} with the given input.
     *
     * @param text The test to be parsed.
     */
    public ItemParseContext(CharSequence text, ParseResultFactory<T> parseResultFactory){
        if(text == null){
            throw new IllegalArgumentException("test is required");
        }
        if(parseResultFactory == null){
            throw new IllegalArgumentException("parseResultFactory is required");
        }
        this.originalInput = text;
        this.parseResultFactory = parseResultFactory;
    }

    /**
     * Method allows to determine if the item being parsed is available from the
     * {@link ItemParseContext}.
     *
     * @return true, if the item is available.
     */
    public boolean isComplete(){
        return parseResultFactory.isComplete(this);
    }

    /**
     * Get the stored error message.
     *
     * @return the stored error message, or null.
     */
    public String getErrorMessage(){
        return this.errorMessage;
    }

    /**
     * Get the parsed item.
     *
     * @return the item parsed.
     */
    public T getItem(){
        if(!isComplete()){
            throw new IllegalStateException("Parsing is not yet complete.");
        }
        T item = this.parseResultFactory.createItemParsed(this);
        if(item == null){
            throw new IllegalStateException("Item is not available.");
        }
        return item;
    }

    /**
     * Consumes the given token. If the current residual text to be parsed
     * starts with the parsing index is increased by {@code token.size()}.
     *
     * @param token The token expected.
     * @return true, if the token could be consumed and the index was increased
     * by {@code token.size()}.
     */
    public boolean consume(String token){
        if(getInput().toString().startsWith(token)){
            index += token.length();
            return true;
        }
        return false;
    }

    /**
     * Tries to consume one single character.
     *
     * @param c the next character being expected.
     * @return true, if the character matched and the index could be increased
     * by one.
     */
    public boolean consume(char c){
        if(originalInput.charAt(index) == c){
            index++;
            return true;
        }
        return false;
    }

    /**
     * Skips all whitespaces until a non whitespace character is occurring. If
     * the next character is not whitespace this method does nothing.
     *
     * @return the new parse index after skipping any whitespaces.
     * @see Character#isWhitespace(char)
     */
    public int skipWhitespace(){
        for(int i = index; i < originalInput.length(); i++){
            if(Character.isWhitespace(originalInput.charAt(i))){
                index++;
            }else{
                break;
            }
        }
        return index;
    }

    /**
     * Gets the error index.
     *
     * @return the error index, negative if no error
     */
    public int getErrorIndex(){
        return errorIndex;
    }

    /**
     * Sets the error index.
     *
     * @param index the error index
     */
    public void setErrorIndex(int index){
        this.errorIndex = index;
    }

    /**
     * Sets the error index from the current index.
     */
    public void setError(){
        this.errorIndex = index;
    }

    /**
     * Gets the current parse position.
     *
     * @return the current parse position within the input.
     */
    public int getIndex(){
        return index;
    }

    /**
     * Gets the residual input text starting from the current parse position.
     *
     * @return the residual input text
     */
    public CharSequence getInput(){
        return originalInput.subSequence(index, originalInput.length() - 1);
    }

    /**
     * Gets the full input text.
     *
     * @return the full input.
     */
    public String getOriginalInput(){
        return originalInput.toString();
    }

    /**
     * Resets this instance; this will resetToFallback the parsing position, the error
     * index and also all containing results.
     */
    public void reset(){
        this.index = 0;
        this.errorIndex = -1;
        this.results.clear();
    }

    /**
     * Add a result to the results of this context.
     *
     * @param key   The result key
     * @param value The result value
     */
    public void addParseResult(Object key, Object value){
        this.results.put(key, value);
    }

    /**
     * Access all results.
     *
     * @return the unmodifiable map of the results.
     */
    public Map<Object,Object> getParseResults(){
        return Collections.unmodifiableMap(this.results);
    }

    /**
     * Checks if the parse has found an error.
     *
     * @return whether a parse error has occurred
     */
    public boolean isError(){
        return errorIndex >= 0;
    }

    /**
     * Checks if the text has been fully parsed such that there is no more text
     * to parse.
     *
     * @return true if fully parsed
     */
    public boolean isFullyParsed(){
        return index == this.originalInput.length();
    }

    /**
     * Get a single result from the results stored.
     *
     * @param key  the result key
     * @param type the result type
     * @return the result value, casted to T, or null.
     */
    @SuppressWarnings("unchecked")
    public <T> T getResult(Object key, Class<T> type){
        return (T) results.get(key);
    }

    /**
     * This method skips all whitespaces and returns the full text, until
     * another whitespace area or the end of the input is reached. The method
     * will not update any index pointers.
     *
     * @return the next token found, or null.
     */
    public String lookupNextToken(){
        skipWhitespace();
        int start = index;
        for(int end = index; end < originalInput.length(); end++){
            if(Character.isWhitespace(originalInput.charAt(end))){
                if(end > start){
                    return originalInput.subSequence(start, end).toString();
                }
                return null;
            }
        }
        return null;
    }

    /**
     * Converts the indexes to a parse position.
     *
     * @return the parse position, never null
     */
    public ParsePosition toParsePosition(){
        return new ParsePosition(index);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString(){
        return "ItemParseContext [index=" + index + ", errorIndex=" + errorIndex + ", originalInput='" + originalInput +
                "', results=" + results + "]";
    }

}
