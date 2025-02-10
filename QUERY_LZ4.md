# query by lz4 

[![Build Status](https://github.com/influxdata/influxdb-java/workflows/master/badge.svg)](https://github.com/influxdata/influxdb-java/actions)
[![codecov.io](http://codecov.io/github/influxdata/influxdb-java/coverage.svg?branch=master)](http://codecov.io/github/influxdata/influxdb-java?branch=master)
[![Issue Count](https://codeclimate.com/github/influxdata/influxdb-java/badges/issue_count.svg)](https://codeclimate.com/github/influxdata/influxdb-java)

When the server returns the queried data to the client, the data will be compressed first to increase the network transmission rate.  Now the default support for GZIP compression, the new lz4 compression option


## Use-pattern

* Querying data for lz4 compress:
  * influxDB.enableLz4(); enable
  * influxDB.disableLz4(); disable
  * nfluxDB.isLz4Enabled(); query enable or disable


## Quick start

```Java
// Create an object to handle the communication with InfluxDB.
// (best practice tip: reuse the 'influxDB' instance when possible)
final String serverURL = "http://127.0.0.1:8086", username = "root", password = "root";
final InfluxDB influxDB = InfluxDBFactory.connect(serverURL, username, password);

// Create a database...
// https://docs.influxdata.com/influxdb/v1.7/query_language/database_management/
String databaseName = "NOAA_water_database";
influxDB.query(new Query("CREATE DATABASE " + databaseName));
influxDB.setDatabase(databaseName);

// ... and a retention policy, if necessary.
// https://docs.influxdata.com/influxdb/v1.7/query_language/database_management/
String retentionPolicyName = "one_day_only";
influxDB.query(new Query("CREATE RETENTION POLICY " + retentionPolicyName
        + " ON " + databaseName + " DURATION 1d REPLICATION 1 DEFAULT"));
influxDB.setRetentionPolicy(retentionPolicyName);

// Enable batch writes to get better performance.
influxDB.enableBatch(
    BatchOptions.DEFAULTS
      .threadFactory(runnable -> {
        Thread thread = new Thread(runnable);
        thread.setDaemon(true);
        return thread;
      })
);

// Close it if your application is terminating or you are not using it anymore.
Runtime.getRuntime().addShutdownHook(new Thread(influxDB::close));

// Write points to InfluxDB.
influxDB.write(Point.measurement("h2o_feet")
    .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
    .tag("location", "santa_monica")
    .addField("level description", "below 3 feet")
    .addField("water_level", 2.064d)
    .build());

influxDB.write(Point.measurement("h2o_feet")
    .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
    .tag("location", "coyote_creek")
    .addField("level description", "between 6 and 9 feet")
    .addField("water_level", 8.12d)
    .build());

// Wait a few seconds in order to let the InfluxDB client
// write your points asynchronously (note: you can adjust the
// internal time interval if you need via 'enableBatch' call).
Thread.sleep(5_000L);
influxDB.enableLz4(); // enable query for lz4 compress
// Query your data using InfluxQL.
// https://docs.influxdata.com/influxdb/v1.7/query_language/data_exploration/#the-basic-select-statement
QueryResult queryResult = influxDB.query(new Query("SELECT * FROM h2o_feet"));

System.out.println(queryResult);
// It will print something like:
// QueryResult [results=[Result [series=[Series [name=h2o_feet, tags=null,
//      columns=[time, level description, location, water_level],
//      values=[
//         [2020-03-22T20:50:12.929Z, below 3 feet, santa_monica, 2.064],
//         [2020-03-22T20:50:12.929Z, between 6 and 9 feet, coyote_creek, 8.12]
//      ]]], error=null]], error=null]
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
* [FAQ](FAQ.md);
* [Changelog](CHANGELOG.md).


