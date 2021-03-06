import org.javamoney.moneta.convert.frb.USFederalReserveRateProvider;

/*
 * Copyright (c) 2012, 2020, Werner Keil, Anatole Tresch and others.
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
 * Contributors: @keilw
 */
module org.javamoney.moneta.convert.frb {
    requires java.xml;
    requires java.xml.bind;
    requires org.javamoney.moneta;
    requires org.javamoney.moneta.convert;
    requires static org.osgi.core;
    requires static org.osgi.compendium;
    requires static org.osgi.annotation;
    provides javax.money.convert.ExchangeRateProvider with
            USFederalReserveRateProvider;
    uses org.javamoney.moneta.spi.loader.LoaderService;
    uses org.javamoney.moneta.spi.MonetaryAmountProducer;
}