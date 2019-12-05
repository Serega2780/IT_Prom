package org.test.hrdept;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Collections;

@SpringBootApplication
public class HrdeptApplication {

    public static void main(String[] args) {
      //  SpringApplication.run(HrdeptApplication.class, args);
        SpringApplication app = new SpringApplication(HrdeptApplication.class);
        app.setDefaultProperties(Collections.singletonMap("server.servlet.context-path", "/"));

        app.run(args);
    }

    private DataSource dataSource;

    @Resource(name = "dataSource")
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;

    }
}
