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
import org.test.hrdept.domain.Employee;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployeeRestControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @Order(1)
    void getEmployeeById_test() {
        // act
        Employee employee = restTemplate.getForObject("http://localhost:" + port + "/rest/emp/edit/?id=1", Employee.class);
        // assert
        assertThat(employee)
                .extracting(Employee::getId, Employee::getName, Employee::getDescription)
                .containsExactly(1, "ivanov", "sys.admin.");
    }

    @Test
    @Order(2)
    void getEmployees_test() {
        ResponseEntity<List<Employee>> response =
                restTemplate
                        .exchange(
                                "http://localhost:" + port + "/rest/emp/emps",
                                HttpMethod.GET,
                                null,
                                new ParameterizedTypeReference<List<Employee>>() {
                                });

        assertEquals(response.getStatusCode(), HttpStatus.OK);

        List<Employee> employees = response.getBody();
        assertThat(employees.get(2))
                .extracting(Employee::getId, Employee::getName, Employee::getDescription)
                .containsExactly(3, "sidorov", "chief engineer");
    }

    @Test
    @Order(3)
    void insertEmployee_test() {
        final String emp = "newName";

        Employee employee = new Employee(emp, "newDescription");

        ResponseEntity<Employee> response =
                restTemplate.postForEntity("http://localhost:" + port + "/rest/emp/insert", employee, Employee.class);

        assertEquals(response.getStatusCode(), HttpStatus.OK);

        employee = restTemplate.getForObject("http://localhost:" + port + "/rest/emp/edit/?id=4",
                Employee.class);

        assertNotNull(employee);
        assertEquals(employee.getName(), emp);

    }

    @Test
    @Order(4)
    void updateEmployee_test() {
        final int empId = 1;
        final String newEmp = "newNewName";

        Employee employee = restTemplate.getForObject("http://localhost:" + port + "/rest/emp/edit/?id=" + empId,
                Employee.class);
        employee.setName(newEmp);

        restTemplate.put("http://localhost:" + port + "/rest/emp/update", employee);

        employee = restTemplate.getForObject("http://localhost:" + port + "/rest/emp/edit/?id=" + empId,
                Employee.class);

        assertNotNull(employee);
        assertEquals(employee.getName(), newEmp);

    }

    @Test
    @Order(5)
    void deleteEmployee_test() {
        final int empId = 1;

        restTemplate.delete("http://localhost:" + port + "/rest/emp/delete?id=" + empId);
        Employee employee = restTemplate.getForObject("http://localhost:" + port + "/rest/emp/edit/?id=" + empId,
                Employee.class);
        assertNull(employee);
    }

}
