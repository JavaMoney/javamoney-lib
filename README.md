JavaMoney financial library, evolved from JSR 354 development
=============================================================

Java Money and Currency - Modules that are not part of JSR 354

The current project structure is as follows:

- [calc](./calc) Calculation module
- [common](./common) Common module
- [conversion](./conversion) Conversion module
- [extensions](./extensions) Extensions (Region, Currency Providers)
- [format](./format) Formatting module
- [validity](./validity) Validation module
- [bundles](./bundles) Bundles for particular Java Environments
  - [cdi](./cdi) CDI Support
  - [java-ee](./java-ee) Java EE Functionality
  - [java-se](./java-se) Java SE Functionality

JavaMoney-lib is a financial library that is built upon JSR 354 (or compatible libraries).
Whereas the JSR 354 API and reference implementation provides the fundamentals like monetary amounts, customizable currencies and interfaces for interoperation this library adds additional powerful APIs and SPIs that were implemented during JSR development as a proof of concept:

* **Currency Conversion**, supporting multiple conversion data providers, direct and derived (chained) rates, current, as well as historic conversion.
Also feed implementations from the European Central Bank (EZB) and the International Monetary Funds (IMF) were available, that support currency conversion back to 1995 for almost every ISO currency.
* Currency Services, provide a EE styled service, which allows querying currencies available and supports mapping between different currency naming schemes.
* **Formatting** provides an extendable formatting library that allows to define complex formatters, that can be configured in arbitrary ways using `LocalizationStyle` instances.
Also available is a flexible Builder for creating arbitrary complex formatters and parsers based on an ordered set of arbitrary tokens.
* **Region API** provides a forest (a set of trees) of regions. This allows to model regional hierarchies in a more flexible and intuitive way, than adding all functionalities into `java.util.Locale`.
By default the Unicode CLDR region tree, well as ISO countries defined by the 2- or 3-letter country code are available.
Of course, the API is fully extendible, so customer related regions such as legal units, customer segments etc can be mapped easily to this API, also.
* **Validity API** This API provides a generic API for accessing historic validity information for arbitrary items, and for relationships between items.
By default the API provides access to the historic relationship of currencies to countries using the Unicode CLDR data.
* **common** provides general functionality, such as an automatic data loader, which allows to update and locally cache arbitrary remote resources, and inform clients, if a new version of a resource was detected.
* **calc** finally provides a set of monetary calculations and formulas. The idea here is to provide a comprehensive set of algorithms and tools to perform complex financial mathematics.
* **bundles** finally provides the library in different flavors, targeting different execution environments:
  * **se** loading SPIs only using the JDK ServiceLoader.
  * **se-cdi** loading SPIs based on a Weld SE container.
  * **ee** loading SPIs based on CDI in a Java EE environment >= EE6.

To use the library you simply have to add the Maven dependency to your project (tbd)

```xml
<dependency>
  <groupId>org.javamoney</groupId>
  <artifactId>javamoney-${module}<artifactId>
  <versionId>the current version</version>
</dependency>
```

Everything is licenced under [Apache 2 Licence](./LICENCE.txt).

[![Build Status](https://api.travis-ci.org/JavaMoney/javamoney-lib.png?branch=master)](https://travis-ci.org/JavaMoney/javamoney-lib)
[![Coverage Status](https://coveralls.io/repos/JavaMoney/javamoney-lib/badge.png)](https://coveralls.io/r/JavaMoney/javamoney-lib)

[![Built with Maven](http://maven.apache.org/images/logos/maven-feather.png)](http://maven.org/)
