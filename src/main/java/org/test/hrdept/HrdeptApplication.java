package org.test.hrdept;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;
import javax.sql.DataSource;


@SpringBootApplication
public class HrdeptApplication {

    private DataSource dataSource;


    public static void main(String[] args) {

        SpringApplication.run(HrdeptApplication.class, args);
    }

    @Resource(name = "dataSource")
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;

    }
}
