Java Money and Currency - Libraries extending JSR 354
==============================================================
 
JavaMoney-lib provides extensions and libraries built upon JSR 354 (compatible implementations).
Whereas the JSR 354 API and reference implementation provides the fundamentals like monetary amounts,
customizable currencies and interfaces for interoperation this library adds additional powerful
APIs and SPIs that were implemented during JSR development as a proof of concept (artifactIds in brackets):

* **Calculation** (javamoney-calc) provides a set of monetary calculations and formulas. The idea here is to provide a comprehensive set of algorithms and tools to perform complex financial mathematics.
* **Exchange** (javamoney-exchange) provides further conversion exchange resources such as FRD and Yahoo.
  * **FRB** (javamoney-exchange-frb) provides conversion exchange for US Federal Reserve Department FRD.
  * **Yahoo** (javamoney-exchange-yahoo) provides conversion exchange using Yahoo financial APIs.
* **javamoney-cdi** (javamoney-cdi) Integrates JavaMoney with Jakarta CDI (Java SE mode).

To use the library you simply have to add the Maven dependency to your project:

```xml
<dependency>
  <groupId>org.javamoney.lib</groupId>
  <artifactId>${artifactId}</artifactId>
  <versionId>the current library version</version>
</dependency>
```

Authors and Contributors
------------------------
Different people have contributed to this project. During the development of JSR 354, @atsticks and @keilw
founded this project and were also the main contributors. Nevertheless everybody is really welcome to help
us, to make this library more feasible and add features.

Special thanks go to @manuela-grindei for her awsome help implementing financial formulas.

Release Notes
-------------

* **1.0** First release of the libraries.


javamoney-shelter
-----------------
The javamoney [shelter module](http://javamoney.github.io/shelter.html) is for testing out new features or
modules before they may enter the official javamoney library.

Support or Contact
------------------
Having trouble with Pages? Check out [documentation](http://javamoney.org) or contact support@javamoney.org.

Everything is licenced under [Apache 2 Licence](LICENSE.txt).

[![Build Status](https://api.travis-ci.org/JavaMoney/javamoney-lib.png?branch=master)](https://travis-ci.org/JavaMoney/javamoney-lib)
[![License](http://img.shields.io/badge/license-Apache2-red.svg)](http://opensource.org/licenses/apache-2.0) 

[![Built with Maven](http://maven.apache.org/images/logos/maven-feather.png)](http://maven.org/)
