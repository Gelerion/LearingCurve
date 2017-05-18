package com.denis.learning.flyway;

import org.flywaydb.core.Flyway;

public class MigrationTest {
    public static void main(String[] args) {
        Flyway flyway = new Flyway();
        flyway.setDataSource("jdbc:mysql://localhost:3306/", "root", "1234");
        flyway.setSchemas("ads_emr_statistics");
        flyway.migrate();
    }
}
