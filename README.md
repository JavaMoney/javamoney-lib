Java Money and Currency - Libraries extending JSR 354
==============================================================
 
JavaMoney-lib provides extensions and libraries built upon JSR 354 (compatible implementations).
Whereas the JSR 354 API and reference implementation provides the fundamentals like monetary amounts, customizable currencies 
and interfaces for interoperation this library adds additional powerful APIs and SPIs that were implemented during JSR 
development as a proof of concept:

* [**Calculation**](calc) provides a set of monetary calculations and formulas. The idea here is to provide a comprehensive set of algorithms and tools to perform complex financial mathematics.
* [**Integration**](integration) provides bootstraping extensions that change the JavaMoney runtime capabilities:
 * [**javamoney-cdi**](integration/javamoney-cdi) Integrates JavaMoney with CDI, so SPIs can as well be loaded from CDI.

Currently the following modules are retired (or archived, not actively maintained):
* [**Formatting**](retired/format) provides an extendble formatting library that allows to define complex formatters, that can be configured in arbitrary ways using `LocalizationStyle` instances.
Also available is a flexible Builder for creating arbitrary complex formatters and parsers based on an ordered set of arbitrary tokens.
* [**javamoney-all**](retired/javamoney-all) provides a pom that imports everything you need (API, RI and the released JavaMoney libraries).
* [**Region API**](retired/regions) provides a forest (a set of trees) of regions. This allows to model regional hierarchies in a more flexible and intuitive way, than adding all functionalities into `java.util.Locale`.
By default the Unicode CLDR region tree, well as ISO countries defined by the 2- or 3-letter country code are available.
Of course, the API is fully extendible, so customer related regions such as legal units, customer segments etc can be mapped easily to this API, also.
* [**Validity API**](retired/validity) This API provides a generic API for accessing historic validity information for arbitrary items, and for relationships between items.
By default the API provides access to the historic relationship of currencies to countries using the Unicode CLDR data.
* [**Data**](retired/cldr-data) JavaMoney Data

To use the library you simply have to add the Maven dependency to your project (tbd)

```xml
<dependency>
  <groupId>org.javamoney</groupId>
  <artifactId>javamoney-${module}<artifactId>
  <versionId>the current version</version>
</dependency>
```

Authors and Contributors
------------------------
Different people have contributed to this project. During the development of JSR 354, @atsticks and @keilw founded this project and were also the main contributors. Nevertheless everybody is really welcome to help us, to make this library more feasible and add features.

javamoney-shelter
-----------------
The javamoney [shelter module](http://javamoney.github.io/shelter.html) is for testing out new features or modules before they may enter the official javamoney library.

Support or Contact
------------------
Having trouble with Pages? Check out [documentation](http://javamoney.org) or contact support@javamoney.org.

Everything is licenced under [Apache 2 Licence](LICENSE.txt).

[![Build Status](https://api.travis-ci.org/JavaMoney/javamoney-lib.png?branch=master)](https://travis-ci.org/JavaMoney/javamoney-lib)
[![License](http://img.shields.io/badge/license-Apache2-red.svg)](http://opensource.org/licenses/apache-2.0) 

[![Built with Maven](http://maven.apache.org/images/logos/maven-feather.png)](http://maven.org/)
