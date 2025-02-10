# influxdb-java

[![Build Status](https://github.com/influxdata/influxdb-java/workflows/master/badge.svg)](https://github.com/influxdata/influxdb-java/actions)

在原来代码基础上，我们实现了在网络数据传输时支持Lz4 压缩，加速数据传输效率。

## 特性

当服务器将查询的数据返回给客户端时，会先对数据进行压缩，以提高网络传输速率。默认支持GZIP压缩，现新增lz4压缩选项。

## 使用该库

由于InfluxDB-Java已停止维护，如需使用上述新特性，需在本地编译生成JAR文件并引入到项目中。我们团队仅负责维护该新特性，若在使用InfluxDB-Java过程中遇到其他问题，建议切换至[openGemini-Java-Client](https://github.com/openGemini/opengemini-client-java)，该客户端仍兼容Java 8运行环境，但编译时需使用Java 17+。

了解更多，见[QUERY_LZ4](./QUERY_LZ4.md)

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
