# InfluxDB-Java

[![Build Status](https://github.com/influxdata/influxdb-java/workflows/master/badge.svg)](https://github.com/influxdata/influxdb-java/actions) [中文](./README_ZH.md)

This library is for use with InfluxDB 1.x and openGemini. The original code is from https://github.com/influxdata/influxdb-java

Based on the original InfluxDB-Java code, we have implemented support for Lz4 compression during network data transmission to speed up data transmission efficiency.

## Features

When the server returns the queried data to the client, the data will be compressed first to increase the network transmission rate.  Now the default support for GZIP compression, the new lz4 compression option.

For more detail see [Query_LZ4](./QUERY_LZ4.md)

## Adding the library to your project

Since InfluxDB-Java has discontinued maintenance for version 1.x, to utilize the aforementioned new features, you need to compile its JAR file locally and integrate it into your project. 

Our team only maintains support for this new feature. If you encounter other issues while using InfluxDB-Java, we recommend switching to [openGemini-Java-Client](https://github.com/openGemini/opengemini-client-java). The client remains compatible with Java 8 runtime environments, but compiling it requires Java 17.

## Quick start

```Java
final String serverURL = "http://127.0.0.1:8086", username = "root", password = "root";
final InfluxDB influxDB = InfluxDBFactory.connect(serverURL, username, password);

// enable query for lz4 compress
influxDB.enableLz4(); 
// query data
QueryResult queryResult = influxDB.query(new Query("SELECT * FROM h2o_feet"));
```

## Contribute

For version change history have a look at [ChangeLog](https://github.com/influxdata/influxdb-java/blob/master/CHANGELOG.md).

### Build Requirements

* Java 1.8+
* Maven 3.5+
* Docker (for Unit testing)

Then you can build influxdb-java with all tests with:

```bash
$> export INFLUXDB_IP=127.0.0.1

$> mvn clean install

```

There is a shell script running InfluxDB and Maven from inside a Docker container and you can execute it by running:

```bash
$> ./compile-and-test.sh
```

## Useful links

* [Manual](MANUAL.md) (main documentation);
* [InfluxDB Object Mapper](INFLUXDB_MAPPER.md);
* [Query Builder](QUERY_BUILDER.md);
* [QUERY_LZ4](./QUERY_LZ4.md);
* [FAQ](FAQ.md);
* [Changelog](CHANGELOG.md).

## License

```license
The MIT License (MIT)

Copyright (c) 2014 Stefan Majer

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
