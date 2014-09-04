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
package org.javamoney.validity;

import java.io.Serializable;
import java.util.TimeZone;

import org.javamoney.calc.function.MonetaryPredicate;
import org.javamoney.validity.spi.ValidityProviderSpi;

/**
 * For accessing {@link ValidityInfo} instances from the {@link Validities}
 * singleton, instances of this class must be created an configured.
 * <p>
 * This class and its subclasses should be immutable, thread-safe and
 * {@link Serializable}.
 * <p>
 * nOTE: The class and its builder are available for subclassing, since more
 * advanced queries may be created with them.
 *
 * @param <T> the item type, on which validities are defined.
 * @author Anatole Tresch
 */
public class RelatedValidityQuery<T, R> extends ValidityQuery<T>{

    /**
     * The base item.
     */
    private Class<R> relatedToType;
    /**
     * Get the predicate to filter the validity items to be returned.
     */
    private MonetaryPredicate<R> relatedToPredicate;

    /**
     * Constructor.
     *
     * @param validityType     the validity type, not {@code null}.
     * @param itemType         the item type, not {@code null}.
     * @param item             the item constraint.
     * @param from             the starting UTC timestamp.
     * @param to               the ending UTC timestamp.
     * @param targetTimezoneId the target timezone ID.
     */
    protected RelatedValidityQuery(ValidityType validityType, Class<T> itemType, T item, String validityProviderId,
                                   Long from, Long to, String targetTimezoneId, Class<R> relatedToType,
                                   MonetaryPredicate<R> relatedToPredicate){
        super(validityType, itemType, item, validityProviderId, from, from, targetTimezoneId);
        if(relatedToType == null){
            throw new IllegalArgumentException("relatedToType required");
        }
        this.relatedToType = relatedToType;
        this.relatedToPredicate = relatedToPredicate;
    }

    /**
     * Access the related items for which the validities are queried.
     *
     * @return the item The item for which the validity information is queried.
     * @see #getRelatedToType()
     */
    public final MonetaryPredicate<R> getRelatedToPredicate(){
        return relatedToPredicate;
    }

    /**
     * Access the item class for which the validities are queried. It is also
     * possible query only for a related {@link Class} instance, which will
     * select all validities of all items of that type.<br/>
     * If {@link #relatedToInstance} was set, this method returns the class of
     * {@link #relatedToInstance}, so implementors of
     * {@link ValidityProviderSpi} must additionally explicitly check, if
     * {@link #getRelatedToInstance()} is returning any non {@code null} value.
     *
     * @return the item The item for which the validity information is queried.
     * @see #getRelatedToInstance()
     */
    public final Class<R> getRelatedToType(){
        return relatedToType;
    }

    @Override
    public String toString(){
        return "RelatedValidityQuery [itemType=" + getItemType() + ", item=" + getItem() + "relatedType=" +
                getItemType() + ", relatedPredicate=" + getRelatedToPredicate() + ", from=" + getFrom() + ", to=" +
                getTo() + ", targetTimezoneId=" + getTargetTimezoneId() + ", validitySource=" + getValiditySource() +
                ", validityType=" + getValidityType() + "]";
    }

    /**
     * For accessing {@link ValidityInfo} instances from the {@link Validities}
     * singleton, instances of this class must be created an configured.
     * <p>
     * This class is immutable, thread-safe and {@link Serializable}.
     *
     * @param <T> the item type, on which validities are defined.
     * @author Anatole Tresch
     */
    public static class Builder<T, R> extends ValidityQuery.Builder<T>{

        /**
         * The base item.
         */
        protected Class<R> relatedToType;
        /**
         * Get the predicate to filter the validity items to be returned.
         */
        protected MonetaryPredicate<R> relatedToPredicate;

        /**
         * Constructor.
         *
         * @param validityType the validity type, not {@code null}.
         * @param itemType     the item type, not {@code null}.
         */
        public Builder(ValidityType validityType, Class<T> itemType, Class<R> relatedToType){
            super();
            withItemType(itemType);
            withValidityType(validityType);
            withRelatedToType(relatedToType);
        }

        /**
         * Constructor.
         */
        public Builder(){
            super();
        }

