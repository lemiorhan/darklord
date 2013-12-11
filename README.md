DarkLord
========
[![build status](https://secure.travis-ci.org/lemiorhan/darklord.png)](http://travis-ci.org/lemiorhan/darklord)

DarkLord is a Java API for [Project Voldemort](http://www.project-voldemort.com/voldemort/). It uses official Voldemort Java API and provides additional abstraction.

It is named after the fictional Harry Potter villain [Lord Voldemort](http://en.wikipedia.org/wiki/Lord_Voldemort). The Dark Lord is used as a nick name for the Lord Voldemort. In the series, almost no witch or wizard dares to speak his name, instead referring to him by epithets such as "You-Know-Who", "He-Who-Must-Not-Be-Named" or "**the Dark Lord**".

Project Voldemort is a distributed data store that is designed as a key-value store used by LinkedIn for high-scalability storage.

Project Voldemort already has a Java API which is used by Voldemort internally. But DarkLord provides a tiny API as an abstraction on top of official Voldemort client API.

### Purpose

The current design of original Voldemort API is not TDD friendly. The main aim to build such an API is build Voldemort-dependent software by using test driven development.

### Client API

*darklord-client* API provides a java client using the original Voldemort Java API.

### Server API

*darklord-server* API provides a server implementation to startup on local machines for testing purposes. This provides capability to develop or test without any need to Voldemort server installation.

### Main Dependencies

* Java 7
* Voldemort-1.3.0 

### Contributors

Copyright (C) Lemi Orhan Ergin

### License

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this work except in compliance with the License. You may obtain a copy of the License in the LICENSE file, or at:

[http://www.apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.

[![Bitdeli Badge](https://d2weczhvl823v0.cloudfront.net/lemiorhan/darklord/trend.png)](https://bitdeli.com/free "Bitdeli Badge")

