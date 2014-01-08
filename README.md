DarkLord
========

DarkLord is a Java API for [Project Voldemort](http://www.project-voldemort.com/voldemort/). It uses official Voldemort Java API and provides additional abstraction.

[![build status](https://secure.travis-ci.org/lemiorhan/darklord.png)](http://travis-ci.org/lemiorhan/darklord) [![Coverage Status](https://coveralls.io/repos/lemiorhan/darklord/badge.png?branch=master)](https://coveralls.io/r/lemiorhan/darklord?branch=master)

It is named after the fictional Harry Potter villain [Lord Voldemort](http://en.wikipedia.org/wiki/Lord_Voldemort). The Dark Lord is used as a nick name for the Lord Voldemort. In the series, almost no witch or wizard dares to speak his name, instead referring to him by epithets such as "You-Know-Who", "He-Who-Must-Not-Be-Named" or "**the Dark Lord**".

Project Voldemort is a distributed data store that is designed as a key-value store used by LinkedIn for high-scalability storage.

Project Voldemort already has a Java API which is used by Voldemort internally. But DarkLord provides a tiny API as an abstraction on top of official Voldemort client API.

### Purpose

The current design of original Voldemort API is not TDD friendly. It's sometimes impossible to write unit tests using the API. The main aim to build a new abstraction on top of Voldemort API is to be able to have less-features but cleaner codebase.

### DarkLord Client API

*darklord-client* API provides a java client using the original Voldemort Java API.

### DarkLord Server API

*darklord-server* API provides a server implementation to startup Voldemort instances on local machines for testing purposes. This provides capability to develop or test without any need to Voldemort server installation.

### Main Dependencies

Java version:
* Java 7 

Libraries not available in central maven repository. You can access these libraries from project's `lib/` folder and copy to your local maven repository:
* Voldemort-1.3.0
* Je-4.1.17

### Usage

For maven users, you can add DarkLord-Client as the following.

```xml
<dependency>
    <groupId>com.agilistanbul</groupId>
    <artifactId>darklord-client</artifactId>
    <version>0.1</version>
</dependency>
```

And you can defined the dependency to DarkLord-Server as follows.

```xml
<dependency>
    <groupId>com.agilistanbul</groupId>
    <artifactId>darklord-server</artifactId>
    <version>0.1</version>
</dependency>
```

Please note that Voldemort-1.3.0 is not available in central maven repository. You should download and add it to your local repository by yourself.

### Configuring Server and Client

Voldemort server and clients can be configured by changing values of the predefined settings. The list of all possible settings are added to the codebase as sample files. You can check the settings of Voldemort server from [server.properties](../darklord-server/src/main/java/com/agilistanbul/darklord/sample/server.properties.sample), the settings of Voldemort client from [client.properties](../darklord-server/src/main/java/com/agilistanbul/darklord/client/client.properties.sample) and the settings of Voldemort admin client from [admin.properties](../darklord-server/src/main/java/com/agilistanbul/darklord/client/admin.properties.sample) files.

### Contributors

Copyright (C) [Lemi Orhan Ergin](http://www.lemiorhanergin.com)

### License

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this work except in compliance with the License. You may obtain a copy of the License in the LICENSE file, or at:

[http://www.apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.

[![Bitdeli Badge](https://d2weczhvl823v0.cloudfront.net/lemiorhan/darklord/trend.png)](https://bitdeli.com/free "Bitdeli Badge")

