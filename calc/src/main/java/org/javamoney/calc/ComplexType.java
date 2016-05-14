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
package org.javamoney.calc;

import java.io.Serializable;
import java.util.*;
import java.util.function.Predicate;

/**
 * Defines a {@link ComplexType} containing several results. Hereby the
 * different results are identified by arbitrary keys. Additionally each
 * {@link ComplexType} has a <i>leading</i> item that identifies the type of
 * result.<br/>
 * A {@link ComplexType} instance is defined to be implemented as immutable
 * object and therefore is very useful for modeling multidimensional results
 * objects or input parameters as they are common in financial applications.
 *
 * @author Anatole Tresch
 * @author Werner Keil
 */
public final class ComplexType implements Serializable {
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 4831291549617485148L;
    /**
     * The validation preciate to be used, for complex validations of the input.
     */
    private final Predicate<Map<String, Object>> validationPredicate;
    /**
     * The defines input parameters, mapped to their required base type.
     */
    @SuppressWarnings("rawtypes")
    private final Map<String, Class> typeDef = new HashMap<>();
    /**
     * Set of the parameters that are mandatory.
     */
    private final Set<String> typeRequired;
    /**
     * The name of the input type.
     */
    private final String name;

    /**
     * Constructor used by builder.
     *
     * @param builder the Builder instance used.
     */
    private ComplexType(Builder builder) {
        this.name = builder.name;
        this.typeDef.putAll(builder.typeDef);
        this.typeRequired = builder.typeRequired;
        this.validationPredicate = builder.validationPredicate;
    }

    /**
     * A {@link ComplexType}may have a type identifier that helps to identify,
     * what type of items object is returned.
     *
     * @return the {@link ComplexType}'s type, never null.
     */
    public String getName() {
        return this.name;
    }

    /**
     * This method allows to check if a key within the {@code CompoundType} is a
     * required value, so a corresponding {@link ComplexValue} is valid.
     *
     * @param key the key
     * @return true, if the corresponding value is required, false otherwise.
     */
    public boolean isRequired(String key) {
        return typeRequired.contains(key);
    }

    /**
     * Validates if the given {@link ComplexValue} defines all the attributes
     * as required by this {@link ComplexType} instance.
     *
     * @param compundValueMap the {@link Map} to be validated before a {@link ComplexValue}
     *                        is created.
     * @throws IllegalArgumentException if validation fails.
     */
    @SuppressWarnings("unchecked")
    public void validate(Map<String, Object> compundValueMap)
            throws MonetaryConstraintException {
        // Check for required fields to be present
        for (String key : this.typeRequired) {
            Object value = compundValueMap.get(key);
            if (value == null) {
                throw new MonetaryConstraintException("Required value '" + key
                        + "' of type " + typeDef.get(key) + " is missing.");
            }
        }
        // Check the fields type for all possible fields
        for (@SuppressWarnings("rawtypes")
        Map.Entry<String, Class> entry : this.typeDef.entrySet()) {
            Object value = compundValueMap.get(entry.getKey());
            if (value != null
                    && !entry.getValue().isAssignableFrom(value.getClass())) {
                throw new MonetaryConstraintException("Value  for '"
                        + entry.getKey()
                        + "' has invalid type type "
                        + value.getClass().getName() + ", required: "
                        + entry.getValue() + ".");
            }
        }
        if (validationPredicate != null
                && !validationPredicate.test(compundValueMap)) {
            throw new MonetaryConstraintException("Validation predicate failed '"
                    + validationPredicate + ".");
        }
    }

    /**
     * Builder for creating new instances of {@link ComplexType}.
     */
    public static final class Builder {
        /**
         * The validation preciate to be used, for complex validations of the input.
         */
        private Predicate<Map<String, Object>> validationPredicate;
        /**
         * The defines input parameters, mapped to their required base type.
         */
        @SuppressWarnings("rawtypes")
        private Map<String, Class> typeDef = new HashMap<>();
        /**
         * Set of the parameters that are mandatory.
         */
        private Set<String> typeRequired = new HashSet<>();
        /**
         * The name of the input type.
         */
        private String name;

        /**
         * Creates a new Builder.
         *
         * @param name the compound type's name, not null.
         */
        public Builder(String name) {
            Objects.requireNonNull(name);
            this.name = name;
        }

        public Builder setNameForInput(Class<?> type) {
            Objects.requireNonNull(type);
            this.name = type.getName() + "_in";
            return this;
        }

        public Builder setNameForOutput(Class<?> type) {
            Objects.requireNonNull(type);
            this.name = type.getName() + "_out";
            return this;
        }

        public Builder setName(String name) {
            Objects.requireNonNull(name);
            this.name = name;
            return this;
        }

        public Builder setValidationPredicate(
                Predicate<Map<String, Object>> predicate) {
            this.validationPredicate = predicate;
            return this;
        }

        public Builder addParameter(String key, Class<?> type) {
            this.typeDef.put(key, type);
            return this;
        }

        public Builder addRequiredParameter(String key, Class<?> type) {
            this.typeDef.put(key, type);
            this.typeRequired.add(key);
            return this;
        }

        public ComplexType build() {
            return new ComplexType(this);
        }
    }

    public void checkInput(ComplexValue input) {
        if (input == null) {
            throw new IllegalArgumentException("Input missing, required: "
                    + this);
        }
        if (!this.equals(input.getComplexType())) {
            throw new IllegalArgumentException("Invalid input, was " + input
                    + ", required: " + this);
        }
    }
}
