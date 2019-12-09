package org.test.hrdept;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.test.hrdept.domain.Department;
import org.test.hrdept.domain.Employee;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DepartmentRestControllerTests {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @Order(1)
    void getdepartmentById_test() {
        // act
        Department department = restTemplate.getForObject("http://localhost:" + port + "/rest/dept/edit/?id=1",
                Department.class);
        // assertS
        assertThat(department)
                .extracting(Department::getId, Department::getName, Department::getDescription)
                .containsExactly(1, "IT", "IT dept.");
    }

    @Test
    @Order(2)
    void getDepartments_test() {
        ResponseEntity<List<Department>> response =
                restTemplate
                        .exchange(
                                "http://localhost:" + port + "/rest/dept/depts",
                                HttpMethod.GET,
                                null,
                                new ParameterizedTypeReference<List<Department>>() {
                                });

        assertEquals(response.getStatusCode(), HttpStatus.OK);

        List<Department> departments = response.getBody();
        assertThat(departments.get(2))
                .extracting(Department::getId, Department::getName, Department::getDescription)
                .containsExactly(3, "Technical", "Technical dept.");
    }

    @Test
    @Order(3)
    void insertDepartment_test() {
        final String dept = "newName";

        Department department = new Department(dept, "newDescription");

        ResponseEntity<Department> response =
                restTemplate.postForEntity("http://localhost:" + port + "/rest/dept/insert", department, Department.class);

        assertEquals(response.getStatusCode(), HttpStatus.OK);

        department = restTemplate.getForObject("http://localhost:" + port + "/rest/dept/edit/?id=4",
                Department.class);

        assertNotNull(department);
        assertEquals(department.getName(), dept);

    }

    @Test
    @Order(4)
    void updateDepartment_test() {
        final int deptId = 1;
        final String newdept = "newNewName";

        Department department = restTemplate.getForObject("http://localhost:" + port + "/rest/dept/edit/?id=" + deptId,
                Department.class);
        department.setName(newdept);

        restTemplate.put("http://localhost:" + port + "/rest/dept/update", department);

        department = restTemplate.getForObject("http://localhost:" + port + "/rest/dept/edit/?id=" + deptId,
                Department.class);

        assertNotNull(department);
        assertEquals(department.getName(), newdept);

    }

    @Test
    @Order(5)
    void deleteDepartment_test() {
        final int deptId = 1;

        restTemplate.delete("http://localhost:" + port + "/rest/dept/delete?id=" + deptId);
        Department department = restTemplate.getForObject("http://localhost:" + port + "/rest/dept/edit/?id=" + deptId,
                Department.class);
        assertNull(department);
    }

}
