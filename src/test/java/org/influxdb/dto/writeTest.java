package org.influxdb.dto;

import org.influxdb.BatchOptions;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.junit.jupiter.api.Test;

public class writeTest {

    @Test
    public void testEquals() throws Exception {
        final String serverURL = "http://127.0.0.1:63343", username = "root", password = "root";
        InfluxDB influxDB = InfluxDBFactory.connect(serverURL, username, password);


        // Create a database...
        String databaseName = "test_dataBase";
        influxDB.setDatabase(databaseName);

        // ... and a retention policy, if necessary.
        String retentionPolicyName = "one_day_only";
        influxDB.setRetentionPolicy(retentionPolicyName);

        // Enable batch writes to get better performance.
        influxDB.enableBatch(
                BatchOptions.DEFAULTS
                        .threadFactory(runnable ->
                        {
                            Thread thread = new Thread(runnable);
                            thread.setDaemon(true);
                            return thread;
                        })
        );

        // Close it if your application is terminating or you are not using it anymore.
        Runtime.getRuntime().addShutdownHook(new Thread(influxDB::close));

        // Write points to InfluxDB.
        influxDB.write(Point.measurement("h2o_feet")

                .tag("location", "santa_monica")
                .addField("name", "libai")
                .addField("age", 18)
                .build());

        for (int i = 0; i < 100; i++) {
            influxDB.write(Point.measurement("h2o_feet")

                    .tag("location", "coyote_creek")
                    .addField("name", "wangwei")
                    .addField("age", 20)
                    .build());
        }

        influxDB.enableLz4();
        influxDB.disableLz4();
        influxDB.isLz4Enabled();
        QueryResult queryResult = influxDB.query(new Query("SELECT * FROM h2o_feet"));

        System.out.println(queryResult);


    }
}