        /**
         * Sets the related item class for which the related validities are
         * queried. It is also possible query only for a related {@link Class}
         * instance, which will select all validities of all items of that type.<br/>
         * If {@link #relatedToInstance} was set, this method returns the class
         * of {@link #relatedToInstance}, so implementors of
         * {@link ValidityProviderSpi} must additionally explicitly check, if
         * {@link #getRelatedToInstance()} is returning any non {@code null}
         * value.
         *
         * @return the item The item for which the validity information is
         * queried.
         * @see #getRelatedToInstance()
         */
        public Builder<T,R> withRelatedToType(Class<R> relatedToType){
            if(relatedToType == null){
                throw new IllegalArgumentException("relatedToType required");
            }
            this.relatedToType = relatedToType;
            return this;
        }

        /**
         * Sets a predicate to constraint the relatedTo items returned.
         *
         * @param relatedToPredicate
         * @return the Builder, for chaining.
         */
        public Builder<T,R> withRelatedToPredicate(MonetaryPredicate<R> relatedToPredicate){
            this.relatedToPredicate = relatedToPredicate;
            return this;
        }

        /**
         * Builds a new instance of {@link RelatedValidityQuery}. throws
         * IllegalArgumentException if the data provided is not sufficient zo
         * of the instance.
         */
        @Override
        public RelatedValidityQuery<T,R> build(){
            return new RelatedValidityQuery<T,R>(validityType, itemType, item, validitySource, from, to,
                                                 targetTimezoneId, relatedToType, relatedToPredicate);
        }

        /*
         * (non-Javadoc)
         *
         * @see javax.money.ext.ValidityQuery.Builder#withFrom(long)
         */
        @Override
        public RelatedValidityQuery.Builder<T,R> withFrom(long timestamp){
            super.withFrom(timestamp);
            return this;
        }

        /*
         * (non-Javadoc)
         *
         * @see javax.money.ext.ValidityQuery.Builder#withItem(java.lang.Object)
         */
        @Override
        public RelatedValidityQuery.Builder<T,R> withItem(T item){
            super.withItem(item);
            return this;
        }

        /*
         * (non-Javadoc)
         *
         * @see
         * javax.money.ext.ValidityQuery.Builder#withItemType(java.lang.Class)
         */
        @Override
        public RelatedValidityQuery.Builder<T,R> withItemType(Class<T> itemType){
            super.withItemType(itemType);
            return this;
        }

        /*
         * (non-Javadoc)
         *
         * @see
         * javax.money.ext.ValidityQuery.Builder#withTargetTimezone(java.util
         * .TimeZone)
         */
        @Override
        public RelatedValidityQuery.Builder<T,R> withTargetTimezone(TimeZone timezone){
            super.withTargetTimezone(timezone);
            return this;
        }

        /*
         * (non-Javadoc)
         *
         * @see
         * javax.money.ext.ValidityQuery.Builder#withTargetTimezoneID(java.lang
         * .String)
         */
        @Override
        public RelatedValidityQuery.Builder<T,R> withTargetTimezoneID(String timezoneÎD){
            super.withTargetTimezoneID(timezoneÎD);
            return this;
        }

        /*
         * (non-Javadoc)
         *
         * @see javax.money.ext.ValidityQuery.Builder#withTo(long)
         */
        @Override
        public RelatedValidityQuery.Builder<T,R> withTo(long timestamp){
            super.withTo(timestamp);
            return this;
        }

        /*
         * (non-Javadoc)
         *
         * @see
         * javax.money.ext.ValidityQuery.Builder#withValiditySource(java.lang
         * .String)
         */
        @Override
        public RelatedValidityQuery.Builder<T,R> withValiditySource(String source){
            super.withValiditySource(source);
            return this;
        }

        /*
         * (non-Javadoc)
         *
         * @see
         * javax.money.ext.ValidityQuery.Builder#withValidityType(javax.money
         * .ext.ValidityType)
         */
        @Override
        public RelatedValidityQuery.Builder<T,R> withValidityType(ValidityType validityType){
            super.withValidityType(validityType);
            return this;
        }

        /*
         * (non-Javadoc)
         *
         * @see javax.money.ext.ValidityQuery.Builder#toString()
         */
        @Override
        public String toString(){
            return "RelatedValidityQuery.Builder [itemType=" + itemType + ", item=" + item + "relatedType=" +
                    relatedToType + ", relatedPredicate=" + relatedToPredicate + ", from=" + from + ", to=" + to +
                    ", targetTimezoneId=" + targetTimezoneId + ", validitySource=" + validitySource +
                    ", validityType=" + validityType + "]";
        }
    }

}
