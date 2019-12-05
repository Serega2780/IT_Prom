package org.test.hrdept;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.test.hrdept.dao.DepartmentDao;
import org.test.hrdept.dao.impl.DepartmentDaoImpl;
import org.test.hrdept.domain.Department;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Collections;

@SpringBootApplication
public class HrdeptApplication {

    private DataSource dataSource;



    public static void main(String[] args) {

        //  SpringApplication.run(HrdeptApplication.class, args);
        SpringApplication app = new SpringApplication(HrdeptApplication.class);
        app.setDefaultProperties(Collections.singletonMap("server.servlet.context-path", "/"));

        app.run(args);
    }



    @Resource(name = "dataSource")
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;

    }
}
